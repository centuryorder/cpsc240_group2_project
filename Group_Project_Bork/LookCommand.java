package Group_Project_Bork;
/**
 * Allow adventurer to look into the current room's description
 * @author Yohan Hendrawan
 * @version 11/21/16
 *
 */
public class LookCommand extends Command{
	/**
	 * Print out current room description
	 */
	public String execute()
	{
		return GameState.instance().getAdventurersCurrentRoom().lookDescribe();
	}
}
