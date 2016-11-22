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
	private Hashtable<String, Teleport> teleport;
	private Hashtable<String, Disappear> disappear;

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
				String[] events;
				int start;
				int end;
				int value;
				String item;
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
						events = verb.substring(start+1, end).split(",");
						
						v = new StringBuffer(verb);
						verb = v.replace(start, end+1, "").toString();
						for(String i: events)
						{
							int st =0;
							int en =0;
							if(i.contains("Wound"))
							{
								st = i.indexOf("(");
								en = i.indexOf(")");
								value = Integer.parseInt(i.substring(st+1,en));
								this.wound.put(verb, new Wound(value));
							}
							else if(i.contains("Score"))
							{
								st = i.indexOf("(");
								en = i.indexOf(")");
								value = Integer.parseInt(i.substring(st+1,en));
								this.score.put(verb, new Score(value));
							}
							else if(i.contains("Transform"))
							{
								st = i.indexOf("(");
								en = i.indexOf(")");
								item = i.substring(st+1,en);
								this.transform.put(verb, new Transform(item,this.primaryName));
							}
							else if(i.contains("Die"))
							{
								this.die.put(verb, new Die());
							}
							else if(i.contains("Win"))
								this.win.put(verb, new Win());
							else if(i.contains("Teleport"))
								this.teleport.put(verb, new Teleport());
							else if(i.contains("Disappear"))
								this.disappear.put(verb, new Disappear(this));
						}
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
		this.win = new Hashtable<String,Win>();
		this.die = new Hashtable<String,Die>();
		this.teleport = new Hashtable<String, Teleport>();
		this.disappear = new Hashtable<String, Disappear>();
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
		if(wound.get(verb)!=null)
			getWound(verb);
		if(score.get(verb)!=null)
			getScore(verb);
		if(transform.get(verb)!=null)
			getTransform(verb);
		if(win.get(verb)!=null)
			getWin(verb);
		if(die.get(verb)!=null)
			getDie(verb);
		if(teleport.get(verb)!=null)
			getTeleport(verb);
		if(disappear.get(verb)!=null)
			getDisappear(verb);
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
	void getTransform(String verb)
	{
		transform.get(verb).execute();
	}
	void getWin(String verb)
	{
		win.get(verb).execute();
	}
	void getDie(String verb)
	{
		die.get(verb).execute();
	}
	void getTeleport(String verb)
	{
		teleport.get(verb).execute();
	}
	void getDisappear(String verb)
	{
		disappear.get(verb).execute();
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
