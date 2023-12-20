package tp1.control.commands;

import tp1.control.InitialConfiguration;
import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.InitializationException;
import tp1.logic.GameModel;
import tp1.view.Messages;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ResetCommand extends Command{
    private InitialConfiguration initialConfiguration;

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
    public boolean execute(GameModel game) throws CommandExecuteException {
        try {
            game.reset(initialConfiguration);
        } catch (InitializationException e) {
            System.err.println("Initialization error: " + e.getMessage());
        } catch (CommandExecuteException e) {
            System.err.println("Command execution error: " + e.getMessage());
        }
        return true;
    }

    @Override
    public Command parse(String[] commandWords) {
        if (commandWords.length != 2) return null;
        if (!matchCommandName(commandWords[0])) return null;

        try {
            if ("NONE".equalsIgnoreCase(commandWords[1])) {
                return new ResetCommand(null);
            } else {
                InitialConfiguration iC = InitialConfiguration.readFromFile(commandWords[1]);
                return new ResetCommand(iC);
            }
        } catch (FileNotFoundException e) {

            System.err.println("File not found: " + e.getMessage());
            return null;
        } catch (IOException e) {
            System.err.println("File reading error: " + e.getMessage());
            return null;
        }
    }

}
