package tp1.control.commands;

import tp1.control.ExecutionResult;
import tp1.logic.Game;
import tp1.logic.GameModel;
import tp1.view.Messages;

public class ListCommand extends NoParamsCommand {

    @Override
    protected String getName() {
        return Messages.COMMAND_LIST_NAME;
    }

    @Override
    protected String getShortcut() {
        return Messages.COMMAND_LIST_SHORTCUT;
    }

    @Override
    protected String getDetails() {
        return Messages.COMMAND_LIST_DETAILS;
    }

    @Override
    protected String getHelp() {
        return Messages.COMMAND_LIST_HELP;
    }

    @Override
    public ExecutionResult execute(GameModel game) {
        System.out.println(String.format(Messages.UCM_DESCRIPTION,Messages.UCMSHIP_DESCRIPTION,1,3));
        System.out.println(String.format(Messages.ALIEN_DESCRIPTION,Messages.REGULAR_ALIEN_DESCRIPTION,5,0,2));
        System.out.println(String.format(Messages.ALIEN_DESCRIPTION,Messages.DESTROYER_ALIEN_DESCRIPTION,10,1,1));
        System.out.println(String.format(Messages.ALIEN_DESCRIPTION,Messages.UFO_DESCRIPTION,25,0,1));
        System.out.println("\n");
        return new ExecutionResult(false);
    }
}
