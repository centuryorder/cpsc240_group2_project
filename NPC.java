package Group_Project_Bork;

import java.util.Hashtable;
/**
 * Object class that hold the Non Player Character properties
 * @author Yohan Hendrawan
 * @version 11/09/16 
 */
public class NPC {
    private String name;
    private int health;
    private int armor;
    private boolean hostile = false;
    private boolean boss = false;
    private  Hashtable<String,String> message = new Hashtable<String,String>();
    
    public NPC(String name, int health, int armor, boolean hostile, boolean boss)
    {
    	this.name = name;
    	this.health = health;
    	this.armor = armor;
    	this.hostile = hostile;
    	this.boss = boss;
    }
    /**
     * Takes name as argument and associates it with npc
     * @param name takes in string name
     * @return
     */
    public void setName(String name){
        this.name = name;
    }
    public String getName()
    {
    	return this.name;
    }

    /**
     * Uses int health to determine health state of npc
     * @param health takes integer health
     * @return health
     */
    public void setHealth(int health){
        this.health = health;
    }

    /**
     * Returns if the NPC is alive or dead and uses health as reference
     * @param health is taken as an integer.

     */
    public boolean isAlive(int health){
    	if(health > 0)
    		return true;
    	else
    		return false;
    }

    /**
     * Returns boolean that determines whether or not an npc will act aggressively towards the player.
     * @return false by default
     */
    public boolean isHostile(){
        return hostile;
    }
    
    public void addMessage(String command, String message)
    {
    	this.message.put(command, message);
    }
    
    public String toString()
    {
    	return name;
    }
}
