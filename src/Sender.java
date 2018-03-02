
import java.io.*;
import java.net.*;

public class Sender implements Runnable{
    
    private Socket sender_socket;
    private PrintWriter output;
    
    public Sender(Socket s_socket)
    {
        System.out.println("Establishing sender socket");
        sender_socket = s_socket;
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
    }
}