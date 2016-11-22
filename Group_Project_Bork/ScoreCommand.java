package Group_Project_Bork;

/**
 * @author Stephen Well Son
 * @author Yohan Henrawan
 * @version 11/19/2016.
 */
public class ScoreCommand extends Command{
    public String execute(){
    	String score;
    	score = "".format("Score: %d Rank: %s%n",
    			GameState.instance().getScore(), GameState.instance().getRank());
        return score;
    }
}
