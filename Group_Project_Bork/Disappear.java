package Group_Project_Bork;

import Group_Project_Bork.Item.NoItemException;

/**
 * Class that erases item from the dungeon
 * @author Yohan Hendrawan
 * @author Stephen Well Son
 * @version 11/21/16.
 */
public class Disappear extends Event{
	Item item;

	/**
	 * Disappear will handle dismissing items from the running instance of the program.
	 * @param item will be implemented
	 */
	public Disappear(Item item){
		this.item = item;
	}
	/**
	 * Execute Disappear event
	 */
	public void execute()
	{
		if(GameState.instance().getAdventurersCurrentRoom().getItemNamed(item.getPrimaryName()) != null)
			GameState.instance().getAdventurersCurrentRoom().remove(item);
		else
			try {
				if(GameState.instance().getItemFromInventoryNamed(item.getPrimaryName()) != null)
					GameState.instance().removeFromInventory(item);
			} catch (NoItemException e) {
				e.printStackTrace();
			}
	}
}
