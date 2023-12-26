package tp1.logic;

import tp1.control.InitialConfiguration;
import tp1.exceptions.*;
import tp1.logic.gameobjects.*;
import tp1.view.Messages;

import java.util.Random;


public class Game implements GameStatus, GameModel, GameWorld {

	public static final int DIM_X = 9;
	public static final int DIM_Y = 8;
	private GameObjectContainer container;
	private UCMShip player;
	private final AlienManager alienManager;
	private int currentCycle;
	private final long seed;
	private Random random;

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
		return (playerWin() || aliensWin());
	}

	public void exit() {
		System.exit(0);
	}

	public void update() {
		// update the cycle
		setCurrentCycle(getCycle() + 1);
		// move all objects ingame
		this.container.automaticMoves();
		// check collisions and handle damage done to aliens and player
		container.checkCollision();
		container.checkExplosion();

		// Handle other actions
		container.givePoints(player);
		getRemainingAliens();
		alienManager.checkUfo();
		this.container.computerActions();
		alienManager.moveAlienList();
		this.container.deleteDeadObjects();
	}


	//CALLBACK METHODS
	@Override
	public void addObject(GameObject object) {
		this.container.add(object);
	}

	@Override
	public void removeObject(GameObject object) {
		this.container.remove(object);
	}

	@Override
	public GameObjectContainer getContainer() {
		return container;
	}

	//VIEW METHODS

	public String positionToString(int col, int row) { //if there is an alive object on the position, return the object
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
	public int getRemainingAliens() { //count the remaining aliens
		int i=0;
		for (GameObject objects: this.container.getObjects()) {
			if (objects != null && objects.addCounter() && objects.isAlive()) {
				i++;
			}
		}
		return i;
	}
	@Override
	public boolean move(Move move) {
		try {
			if (player.move(move)) {
				return true;
			} else {
				String message = String.format(Messages.OFF_WORLD_MESSAGE, move, player.getPos());
				throw new OffWorldException(message);
			}
		} catch (OffWorldException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public UCMShip getPlayer() {
		return player;
	}

	//methods involving using player shooting actions
	@Override
	public boolean shootLaser() {
		try {
		  if (player.shootLaser())
			  return true;
		  else {
		  	String message = String.format(Messages.LASER_ERROR + "," + Messages.LASER_ALREADY_SHOT);
		  	throw new LaserInFlightException(message);
		  }
		} catch (LaserInFlightException e) {
            System.err.println(e.getMessage());
			return false;
        }
	}

	@Override
	public boolean shootSuperLaser() {
		try {
			if (player.getPoints() >= 5) {
				player.setPoints(player.getPoints() - 5);

				try {
					if (player.shootSuperLaser())
						return true;
					else
						throw new LaserInFlightException(Messages.LASER_ERROR + "," + Messages.LASER_ALREADY_SHOT);
				} catch (LaserInFlightException e) {
					String message = String.format(Messages.SUPERLASER_ERROR + "," + "Superlaser already shot");
					System.err.println(message);
					return false;
				}

			} else {
				String message = String.format("Not enough points: only %s points, %s points required", player.getPoints(), 5);
				throw new NotEnoughtPointsException(message);
			}
		} catch (NotEnoughtPointsException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}


	@Override
	public boolean shockWave() throws NoShockWaveException { //apply the shockwave damage
		try{
			if (player.hasShockWave()){
				player.useShockwave();
				return true;
			}
			else {
				String message = String.format(Messages.SHOCKWAVE_ERROR);
				throw new NoShockWaveException(message);
			}
		}
		catch (NoShockWaveException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	@Override
	public void reset() { //normal resets
		emptyContainer();
		this.container = alienManager.initialize(null);
		this.random = new Random(this.seed);
		this.player = new UCMShip(this, new Position(DIM_X / 2, DIM_Y - 1), null);
		this.container.add(player);
		this.currentCycle = 0;
	}

	@Override
	public void reset(InitialConfiguration initialConfiguration) throws InitializationException, CommandExecuteException{ //the special reset that takes initialConfigurations
		emptyContainer();
		this.container = alienManager.initialize(initialConfiguration);
		this.random = new Random(this.seed);
		this.player = new UCMShip(this, new Position(DIM_X / 2, DIM_Y - 1), null);
		this.container.add(player);
		this.currentCycle = 0;
	}

	//create a new container, used in resets
	public void emptyContainer(){
		this.container = new GameObjectContainer();
	}

	public Random getRandom() {
		return random;
	}


	//methods to involve the rng (randomness)
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