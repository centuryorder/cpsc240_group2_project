package Group_Project_Bork;

/**
 * Command class that activates Room Item Interaction event
 * @author Stephen Well Son
 * @version 11/9/2016.
 */
public class RoomItemSpecificCommand extends Command {
	String verb;
	String target;

	/**
	 * RoomItemSpecificCommand takes noun verb and target to deal with items only found in a specific room.
	 * @param noun
	 * @param verb
	 * @param target
	 */
	public RoomItemSpecificCommand(String noun, String verb, String target){

	}

	/**
	 * Execute activates the action associated with the RoomItemSpecificCommand.
	 * @return
	 */
	public String execute(){
		return target;}

}
