
import java.net.*;
import java.io.*;

public class Receiver implements Runnable{
    
	private int port_num;
	private String ip_addr;
	private ServerSocket receiving; 
	private ChatNode node;
	
    public Receiver(ChatNode node)
    {
    	try 
    	{
    		// Initialize the server socket
    		receiving = new ServerSocket(node.get_port());
    		port_num = receiving.getLocalPort();
    		ip_addr = node.get_ip();
    		
    	}
    	catch(Exception e)
    	{
    		System.out.println("Issue with server socket: ");
    		System.out.println(e);
    	}
    	this.node = node;
    	node.set_connect(true);
    }
    
    @Override
    public void run()
    {
    	
        System.out.println("Started receiver thread on IP: " + ip_addr + ":" + node.get_port());
        
        // While we should still be listening. 
        while(node.is_connected())
        {
        	try
        	{  
        		// Spawn a new thread that takes in the message object
        		(new ReceiverWorker ( (Message) ( new ObjectInputStream( receiving.accept().getInputStream() ).readObject() ), node ) ).run();
        		
        	}
        	catch (Exception e)
        	{
        		//System.out.println(e);
        	}
        }
    }
}