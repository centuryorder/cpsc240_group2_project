package Group_Project_Bork;
import java.util.*;

import hendrawan_borkv3.Item.NoItemException;

import java.io.*;
/**
 * 
 * @author Yohan Hendrawan
 * @version 11/07/2016
 *
 */
public class GameState {
	
	public static class IllegalSaveFormatException extends Exception {
		public IllegalSaveFormatException(String e) {
			super(e);
		}
	}

	static String DEFAULT_SAVE_FILE = "bork_save";
	static String SAVE_FILE_EXTENSION = ".sav";
	static String SAVE_FILE_VERSION = "Bork v3.0 save data";
	static String ADVENTURER_LEADER = "Adventurer:";
	static String CURRENT_ROOM_LEADER = "Current room: ";
	static String INVENTORY_LEADER = "Inventory: ";

	private static GameState theInstance;
	private Dungeon dungeon;
	private Room adventurersCurrentRoom;
	private ArrayList<Item> inventory = new ArrayList<Item>();
	
	//Adventurer Status
	private int HP, Armor, Speed, Capacity, Damage;
	private Score Score;
	private Item headGear, chestGear,legGear, accessoryOne, accessoryTwo, rightHand, leftHand;
	private boolean twoHand;
	private Timer timer;
	private TimerTask daylight;
	public Combat currentCombat;
	
	
	/**
	 * instance is a singleton 
	 * @return
	 */
	static synchronized GameState instance() {
		if (theInstance == null) {
			theInstance = new GameState();
		}
		return theInstance;
	}

	private GameState() {
	}

	void restore(String filename) throws FileNotFoundException,
	IllegalSaveFormatException, Dungeon.IllegalDungeonFormatException, Item.NoItemException {

		Scanner s = new Scanner(new FileReader(filename));

		if (!s.nextLine().equals(SAVE_FILE_VERSION)) {
			throw new IllegalSaveFormatException("Save file not compatible.");
		}

		String dungeonFileLine = s.nextLine();

		if (!dungeonFileLine.startsWith(Dungeon.FILENAME_LEADER)) {
			throw new IllegalSaveFormatException("No '" +
					Dungeon.FILENAME_LEADER + 
					"' after version indicator.");
		}

		dungeon = new Dungeon(dungeonFileLine.substring(
				Dungeon.FILENAME_LEADER.length()),false);
		dungeon.restoreState(s);
		//throw away adventurer header
		s.nextLine();

		String currentRoomLine = s.nextLine();
		adventurersCurrentRoom = dungeon.getRoom(
				currentRoomLine.substring(CURRENT_ROOM_LEADER.length()));
		String inv = s.nextLine();
		String[] line = inv.split(":");
		if (line[0].equals("Inventory"))
		{
			String[] content = line[1].trim().split(",");
			if (getDungeon().getInitState() == false && content.length > 0)
			{
				for(String i:content)
				{
					this.addToInventory(dungeon.getItem(i));
				}
			}
		}
	}

	void store() throws IOException {
		store(DEFAULT_SAVE_FILE);
	}

	void store(String saveName) throws IOException {
		String filename = saveName + SAVE_FILE_EXTENSION;
		PrintWriter w = new PrintWriter(new FileWriter(filename));
		w.println(SAVE_FILE_VERSION);
		dungeon.storeState(w);
		w.println(ADVENTURER_LEADER);
		w.println(CURRENT_ROOM_LEADER + 
				getAdventurersCurrentRoom().getTitle());
		String inv ="";
		if((this.inventory.size() != 0) && (this.getInventoryNames().size() != 0))
		{
			int count = 1;
			for(String s: this.getInventoryNames())
			{
				
				if (count < this.getInventoryNames().size())
				inv += s+",";
				else
				{
					inv += s;
				}
				count++;
			}
			w.println(INVENTORY_LEADER + inv);
		}
		else
			w.println(INVENTORY_LEADER);

		w.close();
	}

	void initialize(Dungeon dungeon) {
		this.dungeon = dungeon;
		adventurersCurrentRoom = dungeon.getEntry();
	}

	Room getAdventurersCurrentRoom() {
		return adventurersCurrentRoom;
	}

	void setAdventurersCurrentRoom(Room room) {
		adventurersCurrentRoom = room;
	}

	Dungeon getDungeon() {
		return dungeon;
	}
	ArrayList<String> getInventoryNames()
	{
		ArrayList<String> InventoryNames = new ArrayList<String>();
		if(this.inventory.size() != 0)
			for(Item i: inventory)
				if (i != null)
					InventoryNames.add(i.getPrimaryName());
		return InventoryNames;

	}

	void addToInventory(Item item)
	{
		this.inventory.add(item);
	}

	void removeFromInventory(Item item)
	{
		this.inventory.remove(item);
	}

	Item getItemInVicinityNamed(String name) throws Item.NoItemException
	{
		if(this.getAdventurersCurrentRoom().getItemNamed(name) !=null
				&& this.getAdventurersCurrentRoom().getItemNamed(name).getPrimaryName().equals(name))
			return this.getAdventurersCurrentRoom().getItemNamed(name);
		else
			return null;

	}

	Item getItemFromInventoryNamed(String name) throws Item.NoItemException
	{	
		Item temp = null;
		if(this.inventory.size() != 0)
			for(Item i: this.inventory)
			{
				if(i != null && i.getPrimaryName().equals(name))
				{
					temp = i;
					break;
				}

			}

		return temp;

	}
	/**
	 * teleportTo used to change the adventurersCurrentRoom during teleportation event
	 * @param room take in room
	 */
	public void teleportTo(Room room){
		
	}
}
