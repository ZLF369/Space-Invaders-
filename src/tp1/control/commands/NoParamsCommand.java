package tp1.control.commands;

public abstract class NoParamsCommand extends Command {

	@Override
	public Command parse(String[] commandWords) {
		return switch(commandWords[0]) {
			case "help", "h" -> new HelpCommand();
			case "exit", "e" -> new ExitCommand();
			case "none", "n" -> new NoneCommand();
			case "reset", "r" -> new ResetCommand();
			case "shoot", "s" -> new ShootCommand();
			case "shockwave", "w" -> new ShockwaveCommand();

            default -> null;
		};
	}
	
}
