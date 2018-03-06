
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Create new chatnode with config file passed in as main argument
        String config_path = args[0];
        
        while(true)
        {
        	System.out.println("Enter ./start to start a chat or ./join [IP:PORT] to join a chat.");
        	Scanner input = new Scanner(System.in);
        	String choice = input.nextLine().replaceAll("\n", "").replaceAll("\r", "");
        	
        	// If we are starting the chat
        	if (choice.equals("./start"))
        	{
        		// Just run the node
        		File config_file = new File(config_path);
                ChatNode new_node = new ChatNode(config_file);
                new_node.run();
                new_node.add_participants(new_node);
                break;
        	}
        	else if (choice.startsWith("./join") && (choice.length() > 7)) 
        	{
        		// Configure 
    			File config_file = new File(config_path);
                ChatNode new_node = new ChatNode(config_file);
                // Start the chat node
                new_node.run();
        		
        		// If joining the chat, want to open up a connection to the node you 
        		// are trying to join
        		String ip = choice.split(" ")[1].split(":")[0];
        		//System.out.println(ip);
        		int port = Integer.parseInt(choice.split(" ")[1].split(":")[1]);
        		//System.out.println(port);
        		
        		try 
        		{
        			// Create the initial socket connection
        			Socket initial_connection = new Socket(ip, port);
                    
                    // Prepare the joining message
                    Message initial_connect = new Message("JOIN", new_node, Integer.toString(new_node.get_port()) + " joined.");
                    ObjectOutputStream send_output = new ObjectOutputStream(initial_connection.getOutputStream());
                    send_output.writeObject(initial_connect);
                    
                    send_output.close();
                    initial_connection.close();
                    break;
        			
        		}
        		catch(Exception e)
        		{
        			System.out.println(e);
        		}
        	}
        	else
        	{
        		System.out.println("Invalid input");
        	}        	
        }
    }
}