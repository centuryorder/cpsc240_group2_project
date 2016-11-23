package Group_Project_Bork;
import java.util.ArrayList;
/**
 * Command that that display the current item in adventurer's inventory
 * @author Yohan Hendrawan
 * @version BorkV3
 */
public class InventoryCommand extends Command{
	private String verb;
	public InventoryCommand(String verb)
	{
		this.verb = verb;
	}
	public String execute() {
		String inv ="You are not carrying anything.";
		ArrayList<String> items = GameState.instance().getInventoryNames();
		items.trimToSize();
		if(items.size() > 0)
		{
			int count=1;
			inv = "You are carrying: ";
			for(String i: items)
			{
				inv += i;

				if(count < items.size())
				{
					inv += ", ";
				}	
				count++;
			}
			inv +="\n";
			return inv;
		}
		else
			return inv+"\n";
	}

}
