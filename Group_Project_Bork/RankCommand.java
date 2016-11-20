package Group_Project_Bork;

/**
 * Created by Stephen Well Son on 11/19/2016.
 */
public class RankCommand extends Command{
    public String execute(){
        return GameState.instance().getRank();
    }
}
