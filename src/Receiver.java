
import java.net.*;
import java.io.*;

public class Receiver implements Runnable{
    
	private int port_num;
	private String ip_addr;
	private ServerSocket receiving; 
	
    public Receiver(ServerSocket receiver_socket)
    {
    	port_num = receiver_socket.getLocalPort();
    	ip_addr = receiver_socket.getInetAddress().toString();
    }
    
    @Override
    public void run()
    {
        System.out.println("Started receiver thread. On IP: " + ip_addr + " Port: " + port_num);
        
        while(true)
        {
        	try
        	{
        		BufferedReader reader = new BufferedReader(new InputStreamReader(receiving.accept().getInputStream()));
        		
        		// If there is an incoming message 
        		if (reader.readLine() != null)
        		{
        			
        			(new ReceiverWorker(reader.readLine())).run();;
        			
        		}
        		
        	}
        	catch (Exception e)
        	{
        		//System.out.println(e);
        	}
        	
        }
    }
}