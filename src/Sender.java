
import java.io.*;
import java.net.*;
import java.util.*;

public class Sender implements Runnable{
    
    private Socket sender_socket;
    private ObjectOutputStream output; 
    private ChatNode node;
    
    public Sender(ChatNode node)
    {
          this.node = node;
    }
    
    
    @Override
    public void run()
    {
        // Looking for user input to make their message
    	while(true)
    	{
    		// Message to be sent to other participatns
    		Message send_msg;
    		
    		// Prompt for input
    		System.out.print(node.get_port() + ": ");
        	Scanner message = new Scanner(System.in);
        	
        	// Get the user input
        	String input = message.nextLine();
        	// Set the type, and the content
			send_msg = new Message("TEXT", input, Integer.toString(node.get_port()) + input);
			
        	// If the user wants to quit
        	if (input.startsWith("./quit"))
    		{
        		
        		// Prepare a LEAVE message
        		send_msg = new Message("LEAVE", node, Integer.toString(node.get_port()) + " left the chat");
        		// Display leave message to user
        		System.out.println("You have left the chat.");
        		// Set a quit flag
        		node.set_connect(false);
    		}
        	// If the user wants to be funny.
    		else if (input.startsWith("./join"))
    		{
    			System.out.println("You are already in chat.");
    		}
        	
        	// Send the message to all of the participants
        	for (ChatNode entry : ChatNode.participants.values())
        	{
        		// If the entry is not the same as ourselves
        		if (entry != node)
        		{
        			try 
            		{
        				// Create a socket to that entry
            			Socket send_socket = new Socket(entry.get_ip(), entry.get_port());
            			//System.out.println("Sending message to " + entry.get_ip() + " " + entry.get_port());
            			// Setup output writing
            			output = new ObjectOutputStream(send_socket.getOutputStream());
            			
            			// Send the message object
            			output.writeObject(send_msg);
            			
            			// Close everything
            			output.close();
            			send_socket.close();
            		}
            		catch (Exception e)
            		{
            			System.out.println("Couldn't create socket to: " + entry.get_ip() + ":" + entry.get_port());
            		}
        		}
        		
        	}
        	
        	// If the node has left the chat
        	if (!node.is_connected())
        	{
        		node.remove_participant(node);
        		break;
        	}
    	}
    }
}