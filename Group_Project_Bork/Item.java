package Group_Project_Bork;
import java.util.*;

/**
 * Object class that hold items properties
 * @author Matthew Aneiro
 * @version 11/08/2016
 */

public class Item 
{
	public static class NoItemException extends Exception {
        public NoItemException(String e) {
            super(e);
        }
    }
	private String primaryName;
	private int weight;
	private Hashtable<String, String> message;
	private Hashtable<String, String> action;
	private ArrayList<Wound> wound;
	private ArrayList<Transform> transform;

	/**
	 * Item constructor
	 * @param s file reader
	 */
	public Item(Scanner s)throws NoItemException
	{
		init();
		this.primaryName = s.nextLine();
		if(!primaryName.equals(Dungeon.TOP_LEVEL_DELIM))
		{
			String line =s.nextLine();
			if (!line.equals(Dungeon.SECOND_LEVEL_DELIM))
			{
				this.weight = Integer.parseInt(line);
			}
			String msg = s.nextLine();
			if(!msg.equals(Dungeon.SECOND_LEVEL_DELIM))
			{
				String[] m;
			
			String verb;
			String noun;
			String action;
			while((!line.equals(Dungeon.SECOND_LEVEL_DELIM) && !line.equals(Dungeon.TOP_LEVEL_DELIM))
					&&(!msg.equals(Dungeon.SECOND_LEVEL_DELIM) && !msg.equals(Dungeon.TOP_LEVEL_DELIM)))
			{
				m = msg.split(":");
				verb = m[0].trim();
				noun = m[1].trim();
				action = m[2].trim();
				message.put(verb,noun);
				this.action.put(verb,action);
				msg =s.nextLine();
			}
			}
		}
		else
			throw new NoItemException("No more item!");
	}
	
	void init()
	{
		this.message = new Hashtable<String,String>();
		this.action = new Hashtable<String, String>();
		this.wound = new ArrayList<Wound>();
		this.transform = new ArrayList<Transform>();
	}
	
	/**
	 * Tests nicknames for items
	 * @param name item's nickname
	 * @return boolean
	 */
	public boolean goesBy(String name)
	{
		boolean n = false;
		if (this.primaryName != null)
			n = true;
		return n;
	}

	/**
	 * Gets item's name
	 * @return item name
	 */
	public String getPrimaryName()
	{
		return this.primaryName;
	}

	/**
	 * Gets text corresponding to specific verb
	 * @param verb verb acting on object
	 * @return text message
	 */
	public String getMessageForVerb(String verb)
	{
		String msg= message.get(verb);
		return msg;

	}

	/**
	 * Gets text corresponding to action related to item
	 * @param verb action on object
	 * @return
	 */
	public String getActionForVerb(String verb)
	{
		String act= action.get(verb);
		return act;
	}
	
	/**
	 * Gets item's name
	 * @return item name
	 */
	public String toString()
	{
		return primaryName;
	}
	
	public Wound getWound()
	{
		for(int i =0; i<wound.size(); i++)
			if(wound.get(i) != null)
				return wound.get(i);
		return null;
	}
	
	public void setWound(Wound w)
	{
		this.wound.add(w);
	}
	
	public Transform getTransform()
	{
		for(int i =0; i<transform.size(); i++)
			if(transform.get(i) != null)
				return transform.get(i);
		return null;
	}
	
	public void setTransform(Transform t)
	{
		this.transform.add(t);
	}
}