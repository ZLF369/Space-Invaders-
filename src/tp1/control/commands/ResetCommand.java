package tp1.control.commands;

import tp1.control.ExecutionResult;
import tp1.control.InitialConfiguration;
import tp1.logic.Game;
import tp1.logic.GameModel;
import tp1.view.Messages;

public class ResetCommand extends NoParamsCommand{
    private InitialConfiguration initialConfiguration;

    public ResetCommand(InitialConfiguration initialConfiguration) {
        this.initialConfiguration = initialConfiguration;
    }

    public ResetCommand() {
    }

    @Override
    public ExecutionResult execute(GameModel game) {
        if (initialConfiguration != null) {
            game.reset(initialConfiguration);
        } else {
            game.reset();
        }
        return new ExecutionResult(true);
    }
    @Override
    protected String getName() {
        return Messages.COMMAND_RESET_NAME;
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
        if (commandWords.length == 2){
            return switch(commandWords[0] + " " + commandWords[1]) {
                case "reset conf_1", "r conf_1" -> new ResetCommand(InitialConfiguration.CONF_1);
                case "reset conf_2", "r conf_2" -> new ResetCommand(InitialConfiguration.CONF_2);
                case "reset conf_3", "r conf_3" -> new ResetCommand(InitialConfiguration.CONF_3);
                default -> null;
            };
        }
        return super.parse(commandWords);
    }

    @Override
    protected String getShortcut() {
        return Messages.COMMAND_RESET_SHORTCUT;
    }

    @Override
    protected String getDetails() {
        return Messages.COMMAND_RESET_DETAILS;
    }

    @Override
    protected String getHelp() {
        return Messages.COMMAND_RESET_HELP;
    }

}
