package Group_Project_Bork;

public class TakeCommand extends Command{

	private String noun;
	private String verb;
	public TakeCommand(String verb, String noun)
	{
		this.noun = noun;
		this.verb = verb;
	}
	public String execute() {
		String msg = "No "+noun+" here.\n";
		if(verb.equals("take"))
		{
			try {
				Item temp = GameState.instance().getItemInVicinityNamed(noun);
				if(temp != null)
				{
					GameState.instance().addToInventory(temp);
					GameState.instance().getAdventurersCurrentRoom().remove(temp);
					msg = "You take " +noun+".\n";
				}

			} catch (Item.NoItemException e) {
				e.printStackTrace();
			}
		}
		return msg;

	}

}
