

import java.net.*;
import java.io.*;
import java.util.*;


public class ChatNode implements Runnable, Serializable{   
    
    public static Map<Integer, ChatNode> participants = new HashMap<Integer, ChatNode>();
    
    private int port;
    private String host;
    
    private boolean is_connected;
    
    public ChatNode(File config_file)
    {
        // Config file of form:
        //      HOST:PORTNUM
        try
        {
        	System.out.println("In chat node initialization.");
            BufferedReader reader = new BufferedReader(new FileReader(config_file));
            String config_info = reader.readLine();
            config_info = config_info.replace("\n", "").replace("\r", "");
            //System.out.println(config_info);
            
            host = config_info.split(":")[0];
            //System.out.println(host);
            port = Integer.parseInt(config_info.split(":")[1]);
            //System.out.println(port);
            this.set_connect(true);
        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("Could not access file. ");
        }
        
    }
    
    public void add_participants(ChatNode new_participant)
    {
        int count = 0;
        // Find set the new key
    	for(Integer key : ChatNode.participants.keySet()) 
    	{
    		// Get the current key and make an offset by one up.
    		count = key;
    		count++;
    	}
    	ChatNode.participants.put(count, new_participant);
    }
    
    public void remove_participant(ChatNode leaving_participant)
    {
    	// Remove it from the participants table
		for (Map.Entry<Integer, ChatNode> entry : ChatNode.participants.entrySet())
		{
			// If found the match 
			if (entry.getValue() == leaving_participant)
			{
				// Remove it
				ChatNode.participants.remove(entry.getKey());
			}
		}
    }
    
    // Set whether the node is connected to the chat
    public void set_connect(boolean is_true)
    {
    	is_connected = is_true;
    }
    
    // Return the connection status
    public boolean is_connected()
    {
    	return is_connected;
    }
    
    // Get the nodes port
    public int get_port() 
    {
    	return port;
    }
    
    // Get the nodes IP
    public String get_ip()
    {
    	return host;
    }
    
    // Set the IP address of node
    public void set_ip(String ip)
    {
    	host = ip;
    }
    
    // Set the port of node
    public void set_port(int port)
    {
    	this.port = port;
    }
    
    
    @Override
    public void run()
    {
    	// Spawn off two new threads for receiving and sending
        try
        {     	
        	(new Thread(new Receiver(this))).start();
            (new Thread(new Sender(this))).start();
        }
        catch (Exception e)
        {
        	System.out.println("Could not start receiving and sending threads.");
        	System.out.println(e);
        }
    }
    
}