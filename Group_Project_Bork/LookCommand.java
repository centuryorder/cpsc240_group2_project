package Group_Project_Bork;
/**
 * 
 * @author Yohan Hendrawan
 * @version 11/21/16
 *
 */
public class LookCommand extends Command{
	public String execute()
	{
		return GameState.instance().getAdventurersCurrentRoom().lookDescribe();
	}
}
