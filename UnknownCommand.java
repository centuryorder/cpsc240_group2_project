package Group_Project_Bork;

public class UnknownCommand extends Command {

	private String bogusCommand;

	public UnknownCommand (String bogusCommand)
	{
		this.bogusCommand = bogusCommand;
	}

	public String execute() {
		if(CommandFactory.ITEM_COMMANDS.contains(bogusCommand))
			return bogusCommand +" what?\n";
		else
			return "I don't understand " + this.bogusCommand + ".\n";
	}

}
