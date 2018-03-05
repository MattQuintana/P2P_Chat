
public class ReceiverWorker implements Runnable{
	
	private String message;
	public ReceiverWorker(String msg)
	{
		message = msg; 
		//System.out.println(msg);
	}
	
	@Override
	public void run()
	{
		//System.out.println("In Receiver worker");
		if (message.startsWith("./quit"))
		{
			System.out.println("Leaving the chat. ");
			
		}
		else if (message.startsWith("./join"))
		{
			System.out.println("Joining the chat");
			
		}
		else
		{
			System.out.println("\n" + message);
		}
	}

}
