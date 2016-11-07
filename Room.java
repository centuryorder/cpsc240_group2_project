
package hendrawan_borkv3;

import java.util.ArrayList;
import java.util.Scanner;

import hendrawan_borkv3.GameState.IllegalSaveFormatException;
import hendrawan_borkv3.Item.NoItemException;

import java.io.IOException;
import java.io.PrintWriter;

public class Room {

	class NoRoomException extends Exception {}

	private String title;
	private String desc;
	private boolean beenHere;
	private ArrayList<Exit> exits;
	private ArrayList<Item> items;

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
			desc += lineOfDesc + "\n";
			lineOfDesc = s.nextLine();
		}

		// throw away delimiter
		if (!lineOfDesc.equals(Dungeon.SECOND_LEVEL_DELIM)) {
			throw new Dungeon.IllegalDungeonFormatException("No '" +
					Dungeon.SECOND_LEVEL_DELIM + "' after room.");
		}
	}

	public Room(Scanner s, Dungeon d) throws NoRoomException, 
	Dungeon.IllegalDungeonFormatException, NoItemException{this(s,d,true);}

	public Room(Scanner s, Dungeon d, boolean initState) throws NoRoomException,
	Dungeon.IllegalDungeonFormatException, NoItemException
	{
		init();
		title = s.nextLine();
		desc = "";
		if (title.equals(Dungeon.TOP_LEVEL_DELIM)) {
			throw new NoRoomException();
		}  

		String lineOfDesc = s.nextLine();
		String[] contents = lineOfDesc.split(" ");
		String header = contents[0];
		String[] content;
		if(contents.length > 1 && header.equals(Dungeon.ROOM_CONTENTS_MARKER))
		{
			if(initState != false)
			{
				content = contents[1].split(",");
				for(String item: content) 
				{	
					if(d.getItem(item) == null)     			
					{throw new Item.NoItemException("No item found");}
					else
						this.add(d.getItem(item));
				}
			}
			lineOfDesc =s.nextLine();
			while (!lineOfDesc.equals(Dungeon.SECOND_LEVEL_DELIM) &&
					!lineOfDesc.equals(Dungeon.TOP_LEVEL_DELIM)) {
				desc += lineOfDesc + "\n";
				lineOfDesc = s.nextLine();
			}
		}
		else
		{
			while (!lineOfDesc.equals(Dungeon.SECOND_LEVEL_DELIM) &&
					!lineOfDesc.equals(Dungeon.TOP_LEVEL_DELIM)) {
				if(!s.nextLine().equals(Dungeon.SECOND_LEVEL_DELIM))
					desc += lineOfDesc + "\n";
				else
					desc +=lineOfDesc;
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
		beenHere = false;
	}

	String getTitle() { return title; }

	void setDesc(String desc) { this.desc = desc; }

	/*
	 * Store the current (changeable) state of this room to the writer
	 * passed.
	 */
	void storeState(PrintWriter w) throws IOException {
		// At this point, nothing to save for this room if the user hasn't
		// visited it.
		if (!this.items.isEmpty()) {
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

	void restoreState(Scanner s, Dungeon d) throws IllegalSaveFormatException
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
			if (d.getInitState() ==false)
				for(String it: i)
					this.add(d.getItem(it));
			s.nextLine();   // consume end-of-room delimiter
		}

	}

	public String describe() {
		String description;
		if (beenHere) {
			description = title +"\n";
		} else {
			description = title + "\n" + desc;
		}
		int count=1;
		for (Item item: items)
		{   
			if(count < items.size())
				description += "There is a "+item+". ";
			else
				description += "There is a "+item+".";
			count++;
		}
		for (Exit exit : exits) {
			description += "\n" + exit.describe();
		}
		beenHere = true;
		return description;
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
}
