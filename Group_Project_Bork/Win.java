package Group_Project_Bork;
/**
 * Class that trigger the win event
 * @author Yohan Hendrawan
 * @author Stephen Well Son 
 * @version 11/7/16.
 */
// Win
public class Win extends Event{
	int score;
	int Score = 0;
	String Rank= "";
	/**
	 * Constructor
	 */
	public Win(){
	}
	/**
	 * Get Score from GameState
	 */
	void getScore(){
		Score = GameState.instance().getScore();
	}
	/**
	 * Get Score from GameState
	 */
	void getRank(){
		Rank = GameState.instance().getRank();
	}
	/**
	 * Execute Win event
	 */
	public void execute()
	{
		getScore();
		getRank();
		System.out.printf("You Win!%nScore: %d Rank: %s",Score, Rank);

		System.exit(0);
	}
}
