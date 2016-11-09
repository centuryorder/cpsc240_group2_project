package Group_Project_Bork;

import java.util.Hashtable;

public class Combat {
	private NPC targetNPC;
	private int targetSpeed, targerArmor, targetDamage, advSpeed, advArmor, advDamage;
	private Hashtable<String, String> battleMessage;
	private static Combat theInstance;

	/**
	 * A singleton Combat instance
	 * @return an instance of Combat if there is none exist
	 */
	static synchronized Combat instance(){
		if (theInstance == null) {
			theInstance = new Combat();
		}
		return theInstance;
	}
	
	/**
	 * Calculate the damage done and send it to the combatants
	 * @return wound to the combatants
	 */
	Wound calculateDamage(){
		return null;
		
	}
	
	/**
	 * Check the health of both combatants
	 */
	void checkHealth(){
		
	}
	
	/**
	 * get the stats of both combatant for damage calculation
	 */
	void getStats(){
		
	}
	
	/**
	 * Kill of the combatant if their hp hit 0
	 * @return die if the combatant die
	 */
	Die kill(){
		return null;
	}
}
