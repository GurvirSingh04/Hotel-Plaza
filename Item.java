
/**
 * This class is part of the "Hotel Plaza" application. 
 * "Hotel Plaza" is a very simple, text based adventure game.
 *
 * An "Item" reprsents the items in the rooms of the game and
 * carried by the player.
 *
 * @author Gurvir Singh (K23010952)
 * @version 2023.12.06
 */
public class Item
{
    private String name;
    private int weight;

    /**
     * Create an item object. 
     * 
     * @param name The name of the item.
     * @param weight The weight of the item.
     */    
    public Item(String name, int weight)
    {
        this.name = name;
        this.weight = weight;
    }
    
    /**
     * @return The name of the item
     * (the one that was defined in the constructor).
     */
    public String getName() 
    {
        return name;
    }
    
    /**
     * @return The weight of the animal
     * (the one that was defined in the constructor).
     */
    public int getWeight() 
    {
        return weight;
    }
   
}
