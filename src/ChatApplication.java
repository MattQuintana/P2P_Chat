
import java.io.*;
import java.net.*;

public class ChatApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Create new chatnode with config file passed in as main argument
        String config_path = args[0];
        
        try
        {
            File config_file = new File(config_path);
            ChatNode new_node = new ChatNode(config_file);
            new_node.run();
        }
        catch(Exception e)
        {
            System.out.println("Error creating the new chat node.");
        }
    }
    
}