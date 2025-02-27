package tp1;

import java.util.Locale;
import java.util.Scanner;

import tp1.control.Controller;
import tp1.logic.Game;
import tp1.logic.Level;
import tp1.view.Messages;
import static tp1.view.Messages.error;


public class Main {

	/**
	 * Show application help message
	 */
	private static void usage() {
		System.out.println(Messages.USAGE);
		System.out.println(Messages.USAGE_LEVEL_PARAM);
		System.out.println(Messages.USAGE_SEED_PARAM);
	}

    /**
     * SpaceInvaders entry point
     * 
     * @param args Arguments for the game.
     */
    public static void main(String[] args) {
        // Required to avoid issues with tests
        Locale.setDefault(new Locale("es", "ES"));

        if (args.length < 1 || args.length > 2) {
            usage();
        } else {
            Level level = Level.valueOfIgnoreCase(args[0]);
            if (level == null) {
                System.out.println(Messages.ALLOWED_LEVELS);
                usage();
            } else {
                long seed = System.currentTimeMillis() % 1000;
                String seedParam = "";
                try {
                    if (args.length == 2) {
                        seedParam = args[1];
                        seed = Long.parseLong(seedParam);
                    }
        
                    System.out.println(Messages.WELCOME);
        
                    System.out.printf((Messages.CONFIGURED_LEVEL) + "%n", level.name());
                    System.out.printf((Messages.CONFIGURED_SEED) + "%n", seed);
        
                    Game game = new Game(level, seed);
                    Scanner scanner = new Scanner(System.in);
                    Controller controller = new Controller(game, scanner);
                    controller.run();
        
                } catch (NumberFormatException nfe) {
                    System.err.printf((Messages.SEED_NOT_A_NUMBER_ERROR) + "%n", seedParam);
                    usage();
                } catch (Exception e) {
                    System.err.println(error(e.getMessage()));
                    System.err.println(e.getMessage());
                }
            }
        }
    }

}

