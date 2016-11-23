package Group_Project_Bork;
/**
 * Triggers the end game event if adventurer dies.
 * @author Stephen Well Son
 * @author Yohan Hendrawan
 * @version 11/21/16.
 * Class that kill off NPC or adventurer.
 */

public class Die extends Event{
	int Score = 0;
	String Rank= "";
	NPC npc = null;
	/**
	 * Constructor
	 */
	public Die()
	{
	}
	/**
	 * Constructor for killing NPC
	 * @param npc NPC to kill
	 */
	public Die(NPC npc){
		this.npc = npc;
	}
	/**
	 * Get Score from GameState
	 * */
	void getScore(){
		Score = GameState.instance().getScore();
	}
	/**
	 * Get Rank from GameState
	 */
	void getRank(){
		Rank = GameState.instance().getRank();
	}
	/**
	 * Remove NPC
	 */
	void removeNPC(){
		GameState.instance().getAdventurersCurrentRoom().removeNPC(npc);
	}
	/**
	 * Execute Die event
	 */
	public void execute()
	{
		if (npc != null)
			removeNPC();
		else
		{
			getScore();
			getRank();
			System.out.printf("GAMEOVER!%nScore: %d Rank: %s",Score, Rank);
			System.exit(0);
		}
			
	}
}
