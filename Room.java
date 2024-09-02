import java.util.Set;
import java.util.HashMap;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "Hotel Plaza" application. 
 * "Hotel Plaza" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * A "Room" stores a reference to items in the room, some of which can be
 * picked up and dropped.
 * 
 * @author  Michael Kölling, David J. Barnes and Gurvir Singh (K23010952)
 * @version 2023.12.06
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private HashMap<String, Item> items;
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "a corridor".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        items = new HashMap<>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
    /**
     * Define an item in this room.
     * @param name The name of the item.
     * @param weight  The weight of the item.
     */
    public void setItem(String name, int weight) 
    {
        Item newItem = new Item(name, weight);
        items.put(name, newItem);
    }
    
    /**
     * Removes a pickable item from the room
     *     
     * @return A pickable item or <null>
     */
    public Item removeItem(String name)
    {
        Set<String> keys = items.keySet();
        for(String item : keys) {
            if (item.equals(name))
            {
                Item pickedItem = items.get(name);
                if (pickedItem.getWeight() == 1000)
                {
                    System.out.println("This item is too heavy, you can't pick this up");
                    return null;
                }
                items.remove(name);
                return pickedItem;
            }
        }
        System.out.println("This item isn't here");
        return null;    
    }
    
    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    private String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the reception.
     *     Exits: east south north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + "\n" + getExitString() + "\n" + getItemString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }
    
    /**
     * Return a string describing the items in the room, for example
     * "Items: key-head key-blade".
     * @return Details of the room's items.
     */
    public String getItemString()
    {
        String returnString = "Items:";
        Set<String> keys = items.keySet();
        for(String item : keys) {
            returnString += " " + item;
        }
        return returnString;
    }
    
    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
}

