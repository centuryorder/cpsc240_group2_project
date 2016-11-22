package Group_Project_Bork;

import java.util.Random;
import java.util.Set;

public class Teleport extends Event{
	public void execute()
	{
		int roomCount = GameState.instance().getDungeon().getRoomCount();
		Random RNG = new Random();
		int r = RNG.nextInt(roomCount)+1;
		String[] rooms = GameState.instance().getDungeon().getRooms();
		Room teleportTo = GameState.instance().getDungeon().getRoom(rooms[r]);
	}
}