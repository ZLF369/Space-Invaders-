package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Position;

public abstract class EnemyWeapon extends Weapon{

    public EnemyWeapon(Game game, Position pos, int life) {
        super(game, pos, life);
    }

    @Override
    public boolean performAttack(GameItem other){
        if (other.isOnPosition(this.pos)){
            other.receiveAttack(this);
            return true;
        }
        return false;
    }

    @Override //do similar logic to ucmlaser and superlaser
    public boolean receiveAttack(UCMWeapon weapon) {
        if (this.pos.equals(weapon.pos)) {
            game.deleteObject(this);
            return true;
        }
        return false;
    }
}
