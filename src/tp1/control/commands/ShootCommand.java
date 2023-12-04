package tp1.control.commands;

import tp1.control.ExecutionResult;
import tp1.logic.Game;
import tp1.logic.GameModel;
import tp1.logic.Move;
import tp1.logic.gameobjects.UCMLaser;
import tp1.view.Messages;

public class ShootCommand extends NoParamsCommand{
    @Override
    public ExecutionResult execute(GameModel game) {
        boolean shoot;
        shoot = game.shootLaser();
        return new ExecutionResult(shoot,true, Messages.LASER_ERROR);
    }
    @Override
    protected String getName() {
        return Messages.COMMAND_SHOOT_NAME;
    }

    @Override
    protected boolean matchCommandName(String name) {
        return super.matchCommandName(name);
    }

    @Override
    public String helpText() {
        return super.helpText();
    }

    @Override
    public Command parse(String[] commandWords) {
        return super.parse(commandWords);
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

}