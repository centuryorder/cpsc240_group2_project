package Group_Project_Bork;
import java.util.*;
/**
 * Class that parses user's input and generate the appropriate command
 * @author Yohan Hendrawan
 * @version 11/21/16
 */
public class CommandFactory {

	private static CommandFactory theInstance;
	public static List<String> MOVEMENT_COMMANDS = 
			Arrays.asList("n","w","e","s","u","d" );
	public static List<String> SAVE_COMMANDS = 
			Arrays.asList("save");
	public static List<String> ITEM_COMMANDS =
			Arrays.asList("take","drop","i","eat","drink","break","shake",
					"touch", "kick", "detonate", "recycle","stomp", "wave", "refill");
	public static List<String> NPC_COMMANDS = Arrays.asList("talk");
	public static List<String> STATUS_COMMANDS =
			Arrays.asList("score","health", "look");
	public static List<String> ATTACK_COMMANDS =
			Arrays.asList("");
	public static List<String> ROOM_ITEM_COMMANDS =
			Arrays.asList("");
	public static List<String> CRAFTING_COMMANDS =
			Arrays.asList("");
	/**instance is a singleton CommandFactory class
	 * 
	 * @return CommandFactory class if no other instance have been created
	 */
	public static synchronized CommandFactory instance() {
		if (theInstance == null) {
			theInstance = new CommandFactory();
		}
		return theInstance;
	}

	private CommandFactory() {
	}
	/**
	 * Parse take in up to four words
	 * @param command take in a string
	 * @return a Command class to generate appropriate action
	 */
	public Command parse(String command) {
		String[] c = command.split(" ");
		String verb= c[0].toLowerCase();
		String noun = "";
		if (c.length > 1 && c.length <= 2)
		{
			noun = c[1];
		}
		else if (c.length > 2 && c[1].equals("to"))
		{
			noun = c[2]+" "+c[3];
		}

		if (MOVEMENT_COMMANDS.contains(verb)) {
			return new MovementCommand(verb);
		} 
		else if (SAVE_COMMANDS.contains(verb)) {
			return new SaveCommand(noun);
		}
		else if (STATUS_COMMANDS.contains(verb)){
			if(verb.equals("score"))
				return new ScoreCommand();
			else if(verb.equals("health"))
				return new HealthCommand();
			else if(verb.equals("look"))
				return new LookCommand();
			else
				return new UnknownCommand(verb);
		}
		else if (NPC_COMMANDS.contains(verb))
		{
			return new NPCSpecificCommand(verb,noun);
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