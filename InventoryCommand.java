package Group_Project_Bork;

import java.util.ArrayList;

public class InventoryCommand extends Command{
	private String verb;
	public InventoryCommand(String verb)
	{
		this.verb = verb;
	}
	public String execute() {
		String inv ="You are carrying: ";
		ArrayList<String> items = GameState.instance().getInventoryNames();
		items.trimToSize();
		int count=1;
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

}
