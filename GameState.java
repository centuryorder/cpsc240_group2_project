package Group_Project_Bork;
import java.util.*;

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
	private HashMap<String, Item> equipment = new HashMap<String, Item>();
	private boolean twoHand;
	private Timer timer;
	private TimerTask daylight;
	public Combat currentCombat;
	
	
	/**
	 * instance is a singleton 
	 * @return GameState
	 */
	static synchronized GameState instance() {
		if (theInstance == null) {
			theInstance = new GameState();
		}
		return theInstance;
	}

	private GameState() {
		this.equipment.put("head", null);
		this.equipment.put("chest", null);
		this.equipment.put("leg", null);
		
	}

	/**
	 * Loads the game
	 * @param filename savefile to load
	 */
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

	/**
	 * Saves the game state
	 * @param saveName save file name
	 */
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

	/**
	 * gets the room the player is currently in
	 * @return the current room
	 */
	Room getAdventurersCurrentRoom() {
		return adventurersCurrentRoom;
	}

	/**
	 * sets the room the player is in
	 * @param room room player moves to
	 */
	void setAdventurersCurrentRoom(Room room) {
		adventurersCurrentRoom = room;
	}

	Dungeon getDungeon() {
		return dungeon;
	}
	
	/**
	 * gets names of items in inventory
	 * @return list of items in inventory
	 */
	ArrayList<String> getInventoryNames()
	{
		ArrayList<String> InventoryNames = new ArrayList<String>();
		if(this.inventory.size() != 0)
			for(Item i: inventory)
				if (i != null)
					InventoryNames.add(i.getPrimaryName());
		return InventoryNames;

	}

	/**
	 * add item to inventory
	 * @param item item to be added to inventory
	 */
	void addToInventory(Item item)
	{
		this.inventory.add(item);
	}

	/**
	 * removes item from inventory
	 * @param item item to be removed from inventory
	 */
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
	/**
	 *getEuipedName return list of name of equipped name and its type for saving purposes
	 * @return a list of name of equipped items
	 */
	public ArrayList getEquippedName(){
		ArrayList<Item> equipped = new ArrayList<Item>();
		return equipped;
		
	}
	/**
	 * Add item to the correct equipment slot
	 * if an item is already equip swap the item
	 * @param item take in item
	 */
	public void addItemToEquipped(Item item){
		
	}
	/**
	 * Remove item from the equipment slot
	 * @param item take in item
	 */
	public void removeItemFromEquipped(Item item){
		
	}
	/**
	 * Changes some room lighting using a timer.
	 * If its on it turn it off and vice versa.
	 */
	void changeRoomLighting(){
		
	}
	/**
	 * Take in the damage that is calculated by during combat
	 * @param wound take in wound
	 */
	public void reciveWound(Wound wound){
		
	}
}
