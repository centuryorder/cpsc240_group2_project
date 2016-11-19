package Group_Project_Bork;
/**
 * Command that execute specific item related event
 * @author Yohan Hendrawan
 * @version BorkV3
 */
public class ItemSpecificCommand extends Command {
	private String verb;
	private String noun;
	private String action;


	public ItemSpecificCommand(String verb, String noun)
	{
		this.verb = verb;
		this.noun = noun;

	}

	public String execute()
	{
		String msg ="Can't do that.";
		Item tempI;
		try {
			tempI = GameState.instance().getItemFromInventoryNamed(noun);
			Item tempV= GameState.instance().getItemInVicinityNamed(noun);
			if (tempI != null)
			{
				action= tempI.getActionForVerb(verb);
				noun = tempI.getMessageForVerb(verb);
			}
			else if (tempV !=null)
			{
				action = tempV.getActionForVerb(verb);
				noun = tempV.getMessageForVerb(verb);
			}
			else
				{
					return "you don't see any " +noun+" here";
				}

				if(action !=null){
				switch (action)
				{
					case "false":
						return noun;
					case "disappear":
						GameState.instance().removeFromInventory(tempI);
						GameState.instance().getAdventurersCurrentRoom().remove(tempV);
						return noun;
					default:
						GameState.instance().removeFromInventory(tempI);
						GameState.instance().getAdventurersCurrentRoom().remove(tempV);
						GameState.instance().addToInventory(GameState.instance().getDungeon().getItem(action));
						return noun;
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
