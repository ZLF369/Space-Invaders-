package tp1.logic;

import tp1.control.InitialConfiguration;
import tp1.logic.gameobjects.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlienManager {

    private Game game;
    private int remainingAliens;
    //    private Move dir;
//    private boolean onBorder;
//    private boolean shouldDescend;
    private Ufo activeUfo;
    private boolean ufoOnScreen;


    public AlienManager(Game game) {
        this.game = game;
//        dir = Move.LEFT;
        ufoOnScreen = false;
    }

    public GameObjectContainer initialize(InitialConfiguration initialConfiguration) {
        this.remainingAliens = 0;
        GameObjectContainer container = new GameObjectContainer();

        initializeUFO(container);
//        initializeRegularAliens(container);
//        initializeDestroyerAliens(container);
        initializeRegularAliens(container, initialConfiguration);
        initializeDestroyerAliens(container, initialConfiguration);

        /*ExplosiveAlien test = new ExplosiveAlien(game, new Position(6, 5), this);
        container.add(test);*/
        return container;
    }

    private void initializeUFO(GameObjectContainer container) {
        // container.add(new Ufo(game));
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
        } else {
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
        } else {
            initializeFromConfiguration(container, initialConfiguration);
        }
    }

    private void initializeFromConfiguration(GameObjectContainer container, InitialConfiguration initialConfiguration) {
        for (String description : initialConfiguration.getShipDescription()) {
            String[] words = description.split(" ");
            container.add(ShipFactory.spawnAlienShip(words[0], game,
                    new Position(Integer.parseInt(words[1]), Integer.parseInt(words[2])), this));
        }
    }
/*
    public void checkOnBorder() { //check if any alien is on the border
        for (GameObject gameObject : game.getContainer().getObjects()) {
            if (gameObject instanceof AlienShip) {
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
            if (gameObject instanceof AlienShip) {
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
        boolean alreadyMoved =false;

        tryShooting();
        checkUfo();

        if (cycle % numCyclesToMoveOneCell == 0) {

            checkOnBorder(); //checkeverythign is inside the border

            if (shouldDescend) {
                moveAllDown();
                shouldDescend = false; // flag it so that it doesnt keep moving down
            } else {
                for (GameObject gameObject : game.getContainer().getObjects()) {
                    if (gameObject instanceof AlienShip && gameObject.isAlive()) {
                        // Move in the current direction
                        gameObject.setPos(gameObject.getPos().move(dir));
                    }
                }

                // switch dir
                if (onBorder()) {
                    shouldDescend = true;
                    if (dir == Move.LEFT) {
                        dir = Move.RIGHT; // Move to the right after descending
                    } else {
                        dir = Move.LEFT;
                    }

                }
                onBorder = false;
            }

            alreadyMoved = true;

        }
    }


    }

    public void moveAllDown() {
        for (GameObject gameObject : game.getContainer().getObjects()) {
            if (gameObject instanceof AlienShip) {
                if (gameObject.isAlive()) {
                    gameObject.setPos(gameObject.getPos().move(Move.DOWN));
                }
            }
        }
    }

    ALIEN BOMB GO BOOM LOGIC

    public boolean shootChance() {
        return game.getRandom().nextDouble() < game.getLevel().getShootFrequency();
    }

    public void tryShooting() {
        List<DestroyerAlien> aliensToShoot = new ArrayList<>();
        for (GameObject gameObject : game.getContainer().getObjects()) {
            if (gameObject instanceof DestroyerAlien) {
                DestroyerAlien alien = (DestroyerAlien) gameObject;
                if (shootChance()) {
                    aliensToShoot.add(alien);
                }
            }
        }
        for (DestroyerAlien alien : aliensToShoot) {
            alien.shootBomb();
            alien.setBomb(null);
        }
        aliensToShoot.clear();
    }*/


    public void checkUfo() {
        if ((activeUfo == null || !activeUfo.isAlive())) {
            createUfo();
        } else if (!activeUfo.isValidPosition(activeUfo.getPos()) && game.getUfoFrequency()) {
            game.deleteObject(activeUfo);
            createUfo();
        }
    }

    private void createUfo() {
        if (game.getUfoFrequency()){
        Ufo ufo = new Ufo(game, new Position(8, 0), 1);
        game.getContainer().add(ufo);
        activeUfo = ufo;
        }
    }



    //COLLISSION LOGIC

   /*public void CheckCollissions(UCMShip player) {
        for (GameObject gameObject : game.getContainer().getObjects()) {
            if ((gameObject instanceof EnemyShip || gameObject instanceof EnemyWeapon) && player.getLaser() != null) {
                if (gameObject.isAlive() && player.getLaser().getPos().equals(gameObject.getPos())) {
                    gameObject.setLife(gameObject.getLife() - 1); //equivalent of receiving damage i guess?
                    //need logic to enable shockwave to true after killing ufo and getting points
                    player.getLaser().setLife(0);
                }

            } else if ((gameObject instanceof EnemyShip || gameObject instanceof EnemyWeapon) && player.getSuperLaser() != null) {
                if (gameObject.isAlive() && player.getSuperLaser().getPos().equals(gameObject.getPos())) {
                    gameObject.setLife(gameObject.getLife() - 2); //equivalent of receiving damage i guess?
                    //need logic to enable shockwave to true after killing ufo and getting points
                    player.getSuperLaser().setLife(player.getSuperLaser().getLife() - 1);
                }
            }
        }
    }*/

    public boolean landed(){
        for (GameObject objects: this.game.getContainer().getObjects()) {
            if (objects.hasLanded())
                return true;
        }
        return false;
    }



}