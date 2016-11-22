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
	public Die()
	{
	}
	public Die(NPC npc){
		this.npc = npc;
	}
	/**
	 * getScore takes the points integer to calculate the score.
	 * and also returns the score
	 *@param points takes the points integer.
	 * */
	void getScore(){
		Score = GameState.instance().getScore();
	}
	
	void getRank(){
		Rank = GameState.instance().getRank();
	}
	/**
	 * removeNpc removes npc
	 */
	void removeNPC(){
		GameState.instance().getAdventurersCurrentRoom().removeNPC(npc);
	}
	
	public void execute()
	{
		if (npc != null)
			removeNPC();
		else
		{
			getScore();
			getRank();
			System.out.printf("GAMEOVER!%n Score: %d Rank: %s",Score, Rank);
			System.exit(0);
		}
			
	}
}
