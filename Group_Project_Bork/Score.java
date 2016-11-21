package Group_Project_Bork;

import java.util.Hashtable;

/**
 * Class that is used to keep track of score and hold a set of ranking titles
 * @author Stephen Well Son
 * @author Yohan Hendrawan
 * @version 11/21/16.
 */
public class Score extends Event{
	private int score;
	
	/**
	 * Constructor to take in score from .bork file
	 * @param score
	 */
	public Score (int score){ this.score = score;}
	
	/**
	 * Return the score contain in the score class
	 * @return score
	 */
	public int getScore()
	{
		return this.score;
	}
	
	public void execute() {
		GameState.instance().recieveScore(this);
	}
	
}
