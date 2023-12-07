package tp1.logic;

import tp1.control.InitialConfiguration;
import tp1.logic.gameobjects.*;
import tp1.view.Messages;

import java.util.Objects;
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
	private boolean onBorder, shouldDescend;
	private Move dir;

	//TODO fill with your code

	public Level getLevel() {
		return level;
	}

	private final Level level;

	public Game (Level level, long seed){
		this.level = level;
		this.seed = seed;
		this.random = new Random(this.seed);
		this.alienManager = new AlienManager(this);
		initGame();
		this.currentCycle = 0;
		this.onBorder = false;
		this.shouldDescend = false;
	}

	public AlienManager getAlienManager() {
		return alienManager;
	}

	public boolean isOnBorder() {
		return onBorder;
	}

	public void setOnBorder(boolean onBorder) {
		this.onBorder = onBorder;
	}

	public boolean isShouldDescend() {
		return shouldDescend;
	}

	public void setShouldDescend(boolean shouldDescend) {
		this.shouldDescend = shouldDescend;
	}

	public int getCurrentCycle() {
		return currentCycle;
	}

	public void setCurrentCycle(int currentCycle) {
		this.currentCycle = currentCycle;
	}

	private void initGame () {
		this.container = alienManager.initialize(null);
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
		alienManager.checkUfo();
		this.container.computerActions();
//		alienManager.moveAlienList();
		container.checkCollision();
		container.checkExplosion();
		container.givePoints(player);
		getRemainingAliens();
		this.container.automaticMoves();
		this.container.deleteDeadObjects();
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
			if(objects.isOnPosition(position) && objects.isAlive()) {
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
			if (objects.addCounter() && objects.isAlive()) {
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
		return this.player.shootLaser();
	}

	@Override
	public boolean shootSuperLaser(){return this.player.shootSuperLaser();}

	@Override
	public boolean shockWave() {
		player.useShockwave();
		return true;
	}

	@Override
	public void reset() {
		emptyContainer();
		this.container = alienManager.initialize(null);
		this.random = new Random(this.seed);
		this.player = new UCMShip(this, new Position(DIM_X / 2, DIM_Y - 1), null);
		this.container.add(player);
		this.currentCycle = 0;
	}

	@Override
	public void reset(InitialConfiguration initialConfiguration) {
		emptyContainer();
		this.container = alienManager.initialize(initialConfiguration);
		this.random = new Random(this.seed);
		this.player = new UCMShip(this, new Position(DIM_X / 2, DIM_Y - 1), null);
		this.container.add(player);
		this.currentCycle = 0;
	}

	public void emptyContainer(){
		this.container = new GameObjectContainer();
	}

	public Random getRandom() {
		return random;
	}

	public boolean shootChance() {
		return this.getRandom().nextDouble() < this.getLevel().getShootFrequency();
	}

	public boolean getUfoFrequency() {
		return this.getRandom().nextDouble() < this.getLevel().getUfoFrequency();
	}


	public void deleteObject(GameObject object) {
		this.container.remove(object);
	}
}