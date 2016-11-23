package Group_Project_Bork;
/**
 * Command that execute specific item related event
 * @author Yohan Hendrawan
 * @version BorkV3
 */
public class ItemSpecificCommand extends Command {
	private String verb;
	private String noun;

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
			if (verb.equals("eat")|| verb.equals("drink")|| verb.equals("break")||verb.equals("stomp")
					||verb.equals("wave")||verb.equals("refill"))
			{
				if(tempV != null)
				{
					msg = tempV.getMessageForVerb(verb);
					//GameState.instance().getAdventurersCurrentRoom().remove(tempV);
				}
				else if(tempI != null)
				{
					msg = tempI.getMessageForVerb(verb);
					//GameState.instance().removeFromInventory(tempI);
				}
			}
			else if(verb.equals("shake")||verb.equals("kick")|| verb.equals("touch")||verb.equals("detonate")
					||verb.equals("recycle")||verb.equals("wave"))
			{
				if(tempV != null)
				{
					msg = tempV.getMessageForVerb(verb);
					
				}
				else if(tempI != null)
				{
					
					msg = tempI.getMessageForVerb(verb);
					//added a little easter egg
					if(verb.equals("kick"))
					{
						GameState.instance().removeFromInventory(tempI);
						GameState.instance().getAdventurersCurrentRoom().add(tempI);
					}
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
