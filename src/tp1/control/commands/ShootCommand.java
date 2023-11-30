package tp1.control.commands;

import tp1.control.ExecutionResult;
import tp1.logic.Game;
import tp1.logic.GameModel;
import tp1.logic.Move;
import tp1.logic.gameobjects.UCMLaser;
import tp1.view.Messages;

public class ShootCommand extends Command {
    private UCMLaser laser;

    public ShootCommand() {}
    protected ShootCommand(UCMLaser laser) {
        this.laser = laser;
    }
    @Override
    protected String getName() {
        return Messages.COMMAND_SHOOT_NAME;
    }

    @Override
    protected String getShortcut() {
        return Messages.COMMAND_SHOOT_SHORTCUT;
    }

    @Override
    protected String getDetails() {
        return Messages.COMMAND_SHOOT_DETAILS;
    }

    @Override
    protected String getHelp() {
        return Messages.COMMAND_SHOOT_HELP;
    }
    @Override
    public ExecutionResult execute(GameModel game) { //DEBUG
        return null;
    }

    @Override
    public Command parse(String[] commandWords) {
        // commandWords[2] == null
        // commandWords[0] == null;

        return new ShootCommand(laser);
    }

}