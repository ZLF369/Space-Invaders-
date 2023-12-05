package tp1.control.commands;

import tp1.control.ExecutionResult;
import tp1.logic.GameModel;

public class ResetParamCommand extends Command{
    @Override
    protected String getName() {
        return null;
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

    @Override
    public Command parse(String[] commandWords) {
        return switch(commandWords[0]) {

    }
}
