
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
    		// Message to be sent to other participants
    		Message send_msg;
    		//System.out.println(ChatNode.participants.keySet());
    		// Prompt for input
    		System.out.print(node.get_port() + ": ");
        	Scanner message = new Scanner(System.in);
        	
        	// Get the user input
        	String input = message.nextLine();
        	
        	// Set the type, and the content
			send_msg = new Message("TEXT", input, Integer.toString(node.get_port()) + ": " + input);
			
        	// If the user wants to quit
        	if (input.startsWith("./quit"))
    		{	
        		for (Map.Entry<Integer, ChatNode> entry : ChatNode.participants.entrySet())
        		{
        			if( entry.getValue().get_ip().equals(node.get_ip()) && entry.getValue().get_port() == node.get_port())
        			{
        				//System.out.println(entry.getValue().is_connected());
        				entry.getValue().set_connect(false);
        				//System.out.println(entry.getValue().is_connected());
        			}
        		}
        		// Prepare a LEAVE message
        		send_msg = new Message("LEAVE", node, Integer.toString(node.get_port()) + " left the chat");
        		broadcast_message(send_msg);
        		// Display leave message to user
        		System.out.println("You have left the chat.");
        		
        		
        		// Do the actual removal from the participants table
        		node.remove_participant(node);
        		
        		
        		// Set a quit flag
        		
        		
        		// Broadcast an update to the other nodes table.
        		Message exit_broadcast = new Message("SETUP", node.participants, "");
        		broadcast_message(exit_broadcast);
        		
        		break;
    		}
        	// If the user wants to be funny.
    		else if (input.startsWith("./join"))
    		{
    			System.out.println("You are already in chat.");
    			continue;
    		}
        	
        	broadcast_message(send_msg);
    	}
    	return;
    }
    
    
    private void broadcast_message(Message msg)
    {
    	// Send the message to all of the participants
    	for (ChatNode entry : ChatNode.participants.values())
    	{
    		if (entry.is_connected())
			{
    			//System.out.println("This PORT is still open!");
    			//System.out.println(entry.get_port());
    			// If the entry is not the same as ourselves
				if (!(entry.get_ip().equals(node.get_ip())) || entry.get_port() != node.get_port() )
				{
    			
    				try 
            		{
        				// Create a socket to that entry
            			Socket send_socket = new Socket(entry.get_ip(), entry.get_port());
            			//System.out.println("Sending message to " + entry.get_ip() + " " + entry.get_port());
            			// Setup output writing
            			output = new ObjectOutputStream(send_socket.getOutputStream());
            			
            			// Send the message object
            			output.writeObject(msg);
            			
            			// Close everything
            			output.close();
            			send_socket.close();
            		}
            		catch (Exception e)
            		{
            			
            			System.out.println("Couldn't create socket to: " + entry.get_ip() + ":" + entry.get_port());
            			System.out.println(e);
            		}
    			}
    		}
    	}
    }
}