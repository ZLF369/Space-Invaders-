package tp1.logic.gameobjects;

import tp1.logic.AlienManager;
import tp1.logic.Game;
import tp1.logic.Position;
import tp1.view.Messages;

public class ExplosiveAlien extends RegularAlien {
    public ExplosiveAlien(Game game, Position pos, AlienManager am) {
        super(game, pos, am);
        this.points = 12;
    }

    public ExplosiveAlien() {
    }

    @Override
    protected AlienShip copy(Game game, Position pos, AlienManager am) {
        return new ExplosiveAlien(game, pos, am);
    }

    @Override
    protected String getSymbol() {
        return Messages.EXPLOSIVE_ALIEN_SYMBOL;
    }

    public boolean isDead() {
        return this.getLife() == 0;
    }

}
