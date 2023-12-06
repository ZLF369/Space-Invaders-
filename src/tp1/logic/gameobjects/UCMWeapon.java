package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Position;

public abstract class UCMWeapon extends Weapon{
    public UCMWeapon(Game game, Position pos, int life) {
        super(game, pos, life);
    }

    @Override
    public boolean performAttack(GameItem other) {
        if (other.isOnPosition(this.pos)) {
            other.receiveAttack(this);
            return true;
        }
        return false;
    }

    public void receiveAttack() {
        this.life = 0;
    }
}
