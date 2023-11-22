package tp1.logic;

import java.util.ArrayList;
import java.util.List;

import tp1.logic.gameobjects.GameObject;

public class GameObjectContainer {

	private List<GameObject> objects;

	public GameObjectContainer() {
		objects = new ArrayList<>();
	}

	public void add(GameObject object) {
		objects.add(object);
	}

	public void remove(GameObject object) {
		objects.remove(object);
	}

	public void automaticMoves() {
        for (GameObject object : objects) {
            //TODO fill with your code
        }
		//TODO fill with your code
	}

	public void computerActions() {
		// TODO fill with your code
	}



    //TODO fill with your code
	
}
