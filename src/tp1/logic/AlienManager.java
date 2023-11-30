package tp1.logic;import tp1.logic.gameobjects.AlienShip;import tp1.logic.gameobjects.DestroyerAlien;import tp1.logic.gameobjects.RegularAlien;import tp1.logic.gameobjects.Ufo;import java.util.ArrayList;import java.util.List;public class AlienManager  {		private Game game;	private int remainingAliens;	private Move dir;	private boolean shouldDescend;	private List<AlienShip> aliens = new ArrayList<>();		public AlienManager(Game game) {		this.game = game;	}	public GameObjectContainer initialize() {		this.remainingAliens = 0;		GameObjectContainer container = new GameObjectContainer();				initializeUFO(container);		initializeRegularAliens(container);		initializeDestroyerAliens(container);				//TODO fill with your code						return container;	}		private void initializeUFO(GameObjectContainer container) {		// container.add(new Ufo(game));	}	private void initializeRegularAliens(GameObjectContainer container) {		Level level = this.game.getLevel();		for (int row = 0, index = 0; row < level.numRowsRegularAliens; row++) {			for (int col = 0; col < level.getNumAliensPerRow(); col++, index++) {				int reqCenter = (Game.DIM_X/2) - (level.getNumAliensPerRow()/2);				container.add(new RegularAlien(						this.game,						new Position(col + reqCenter , row + 1),						2				));				aliens.add(new RegularAlien(						this.game,						new Position(col + reqCenter , row + 1),						2));			}		}	}		private void initializeDestroyerAliens(GameObjectContainer container) {		Level level = this.game.getLevel();		for(int i = 0; i < level.numDestroyerAliens; i++) {			int reqCenter = (Game.DIM_X/2) - (level.getNumAliensPerRow()/2);			int offset = reqCenter + (level.getNumAliensPerRow() / level.numDestroyerAliens) - 1;			container.add(new DestroyerAlien(					this.game,					new Position(i + offset, level.numRowsRegularAliens + 1),					1			));			aliens.add(new DestroyerAlien(					this.game,					new Position(i + offset, level.numRowsRegularAliens + 1),					1			));		}	}	public void checkOnBorder() { //check if any alien is on the border		for (AlienShip alienShip : aliens) {			if (alienShip.isAlive()					&& (alienShip.getPos().row + 1 == Game.DIM_Y || alienShip.getPos().row == 0					|| alienShip.getPos().col + 1 == Game.DIM_X || alienShip.getPos().col == 0)) {				break; // No need to check once one alien reaches the border			}		}	}	public boolean onBorder() { //check if any alien is on the border		for (AlienShip alienShip : aliens) {			if (alienShip.getPos().col == 0 || alienShip.getPos().col == 8 || alienShip.getPos().row == 8)				return true;		}		return false;	}	public void moveAlienList() {		int cycle = game.getCycle();		int numCyclesToMoveOneCell = game.getLevel().getNumCyclesToMoveOneCell();		if (cycle % numCyclesToMoveOneCell == 0) {			checkOnBorder(); //make sure that the whole list is inside the border			if (shouldDescend) {				moveAllDown(); // move down in this  cycle				shouldDescend = false; // reset for  next cycle so that it doesnt keep moving down			} else {				// move all aliens to the left				if (dir == Move.LEFT) {					for (AlienShip alienShip : aliens) {						Position position = new Position(alienShip.getPos().col - 1,alienShip.getPos().row);						alienShip.setPos(position);//						alienShip.getPos().move(Move.LEFT);					}					// move aliens to the right				} else if (dir == Move.RIGHT) {					for (AlienShip alienShip : aliens) {						Position position = new Position(alienShip.getPos().col,alienShip.getPos().row+1);						alienShip.setPos(position); // move all the regular aliens to the left//						alienShip.getPos().move(Move.RIGHT);					}				}				// after moving, switch direction if needed				if (onBorder()) {					shouldDescend = true; // put so that they descend in the next cycle					if (dir == Move.LEFT) {						dir = Move.RIGHT; // move to  right or left after descending					} else {						dir = Move.LEFT;					}				}			}		}	}	public void moveAllDown() {		for (AlienShip alienShip : aliens) {			if (alienShip.isAlive()) {				alienShip.getPos().move(Move.DOWN);			}		}	}}