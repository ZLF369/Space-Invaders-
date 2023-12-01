package tp1.logic.gameobjects;

import tp1.logic.AlienManager;
import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

/**
 * Class containing all the attributes and methods of the destroyer aliens. <br>
 * Also, this class extends the abstract class of Alien.
 */
public class DestroyerAlien extends AlienShip
{
    private Bomb bomb;
    private boolean cantShootBomb;

    public DestroyerAlien(Game game, Position pos, int life) {
        super(game, pos, life);
        this.bomb = null;
        cantShootBomb = false;
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

    public void shootBomb(){
        if (bomb != null) {
            return;
        }
        setCantShootBomb(true);
        bomb = new Bomb(game, pos, 1); //CREATION OF BOMB IS CORRECT, BUT NEED TO ADD IT TO GAME WITHOUT MAKING
        //NULLPTR EXCEPTIONS
    }

    public void moveBomb(){
        if (bomb != null && bomb.isAlive()){
            bomb.computerAction();
        }
        else if (bomb != null && (!bomb.isAlive() || !bomb.isValidPosition(bomb.getPos()))){
            setCantShootBomb(false);
        }
    }

    public Bomb getBomb() {
        return bomb;
    }

    public void setBomb(Bomb bomb) {
        this.bomb = bomb;
    }

    public boolean isCantShootBomb() {
        return cantShootBomb;
    }

    public void setCantShootBomb(boolean cantShootBomb) {
        this.cantShootBomb = cantShootBomb;
    }
}
