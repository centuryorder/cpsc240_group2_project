package Group_Project_Bork;
import java.util.*;
import Group_Project_Bork.Dungeon.IllegalDungeonFormatException;
import java.io.*;
/**
 * Class that keep track the status of the game and adventurer
 * @author Yohan Hendrawan
 * @author Stephen Willson
 * @version 12/07/2016
 */
public class GameState {

	public static class IllegalSaveFormatException extends Exception {
		public IllegalSaveFormatException(String e) {
			super(e);
		}
	}
	static String DEFAULT_SAVE_FILE = "bork_save";
	static String SAVE_FILE_EXTENSION = ".sav";
	static String SAVE_FILE_VERSION = "Group Bork v3.0 save data";
	static String ADVENTURER_LEADER = "Adventurer:";
	static String CURRENT_ROOM_LEADER = "Current room: ";
	static String INVENTORY_LEADER = "Inventory: ";
	static String HP_LEADER = "HP: ";
	static String WEIGHT_LEADER = "Weight: ";

	private static GameState theInstance;
	private Dungeon dungeon;
	private Room adventurersCurrentRoom;
	private ArrayList<Item> inventory = new ArrayList<Item>();

	//Adventurer Status
	private int HP = 100, Armor, Speed = 10, Damage, currentWeight = 0;
	private int Score, TimeSpan;
	private final int MAXWEIGHT = 55;
	private Item headGear, chestGear,legGear, accessoryOne, accessoryTwo, rightHand, leftHand;
	private HashMap<String, Item> equipment = new HashMap<String, Item>();
	private boolean twoHand;
	private Timer timer;
	private Combat currentCombat;
	private ArrayList<Wound> wound= new ArrayList<Wound>();	
	private Hashtable<Integer,String> Rank; 
	private boolean light = false;
	private boolean verboseMode = false;

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
		this.timer = new Timer();
		this.timer.schedule(new addTimeSpan(), 0, 1*1000);
	}

	/**
	 * Loads the game
	 * @param filename savefile to load
	 * @throws IllegalDungeonFormatException 
	 */
	void restore(String filename) throws FileNotFoundException,
	IllegalSaveFormatException, Item.NoItemException, IllegalDungeonFormatException {

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
					if(!i.equals(""))
						this.addToInventory(dungeon.getItem(i));
				}
			}
		}
		String[] HP = s.nextLine().split(":");
		if (HP[0].trim().equals("HP"))
		{
			int hp = Integer.parseInt(HP[1].trim());
			this.setHp(hp);
		}
		//System.out.println(this.HP);
		String[] elapse = s.nextLine().split(":");
		if(elapse[0].trim().equals("Elapse"))
		{
			int time = Integer.parseInt(elapse[1].trim());
			this.TimeSpan = time;
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

		w.println(HP_LEADER + this.HP);
		w.println("Elapse:"+this.TimeSpan);
		w.println(WEIGHT_LEADER + this.currentWeight);
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
		if(item.getLight() == true)
			this.light = true;
		currentWeight += item.getWeight();
	}

	/**
	 * removes item from inventory
	 * @param item item to be removed from inventory
	 */
	void removeFromInventory(Item item)
	{
		this.inventory.remove(item);
		currentWeight -= item.getWeight();
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
		this.adventurersCurrentRoom = room;
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
	class addTimeSpan extends TimerTask{
		public void run()
		{
			TimeSpan += 1;
			if((TimeSpan % 60000) == 0)
			{
				GameState.instance().changeRoomLighting();
			}
		}
	}
	/**
	 * Changes some room lighting using a timer.
	 * If its on it turn it off and vice versa.
	 */
	public void changeRoomLighting(){
		instance().getDungeon().changeRoomLighting();
	}
	/**
	 * Take in the damage that is calculated
	 * @param wound 
	 */
	public void recieveWound(Wound wound){
		this.HP -= wound.getDamage();
		if (this.HP >100)
			this.HP = 100;
		else if(this.HP <= 0)
			new Die().execute();
	}
	/**
	 * Update Score
	 */
	public void recieveScore(Score score){
		this.Score += score.getScore();
	}
	/**
	 * Save Rank from a .bork file
	 * @param tier
	 * @param Rank
	 */
	public void addRank(int tier,String Rank)
	{
		this.Rank.put(tier, Rank);
	}
	/**
	 * Return a string of rank in a predetermine score range.
	 * Either used an input form a .bork file or used the default rank.
	 * @return rank
	 */

	public int getcurrentWeight()
	{
		return currentWeight;
	}
	public int getMAXWEIGHT()
	{
		return MAXWEIGHT;
	}

	public String getRank()
	{
		if (this.Rank!= null)
		{
			if (this.Score > 0 && this.Score <= 100)
			{
				return this.Rank.get(1);
			}
			else if (this.Score >100 && this.Score <= 300)
			{
				return this.Rank.get(2);
			}
			else if (this.Score > 300 && this.Score <= 900)
			{
				return this.Rank.get(3);
			}
			else if( this.Score >900)
			{
				return this.Rank.get(4);
			}
			else
				return "No Body.";
		}
		else
		{
			if (this.Score > 0 && this.Score <= 100)
			{
				return "Curious Person";
			}
			else if (this.Score >10 && this.Score <= 300)
			{
				return "Adventurer";
			}
			else if (this.Score > 30 && this.Score <= 900)
			{
				return "Explorer";
			}
			else if( this.Score >900)
			{
				return "Beater";
			}
			else
				return "No Body.";
		}
	}
	/**
	 * Set HP
	 * @param hp
	 */
	void setHp(int hp)
	{
		this.HP = hp;
	}
	/**
	 * Return the current Score
	 * @return Score
	 */
	public int getScore()
	{
		return this.Score;
	}
	/**
	 * Return Current HP
	 * @return HP
	 */
	public int getHP()
	{
		return this.HP;
	}

	public boolean getVerbose()
	{
		return this.verboseMode;
	}

	public void setVerbose()
	{
		if(verboseMode == false)
		{
			verboseMode = true;
		}
		else
			verboseMode = false;
	}
	public String getTimeSpan()
	{
		String Time="";
		int conv = (60*60);
		int Hours = TimeSpan / conv;
		int Minutes = ((TimeSpan-(Hours*conv)) % conv / (60));
		int Seconds = ((TimeSpan-(Hours*conv)-(Minutes*60))% conv);
		Time = "".format("%02d:%02d:%02d", Hours, Minutes, Seconds);
		return Time;
		
	}
	public boolean getLight()
	{
		return this.light;
	}
}
