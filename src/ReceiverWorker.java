
public class ReceiverWorker implements Runnable{
	
	private String message;
	public ReceiverWorker(String msg)
	{
		message = msg; 
	}
	
	@Override
	public void run()
	{
		if (message.startsWith("./quit"))
		{
			System.out.println("Leaving the chat. ");
			
		}
		if (message.startsWith("./join"))
		{
			System.out.println("Joining the chat");
			
		}
	}

}
