package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Position;
import tp1.view.Messages;

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
		if (this.getArmour() < 0)
			return Messages.UCMSHIP_DEAD_SYMBOL;
		else
			return Messages.UCMSHIP_SYMBOL;
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
