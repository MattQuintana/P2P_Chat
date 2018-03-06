
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
                break;
        	}
        	else if (choice.startsWith("./join") && (choice.length() > 7)) 
        	{
        		// If joining the chat, want to open up a connection to the node you 
        		// are trying to join
        		String ip = choice.split(" ")[1].split(":")[0];
        		int port = Integer.parseInt(choice.split(" ")[1].split(":")[1]);
        		
        		try 
        		{
        			// Create the initial socket connection
        			Socket initial_connection = new Socket(ip, port);
        			
        			// Configure 
        			File config_file = new File(config_path);
                    ChatNode new_node = new ChatNode(config_file);
                    
                    // Prepare the joining message
                    Message initial_connect = new Message("JOIN", new_node, " joined.");
                    PrintWriter send_output = new PrintWriter(initial_connection.getOutputStream());
                    send_output.println(initial_connect);
                    
                    // Start the chat node
                    new_node.run();
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