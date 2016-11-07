
package hendrawan_borkv3;

import java.util.*;

public class CommandFactory {

	private static CommandFactory theInstance;
	public static List<String> MOVEMENT_COMMANDS = 
			Arrays.asList("n","w","e","s","u","d" );
	public static List<String> SAVE_COMMANDS = 
			Arrays.asList("save");
	public static List<String> ITEM_COMMANDS =
			Arrays.asList("take","drop","i","eat","drink","break","shake",
					"touch", "kick");

	public static synchronized CommandFactory instance() {
		if (theInstance == null) {
			theInstance = new CommandFactory();
		}
		return theInstance;
	}

	private CommandFactory() {
	}

	public Command parse(String command) {
		String[] c = command.split(" ", 2);
		String verb= c[0].toLowerCase();
		String noun = "";
		if (c.length > 1)
		{
			noun = c[1];
		}

		if (MOVEMENT_COMMANDS.contains(verb)) {
			return new MovementCommand(verb);
		} 
		else if (SAVE_COMMANDS.contains(verb)) {
			return new SaveCommand(noun);
		}
		else if (ITEM_COMMANDS.contains(verb) && !noun.equals("")){
			if(verb.equals("take"))
				return new TakeCommand(verb , noun);
			else if(verb.equals("drop"))
				return new DropCommand(verb,noun);
			else
				return new ItemSpecificCommand(verb, noun);
		}
		else if (ITEM_COMMANDS.contains(verb) && noun.equals("")){
			if(verb.equals("i"))
					return new InventoryCommand(verb);
			else
				return new UnknownCommand(verb);
		}
		else
			return new UnknownCommand(command);
	}
}