
import java.io.*;
import java.net.*;
import java.util.*;

public class Sender implements Runnable{
    
    private Socket sender_socket;
    private PrintWriter output;
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
    		//System.out.println(ChatNode.participants.keySet());
    		System.out.print(node.get_port() + ": ");
        	Scanner message = new Scanner(System.in);
        	
        	String input = message.nextLine();
        	
        	if (input.startsWith("./quit"))
    		{
        		
        		input = "Left the chat.";
        		System.out.println("You have left the chat.");
        		// Set a quit flag
        		node.set_connect(false);
    		}
    		else if (input.startsWith("./join"))
    		{
    			input = node.get_ip() + " has joined the chat.";
    			System.out.println("You have joined the chat.");
    		}
    		
        	
        	for (ChatNode entry : ChatNode.participants.values())
        	{
        		//System.out.println(entry.get_ip());
        		//System.out.println(entry.get_port());
        		if (entry != node)
        		{
        			try 
            		{
            			Socket send_socket = new Socket(entry.get_ip(), entry.get_port());
            			output = new PrintWriter(send_socket.getOutputStream());
            			output.println(node.get_port() + ":" + input);
            			//System.out.println(input);
            			output.close();
            		}
            		catch (Exception e)
            		{
            			System.out.println("Couldn't create socket to: " + entry.get_ip() + ":" + entry.get_port());
            		}
        		}
        		
        	}
        	
        	// for every entry in the participants
        		// try to create a socket with Chatnode.ip
        		// and ChatNode.port 
        		// Send the message over the printwriter - output - to the socket        		
        	
        	// Check quit flag 
        		// If has quit, 
        			// break loop and close connection, and remove from table
        	if (!node.is_connected())
        	{
        		//System.out.println(node.get_port() + " Exiting");
        		for (Map.Entry<Integer, ChatNode> entry : ChatNode.participants.entrySet())
        		{
        			if (entry.getValue() == node)
        			{
        				ChatNode.participants.remove(entry.getKey());
        			}
        		}
        		break;
        	}
    	}
    }
}