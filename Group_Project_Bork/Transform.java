package Group_Project_Bork;

import java.util.Scanner;

/**
 * Class that transform items into another one
 * @author Stephen Well Son
 * @version 11/9/2016.
 */
public class Transform{

	Item olditem;
	Item newitem;
	/**
	 * setNewItem takes item as a parameter to determine what the new item will be.
	 * @param item
	 * @return
	 */
	public void setNewItem(Item item)
	{
		this.newitem = item;
	}

	/**
	 * setOldItem establishes the old items used in transformation.
	 * @param item taken as item.
	 * @return old item.
	 */
	public void setOldItem(Item item)
	{
		this.olditem = item;
	}

	/**
	 * Transforms the old item into a new one.
	 * @return
	 */
	public Item Transform(){
		return newitem;

	}
}
