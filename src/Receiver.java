
import java.net.*;
import java.io.*;

public class Receiver implements Runnable{
    
    public Receiver(ServerSocket receiver_socket)
    {
    
    }
    
    @Override
    public void run()
    {
        System.out.println("Started receiver thread. ");
    }
}