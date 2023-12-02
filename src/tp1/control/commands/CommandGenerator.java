package tp1.control.commands;

import java.util.Arrays;
import java.util.List;

public class CommandGenerator {

	private static final List<Command> availableCommands = Arrays.asList(
		new HelpCommand(),
		new MoveCommand(),
		new ExitCommand(),
		new NoneCommand(),
		new ResetCommand(),
		new ShootCommand(),
		new ShockwaveCommand(),
		new ListCommand()
		//TODO fill with your code
	);


	public static Command parse(String[] commandWords) {
		Command command = null;
		for (Command c: availableCommands) {
			if(c.matchCommandName(commandWords[0])) {
				command = c.parse(commandWords);
				break;
			}
		}
		return command;
	}
		
	public static String commandHelp() {
		StringBuilder commands = new StringBuilder();	
		for (Command c: availableCommands) {
			//TODO fill with your code
		}
		return commands.toString();
	}

}
