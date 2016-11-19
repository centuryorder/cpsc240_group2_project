package Group_Project_Bork;
/**
 * Command that drop an item into the adventurer's current room
 * @author Yohan Hendrawan
 * @version BorkV3
 *
 */
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

			} catch (Item.NoItemException e) {
				e.printStackTrace();
			}
		}
		return msg;
	}
}
