package Group_Project_Bork;
import java.util.*;
import java.io.*;
/**
 * Class that contain all the rooms, items, and NPC in the game
 * @author Yohan Hendrawan
 * @version 11/08/16
 */
public class Dungeon {

	public static class IllegalDungeonFormatException extends Exception {
		public IllegalDungeonFormatException(String e) {
			super(e);
		}
	}

	// Variables relating to both dungeon file and game state storage.
	public static String TOP_LEVEL_DELIM = "===";
	public static String SECOND_LEVEL_DELIM = "---";

	// Variables relating to dungeon file (.bork) storage.
	public static String ROOMS_MARKER = "Rooms:";
	public static String EXITS_MARKER = "Exits:";
	public static String ITEMS_MARKER = "Items:";

	// Variables relating to game state (.sav) storage.
	static String FILENAME_LEADER = "Dungeon file: ";
	static String ROOM_STATES_MARKER = "Room states:";
	static String ROOM_CONTENTS_MARKER = "Contents:";
	// Variable for the Dungeon
	private String name;
	private Room entry;
	private Hashtable<String,Room> rooms;
	private Hashtable<String,Item> items;
	private Hashtable<String,NPC> NPC;
	private String filename;
	private boolean initState;

	Dungeon(String name, Room entry) {
		init();
		this.filename = null;    // null indicates not hydrated from file.
		this.name = name;
		this.entry = entry;
	}

	/**
	 * Read from the .bork filename passed, and instantiate a Dungeon object
	 * based on it.
	 * @throws NoItemException 
	 */
	public Dungeon(String filename) throws FileNotFoundException,
	IllegalDungeonFormatException, Item.NoItemException {this(filename, true);}{
/**
		init();
		this.filename = filename;

		Scanner s = new Scanner(new FileReader(filename));
		name = s.nextLine();

		s.nextLine();   // Throw away version indicator.

		// Throw away delimiter.
		if (!s.nextLine().equals(TOP_LEVEL_DELIM)) {
			throw new IllegalDungeonFormatException("No '" +
					TOP_LEVEL_DELIM + "' after version indicator.");
		}

		// Throw away Rooms starter.
		if (!s.nextLine().equals(ROOMS_MARKER)) {
			throw new IllegalDungeonFormatException("No '" +
					ROOMS_MARKER + "' line where expected.");
		}


		try {
			// Instantiate and add first room (the entry).
			entry = new Room(s);
			add(entry);

			// Instantiate and add other rooms.
			while (true) {
				add(new Room(s));
			}
		} catch (Room.NoRoomException e) {  // end of rooms // }

		// Throw away Exits starter.
		if (!s.nextLine().equals(EXITS_MARKER)) {
			throw new IllegalDungeonFormatException("No '" +
					EXITS_MARKER + "' line where expected.");
		}

		try {
			// Instantiate exits.
			while (true) {
				Exit exit = new Exit(s, this);
			}
		} catch (Exit.NoExitException e) {  // end of exits // }

		s.close();*/
	}

	public Dungeon(String filename, boolean initState) throws IllegalDungeonFormatException,
	FileNotFoundException, Item.NoItemException
	{
		init();
		this.initState = initState;
		this.filename = filename;

		Scanner s = new Scanner(new FileReader(filename));
		name = s.nextLine();

		s.nextLine();   // Throw away version indicator.

		// Throw away delimiter.
		if (!s.nextLine().equals(TOP_LEVEL_DELIM)) {
			throw new IllegalDungeonFormatException("No '" +
					TOP_LEVEL_DELIM + "' after version indicator.");
		}
		// Throw away Items starter.
		if (!s.nextLine().equals(ITEMS_MARKER)) {
			throw new IllegalDungeonFormatException("No '" +
					ITEMS_MARKER + "' line where expected.");
		}
			try{
				while(true){
				this.add(new Item(s));
				}
			}
			catch(Item.NoItemException e){}
			//String line = s.nextLine();
			// Throw away Rooms starter.
			if (!s.nextLine().equals(ROOMS_MARKER)) {
				throw new IllegalDungeonFormatException("No '" +
						ROOMS_MARKER + "' line where expected.");
			}


			try {
				// Instantiate and add first room (the entry).
				entry = new Room(s, this, initState);
				add(entry);

				// Instantiate and add other rooms.
				while (true) {
					add(new Room(s, this, initState));
				}
			}
			catch(Room.NoRoomException e){}

			// Throw away Exits starter.
			if (!s.nextLine().equals(EXITS_MARKER)) {
				throw new IllegalDungeonFormatException("No '" +
						EXITS_MARKER + "' line where expected.");
			}

			try {
				// Instantiate exits.
				while (true) {
					Exit exit = new Exit(s, this);
				}
			} catch (Exit.NoExitException e) {  /* end of exits */ }

			s.close();
	}

	// Common object initialization tasks, regardless of which constructor
	// is used.
	private void init() {
		rooms = new Hashtable<String,Room>();
		items = new Hashtable<String,Item>();
	}

	/*
	 * Store the current (changeable) state of this dungeon to the writer
	 * passed.
	 */
	void storeState(PrintWriter w) throws IOException {
		w.println(FILENAME_LEADER + getFilename());
		w.println(ROOM_STATES_MARKER);
		for (Room room : rooms.values()) {
			room.storeState(w);
		}
		w.println(TOP_LEVEL_DELIM);
	}

	/*
	 * Restore the (changeable) state of this dungeon to that reflected in the
	 * reader passed.
	 */
	void restoreState(Scanner s) throws GameState.IllegalSaveFormatException {

		// Note: the filename has already been read at this point.

		if (!s.nextLine().equals(ROOM_STATES_MARKER)) {
			throw new GameState.IllegalSaveFormatException("No '" +
					ROOM_STATES_MARKER + "' after dungeon filename in save file.");
		}

		String roomName = s.nextLine();
		while (!roomName.equals(TOP_LEVEL_DELIM)) {
			getRoom(roomName.replace(":","")).restoreState(s,this);
			roomName = s.nextLine();
		}
	}

	public Room getEntry() { return entry; }
	public String getName() { return name; }
	public String getFilename() { return filename; }
	public void add(Room room) { rooms.put(room.getTitle(),room); }

	public Room getRoom(String roomTitle) {
		return rooms.get(roomTitle);
	}
	public Item getItem(String primaryName)
	{
		return items.get(primaryName);
	}

	public void add(Item item)
	{
		items.put(item.getPrimaryName(), item);
	}
	
	public boolean getInitState()
	{
		return this.initState;
	}
	/**
	 * Add NPC into hashtable for easier placement throughout
	 * the dungeon
	 * @param NPC take in NPC class
	 */
	public void addNPC(NPC NPC){
		this.NPC.put(NPC.getName(), NPC);
	}
}
