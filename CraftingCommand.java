package Group_Project_Bork;
/**
 * Command class that activates crafting event
 * @author Stephen Well Son
 * @version 11/9/16.
 */
public class CraftingCommand extends Command {
	String noun;
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
this.noun = noun;
		this.verb= verb;
		this.target = target;

	}
	/**
	 * Triggers transform and disappears on an item
	 */
	public String execute() {
		String msg ="Can't do that.";
		Item tempI;
		try {
			tempI = GameState.instance().getItemFromInventoryNamed(noun);
			Item tempV= GameState.instance().getItemInVicinityNamed(noun);
			if (verb.equals("transform"))
			{
				if(tempV != null)
				{
					msg = tempV.getMessageForVerb(verb);
					GameState.instance().getAdventurersCurrentRoom().remove(tempV);
				}
				else if(tempI != null)
				{
					msg = tempI.getMessageForVerb(verb);
					GameState.instance().removeFromInventory(tempI);
				}
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
