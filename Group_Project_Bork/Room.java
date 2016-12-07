package Group_Project_Bork;
import java.util.*;

import Group_Project_Bork.NPC.NoNPCException;

import java.io.*;
/**
 * Object class used to hold room properties
 * @author Yohan Hendrawan
 * @version 11/09/16
 */
public class Room {

	class NoRoomException extends Exception {}

	private String title;
	private String desc;
	private boolean beenHere;
	private ArrayList<Exit> exits;
	private ArrayList<Item> items;
	private ArrayList<NPC> NPC;
	private boolean lightable = false;
	private boolean light = true;
	private boolean hideName;
	private boolean locked;

	Room(String title) {
		init();
		this.title = title;
	}

	/** Given a Scanner object positioned at the beginning of a "room" file
        entry, read and return a Room object representing it. 
        @throws NoRoomException The reader object is not positioned at the
        start of a room entry. A side effect of this is the reader's cursor
        is now positioned one line past where it was.
        @throws IllegalDungeonFormatException A structural problem with the
        dungeon file itself, detected when trying to read this room.
	 */
	Room(Scanner s) throws NoRoomException,
	Dungeon.IllegalDungeonFormatException {

		init();
		title = s.nextLine();
		desc = "";
		if (title.equals(Dungeon.TOP_LEVEL_DELIM)) {
			throw new NoRoomException();
		}

		String lineOfDesc = s.nextLine();
		while (!lineOfDesc.equals(Dungeon.SECOND_LEVEL_DELIM) &&
				!lineOfDesc.equals(Dungeon.TOP_LEVEL_DELIM)) {
			desc += "\n" + lineOfDesc;
			lineOfDesc = s.nextLine();
		}

		// throw away delimiter
		if (!lineOfDesc.equals(Dungeon.SECOND_LEVEL_DELIM)) {
			throw new Dungeon.IllegalDungeonFormatException("No '" +
					Dungeon.SECOND_LEVEL_DELIM + "' after room.");
		}
	}

	public Room(Scanner s, Dungeon d) throws NoRoomException, 
	Dungeon.IllegalDungeonFormatException, 
	Item.NoItemException, Item.NoItemException, NoNPCException{this(s,d,true);}

	public Room(Scanner s, Dungeon d, boolean initState) throws NoRoomException,
	Dungeon.IllegalDungeonFormatException, Item.NoItemException, NoNPCException
	{
		init();
		title = s.nextLine();
		desc = "";
		if (title.equals(Dungeon.TOP_LEVEL_DELIM)) {
			throw new NoRoomException();
		}
		String lineOfDesc = s.nextLine();
		String[] contents = lineOfDesc.split(":");
		String header = contents[0].trim()+":";
		if(contents.length > 1 && header.equals(Dungeon.ROOM_NPC_MARKER))
		{
			if(initState != false)
			{
				String[] NPC = contents[1].split(",");
				for(String n:NPC)
					if(d.getNPC(n.trim()) == null)
					{throw new NPC.NoNPCException("No NPC found");}
					else
						this.addNPC(d.getNPC(n.trim()));
			}
			lineOfDesc =s.nextLine();
			contents = lineOfDesc.split(":");
			header = contents[0].trim()+":";
			if(contents.length > 1 && header.equals(Dungeon.ROOM_CONTENTS_MARKER))
			{
				if(initState != false)
				{
					String[] content = contents[1].split(",");
					for(String item: content) 
					{	
						if(d.getItem(item.trim()) == null)     			
						{throw new Item.NoItemException("No item found");}
						else
							this.add(d.getItem(item.trim()));
					}
				}
				lineOfDesc =s.nextLine();
				while (!lineOfDesc.equals(Dungeon.SECOND_LEVEL_DELIM) &&
						!lineOfDesc.equals(Dungeon.TOP_LEVEL_DELIM)) {
					desc += "\n"+ lineOfDesc;
					lineOfDesc = s.nextLine();
				}
			}
			else
			{
				while (!lineOfDesc.equals(Dungeon.SECOND_LEVEL_DELIM) &&
						!lineOfDesc.equals(Dungeon.TOP_LEVEL_DELIM)) {
					desc += "\n"+ lineOfDesc;
					lineOfDesc = s.nextLine();
				}
			}
		}
		else if(contents.length > 1 && header.equals(Dungeon.ROOM_CONTENTS_MARKER))
		{
			if(initState != false)
			{
				String[] content = contents[1].split(",");
				for(String item: content) 
				{	
					if(d.getItem(item.trim()) == null)     			
					{throw new Item.NoItemException("No item found");}
					else
						this.add(d.getItem(item.trim()));
				}
			}
			lineOfDesc =s.nextLine();
			while (!lineOfDesc.equals(Dungeon.SECOND_LEVEL_DELIM) &&
					!lineOfDesc.equals(Dungeon.TOP_LEVEL_DELIM)) {
				desc += "\n"+ lineOfDesc;
				lineOfDesc = s.nextLine();
			}
		}
		else
		{
			while (!lineOfDesc.equals(Dungeon.SECOND_LEVEL_DELIM) &&
					!lineOfDesc.equals(Dungeon.TOP_LEVEL_DELIM)) {
				desc += "\n"+ lineOfDesc;
				lineOfDesc = s.nextLine();
			}
		}

		// throw away delimiter
		if (!lineOfDesc.equals(Dungeon.SECOND_LEVEL_DELIM)) {
			throw new Dungeon.IllegalDungeonFormatException("No '" +
					Dungeon.SECOND_LEVEL_DELIM + "' after room.");
		}
	}

	// Common object initialization tasks.
	private void init() {
		exits = new ArrayList<Exit>();
		items = new ArrayList<Item>();
		NPC = new ArrayList<NPC>();
		beenHere = false;
	}

	String getTitle() { return title; }

    public boolean getLock(boolean locked){return locked;}

	void setDesc(String desc) { this.desc = desc; }

	/*
	 * Store the current (changeable) state of this room to the writer
	 * passed.
	 */
	void storeState(PrintWriter w) throws IOException {
		// At this point, nothing to save for this room if the user hasn't
		// visited it.
		if (this.beenHere) {
			w.println(title + ":");
			w.println("beenHere="+this.beenHere);
			String c ="";
			int count = 1;
			for(Item i: this.items)
			{
				if(count < this.items.size())
					c += i.getPrimaryName()+",";
				else
					c += i.getPrimaryName();
				count++;
			}
			w.println(Dungeon.ROOM_CONTENTS_MARKER+ " " + c);
			count = 1;
			c="";
			for(NPC i: this.NPC)
			{
				if(count < this.NPC.size())
					c += i.getName()+",";
				else
					c += i.getName();
				count++;
			}
			w.println(Dungeon.ROOM_NPC_MARKER+ " " + c);
			w.println(Dungeon.SECOND_LEVEL_DELIM);
		}
	}

	void restoreState(Scanner s) throws GameState.IllegalSaveFormatException {

		String line = s.nextLine();
		if (!line.startsWith("beenHere")) {
			throw new GameState.IllegalSaveFormatException("No beenHere.");
		}
		beenHere = Boolean.valueOf(line.substring(line.indexOf("=")+1));

		s.nextLine();   // consume end-of-room delimiter
	}

	void restoreState(Scanner s, Dungeon d) throws GameState.IllegalSaveFormatException
	{
		String line = s.nextLine();
		if (!line.startsWith("beenHere")) {
			throw new GameState.IllegalSaveFormatException("No beenHere.");
		}
		beenHere = Boolean.valueOf(line.substring(line.indexOf("=")+1));
		line = s.nextLine();
		String[] contents = line.split(" ");
		String[] i;
		if (!line.equals(Dungeon.SECOND_LEVEL_DELIM)&& !line.equals(Dungeon.TOP_LEVEL_DELIM) && contents[0].equals(Dungeon.ROOM_CONTENTS_MARKER))
		{
			i =contents[1].split(",");
			if (d.getInitState() == false)
				for(String it: i)
					this.add(d.getItem(it));
			line =s.nextLine();
			contents = line.split(":");
			i =contents[1].split(",");
			if (d.getInitState() == false)
				for(String n:i)
					this.addNPC(d.getNPC(n.trim()));
			s.nextLine();   // consume end-of-room delimiter
		}

	}
	/**
	 * Describe the room. Hide the name if a revealing event have not been trigger
	 * hide the description of the room if the adventurer have been in the room.
	 * @return description of the room.
	 */
	public String describe() {
		String description;
		if(hideName){
			description = "??????????";
		}
		else if (beenHere && !hideName) {
			if (title.contains("Forest"))
			{
				description = "Forest";
			}
			else
				description = title;
		} else {
			if (title.contains("Forest"))
			{
				description = "Forest"+ desc;
			}
			else
				description = title + desc;
		}
		int count= 0;
		if(!beenHere)
		{
			for (NPC i: NPC)
			{   
				if(count < NPC.size())
					description += " There is a "+i+". ";
				else
					description += "There is a "+i+".";
				count++;
			}
			for (Item item: items)
			{   
				if(count < items.size())
					description += " There is a "+item+". ";
				else
					description += "There is a "+item+".";
				count++;
			}
		}
		if(!GameState.instance().getVerbose())
		{
			for (Exit exit : exits) {
				description += "\n" + exit.describe();
			}
		}
		beenHere = true;
		if (!light)
			description = "It's to dark to see.";
		return description;
	}

	public String lookDescribe()
	{
		String dsc="";
		if(hideName){
			dsc = "??????????\n";
		}
		else
		{
			if (title.contains("Forest"))
			{
				dsc = "Forest"+ desc;
			}
			else
				dsc = title + desc;
		}
		int count=0;
		for (NPC i: NPC)
		{   
			if(count < NPC.size())
				dsc += " There is a "+i+". ";
			else
				dsc += "There is a "+i+".";
			count++;
		}
		count = 0;
		for (Item item: items)
		{   
			if(count < items.size())
				dsc += " There is a "+item+". ";
			else
				dsc += "There is a "+item+".";
			count++;
		}
		if(!GameState.instance().getVerbose())
		{
			for (Exit exit : exits) {
				dsc += "\n" + exit.describe();
			}
		}
		if (!light)
			dsc = "It's to dark to see.";
		return dsc+"\n";
	}

	public Room leaveBy(String dir) {
		for (Exit exit : exits) {
			if (exit.getDir().equals(dir)) {
				return exit.getDest();
			}
		}
		return null;
	}

	void addExit(Exit exit) {
		exits.add(exit);
	}

	void add(Item item)
	{
		this.items.add(item);
	}

	void remove(Item item)
	{
		this.items.remove(item);
	}

	Item getItemNamed(String name)
	{
		Item temp = null;
		for(Item i:items)
		{
			if (i.getPrimaryName().equals(name))
			{
				temp = i;
				break;
			}
		}
		return temp;
	}

	ArrayList<Item> getContents()
	{
		return this.items;

	}
	/**
	 * Add NPC too the room from dungeon file
	 * @param NPC take in NPC
	 */
	void addNPC(NPC NPC){
		this.NPC.add(NPC);
	}
	/**
	 * Remove NPC in the case that it dies or move to another location
	 * @param NPC take in NPC
	 */
	void removeNPC(NPC NPC){
		this.NPC.remove(NPC);
	}
	public NPC getNPC(String NPC)
	{
		NPC temp = null;
		for(NPC i:this.NPC)
		{
			if (i.getName().equals(NPC))
			{
				temp = i;
				break;
			}
		}
		return temp;
	}
	public boolean getLightable()
	{
		return lightable;
	}
	public void setLightable(boolean status)
	{
		this.lightable = status;
	}
	/**
	 * Change the room lighting.
	 * @param status take in the status
	 */
	public void setLight(boolean status){
		this.light = status;
	}
	/**
	 * Use when the trigger event happen to show the room title
	 * @param status take in the status
	 */
	public void setHideName(boolean status){
		this.hideName = status;
	}
}
