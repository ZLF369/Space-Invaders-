package tp1.logic.gameobjects;

import tp1.logic.AlienManager;
import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;

public abstract class AlienShip extends EnemyShip {
    private Move dir;

    public AlienShip(Game game, Position pos, int life) {
        super(game, pos, life);
        dir = Move.LEFT;
    }

    public AlienShip() {
        super();
    }

    protected abstract AlienShip copy(Game game, Position pos, AlienManager am);

    public void checkOnBorder() { //check if any alien is on the border
        if (this.isAlive()
                && (this.getPos().row + 1 == Game.DIM_Y || this.getPos().row == 0
                || this.getPos().col + 1 == Game.DIM_X || this.getPos().col == 0)) {
            game.onBorder = true;
        }

    } //maybe change onBorder or boolean variables so that it has access to game or alienmanager.


    public boolean onBorder() { //check if any alien is on the border
        return this.getPos().col == 0 || this.getPos().col == 8 || this.getPos().row == 8;
    }
@Override
    public void automaticMove() {
        int cycle = game.getCycle();
        int numCyclesToMoveOneCell = game.getLevel().getNumCyclesToMoveOneCell();

        if (cycle % numCyclesToMoveOneCell == 0) {

            checkOnBorder(); //checkeverythign is inside the border

            if (game.shouldDescend) {
                this.setPos(this.getPos().move(Move.DOWN));
                game.shouldDescend = false; // flag it so that it doesnt keep moving down
            } else {
                if (this.isAlive()) {
                    // Move in the current direction
                    this.setPos(this.getPos().move(dir));
                }
            }

            // switch dir
            if (onBorder()) {
                game.shouldDescend = true;
                if (dir == Move.LEFT) {
                    dir = Move.RIGHT; // Move to the right after descending
                } else {
                    dir = Move.LEFT;
                }

            }
            game.onBorder = false;
        }

    }


    @Override
    public String toString() {
        return " " + this.getSymbol() + "[" + "0" + this.getLife() + "]";
    }


}
