package Group_Project_Bork;
import java.util.Scanner;
/**
 * Interpreter class that interact with user
 * @author Yohan Hendrawan
 * @version BorkV3
 */
public class Interpreter {

    private static GameState state; // not strictly necessary; GameState is 
                                    // singleton
    private static String BASIC_COMMANDS = "Some of basic command are look, save, i(inventory view), and q(quit).";
    public static String USAGE_MSG = 
        "Usage: Interpreter borkFile.bork|saveFile.sav.";

    public static void main(String args[]) {

        if (args.length < 1) {
            System.err.println(USAGE_MSG);
            System.exit(1);
        }

        String command;
        Scanner commandLine = new Scanner(System.in);

        try {
            state = GameState.instance();
            if (args[0].endsWith(".bork")) {
                state.initialize(new Dungeon(args[0],true));
                System.out.println("\nWelcome to " + 
                    state.getDungeon().getName() + "!");
                System.out.println(BASIC_COMMANDS);
            } else if (args[0].endsWith(".sav")) {
                state.restore(args[0]);
                System.out.println("\nWelcome back to " + 
                    state.getDungeon().getName() + "!");
                System.out.println(BASIC_COMMANDS);
            } else {
                System.err.println(USAGE_MSG);
                System.exit(2);
            }

            System.out.print("\n" + 
                state.getAdventurersCurrentRoom().describe() + "\n");

            command = promptUser(commandLine);

            while (!command.equals("q")) {

                System.out.print(
                    CommandFactory.instance().parse(command).execute());

                command = promptUser(commandLine);
            }

            System.out.println("Bye!");
            System.exit(0);

        } catch(Exception e) { 
            e.printStackTrace(); 
        }
    }

	/**
	 * Prompt user with message
	 * @param commandLine scanner used to return user input
	 * @return user input
	 */
    private static String promptUser(Scanner commandLine) {

        System.out.print("> ");
        return commandLine.nextLine();
    }

}
