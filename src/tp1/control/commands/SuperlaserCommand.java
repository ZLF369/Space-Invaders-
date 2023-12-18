package tp1.control.commands;

import tp1.control.ExecutionResult;
import tp1.logic.GameModel;
import tp1.view.Messages;

public class SuperlaserCommand extends NoParamsCommand{

    @Override
    public ExecutionResult execute(GameModel game) {
        boolean shot;
        shot = game.shootSuperLaser();
        return new ExecutionResult(shot, true, Messages.SUPERLASER_ERROR);
    }
    @Override
    protected String getName() {
        return Messages.COMMAND_SUPERLASER_NAME;
    }

    @Override
    protected String getShortcut() {
        return Messages.COMMAND_SUPERLASER_SHORTCUT;
    }

    @Override
    protected String getDetails() {
        return Messages.COMMAND_SUPERLASER_DETAILS;
    }

    @Override
    protected String getHelp() {
        return Messages.COMMAND_SUPERLASER_HELP;
    }

    @Override
    public Command parse(String[] commandWords) throws CommandParseException {
        return super.parse(commandWords);
    }

    @Override
    protected boolean matchCommandName(String name) {
        return super.matchCommandName(name);
    }
}
