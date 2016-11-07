package hendrawan_borkv3;

import hendrawan_borkv3.Item.NoItemException;

public class DropCommand extends Command{
	private String noun;
	private String verb;
	public DropCommand(String verb, String noun)
	{
		this.noun = noun;
		this.verb = verb;
	}
	public String execute() {
		String msg = "No "+noun+" in the inventory.\n";
		if(verb.equals("drop"))
		{
			try {
				Item temp = GameState.instance().getItemFromInventoryNamed(noun);
				if(temp != null)
				{
					GameState.instance().getAdventurersCurrentRoom().add(temp);
					GameState.instance().removeFromInventory(temp);
					msg = "You drop " +noun+".\n";
				}

			} catch (NoItemException e) {
				e.printStackTrace();
			}
		}
		return msg;
	}
}
