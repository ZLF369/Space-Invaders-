package tp1.control.commands;

import tp1.control.InitialConfiguration;

import java.util.Arrays;
import java.util.List;

public class CommandGenerator {

	private static final List<Command> availableCommands = Arrays.asList( //Add all the variations of commands here
		new HelpCommand(),
		new MoveCommand(),
		new ExitCommand(),
		new NoneCommand(),
		new ResetCommand(InitialConfiguration.NONE),
//		new ResetCommand(InitialConfiguration.CONF_1),
//		new ResetCommand(InitialConfiguration.CONF_2),
//		new ResetCommand(InitialConfiguration.CONF_3),
		new ShootCommand(),
		new ShockwaveCommand(),
		new ListCommand(),
		new SuperlaserCommand()
	);

	public static Command parse(String[] commandWords) {
		Command command = null;
		for (Command c: availableCommands) {
			if(c.matchCommandName(commandWords[0])) {
				command = c.parse(commandWords);
				break;
			} else if (commandWords[0].equals("")){
				command = new NoneCommand();
				break;
			}
		}
		return command;
	}
		
	public static String commandHelp() {
		StringBuilder commands = new StringBuilder();	
		for (Command c: availableCommands) {
			commands.append(c.helpText());
		}
		return commands.toString();
	}

}
