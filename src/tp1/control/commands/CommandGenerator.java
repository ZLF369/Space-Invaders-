package tp1.control.commands;

import tp1.control.InitialConfiguration;
import tp1.view.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

	public static Command parse(String[] commandWords) throws CommandParseException {
		Optional match = availableCommands
				.stream().filter(c -> c.matchCommandName(commandWords[0])).
				findFirst();
		if (Objects.equals(commandWords[0], "")) {
			return new NoneCommand();
		} else if (match.isPresent()) {
			return ((Command) match.get()).parse(commandWords);
		} else {
			throw new CommandParseException(Messages.UNKNOWN_COMMAND);
		}
		// throw new CommandParseException(Messages.UNKNOWN_COMMAND);
	}
		
	public static String commandHelp() {
		StringBuilder commands = new StringBuilder();	
		for (Command c: availableCommands) {
			commands.append(c.helpText());
		}
		return commands.toString();
	}

}
