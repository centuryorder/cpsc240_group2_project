package Group_Project_Bork;

import java.util.Hashtable;
import java.util.Scanner;

import Group_Project_Bork.Dungeon.IllegalDungeonFormatException;
/**
 * Object class that hold the Non Player Character properties
 * @author Yohan Hendrawan
 * @version 12/05/16
 */
public class NPC {
	private String name;
	private int maxHP;
	private int health;
	private int armor;
	private boolean hostile = false;
	private boolean boss = false;
	private  Hashtable<String,String> message = new Hashtable<String,String>();
	
	private Hashtable<String, Wound> wound;
	private Hashtable<String, Score> score;
	private Hashtable<String, Transform> transform;
	private Hashtable<String, Die> die;
	private Hashtable<String, Win> win;
	private Hashtable<String, Teleport> teleport;
	private Hashtable<String, Disappear> disappear;

	public NPC(String name, int health, int armor, boolean hostile, boolean boss)
	{
		init();
		this.name = name;
		this.health = health;
		this.armor = armor;
		this.hostile = hostile;
		this.boss = boss;
	}

	public NPC(Scanner s) throws IllegalDungeonFormatException
	{
		init();
		this.name =s.nextLine();
		String [] info;
		info = s.nextLine().split(":");
		this.health = Integer.parseInt(info[1]);
		info = s.nextLine().split(":");
		this.armor = Integer.parseInt(info[1]);
		String [] events;
		info = s.nextLine().split(":");
		String verb = info[0], noun = info[1];
		int start, end, value;
		if (verb.contains("["))
		{
			start = verb.indexOf("[");
			end = verb.indexOf("]");
			events = verb.substring(start+1, end).split(",");
			StringBuffer v = new StringBuffer(verb);
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
				else if(i.contains("Die"))
				{
					this.die.put(verb, new Die(this));
				}
				else if(i.contains("Win"))
					this.win.put(verb, new Win());
				else if(i.contains("Teleport"))
					this.teleport.put(verb, new Teleport());
			}
		}
		this.addMessage(verb, noun);
		if (!s.nextLine().equals(Dungeon.SECOND_LEVEL_DELIM)) {
			throw new Dungeon.IllegalDungeonFormatException("No '" +
					Dungeon.SECOND_LEVEL_DELIM + "' after NPC.");
		}
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
	 * Takes name as argument and associates it with npc
	 * @param name takes in string name
	 * @return
	 */
	public void setName(String name){
		this.name = name;
	}
	public String getName()
	{
		return this.name;
	}

	/**
	 * Uses int health to determine health state of npc
	 * @param health takes integer health
	 * @return health
	 */
	public void setHealth(int health){
		this.health = health;
	}
	/**
	 * Returns boolean that determines whether or not an npc will act aggressively towards the player.
	 * @return false by default
	 */
	public boolean isHostile(){
		return hostile;
	}

	public void addMessage(String command, String message)
	{
		this.message.put(command, message);
	}

	public void recieveWound(Wound wound)
	{
		this.health -= wound.getDamage();
		if (this.health > maxHP)
			this.health = maxHP;
		else if(this.health <= 0)
			new Die().execute();
	}
	public String toString()
	{
		return name;
	}
}
