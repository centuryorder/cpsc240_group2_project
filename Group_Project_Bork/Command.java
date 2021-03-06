
package Group_Project_Bork;
/**
 * Abtract class for the other command classes
 * @author Yohan Hendrawan
 * @version BorkV3
 *
 */
// For now, only direction commands. If the "direction" is bogus, then this
// effectively doubles as an UnknownCommand (to be a subclass later).
public abstract class Command {

	private String dir;     // for now, this class is only for direction 
	// commands.
	/**
    Command(String dir) {
        this.dir = dir;
    }*/

	public abstract String execute(); /**
    {

        if (dir.equals("save")) {
            try {
                GameState.instance().store();
                return "Data saved to " + GameState.DEFAULT_SAVE_FILE + 
                    GameState.SAVE_FILE_EXTENSION + ".\n";
            } catch (Exception e) {
                System.err.println("Couldn't save!");
                e.printStackTrace();
                return "";
            }
        } else if (CommandFactory.MOVEMENT_COMMANDS.contains(dir)) {
            Room currentRoom = 
                GameState.instance().getAdventurersCurrentRoom();
            Room nextRoom = currentRoom.leaveBy(dir);
            if (nextRoom != null) {  // could try/catch here.
                GameState.instance().setAdventurersCurrentRoom(nextRoom);
                return "\n" + nextRoom.describe() + "\n";
            } else {
                return "You can't go " + dir + ".\n";
            }
        }
        return "Unknown command '" + dir + "'?\n";
    }*/
}
