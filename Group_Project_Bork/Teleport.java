package Group_Project_Bork;
import java.util.Random;
import java.util.Set;
/**
 * Teleport event that teleport player to another location
 * either randomly or to a targeted location
 * @author Yohan Hendrawan
 * @version 11/22/16
 */
public class Teleport extends Event{
	private String target = null;
	private String NPC = null;
	/**
	 * Constructor for random teleport
	 */
	public Teleport(){}
	/**
	 * Constructor for targeted teleport
	 * @param target
	 */
	public Teleport(String target)
	{
		this.target = target;
	}
	public Teleport(String NPC, String target)
	{
		this.NPC = NPC;
		this.target = target;
	}
	/**
	 * Execute the teleport
	 */
	public void execute()
	{
		int roomCount = GameState.instance().getDungeon().getRoomCount();
		Random RNG = new Random();
		int r = RNG.nextInt(roomCount);
		Set<String> rooms = GameState.instance().getDungeon().getRooms();
		String temp ="";
		int count = 0;
		if (this.target !=null && this.NPC != null)
		{
			NPC npc = GameState.instance().getDungeon().getNPC(NPC);
			GameState.instance().getAdventurersCurrentRoom().removeNPC(npc);
			GameState.instance().getDungeon().getRoom(target).addNPC(npc);
		}
		else if (this.target != null && this.NPC == null)
			temp = this.target;
		else
		{
			for (String i: rooms)
			{
				temp = i;
				if(count == r)
					break;
				count++;
			}
		}
		Room telTo = GameState.instance().getDungeon().getRoom(temp);

		GameState.instance().teleportTo(telTo);
		System.out.println(telTo.lookDescribe());
	}
}