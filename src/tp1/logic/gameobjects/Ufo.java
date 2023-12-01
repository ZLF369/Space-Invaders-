package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

/**
 * This class manages the UFO. <br>
 * Contains the attributes and the movement of the UFO.
 */

public class Ufo extends EnemyShip {
	public Ufo(Game game, Position pos, int life) {
		super(game, pos, life);
	}

	@Override
	protected String getSymbol() {
		return Messages.UFO_SYMBOL +"[" + life + "]";
	}

	@Override
	protected int getDamage() {
		return 1;
	}

	@Override
	protected int getArmour() {
		return this.life;
	}

/*	@Override
	public void onDelete() {

	}*/

	@Override
	public void computerAction() {
		if (this != null && isValidPosition(getPos())){
			pos = pos.move(Move.LEFT);
		}
		else {
			life = 0;
		}
	}

	private boolean isValidPosition(Position pos) {
		return pos.col >= 0 && pos.col < game.DIM_X;
	}

}
