package Group_Project_Bork;

public class NPCSpecificCommand extends Command {

	private String msg = "No one here!";
	private String verb, noun;
	public NPCSpecificCommand(String v, String n)
	{
		this.verb = v;
		this.noun = n;
	}
	
	public String execute() {
		NPC temp = GameState.instance().getAdventurersCurrentRoom().getNPC(noun);
		
		if (temp != null)
			msg = temp.getName()+": "+temp.getMessageForVerb(verb);
		if (msg != null)
			return msg+"\n";
		else
			return "Can't do that!\n";
	}

}
