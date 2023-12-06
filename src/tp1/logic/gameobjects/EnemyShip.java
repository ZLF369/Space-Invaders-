package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Position;

public abstract class EnemyShip extends Ship{
    int points;
    public EnemyShip(Game game, Position pos, int life) {
        super(game, pos, life);
    }

    @Override
    public int getPoints() {
        return this.points;
    }

    @Override
    public void setPoints(int points) {
        this.points = points;
    }

    public EnemyShip() {
        super();
    }

    @Override
    public boolean receiveAttack(UCMWeapon weapon) {
        if(!this.pos.equals(weapon.pos)) return false;

        this.dealDamage(weapon);
        weapon.setLife(0);
        return true;
    }

    public void delete(){
            this.game.getContainer().remove(this);
    }

}
