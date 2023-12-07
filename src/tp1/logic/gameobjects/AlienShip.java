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

    public boolean onBorder() {
        return this.getPos().col == 0 || this.getPos().col == Game.DIM_X - 1;
    }
    @Override
    public void automaticMove() {
        int cycle = game.getCycle();
        int numCyclesToMoveOneCell = game.getLevel().getNumCyclesToMoveOneCell();

        if (cycle % numCyclesToMoveOneCell == 0) {
            performMovement();
        }

    }

    public void descend(){
        this.pos = this.pos.move(Move.DOWN);
        game.setShouldDescend(false);
    }

    public void performMovement(){
        if (onBorder()){
            game.setOnBorder(true);
            game.setShouldDescend(true);
        }
        if (game.isOnBorder()){
            descend();
            switchDirection();
            move();
        }
        else {
            move();
        }
    }

    public void move() {
        if (dir == Move.LEFT) {
            pos = pos.move(Move.LEFT);
        } else if (dir == Move.RIGHT) {
            pos = pos.move(Move.RIGHT);
        }
    }

    private void switchDirection() {
        dir = (dir == Move.LEFT) ? Move.RIGHT : Move.LEFT;
        game.setOnBorder(false);
    }

    @Override
    public String toString() {
        return " " + this.getSymbol() + "[" + "0" + this.getLife() + "]";
    }

    @Override
    public boolean hasLanded() {
        return this.getPos().row == 8;
    }

    @Override
    public String getMessage() {
        return getSymbol();
    }

    @Override
    public boolean addCounter(){
        return true;
    }
}

