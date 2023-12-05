package tp1.logic;

public interface GameModel {
    public boolean move(Move move);
    public boolean shootLaser();

    public boolean shootSuperLaser();
    public boolean shockWave();
    public void reset();
    public boolean isFinished();
    public void exit();

    void update();
}
