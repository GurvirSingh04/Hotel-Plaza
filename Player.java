import java.util.Set;
import java.util.HashMap;

/**
 * This class is part of the "Hotel Plaza" application. 
 * "Hotel Plaza" is a very simple, text based adventure game.
 *
 * The "Player" adds items picked up by the user and removes
 * once the user drops them.
 * 
 * @author Gurvir Singh (K23010952)
 * @version 2023.12.06
 */
public class Player
{
    private HashMap<String, Item> inventory;
    private int remainingWeight;
    private boolean hasKeyHead;
    private boolean hasKeyBlade;
    
    /**
     * Creates a Player object and initialise the inventory
     */
    public Player()
    {
        inventory = new HashMap<>();
        // the maximum weight the player can pickup.
        remainingWeight = 50;
        hasKeyHead = false;
        hasKeyBlade = false;
    }

    /**
     * @param  item The item to be added to the inventory
     * Adds the item to the inventory and checks if the item
     * is a component of the key.
     */
    public void addInventory(Item item)
    {
        String name = item.getName();
        
        if (name.equals("key-head"))
        {
            hasKeyHead = true;
        }
        else if (name.equals("key-blade"))
        {
            hasKeyBlade = true;
        }
        
        inventory.put(name, item);
        remainingWeight -= item.getWeight();
    }
    
    /**
     * @param  name The name of the item to be removed to the inventory
     * Drops the item to the inventory.
     * @return the dropped item
     */
    public Item dropItem(String name)
    {
        Set<String> keys = inventory.keySet();
        
        for(String item : keys) 
        {
            if (item.equals(name))
            {
                Item droppedItem = inventory.get(name);
                inventory.remove(name);
                remainingWeight += droppedItem.getWeight();
                return droppedItem;
            }   
        }
        
        System.out.println("You don't have this item");
        return null;
    }
    
    /**
     * Return a string describing the player's inventory, for example
     * "Inventory: sweets".
     * @return A string of items in the inventory.
     */
    public String getInventoryString()
    {
        String returnString = "Inventory:";
        Set<String> keys = inventory.keySet();
        
        for(String item : keys) 
        {
            returnString += " " + item;
        }
        return returnString;
    }
    
    /**
     * Return a string describing the player's remaining weight he can carry, for example
     * "Weight: 50".
     * @return A string of the player's remaining weight he can carry.
     */
    public String getWeightString()
    {
        return "Remaining weight: " + remainingWeight;
    }
    
    /**
     * Return the player's remaining weight he can carry,
     * for example 50.
     * @return player's remaining weight he can carry.
     */
    public int getRemainingWeight()
    {
        return remainingWeight;
    }
    
    /**
     * Checks if the user has the components of the key.
     * @return true, if he does, false otherwise
     */
    public boolean hasKeysComponents()
    {
        if (hasKeyBlade && hasKeyHead)
        {
            return true;
        }
        return false;
    }
}
