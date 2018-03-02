

import java.net.*;
import java.io.*;


public class ChatNode implements Runnable{
    
    public static int[] ports;
    
    private int port;
    private String host;
    
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
            
            host = config_info.split(":")[0];
            System.out.println(host);
            port = Integer.parseInt(config_info.split(":")[1]);
            System.out.println(port);
        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("Could not access file. ");
        }
        
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
            (new Sender(new Socket("localhost", 12345))).run();
            //(new Thread(new Sender(new Socket(host, port)))).start();
            //(new Receiver(new ServerSocket(12345))).run();
            //(new Thread(new Receiver(new ServerSocket(port)))).start();
        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("Could not create all sockets. ");
        }
    }
    
}