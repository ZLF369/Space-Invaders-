package tp1.logic.gameobjects;

import tp1.logic.AlienManager;
import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

import java.util.ArrayList;
import java.util.List;

/**
 * Class containing all the attributes and methods of the destroyer aliens. <br>
 * Also, this class extends the abstract class of Alien.
 */
public class DestroyerAlien extends AlienShip
{
    private Bomb bomb;
    private boolean hasBomb;
    public DestroyerAlien(Game game, Position pos, AlienManager alienManager) {
        super(game, pos, 1);
        this.bomb = null;
        this.points = 10;
    }

    @Override
    public void computerAction() {
        super.computerAction();
        /*if (this.bomb == null && game.shootChance()) {
            Position bombPosition = new Position(this.pos.col, this.pos.row + 1);
                this.bomb = new Bomb(this.game, bombPosition, 1, this);
                this.game.addObject(bomb);
        }*/
    }


    public DestroyerAlien() {
        super();
    }

    @Override
    protected AlienShip copy(Game game, Position pos, AlienManager am) {
        return new DestroyerAlien(game, pos, am);
    }

    @Override
    protected String getSymbol() {
        return Messages.DESTROYER_ALIEN_SYMBOL;
    }
    @Override
    protected int getDamage() {
        return 0;
    }
    @Override
    protected int getArmour() {
        return 0;
    }


    /*public void tryShooting() {
        if (bomb == null && shootChance()) {
                this.shootBomb();
        }
    }
    public void shootBomb() {
        if (bomb != null) {
            return;
        }
        setHasBomb(true);
        new Bomb(this.game, new Position(this.pos.col, this.pos.row + 1), 1);
    }
*/

    public Bomb getBomb() {
        return bomb;
    }

    public void setBomb(Bomb bomb) {
        this.bomb = bomb;
    }

    public boolean isHasBomb() {
        return hasBomb;
    }

    public void setHasBomb(boolean cantShootBomb) {
        this.hasBomb = cantShootBomb;
    }

    /*public void deleteBomb() {
        setBomb(null);
    }*/
}
