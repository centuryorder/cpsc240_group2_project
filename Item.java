package Group_Project_Bork;

import java.util.*;

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
			while((!line.equals(Dungeon.SECOND_LEVEL_DELIM) && !line.equals(Dungeon.TOP_LEVEL_DELIM))
					&&(!msg.equals(Dungeon.SECOND_LEVEL_DELIM) && !msg.equals(Dungeon.TOP_LEVEL_DELIM)))
			{
				m = msg.split(":");
				verb = m[0].trim();
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
	}
	public boolean goesBy(String name)
	{
		boolean n = false;
		if (this.primaryName != null)
			n = true;
		return n;
	}

	public String getPrimaryName()
	{
		return this.primaryName;
	}

	public String getMessageForVerb(String verb)
	{
		String msg= message.get(verb);
		return msg;

	}
	public String toString()
	{
		return primaryName;
	}
}
