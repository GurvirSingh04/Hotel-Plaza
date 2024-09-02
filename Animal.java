
/**
 * This class is part of the "Hotel Plaza" application. 
 * "Hotel Plaza" is a very simple, text based adventure game.
 *
 * An "Animal" reprsents an animal in the scenery of the game.
 * The "Animal" can move on its own.
 * 
 * @author Gurvir Singh (K23010952)
 * @version 2023.12.06
 */
public class Animal
{
    private String name;
    private Room room;
    
    /**
     * Create an animal object. 
     * 
     * @param name The name of the animal.
     * @param room The room in which the animal is at the start of the game.
     */
    public Animal(String name, Room room)
    {
        this.name = name;
        this.room = room;
    }
    
    /**
     * Changes the room of the animal.
     * 
     * @param room  The room where the animal is in this instance.
     */
    public void setRoom(Room room)
    {
        this.room = room;
    }
    
    /**
     * @return The name of the animal
     * (the one that was defined in the constructor).
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * @return The room of the animal.
     */
    public Room getRoom()
    {
        return room;
    }
}
