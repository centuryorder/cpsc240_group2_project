package Group_Project_Bork;
/**
 * Class that contain damage that affect NPC or adventurer
 * @author Yohan Hendrawan
 * @version 11/08/16
 * 
 */
public class Wound extends Event{
	private int Damage = 0;
	private Item item = null;
	private NPC npc = null;
	/**
	 * Wound take item event damage and apply to adventurer.
	 * Healing by item is show as negative damage.
	 * @param damage
	 */
	public Wound(int damage, Item item){
		this.Damage = damage;
		this.item = item;
	}
	/**
	 * Wound take calculated damage from Combat class and apply to NPC.
	 * Healing by item is show as negative damage.
	 * @param damage
	 * @param target
	 */
	public Wound(int damage, NPC target){
		this.Damage = damage;
		this.npc = target;
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
	
	public int getDamage(){return this.Damage;}
	
	public void execute() {
		if(npc != null)
			npc.recieveWound(this);
		else
			GameState.instance().recieveWound(this);
	}
}
