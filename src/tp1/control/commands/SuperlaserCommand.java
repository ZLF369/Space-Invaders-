package tp1.control.commands;

import tp1.control.ExecutionResult;
import tp1.logic.GameModel;
import tp1.view.Messages;

public class SuperlaserCommand extends NoParamsCommand{

    @Override
    protected String getName() {
        return Messages.COMMAND_SUPERLASER_NAME;
    }

    @Override
    protected String getShortcut() {
        return null;
    }

    @Override
    protected String getDetails() {
        return null;
    }

    @Override
    protected String getHelp() {
        return null;
    }

    @Override
    public ExecutionResult execute(GameModel game) {
        return null;
    }
}
