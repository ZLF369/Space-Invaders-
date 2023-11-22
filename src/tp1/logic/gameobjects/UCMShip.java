package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Position;

public class UCMShip extends GameObject {

	//TODO fill with your code

	public UCMShip(Game game, Position position) {
		super(game, position, 3);
	}

	@Override
	public boolean isOnPosition(Position pos) {
		return false;
	}

	@Override
	protected String getSymbol() {
		return null;
	}

	@Override
	protected int getDamage() {
		return 0;
	}

	@Override
	public int getArmour() {
		return 0;
	}

	@Override
	public void onDelete() {

	}

	@Override
	public void automaticMove() {

	}

	//TODO fill with your code
}
