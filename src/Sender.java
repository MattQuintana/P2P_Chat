
import java.io.*;
import java.net.*;

public class Sender implements Runnable{
    
    private Socket sender_socket;
    private PrintWriter output;
    
    private int port_num;
	private String ip_addr;
    
    public Sender(Socket s_socket)
    {
        System.out.println("Establishing sender socket");
        sender_socket = s_socket;
        
        port_num = sender_socket.getLocalPort();
    	ip_addr = sender_socket.getInetAddress().toString().replace("/", "");
        try
        {
            output = new PrintWriter(sender_socket.getOutputStream());
        }
        catch (Exception e)
        {
            System.out.println("Error creating output writer.");
        }        
    }
    
    @Override
    public void run()
    {
        System.out.println("Started sender thread. ");
        System.out.println("Started Sender thread. On IP: " + ip_addr + " Port: " + port_num);
    }
}