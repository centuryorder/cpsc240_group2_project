package Group_Project_Bork;

public class SaveCommand extends Command{
	private String Filename;
	public SaveCommand(String f) {
		this.Filename = f;
	}
//Test @Swilson
	public String execute() {
		if ((Filename.equals(""))) {
            try {
                GameState.instance().store();
                return "Data saved to " + GameState.DEFAULT_SAVE_FILE + 
                    GameState.SAVE_FILE_EXTENSION + ".\n";
            } catch (Exception e) {
                System.err.println("Couldn't save!");
                e.printStackTrace();
                return "";
            }
	}
		else
		{
			try {
                GameState.instance().store(this.Filename);
                return "Data saved to " + Filename + 
                    GameState.SAVE_FILE_EXTENSION + ".\n";
            } catch (Exception e) {
                System.err.println("Couldn't save!");
                e.printStackTrace();
                return "";
            }
		}

	}
}
