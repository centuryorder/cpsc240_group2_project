package Group_Project_Bork;
/**
 * /**
 * @author Stephen Well Son
 * @version 11/8/2016.
 */

public class Die {
boolean dead = false;

    /**
     * Die simply returns whether dead is true or not
     * @param dead
     */
    public Die(boolean dead){
      dead= false;
    }
    /**
     * getScore takes the points integer to calculate the score.
     * and also returns the score
     *@param points takes the points integer.
     * */
    public int getScore(int points, int score){
		return score;

    }

    /**
     * removeNpc removes npc based on npc's hp
     * @return
     */
    public NPC removeNpc(){
		return null;

    }

    /**
     * endGame takes dead as an argument to determine whether the game should keep running or not (dead = close/true/)
     *  otherwise keep running/ false
     * @param dead take in dead
     * @return return a boolean value
     */
    public boolean endGame(boolean dead){
        return false;

}}
