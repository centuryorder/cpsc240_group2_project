package Group_Project_Bork;
/**
 * 
 * @author Yohan Hendrawan
 * @version 11/08/16
 *
 */
public class Wound {
	int Damage;
	/**
	 * Wound take item event damage and apply to adventurer.
	 * Healing by item is show as negative damage.
	 * @param damage
	 */
	public Wound(int damage){
		this.Damage = damage;
	}
	/**
	 * Wound take calculated damage from Combat class and apply to NPC.
	 * Healing by item is show as negative damage.
	 * @param damage
	 * @param target
	 */
	public Wound(int damage, NPC target){
		this.Damage = damage;
	}
	/**
	 * Wound take calculated damage from Combat class or healing by NPC.
	 * and apply to adventurer.
	 * Healing by item is show as negative damage.
	 * @param damage
	 * @param instance
	 */
	public Wound (int damage, GameState instance){
		this.Damage =damage;
	}
}
