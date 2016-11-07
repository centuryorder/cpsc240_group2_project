package hendrawan_borkv3;

import hendrawan_borkv3.Item.NoItemException;

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
			if (verb.equals("eat")|| verb.equals("drink")|| verb.equals("break"))
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
			if(verb.equals("shake")||verb.equals("kick")|| verb.equals("touch"))
			{
				if(tempV != null)
				{
					msg = tempV.getMessageForVerb(verb);
					
				}
				else if(tempI != null)
				{
					
					msg = tempI.getMessageForVerb(verb);
					if(verb.equals("kick"))
					{
						GameState.instance().removeFromInventory(tempI);
						GameState.instance().getAdventurersCurrentRoom().add(tempI);
					}
				}
			}
		} catch (NoItemException e) {
			e.printStackTrace();
		}

		if(msg != null)
			return msg+"\n";
		else
			return "Can't "+verb+" "+noun+".\n";
	}

}
