package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Position;

public class SuperLaser extends UCMWeapon {
    public SuperLaser(Game game, Position pos, int life) {
        super(game, pos, life);
    }

    @Override
    protected String getSymbol() {
        return "ǁǁ";
    }

    @Override
    protected int getDamage() {
        return 1;
    }

    @Override
    protected int getArmour() {
        return 0;
    }


}
