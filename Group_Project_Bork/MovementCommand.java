package Group_Project_Bork;
/**
 * Command that control movement of adventurer in the dungeon
 * @author Yohan Hendrawan
 * @version BorkV3
 */
public class MovementCommand extends Command{
	private String dir;
	MovementCommand(String dir) {
		this.dir = dir;
	}
	
	public String execute()
	{
		if (CommandFactory.MOVEMENT_COMMANDS.contains(dir)) {
            Room currentRoom = 
                GameState.instance().getAdventurersCurrentRoom();
            Room nextRoom = currentRoom.leaveBy(dir);
			Exit exit = currentRoom.getExit(dir);
			//make lock in exit class
			if (!exit.getLock()){
            if (nextRoom != null) {  // could try/catch here.
                GameState.instance().setAdventurersCurrentRoom(nextRoom);
                return nextRoom.describe() + "\n";
            }
            else if(exit.getLock()){
				 return "Entrance Locked!";

			}
            else {
                return "You can't go " + dir + ".\n";
            }
		}}
		return "Unknown Command!";
	}

}
