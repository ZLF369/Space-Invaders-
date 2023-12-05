package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;

public abstract class GameObject implements GameItem {
	protected Position pos;
	protected int life;
	protected Game game;
	protected Move dir;

	public int points;

	private boolean hasGivenPoints;
	public GameObject(Game game, Position pos, int life) {
		this.pos = pos;
		this.game = game;
		this.life = life;
		this.hasGivenPoints = false;
	}

	public int getPoints() {
		return 0;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public GameObject() {
    }

    public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}

	@Override
	public boolean isAlive() {
		return this.life > 0;
	}

	public int getLife() {
		return this.life;
	}

	//TODO fill with your code
	protected abstract String getSymbol();
	protected abstract int getDamage();
	protected abstract int getArmour();


	/*public abstract void onDelete();
	 *//*public abstract void automaticMove(){
		boolean nowOnBorder = false;
		GameObject[] AlienShip = get;
		for (GameObject o: AlienShip) {
			if(Game.isOnBorderX(a.getPosition())) {
				nowOnBorder = true;
				break;
			}
		}
		for (Alien a: aliens) {
			if(a instanceof DestroyerAlien da) {
				da.moveBomb();
				if(game.tryFiringChance()) da.enableBomb();
			}
		}

		if(nowOnBorder) {
			if(onBorder) {
				// already was on border -> wait for move then reset onBorder
				if(cyclesToMove == 0) onBorder = false;

			} else {
				// newly on border -> descend now
				for (Alien a: aliens) {
					a.changeDirection();
					if(Game.isInFinalRow(a.getPosition()))
						this.squadInFinalRow = true;
				}
				if(cyclesToMove == 0) cyclesToMove++;
				onBorder = true;
			}
		}

		if(cyclesToMove-- == 0) {
			for (Alien a: aliens) a.automaticMove();
			// reset cycle counter back to default
			cyclesToMove = (level.numCyclesToMoveOneCell-1);
		}
*//*



	}*/
	public void computerAction() {};

	//TODO fill with your code

	@Override
	public boolean performAttack(GameItem other) {return false;}

	@Override
	public boolean receiveAttack(EnemyWeapon weapon) {return false;}

	@Override
	public boolean receiveAttack(UCMWeapon weapon) {return false;}

	@Override
	public String toString() {
		return this.getSymbol();
	}

	public void setLife(int life) {
		this.life = life;
	}

	@Override
	public boolean isOnPosition(Position pos) {
		return this.pos.equals(pos);
	}


	public boolean hasGivenPoints() {
		return hasGivenPoints;
	}

	public void setHasGivenPoints(boolean b) {
		this.hasGivenPoints = b;
	}
}
