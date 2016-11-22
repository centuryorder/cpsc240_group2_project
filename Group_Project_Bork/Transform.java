package Group_Project_Bork;

import java.util.Scanner;

/**
 * Class that transform items into another one
 * @author Stephen Well Son
 * @author Yohan Hendrawan
 * @version 11/21/2016.
 */
public class Transform extends Event{
	String oldItem;
	String newItem;
	
	public Transform (String n, String o)
	{
		this.newItem = n;
		this.oldItem = o;
	}
	
	public void execute() {
		
		
	}
}
