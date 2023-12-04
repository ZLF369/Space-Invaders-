package tp1.logic;

import tp1.logic.gameobjects.*;

import java.util.ArrayList;
import java.util.List;

public class AlienManager {

    private Game game;
    private int remainingAliens;
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

    public GameObjectContainer initialize() {
        this.remainingAliens = 0;
        GameObjectContainer container = new GameObjectContainer();

        initializeUFO(container);
        initializeRegularAliens(container);
        initializeDestroyerAliens(container);

        //TODO fill with your code


        return container;
    }

    private void initializeUFO(GameObjectContainer container) {
        // container.add(new Ufo(game));
    }

    private void initializeRegularAliens(GameObjectContainer container) {

        Level level = this.game.getLevel();

        for (int row = 0, index = 0; row < level.numRowsRegularAliens; row++) {
            for (int col = 0; col < level.getNumAliensPerRow(); col++, index++) {
                int reqCenter = (Game.DIM_X / 2) - (level.getNumAliensPerRow() / 2);

                container.add(new RegularAlien(
                        this.game,
                        new Position(col + reqCenter, row + 1),
                        2
                ));
            }
        }
    }

    private void initializeDestroyerAliens(GameObjectContainer container) {
        Level level = this.game.getLevel();

        for (int i = 0; i < level.numDestroyerAliens; i++) {

            int reqCenter = (Game.DIM_X / 2) - (level.getNumAliensPerRow() / 2);
            int offset = reqCenter + (level.getNumAliensPerRow() / level.numDestroyerAliens) - 1;

            container.add(new DestroyerAlien(
                    this.game,
                    new Position(i + offset, level.numRowsRegularAliens + 1),
                    1
            ));
        }
    }

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



//    }

    public void moveAllDown() {
        for (GameObject gameObject : game.getContainer().getObjects()) {
            if (gameObject instanceof AlienShip) {
                if (gameObject.isAlive()) {
                    gameObject.setPos(gameObject.getPos().move(Move.DOWN));
                }
            }
        }
    }

    //ALIEN BOMB GO BOOM LOGIC

    public boolean shootChance(){
        return game.getRandom().nextDouble() < game.getLevel().getShootFrequency();
    }

    public void tryShooting(){
        List<DestroyerAlien> aliensToShoot = new ArrayList<>();
        for (GameObject gameObject : game.getContainer().getObjects()){
            if (gameObject instanceof DestroyerAlien){
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
    }


    public void checkUfo() {
        if (activeUfo == null && getUfoFrequency()) {
            Ufo ufo = new Ufo(game, new Position(8, 0), 1);
            game.getContainer().add(ufo);
            activeUfo = ufo;
        } else if (activeUfo != null && !activeUfo.isAlive()) {
            activeUfo = null;
        }
    }

    public boolean getUfoFrequency() {
        return game.getRandom().nextDouble() < game.getLevel().getUfoFrequency();
    }

    //COLLISSION LOGIC

    public void CheckHostileShot(UCMShip player) {
        for (GameObject gameObject : game.getContainer().getObjects()) {
            if (gameObject instanceof EnemyShip && player.getLaser() != null) {
                if (gameObject.isAlive() && player.getLaser().getPos().equals(gameObject.getPos())) {
                    gameObject.setLife(gameObject.getLife() - 1);
                    //need logic to enable shockwave to true after killing ufo and getting points
                    player.getLaser().setLife(0);
                }
            }
        }
    }


    public boolean landed() {
        for (GameObject gameObject : game.getContainer().getObjects()){
            if (gameObject instanceof AlienShip){
                if (gameObject.getPos().row == Game.DIM_Y){
                    return true;
                }
            }
        }
        return false;
    }







































}
