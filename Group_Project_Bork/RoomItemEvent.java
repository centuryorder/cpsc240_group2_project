package Group_Project_Bork;

import Group_Project_Bork.Item.NoItemException;

public class RoomItemEvent extends Event {
	private Item item;
	private Room room;
	private Event event;
	public RoomItemEvent(Item i, Room r, Event e)
	{
		this.item = i;
		this.room = r;
		this.event = e;
	}
	public void execute() {
		try {
			if(GameState.instance().getAdventurersCurrentRoom() == room && GameState.instance().getItemFromInventoryNamed(item.getPrimaryName()) != null)
			{
				GameState.instance().removeFromInventory(item);
				event.execute();
			}
			else if(GameState.instance().getAdventurersCurrentRoom() == room && GameState.instance().getItemInVicinityNamed(item.getPrimaryName()) != null)
			{
				GameState.instance().removeItemFromEquipped(item);
				event.execute();
			}
		} catch (NoItemException e) {
			e.printStackTrace();
		}

	}

}
