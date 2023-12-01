package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

/**
 * 
 * Class that represents the laser fired by {@link UCMShip}
 *
 */
public class UCMLaser extends UCMWeapon {
	public UCMLaser(Game game, Position pos, int life) {
		super(game, pos, life);
	}
	@Override
	protected String getSymbol() {
		return Messages.LASER_SYMBOL;
	}

	@Override
	protected int getDamage() {
		return 1;
	}

	@Override
	protected int getArmour() {
		return 0;
	}
	/*@Override
	public void onDelete() {

	}

	@Override
	public void automaticMove() {

	}*/
	public boolean isValidPosition(Position position) {
		return position.row >= 0 && position.row < game.DIM_Y;
	}



	@Override
	public void computerAction(){
		if (isValidPosition(getPos())){
			pos = pos.move(Move.UP);
		}
		else {
			life = 0;
		}
	}

}
