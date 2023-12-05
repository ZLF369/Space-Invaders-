package tp1.logic.gameobjects;

import tp1.logic.AlienManager;
import tp1.logic.Game;
import tp1.logic.Position;
import tp1.view.Messages;

public class ExplosiveAlien extends RegularAlien {
    public ExplosiveAlien(Game game, Position pos, int life) {
        super(game, pos, life);
        this.points = 12;
    }

    @Override
    protected String getSymbol() {
        return Messages.EXPLOSIVE_ALIEN_SYMBOL;
    }

    public boolean isDead() {
        return this.getLife() == 0;
    }

    @Override
    public int getPoints() {
        return this.points;
    }
    @Override
    public void setPoints(int points) {
        this.points = points;
    }
}
