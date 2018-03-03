
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
    		System.out.print("Message: \n");
        	Scanner message = new Scanner(System.in);
        	
        	String input = message.nextLine();
        	
        	if (input.startsWith("./quit"))
    		{
        		input = "You have left the chat.";
        		node.set_connect(false);
        		
        		// Set a quit flag 
    			
    		}
    		else if (input.startsWith("./join"))
    		{
    			input = "You have joined the chat.";    			
    		}
    		
        	
        	for (ChatNode entry : ChatNode.participants.values())
        	{
        		try 
        		{
        			Socket send_socket = new Socket(entry.get_ip(), entry.get_port());
        			output = new PrintWriter(send_socket.getOutputStream());
        			output.println(input);
        			System.out.println(input);
        			output.close();
        		}
        		catch (Exception e)
        		{
        			System.out.println("Couldn't create socket to: " + entry.get_ip() + entry.get_port());
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
        		System.out.println("Exiting");
        		break;
        	}
    	}
    }
}