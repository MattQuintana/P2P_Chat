
public class ReceiverWorker implements Runnable{
	
	private Message message;
	public ReceiverWorker(Message msg)
	{
		message = msg; 
		//System.out.println(msg);
	}
	
	@Override
	public void run()
	{
		//System.out.println("In Receiver worker");
		if (message.get_type() == "LEAVE")
		{
			System.out.println("Leaving the chat. ");
			
		}
		else if (message.get_type() == "JOIN")
		{
			System.out.println("Joining the chat");
			
		}
		else if (message.get_type() == "TEXT")
		{
			System.out.println("\n" + message);
		}
	}

}
