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
	String Rank= "", Time="";
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
	void getTime(){
		Time = GameState.instance().getTimeSpan();
	}
	/**
	 * Execute Win event
	 */
	public void execute()
	{
		getScore();
		getRank();
		getTime();
		System.out.printf("GAMEOVER!%nScore: %d Rank: %s Time: %s",Score, Rank,Time);
		System.out.println("One journey end and another begin.");
		System.exit(0);
	}
}
