package tp1.logic;

import tp1.logic.gameobjects.DestroyerAlien;
import tp1.logic.gameobjects.EnemyShip;
import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.UCMShip;
import tp1.util.MyStringUtils;
import tp1.view.Messages;

import java.util.Random;


public class Game implements GameStatus, GameModel, GameWorld {

	//TODO fill with your code

	public static final int DIM_X = 9;
	public static final int DIM_Y = 8;
	
	private GameObjectContainer container;
	private UCMShip player;
	private final AlienManager alienManager;
	private int currentCycle;
	private long seed;
	private Random random;

	private Move move;

	//TODO fill with your code

	public Level getLevel() {
		return level;
	}

	private final Level level;

	public Game (Level level, long seed){

		this.level = level;
		this.random = new Random(seed);
		this.alienManager = new AlienManager(this);
		initGame();
		this.currentCycle = 0;
	}

	public int getCurrentCycle() {
		return currentCycle;
	}

	public void setCurrentCycle(int currentCycle) {
		this.currentCycle = currentCycle;
	}

	private void initGame () {
		this.container = alienManager.initialize();
		this.player = new UCMShip(this, new Position(DIM_X / 2, DIM_Y - 1), null);
		addObject(player);
	}

	//CONTROL METHODS
	
	public boolean isFinished() {
		// TODO fill with your code
		return (playerWin() || aliensWin());
	}

	public void exit() {
		System.exit(0);
		// TODO fill with your code
	}

	public void update() {
		//check priorities of actions
		setCurrentCycle(getCycle() + 1);
	    this.container.computerActions();
		alienManager.moveAlienList();
		alienManager.CheckHostileShot(player);
		getRemainingAliens();
	    /*this.container.automaticMoves();*/
	}

	// TODO fill with your code

	//CALLBACK METHODS
	
	public void addObject(GameObject object) {
	   	 this.container.add(object);
	}

	public GameObjectContainer getContainer() {
		return container;
	}
	// TODO fill with your code
	
	//VIEW METHODS
	
	public String positionToString(int col, int row) {
		Position position = new Position(col, row);
		for (GameObject objects: this.container.getObjects()) {
			if(objects.isOnPosition(position) && objects.getLife() > 0) {
				return objects.toString();
			}
		}
		return "";
	}

	@Override
	public String infoToString() {
		// TODO fill with your code
		return null;
	}

	@Override
	public String stateToString() { //to display state of game
		return "Life: " + player.getLife() + "\n" +
				"Points: " + player.getPoints()
				+ "\n" +
				"shockWave: " + (player.hasShockWave() ? "ON" : "OFF")
				+ "\n";
	}

	@Override
	public boolean playerWin() {
        return getRemainingAliens() == 0;
    }

	@Override
	public boolean aliensWin() {
		return (!player.isAlive() || alienManager.landed());
	}

	@Override
	public int getCycle() {
		return currentCycle;
	}

	@Override
	public int getRemainingAliens() {
		int i=0;
		for (GameObject objects: this.container.getObjects()) {
			if (objects instanceof EnemyShip && objects.getLife() > 0) {
				i++;
			}
		}
		return i;
	}

	@Override
	public boolean move(Move move) {
		return this.player.move(move);
	}

	public UCMShip getPlayer() {
		return player;
	}


	@Override
	public boolean shootLaser() {
		boolean shot = this.player.shootLaser();
		container.add(player.getLaser());
		return shot;
	}

	@Override
	public boolean shockWave() {
		if(player.hasShockWave()) {
			for (GameObject objects: this.container.getObjects()) {
				if(objects instanceof EnemyShip) {
					objects.setLife(objects.getLife() - 1);
				}
			}
			player.setShockWave(false);
			return true;
		}
		return false;
	}

	@Override
	public void reset() {
		this.container = alienManager.initialize();
		this.player = new UCMShip(this, new Position(DIM_X / 2, DIM_Y - 1), null);
		this.container.add(player);
		this.currentCycle = 0;
	}

	public Random getRandom() {
		return random;
	}


}
