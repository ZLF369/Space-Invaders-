package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.util.MyStringUtils;
import tp1.view.Messages;

/**
 * Class of the UCMShip that contains all the attributes and methods.
 */
public class UCMShip extends Ship{
    /**
     * Constructor for the UCMShip.
     */
    private UCMLaser laser;

    private boolean hasShockWave;

    public UCMShip(Game game, Position position, UCMLaser laser) {
        super(game, position, 3);
        this.dir = Move.NONE;
        this.laser = laser;
        hasShockWave = true; //false in real case
    }

    @Override
    protected String getSymbol() {
        return Messages.UCMSHIP_SYMBOL;
    }

    @Override
    protected int getDamage() {
        return 0;
    }

    @Override
    protected int getArmour() {
        return 0;
    }

/*    @Override
    public void onDelete() {

    }

    @Override
    public void automaticMove() {
    }*/

    public boolean move(Move move) {
        if (onBorderLeft() && (move.equals(Move.LEFT) || move.equals(Move.LLEFT))) {
            return false;
        } else if (onBorderRight() && (move.equals(Move.RIGHT) || move.equals(Move.RRIGHT))) {
            return false;
        } else {
            super.pos = pos.move(move);
            return true;
        }
    }


    public boolean onBorderLeft(){
        return super.pos.col == 0;
    }
    public boolean onBorderRight(){
        return super.pos.col == Game.DIM_X - 1;
    }

    public boolean shootLaser(){
        if (laser == null) {
            laser = new UCMLaser(game, pos.move(Move.UP), 1);
            return true;
        }
        return false;
    }

    @Override
    public boolean receiveAttack(EnemyWeapon weapon) {
        if(!this.pos.equals(weapon.pos)) return false;
        this.dealDamage(weapon);
        return true;
    }

    public GameObject getLaser() {
        return laser;
    }

    public boolean hasShockWave() {
    	return hasShockWave;
    }

    public void setShockWave(boolean hasShockWave){ this.hasShockWave = hasShockWave; }

}
