package tp1.logic;

import tp1.exceptions.LaserInFlightException;
import tp1.exceptions.NotEnoughtPointsException;
import tp1.control.InitialConfiguration;

public interface GameModel {
    boolean move(Move move);
    boolean shootLaser();
    public void shootSuperLaser() throws LaserInFlightException, NotEnoughtPointsException;
    boolean shockWave();
    void reset();
    void reset(InitialConfiguration initialConfiguration);
    boolean isFinished();
    void exit();

    void update();
}
