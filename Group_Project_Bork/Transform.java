package Group_Project_Bork;

import java.util.Scanner;

import Group_Project_Bork.Item.NoItemException;

/**
 * Class that transform items into another one
 * 
 * @author Stephen Well Son
 * @author Yohan Hendrawan
 * @version 11/21/2016.
 */
public class Transform extends Event {
	private String oldItem;
	private String newItem;
	private String anotherItem;

	public Transform(String n, String o) {
		this.newItem = n;
		this.oldItem = o;
	}

	public Transform(String a, String n, String o)
	{
		this.newItem = n;
		this.oldItem = o;
		this.anotherItem = a;
	}

	public void execute() {

		Item resultItem = GameState.instance().getDungeon().getItem(newItem);
		Item firstItem = GameState.instance().getDungeon().getItem(oldItem);

		try {
			if(anotherItem != null)
			{		
				Item aitem = GameState.instance().getDungeon().getItem(anotherItem);
				if (GameState.instance().getItemFromInventoryNamed(oldItem) != null 
						&& GameState.instance().getItemFromInventoryNamed(anotherItem) != null)
				{
					GameState.instance().removeFromInventory(firstItem);
					GameState.instance().removeFromInventory(aitem);
					GameState.instance().addToInventory(resultItem);
				} 
				else if (GameState.instance().getItemInVicinityNamed(oldItem) != null
						&& GameState.instance().getItemInVicinityNamed(anotherItem) != null) {
					GameState.instance().getAdventurersCurrentRoom().remove(firstItem);
					GameState.instance().getAdventurersCurrentRoom().remove(aitem);
					GameState.instance().getAdventurersCurrentRoom().add(resultItem);
				}
				else if(oldItem.trim().equals("Pickaxe"))
				{
					GameState.instance().getAdventurersCurrentRoom().remove(aitem);
					GameState.instance().addToInventory(resultItem);
				}
			}
			else 
			{
				if (GameState.instance().getItemFromInventoryNamed(oldItem) != null) {
					GameState.instance().removeFromInventory(firstItem);
					GameState.instance().addToInventory(resultItem);
				} 
				else if (GameState.instance().getItemInVicinityNamed(oldItem) != null) {
					GameState.instance().getAdventurersCurrentRoom().remove(firstItem);
					GameState.instance().getAdventurersCurrentRoom().add(resultItem);
				}
			}
		} catch (NoItemException e) {
			e.printStackTrace();
		}
	}
}
