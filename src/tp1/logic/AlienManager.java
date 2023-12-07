package tp1.logic;

import tp1.control.InitialConfiguration;
import tp1.logic.gameobjects.*;
import tp1.view.Messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AlienManager {

    private Game game;
    private Move dir;
    private boolean onBorder;
    private boolean shouldDescend;
    private Ufo activeUfo;
    private boolean ufoOnScreen;


    public AlienManager(Game game) {
        this.game = game;
        dir = Move.LEFT;
        ufoOnScreen = false;
    }

    public GameObjectContainer initialize(InitialConfiguration initialConfiguration) {
        GameObjectContainer container = new GameObjectContainer();
        initializeRegularAliens(container, initialConfiguration);
        initializeDestroyerAliens(container, initialConfiguration);
        return container;
    }

    private void initializeRegularAliens(GameObjectContainer container, InitialConfiguration initialConfiguration) {

        Level level = this.game.getLevel();
        if (initialConfiguration == null || initialConfiguration == InitialConfiguration.NONE) {
            for (int row = 0, index = 0; row < level.numRowsRegularAliens; row++) {
                for (int col = 0; col < level.getNumAliensPerRow(); col++, index++) {
                    int reqCenter = (Game.DIM_X / 2) - (level.getNumAliensPerRow() / 2);

                    container.add(new RegularAlien(
                            this.game,
                            new Position(col + reqCenter, row + 1),
                            this
                    ));
                }
            }
        } else { // Case for initialconfigurations
            initializeFromConfiguration(container, initialConfiguration);
        }
    }

    private void initializeDestroyerAliens(GameObjectContainer container, InitialConfiguration initialConfiguration) {
        Level level = this.game.getLevel();

        if (initialConfiguration == null || initialConfiguration == InitialConfiguration.NONE) {
            for (int i = 0; i < level.numDestroyerAliens; i++) {
                int reqCenter = (Game.DIM_X / 2) - (level.getNumAliensPerRow() / 2);
                int offset = reqCenter + (level.getNumAliensPerRow() / level.numDestroyerAliens) - 1;

                container.add(new DestroyerAlien(
                        this.game,
                        new Position(i + offset, level.numRowsRegularAliens + 1),
                        this
                ));
            }
        } else { // Case for initialconfigurations
            initializeFromConfiguration(container, initialConfiguration);
        }
    }

    private void initializeFromConfiguration(GameObjectContainer container, InitialConfiguration initialConfiguration) {
        for (String description : initialConfiguration.getShipDescription()) {
            String[] words = description.split(" ");
            container.add(ShipFactory.spawnAlienShip(words[0], game,
                    new Position(Integer.parseInt(words[1]), Integer.parseInt(words[2])), this));
        }
    } //Add the ships from the configuration file, in their format (type, row, col)

    public void checkOnBorder() { //check if any alien is on the border
        for (GameObject gameObject : game.getContainer().getObjects()) {
            if ((Objects.equals(gameObject.getMessage(), Messages.REGULAR_ALIEN_SYMBOL)
                            || Objects.equals(gameObject.getMessage(), Messages.DESTROYER_ALIEN_SYMBOL)) ||
                            Objects.equals(gameObject.getMessage(), Messages.EXPLOSIVE_ALIEN_SYMBOL)
                                    && gameObject.isAlive()) {
                if (gameObject.isAlive()
                        && (gameObject.getPos().row + 1 == Game.DIM_Y || gameObject.getPos().row == 0
                        || gameObject.getPos().col + 1 == Game.DIM_X || gameObject.getPos().col == 0)) {
                    onBorder = true;
                    break; // No need to check once one alien reaches the border
                }
            }
        }
    }

    public boolean onBorder() { //check if any alien is on the border
        boolean onBorder = false;
        for (GameObject gameObject : game.getContainer().getObjects()) {
            if ((Objects.equals(gameObject.getMessage(), Messages.REGULAR_ALIEN_SYMBOL)
                            || Objects.equals(gameObject.getMessage(), Messages.DESTROYER_ALIEN_SYMBOL)) ||
                            Objects.equals(gameObject.getMessage(), Messages.EXPLOSIVE_ALIEN_SYMBOL)
                                    && gameObject.isAlive()) {
                if (gameObject.getPos().col == 0 || gameObject.getPos().col == 8 || gameObject.getPos().row == 8) {
                    onBorder = true;
                }
            }
        }
        return onBorder;
    }

    public void moveAlienList() {
        int cycle = game.getCycle();
        int numCyclesToMoveOneCell = game.getLevel().getNumCyclesToMoveOneCell();

        tryShooting();
        checkUfo();

        if (cycle % numCyclesToMoveOneCell == 0) {
            checkOnBorder(); //checkeverything is inside the border

            if (shouldDescend) {
                moveAllDown();
                shouldDescend = false; // flag it so that it doesnt keep moving down
            } else {
                for (GameObject gameObject : game.getContainer().getObjects()) {
                    if ((Objects.equals(gameObject.getMessage(), Messages.REGULAR_ALIEN_SYMBOL)
                            || Objects.equals(gameObject.getMessage(), Messages.DESTROYER_ALIEN_SYMBOL)) ||
                            Objects.equals(gameObject.getMessage(), Messages.EXPLOSIVE_ALIEN_SYMBOL)
                                    && gameObject.isAlive()) {
                        // Move in the current direction
                        gameObject.setPos(gameObject.getPos().move(dir));
                    }
                }

                // switch dir
                if (onBorder()) {
                    shouldDescend = true;
                    if (dir == Move.LEFT) {
                        dir = Move.RIGHT; // move to right after descending
                    } else {
                        dir = Move.LEFT;
                    }

                }
                onBorder = false;
            }
        }
    }

    public void moveAllDown() {
        for (GameObject gameObject : game.getContainer().getObjects()) {
            if ((Objects.equals(gameObject.getMessage(), Messages.REGULAR_ALIEN_SYMBOL)
                    || Objects.equals(gameObject.getMessage(), Messages.DESTROYER_ALIEN_SYMBOL)) ||
                    Objects.equals(gameObject.getMessage(), Messages.EXPLOSIVE_ALIEN_SYMBOL)) {
                if (gameObject.isAlive()) {
                    gameObject.setPos(gameObject.getPos().move(Move.DOWN));
                }
            }
        }
    }
   // ALIEN BOMB GO BOOM LOGIC

    public void tryShooting() {
        List<DestroyerAlien> aliensToShoot = new ArrayList<>();
        for (GameObject gameObject : game.getContainer().getObjects()) {
            if ((Objects.equals(gameObject.getMessage(), Messages.DESTROYER_ALIEN_SYMBOL))) {
                DestroyerAlien alien = (DestroyerAlien) gameObject;
                if (game.shootChance()) {
                    aliensToShoot.add(alien);
                }
            }
        }
        for (DestroyerAlien alien : aliensToShoot) {
                alien.shootBomb();
                // alien.setBomb(null);
            if (!alien.getBomb().isValidPosition(alien.getBomb().getPos()) && !alien.getBomb().isAlive()){
                alien.setBomb(null);
            }
        }
        aliensToShoot.clear();
    }

    public void checkUfo() { //method to check if ufo should be created or not.
        //if it exits the bounds, it should be deleted and a will be tried to be created.
        if ((activeUfo == null || !activeUfo.isAlive())) {
            createUfo();
        } else if (!activeUfo.isValidPosition(activeUfo.getPos()) && game.getUfoFrequency()) {
            game.deleteObject(activeUfo);
            createUfo();
        }
    }

    private void createUfo() { //if the probability allows it, create a new ufo.
        if (game.getUfoFrequency()){
        Ufo ufo = new Ufo(game, new Position(8, 0), 1);
        game.getContainer().add(ufo);
        activeUfo = ufo;
        }
    }

    public boolean landed(){ //if any alien has landed, true is returned.
        for (GameObject objects: this.game.getContainer().getObjects()) {
            if (objects.hasLanded())
                return true;
        }
        return false;
    }



}