package Group_Project_Bork;
/**
 * Command class that activates crafting event
 * @author Stephen Well Son
 * @version 11/9/16.
 */
public class CraftingCommand extends Command {
	String noun, noun2;
	String verb;
	String target;

	/**
	 * CraftingCommand allows for player to transform items into new items based on strings noun verb and target.
	 * @param noun takes in the string noun
	 * @param verb takes in the string verb
	 * @param target takes in the string target.
	 */
	public CraftingCommand (String noun, String verb, String target)
	{
		this.verb = verb.trim();
		this.noun = noun.trim();
		this.target = target.trim();
	}

	/**
	 * Triggers transform and disappears on an item
	 */
	public String execute() {

		String msg ="Can't do that.";
		Item tempI,tempV,tempT;
		try {
			tempI = GameState.instance().getItemFromInventoryNamed(noun);
			tempV= GameState.instance().getItemInVicinityNamed(noun);
			tempT = GameState.instance().getItemInVicinityNamed(target);
			if(tempV != null)
			{
				msg = tempV.getMessageForVerb(verb);
				//GameState.instance().removeFromInventory(tempI);
			}
			else if(tempI != null)
			{
				msg = tempI.getMessageForVerb(verb);
				//GameState.instance().removeFromInventory(tempI);
			}
		} catch (Item.NoItemException e) {
			e.printStackTrace();
		}

		if(msg != null)
			return msg+"\n";
		else
			return "Can't "+verb+" "+noun+".\n";
	}
}
