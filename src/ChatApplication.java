
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Create new chatnode with config file passed in as main argument
        String config_path = args[0];
        
        while(true)
        {
        	System.out.println("Enter ./start to start a chat or ./join to join a chat.");
        	Scanner input = new Scanner(System.in);
        	String choice = input.nextLine().replaceAll("\n", "").replaceAll("\r", "");
        	if (choice.equals("./start")) break;
        	else if (choice.startsWith("./join")) 
        	{
        		String ip = choice.split(" ")[1].split(":")[0];
        		int port = Integer.parseInt(choice.split(" ")[1].split(":")[1]);
        		
        		try 
        		{
        			Socket initial_connection = new Socket(ip, port);
        			File config_file = new File(config_path);
                    ChatNode new_node = new ChatNode(config_file);
                    
                    Message initial_connect = new Message("JOIN", new_node, " joined.");
                    PrintWriter send_output = new PrintWriter(initial_connection.getOutputStream());
                    send_output.println(initial_connect);
                    new_node.run();
                    send_output.close();
                    initial_connection.close();
                    
        			
        		}
        		catch(Exception e)
        		{
        			
        		}
        	}
        	else
        	{
        		System.out.println("Invalid input");
        	}        	
        }
        
        try
        {
            File config_file = new File(config_path);
            ChatNode new_node = new ChatNode(config_file);
            new_node.run();
            
            /*
            ChatNode test_node = new ChatNode(config_file);
            test_node.run();
            System.out.println(ChatNode.participants.keySet());*/
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}