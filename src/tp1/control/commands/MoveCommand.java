package tp1.control.commands;

import tp1.control.ExecutionResult;
import tp1.logic.Game;
import tp1.logic.GameModel;
import tp1.logic.Move;
import tp1.view.Messages;

public class MoveCommand extends Command {
	private Move move;

	public MoveCommand() {}
	protected MoveCommand(Move move) {
		this.move = move;
	}

	@Override
	protected String getName() {
		return Messages.COMMAND_MOVE_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.COMMAND_MOVE_SHORTCUT;
	}

	@Override
	protected String getDetails() {
		return Messages.COMMAND_MOVE_DETAILS;
	}

	@Override
	protected String getHelp() {
		return Messages.COMMAND_MOVE_HELP;
	}

	@Override
	public ExecutionResult execute(GameModel game) { //DEBUG
		if (move == Move.UP || move == Move.DOWN)
			return new ExecutionResult(true);

		boolean siu = game.move(move);
		if (!siu)
			return new ExecutionResult(true);
		game.update();
		return new ExecutionResult(siu, true, Messages.MOVEMENT_ERROR);
	}

	@Override
	public Command parse(String[] commandWords) {
		// commandWords[2] == null
		// commandWords[0] == null;

		Move move = Move.valueOf(commandWords[1].toUpperCase());
		return new MoveCommand(move);
	}

}