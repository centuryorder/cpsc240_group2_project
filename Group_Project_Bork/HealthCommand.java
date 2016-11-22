package Group_Project_Bork;
/**
 * 
 * @author Yohan Hendrawan
 * @version 11/21/16
 *
 */
public class HealthCommand extends Command{
	public String execute()
	{
		int health = GameState.instance().getHP();
		String msg = "";
		if (health > 0 && health <= 10)
			msg = "You are about to die!\n";
		else if (health > 10 && health <= 25)
			msg = "You feel really weak!\n";
		else if (health > 25 && health <= 50)
			msg = "You are critically wounded!\n";
		else if (health > 50 && health <= 75)
			msg = "You are wounded!\n";
		else if (health > 75 && health < 100)
			msg = "You got some scrapes and bruise.\n";
		else if (health == 100)
			msg = "You are healthy.\n";
		return msg;
	}
}
