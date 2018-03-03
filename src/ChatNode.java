

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
            System.out.println(config_info);
            
            //host = config_info.split(":")[0];
            //System.out.println(host);
            //port = Integer.parseInt(config_info.split(":")[1]);
            //System.out.println(port);
        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("Could not access file. ");
        }
        int count = 0;
    	for(Integer key : ChatNode.participants.keySet()) 
    	{
    		count++;
    	}
    	ChatNode.participants.put(count, this);
        
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
    
    @Override
    public void run()
    {
        // Start sender thread
            // Specifically create a new socket
        // Start receiver thread
            // Specifically create a new serversocket
        System.out.println("In chatnode run. ");
        try
        {
        	ServerSocket receiver = new ServerSocket(0);
        	String full_ip_string = receiver.getInetAddress().toString().replace("/", ":");
        	String host_ip = full_ip_string.split(":")[0];
        	
        	host = host_ip;
        	port = receiver.getLocalPort();        	
        	System.out.println(host_ip);
        	System.out.println(receiver.getLocalPort());
        	
        	(new Thread(new Receiver(receiver, this))).start();
            (new Thread(new Sender(this))).start();
            
        	//(new Sender(new Socket("192.168.1.4", 5555))).run();
            //(new Thread(new Sender(new Socket(host, port)))).start();
            
            //(new Thread(new Receiver(new ServerSocket(port)))).start();
        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("Could not create all sockets. ");
        }
    }
    
}