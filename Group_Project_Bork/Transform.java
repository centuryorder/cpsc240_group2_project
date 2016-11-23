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
	String oldItem;
	String newItem;

	public Transform(String n, String o) {
		this.newItem = n;
		this.oldItem = o;
	}

	public void execute() {

		Item resultItem = GameState.instance().getDungeon().getItem(newItem);
		Item firstItem = GameState.instance().getDungeon().getItem(oldItem);
		try {
			if (GameState.instance().getItemFromInventoryNamed(oldItem) != null) {
				GameState.instance().removeFromInventory(firstItem);
				GameState.instance().addToInventory(resultItem);
			} 
			else if (GameState.instance().getItemInVicinityNamed(oldItem) != null) {
				GameState.instance().getAdventurersCurrentRoom().remove(firstItem);
				GameState.instance().getAdventurersCurrentRoom().add(resultItem);
			}
		} catch (NoItemException e) {
			e.printStackTrace();
		}
	}
}
