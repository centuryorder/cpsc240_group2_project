package Group_Project_Bork;
import java.util.*;

/**
 * Object class that hold items properties
 * @author Matthew Aneiro
 * @author Yohan Hendrawan
 * @version 11/21/2016
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
	private Hashtable<String, Wound> wound;
	private Hashtable<String, Score> score;
	private Hashtable<String, Transform> transform;
	private Hashtable<String, Die> die;
	private Hashtable<String, Win> win;

	/**
	 * Item constructor
	 * @param s file reader
	 */
	public Item(Scanner s)throws NoItemException
	{
		init();
		this.primaryName = s.nextLine();
		String line =s.nextLine();
		if(!primaryName.equals(Dungeon.TOP_LEVEL_DELIM))
		{
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
				String[] event;
				int start;
				int end;
				StringBuffer v;
				while((!line.equals(Dungeon.SECOND_LEVEL_DELIM) && !line.equals(Dungeon.TOP_LEVEL_DELIM))
						&&(!msg.equals(Dungeon.SECOND_LEVEL_DELIM) && !msg.equals(Dungeon.TOP_LEVEL_DELIM)))
				{
					m = msg.split(":");
					verb = m[0].trim();
					if(verb.contains("["))
					{
						start = verb.indexOf("[");
						end = verb.indexOf("]");
						event = verb.substring(start+1, end).split(",");
						v = new StringBuffer(verb);
						verb = v.replace(start, end+1, "").toString();
					}
					noun = m[1].trim();
					message.put(verb,noun);
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
		this.wound = new Hashtable<String,Wound>();
		this.score = new Hashtable<String,Score>();
		this.transform = new Hashtable<String,Transform>();
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
	void getWound(String verb)
	{
		wound.get(verb).execute();
	}
	void getScore(String verb)
	{
		score.get(verb).execute();
	}
	void getTranform(String verb)
	{
		transform.get(verb).execute();
	}
	
	/**
	 * Gets item's name
	 * @return item name
	 */
	public String toString()
	{
		return primaryName;
	}
}
