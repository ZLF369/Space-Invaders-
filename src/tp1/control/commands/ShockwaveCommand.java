package tp1.control.commands;

import tp1.exceptions.CommandParseException;
import tp1.logic.GameModel;
import tp1.view.Messages;

public class ShockwaveCommand extends NoParamsCommand{
//    @Override
//    public ExecutionResult execute(GameModel game) {
//        boolean z;
//        z = game.shockWave();
//        return new ExecutionResult(z,true, Messages.SHOCKWAVE_ERROR);
//    }

    @Override
    public boolean execute(GameModel game) {
        return game.shockWave();
    }
    @Override
    protected String getName() {
        return Messages.COMMAND_SHOCKWAVE_NAME;
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
    public Command parse(String[] commandWords) throws CommandParseException {
        return super.parse(commandWords);
    }

    @Override
    protected String getShortcut() {
        return Messages.COMMAND_SHOCKWAVE_SHORTCUT;
    }

    @Override
    protected String getDetails() {
        return Messages.COMMAND_SHOCKWAVE_DETAILS;
    }

    @Override
    protected String getHelp() {
        return Messages.COMMAND_SHOCKWAVE_HELP;
    }

}