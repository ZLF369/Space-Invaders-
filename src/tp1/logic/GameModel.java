package tp1.logic;

import tp1.control.InitialConfiguration;
import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.InitializationException;
import tp1.exceptions.LaserInFlightException;
import tp1.exceptions.NotEnoughtPointsException;

public interface GameModel {
    boolean move(Move move);
    boolean shootLaser();
    public boolean shootSuperLaser() throws LaserInFlightException, NotEnoughtPointsException;
    boolean shockWave();
    void reset();
    void reset(InitialConfiguration initialConfiguration) throws InitializationException, CommandExecuteException;
    boolean isFinished();
    void exit();

    void update();
}
