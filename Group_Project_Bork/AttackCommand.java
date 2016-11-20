package Group_Project_Bork;
/**
 * Command class that activates combat event
 * @author Yohan Hendrawan
 * @Version 11/09/16
 */
public class AttackCommand extends Command{
	String Noun;
	String Verb;
	String Target;
	/**
	 * Take in three parse string ignoring "on"
	 * @param noun
	 * @param verb
	 * @param target
	 */
	public AttackCommand(String noun, String verb, String target){
		this.Noun = noun;
		this.Verb = verb;
		this.Target = target;
	}
	/**
	 * Find if target exist and instantiate Combat class
	 * and print appropriate combat message
	 * @return
	 */
	public String execute(){
		return Target;
	}
	
}
