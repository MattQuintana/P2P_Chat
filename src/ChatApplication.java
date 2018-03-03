
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
            ChatNode test_node = new ChatNode(config_file);
            ChatNode new_node = new ChatNode(config_file);
            //ChatNode.participants.put(1, test_node);
            //ChatNode.participants.put(2, new_node);
            System.out.println(ChatNode.participants.keySet());
            //ChatNode another_node = new ChatNode(config_file);
            new_node.run();
            //another_node.run();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
}