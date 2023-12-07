package tp1.control.commands;

import tp1.control.ExecutionResult;
import tp1.control.InitialConfiguration;
import tp1.logic.Game;
import tp1.logic.GameModel;
import tp1.logic.Move;
import tp1.view.Messages;

public class ResetCommand extends Command{
    private InitialConfiguration initialConfiguration;
    private Game game;

    public ResetCommand(InitialConfiguration initialConfiguration) {
        this.initialConfiguration = initialConfiguration;
    }

    public ResetCommand() {
    }

    @Override
    protected String getName() {
        return Messages.COMMAND_RESET_NAME;
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

    @Override
    public ExecutionResult execute(GameModel game) {
        game.reset(initialConfiguration);
        return new ExecutionResult(true, true, Messages.MOVEMENT_ERROR);
    }

    @Override
    public Command parse(String[] commandWords) {
        if (commandWords.length != 2) return null;
        if (!matchCommandName(commandWords[0])) return null;

        InitialConfiguration iC;

        switch (commandWords[1].toUpperCase()) {
            case "NONE"-> iC = InitialConfiguration.NONE;
            case "CONF_1" -> iC = InitialConfiguration.CONF_1;
            case "CONF_2" -> iC = InitialConfiguration.CONF_2;
            case "CONF_3" -> iC = InitialConfiguration.CONF_3;
            default -> {
                return null;
            }
        }
        return new ResetCommand(iC);
    }

}
