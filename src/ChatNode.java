

import java.net.*;
import java.io.*;
import java.util.*;


public class ChatNode implements Runnable{   
    
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
        
        /*
        int count = 0;
    	for(Integer key : ChatNode.participants.keySet()) 
    	{
    		count++;
    	}
    	ChatNode.participants.put(count, this);*/
        
    }
    
    public void set_connect(boolean is_true)
    {
    	is_connected = is_true;
    }
    
    public boolean is_connected()
    {
    	return is_connected;
    }
    
    public int get_port() 
    {
    	return port;
    }
    
    public String get_ip()
    {
    	return host;
    }
    
    public void set_ip(String ip)
    {
    	host = ip;
    }
    
    public void set_port(int port)
    {
    	this.port = port;
    }
    
    @Override
    public void run()
    {
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