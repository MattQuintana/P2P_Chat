
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
    		receiving = new ServerSocket(0);
    		port_num = receiving.getLocalPort();
    		String temp_ip = receiving.getLocalSocketAddress().toString().split(":")[0];
    		//System.out.println(temp_ip);
    		ip_addr = temp_ip.split("/")[0];  
    		
            int count = 0;
        	for(Integer key : ChatNode.participants.keySet()) 
        	{
        		count++;
        	}
        	ChatNode.participants.put(count, node);
    	}
    	catch(Exception e)
    	{
    		System.out.println("Issue with server socket: ");
    		System.out.println(e);
    	}
    	this.node = node;
    	node.set_ip(ip_addr);
    	node.set_port(port_num);
    }
    
    @Override
    public void run()
    {
    	
        System.out.println("Started receiver thread on IP: " + ip_addr + ":" + node.get_port());
        
        while(true)
        {
        	try
        	{        		
        		(new ReceiverWorker(new BufferedReader(new InputStreamReader(receiving.accept().getInputStream())).readLine())).run();
        		
        	}
        	catch (Exception e)
        	{
        		//System.out.println(e);
        	}
        	
        	if (!node.is_connected())
        	{
        		break;
        	}
        	
        }
    }
}