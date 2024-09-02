import java.util.Scanner;

/**
 * This class is part of the "Hotel Plaza" application. 
 * "Hotel Plaza" is a very simple, text based adventure game.  
 * 
 * This parser reads user input and tries to interpret it as an "Adventure"
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a three word command. It returns the command
 * as an object of class Command.
 * 
 * @author  Michael Kölling, David J. Barnes and Gurvir Singh (K23010952)
 * @version 2023.12.06
 */
public class Parser 
{
    private Scanner reader;         // source of command input
    private static final String[] validCommands = {
        "go", "quit", "help" , "back" , "combine", "take", "drop", "inventory", "use"
    };
    
    /**
     * Create a parser to read from the terminal window.
     */
    public Parser() 
    {
        reader = new Scanner(System.in);
    }

    /**
     * @return The next command from the user.
     */
    public Command getCommand() 
    {
        String inputLine;   // will hold the full input line
        String word1 = null;
        String word2 = null;
        String word3 = null;

        System.out.print("> ");     // print prompt

        inputLine = reader.nextLine();

        // Find up to two words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();      // get first word
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();      // get second word
                if (tokenizer.hasNext())  {
                    word3 = tokenizer.next();      // get third word
                }
                // note: we just ignore the rest of the input line.
            }
        }
        
        return new Command(word1, word2, word3);
    }

    /**
     * Print out a list of valid command words.
     */
    public void showCommands()
    {
        for(String command: validCommands) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }
}
