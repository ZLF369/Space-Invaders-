package tp1.logic;

import java.util.ArrayList;
import java.util.List;

import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.UCMShip;

public class GameObjectContainer {
	private List<GameObject> objects;
	private List<GameObject> deadObjects;

	public GameObjectContainer() {
		objects = new ArrayList<>();
		deadObjects = new ArrayList<>();
	}

	public void add(GameObject object) {
		objects.add(object);
	}

	public void remove(GameObject object) {
		deadObjects.add(object);
	}

	public void automaticMoves() {
		for (GameObject o: objects) {
			o.automaticMove();
		}
	}

	public void computerActions() {
		for (GameObject objects: objects) {
			objects.computerAction();
		}
	}

	public void givePoints(UCMShip player) {
		for (GameObject objects: objects) {
			if (!objects.isAlive() && !objects.hasGivenPoints()) {
				player.setPoints(player.getPoints() + objects.getPoints());
				objects.setHasGivenPoints(true);
			}
		}
	}

	public void deleteDeadObjects() {
		for (GameObject d: deadObjects) {
			this.objects.remove(d);
		}
		this.deadObjects.clear();
	}

	public void checkCollision(){ //checks all the collissions between objects (laser needs to be removeed still)
		for (int i=0; i < objects.size(); i++) {
			for (int j=0; j < objects.size(); j++) {
				if (i == j) continue;
				GameObject object1, object2;
				object1 = objects.get(i);
				object2 = objects.get(j);
				if (object1 == object2) continue;
				if (object1.isOnPosition(object2.getPos())) {
					object1.performAttack(object2);
					object1.giveShockwave();
				}
			}
		}
	}

	public void checkExplosion(){
		for (GameObject objects: objects) {
			objects.kamikaze();
		}
	}

	public List<GameObject> getObjects() {
		return objects;
	}
}
