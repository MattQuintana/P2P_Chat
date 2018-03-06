import java.util.*;
import java.net.*;


public class Message<C> {
	
	private String type;
	private C content;
	private String string_msg;
	
	// Generic object to contain message content
	// Used to send ChatNode objects through stream
	private class Content<C>{
		C content;
		public void set(C content)
		{
			this.content = content;
		}
		public C get()
		{
			return this.content;
		}
	}
	
	public Message(String type, C content, String msg) 
	{
		this.type = type;
		this.content = content;
		this.string_msg = msg;
	}
	
	public C get_content()
	{
		return content;
	}
	
	public String get_msg()
	{
		return string_msg;
	}
	
	public String get_type()
	{
		return type;
	}

}