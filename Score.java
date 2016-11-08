package Group_Project_Bork;

import java.util.Hashtable;

/**
 * @author Stephen Well Son
 * @version 11/8/2016.
 */

public class Score {
int points;
    private Hashtable<int, String> rank;

    Score (){

    }

    /**
     *setRank takes points to calculate a rank.
     * @param rank takes the Hashtable rank.
     * @param points takes the points integer.
     */
    public Hashtable setRank(int points, Hashtable rank){
        this.rank = rank;
        return rank;
    }

    /**
     * getRank takes in Score to get the rank.
     * @param Score takes the Score int.
     * @return
     */
    public String getRank(int Score){

    }

/**
 * getScore takes the points integer to calculate the score.
 * and also returns the score
 *@param points takes the points integer.
 * */
    public int getScore(int points, int score){

    }
}
