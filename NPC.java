package Group_Project_Bork;

import java.util.Hashtable;
/**
 * Object class that hold the Non Player Character properties
 * @author Yohan Hendrawan
 * @version 11/09/16 
 */
public class NPC {
    String name;
    int health;
    int armor;
    boolean hostile = false;
    boolean boss = false;
    Hashtable message;

    /**
     * Takes name as argument and associates it with npc
     * @param name takes in string name
     * @return
     */
    public String getName(String name){
        this.name = name;
        return name;
    }

    /**
     * Uses int health to determine health state of npc
     * @param health takes integer health
     * @return health
     */
    public int getHealth(int health){
        this.health = health;
        return health;
    }

    /**
     * Returns if the NPC is alive or dead and uses health as reference
     * @param health is taken as an integer.

     */
    public boolean isAlive(int health){

        return true;
    }

    /**
     * Returns boolean that determines whether or not an npc will act aggressively towards the player.
     * @return false by default
     */
    public boolean isHostile(){
        return false;
    }
}
