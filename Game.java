import java.util.Stack;
import java.util.ArrayList;
import java.util.Random;

/**
 *  This class is the main class of the "Hotel Plaza" application. 
 *  "Hotel Plaza" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. They need to find two components of
 *  a key and combine them, then use the key to escape Hotel Plaza.
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser, creates the animals and starts the game.  
 *  It also evaluates and executes the commands that the parser returns.
 * 
 * @author  Michael Kölling, David J. Barnes and Gurvir Singh (K23010952)
 * @version 2023.12.06
 */

public class Game 
{
    private Parser parser;
    private Player player;
    private Room currentRoom, reception, corridor, bar, toilet, roomAlpha, roomBeta, roomGamma, transporter;
    private Animal cat, dog, parrot;
    private Stack<Room> roomsVisited;
    private ArrayList<Room> rooms;
    private ArrayList<Animal> animals;
    private Random rand;
    
    private boolean winningCondition;
    private boolean wonGame;
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        player = new Player();
        roomsVisited = new Stack<>();
        rand = new Random();
        
        winningCondition = false;
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        // create the rooms
        reception = new Room("in the reception of Hotel Plaza");
        corridor = new Room("in the hotel's corridor");
        bar = new Room("in Joe's bar");
        toilet = new Room("in the toilet");
        roomAlpha = new Room("in roomAlpha");
        roomBeta = new Room("in roomBeta");
        roomGamma = new Room("in roomGamma");
        transporter = new Room("You entered a magic room");
        
        rooms = new ArrayList<>();
        rooms.add(reception);
        rooms.add(corridor);
        rooms.add(bar);
        rooms.add(toilet);
        rooms.add(roomAlpha);
        rooms.add(roomBeta);
        rooms.add(roomGamma);
        
        
        // initialise room exits
        reception.setExit("east", corridor);
        reception.setExit("south", toilet);
        reception.setExit("west", bar);
        reception.setExit("north", transporter);
        
        corridor.setExit("east", corridor);
        corridor.setExit("south", roomAlpha);
        corridor.setExit("west", roomBeta);
        corridor.setExit("north", roomGamma);

        bar.setExit("east", reception);

        toilet.setExit("north", reception);

        roomAlpha.setExit("north", corridor);
        
        roomBeta.setExit("east", corridor);
        
        roomGamma.setExit("south", corridor);
        
        
        // initialise room's items
        reception.setItem("computer", 1000);
        reception.setItem("monitor", 1000);
        reception.setItem("sweets", 5);
        
        corridor.setItem("vase", 20);
        corridor.setItem("painting", 40);
        
        bar.setItem("glass", 5);
        bar.setItem("key-blade", 10);
        
        toilet.setItem("soap", 5);
        toilet.setItem("plunger", 10);
        
        roomAlpha.setItem("bed", 1000);
        roomAlpha.setItem("diary", 10);
        roomAlpha.setItem("pen", 5);
        
        roomBeta.setItem("TV", 1000);
        
        roomGamma.setItem("wardrobe", 1000);
        roomGamma.setItem("key-head", 10);
        
        
        // create the animals
        cat = new Animal("cat", bar);
        dog = new Animal("dog", corridor);
        parrot = new Animal("parrot", roomBeta);
        
        animals = new ArrayList<>();
        
        animals.add(cat);
        animals.add(dog);
        animals.add(parrot);
        
        currentRoom = reception;  // start game reception
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        
        boolean finished = false;
        while (! finished) 
        {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println("\nWelcome to Hotel Plaza!"
        + "\nHotel Plaza is a haunted hotel, you need to create a key to escape."
        + "\nThe components of the keys are dispersed around the hotel."
        + "\nFind these components and combine them to create a key."
        + "\nYour backpack can carry items up to a weight of 50."
        + "\nType 'help' if you need help.\n");
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;
    
        String commandWord = command.getCommandWord();
        
        switch (commandWord) {
            case "help":
                printHelp(command);
                break;
                    
            case "go":
                goRoom(command);
                break;
                    
            case "quit":
                wantToQuit = quit(command);
                break;
                
            case "back":
                goBack(command);
                break;
                
            case "take":
                pickUpItem(command);
                break;
            
            case "drop":
                dropItem(command);
                break;
                
            case "inventory":
                printInventory(command);
                break;
            
            case "combine":
                combineKey(command);
                break;
                
            case "use":
                winGame(command);
                // if the player has won, quit the game.
                if (wonGame)
                {
                    return wantToQuit = true;
                }
                break;
                
            default:
                System.out.println("I don't know what you mean...");
                }      
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out a list of the command words.
     */
    private void printHelp(Command command) 
    {
        if(command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Help is a one word command.");
            return;
        }
        
        System.out.println("You are lost. You are alone. You wander"
        + "\naround the hotel.\n"
        + "Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord() || command.hasThirdWord()) 
        {
            // if there is no second word, we don't know where to go...
            System.out.println("Go is a two word command, type go and the direction.");
            return;
        }

        String direction = command.getSecondWord();
        
        if (!checkDirection(direction))
        {
            System.out.println("The direction entered is not valid.");
            return;
        }
        
        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            roomsVisited.push(currentRoom);
            currentRoom = nextRoom;
            updateAnimals();
            
            // If the user enter the transporter room, teleport him in a random room
            if (currentRoom == transporter)
            {
                roomsVisited.remove(transporter);
                currentRoom = randomRoom();
                System.out.println("You have been teleported to a random room.");
            }
            System.out.println(currentRoom.getLongDescription());
        }
    }
    
    /** 
     * @return a random Room.
     */
    private Room randomRoom()
    {
        int index = rand.nextInt(rooms.size());
        Room room = rooms.get(index);
        return room;
    }
    
    /** 
     * The user can go back to the rooms previously visited.
     * Once he reaches the starting room he cannot back any further.
     */
    private void goBack(Command command)
    {
        if(command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Back is a one word command.");
            return;
        }
        
        if (roomsVisited.empty())
        {
            System.out.println("You cannot go back any further.");
        }
        else
        {
            currentRoom = roomsVisited.pop();
            updateAnimals();
            System.out.println(currentRoom.getLongDescription());
        }
    }
    
    /** 
     * @param direction, the second word the user inputs.
     * @return true if the direction entered is valid, false otherwise.
     */
    private boolean checkDirection(String direction)
    {
        String[] directions = {"north", "west", "east", "south"};
        for (String dir : directions)
        {
            if (dir.equals(direction))
            {
                return true;
            }
        }
        return false;
    }
    
    /** 
     * Checks if the player and the animal are in the same room
     * if they are, outputs a message.
     * Moves the animals into a random room.
     */
    private void updateAnimals()
    {
        for (Animal animal : animals)
        {
            if (animal.getRoom().equals(currentRoom))
            {
                System.out.println("A " + animal.getName() + " wonders around the room.");
            }
            
            Room randomRoom = randomRoom();
            animal.setRoom(randomRoom);
        }
    }
    
    /** 
     * Pick up an item from the room and put it in the player's inventory.
     * Checks if the player's backpack is full, if it is display a message
     * and not pick up the item from the room
     */
    private void pickUpItem(Command command)
    {
        if (!command.hasSecondWord() || command.hasThirdWord()) {
            // if there is no second word, we don't know what to pick up...
            System.out.println("Take is a two word command, type take and the name of the item.");
            return;
        }
        
        String item = command.getSecondWord();
              
        // Remove it from the room's items
        Item desiredItem = currentRoom.removeItem(item);
        
        if (desiredItem != null)
        {     
            // Add it to player's inventory
            if ((player.getRemainingWeight() - desiredItem.getWeight()) >= 0) 
            {
                player.addInventory(desiredItem);
            }
            else
            {
                currentRoom.setItem(desiredItem.getName(), desiredItem.getWeight());
                System.out.println("Your backpack is full, drop some items.");
            }
            System.out.println(player.getInventoryString() + "\n" + player.getWeightString() + "\n" + currentRoom.getItemString());
        }
    }
    
    /** 
     * Drop an item from the player's inventory and put it in the room.
     */
    private void dropItem(Command command)
    {
        if (!command.hasSecondWord() || command.hasThirdWord()) {
            // if there is no second word, we don't know what to drop...
            System.out.println("Drop is a two word command, type drop and the name of the item.");
            return;
        }
        
        String item = command.getSecondWord();
        
        Item droppedItem = player.dropItem(item);
        
        if (droppedItem != null)
        {     
            // Add it to the room
            currentRoom.setItem(droppedItem.getName(), droppedItem.getWeight());
            System.out.println(player.getInventoryString() + "\n" + player.getWeightString() + "\n" + currentRoom.getItemString());
        }
    }
    
    /**
     * Print out a list of the player's inventory.
     */
    private void printInventory(Command command)
    {
        if (command.hasSecondWord() || command.hasThirdWord()) {
            // if there is no second word, we don't know what to drop...
            System.out.println("Inventory is a one word command.");
            return;
        }
        System.out.println(player.getInventoryString() + "\n" + player.getWeightString());
    }
    
    /**
     * Combines the key-head and key-blade to form a key.
     * Add the key to player's inventory and remove the
     * key-head and key-blade from the player's inventory.
     */
    private void combineKey(Command command){
        String head = command.getSecondWord();
        String blade = command.getThirdWord();
        
        if (!((head.equals("key-head") && blade.equals("key-blade")) || (blade.equals("key-head") && head.equals("key-blade"))))
        {
            System.out.println("Combine what?");
            return;
        }
        
        if (!player.hasKeysComponents())
        {
            System.out.println("You don't have all the components to make a key.");
        }
        
        else
        {
            winningCondition = true;
            System.out.println("You made a key for a door.");
            player.addInventory(new Item("key",20));
            player.dropItem("key-head");
            player.dropItem("key-blade");
        }
    }
    
    /**
     * Check if the player has the key and is in the reception,
     * if true, the player has won the game.
     */
    private void winGame(Command command)
    {
        String key = command.getSecondWord();
        
        if(!command.hasSecondWord() || !key.equals("key") || command.hasThirdWord()) 
        {
            System.out.println("Use is a one word command.");
            return;
        }
        
        if (currentRoom == reception)
        {
            if (winningCondition == true)
            {
                System.out.println("Congratulations, you have escaped Hotel Plaza");
                wonGame = true;
            }
            else
            {
                System.out.println("You don't have a key.");
            }
        }
        else
        {
            System.out.println("You can't use the key in this room.");
        }
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord() || command.hasThirdWord()) {
            System.out.println("Quit is a one word command.");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
