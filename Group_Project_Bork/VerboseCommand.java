package Group_Project_Bork;

public class VerboseCommand extends Command {
	private boolean verbose;
	public VerboseCommand(){this.verbose = GameState.instance().getVerbose();}
	public String execute() {
		GameState.instance().setVerbose();
		String msg = "";
		if(verbose == false)
			msg = "Verbose Off";
		else
			msg = "Verbode On";
		return msg+"\n";
	}

}
