

import java.net.*;
import java.io.*;
import java.util.*;

public class ReceiverWorker implements Runnable{
	
	private Message message;
	private ChatNode parent_node;
	
	public ReceiverWorker(Message msg, ChatNode node)
	{
		message = msg; 
		parent_node = node;
	}
	
	@Override
	public void run()
	{
		//System.out.println("In Receiver worker");
		//System.out.println(message.get_type());
		if (message.get_type().equals("LEAVE"))
		{
			// If we've received a message of someone leaving the chat
			System.out.println(message.get_msg());
			//ChatNode leaving_node = (ChatNode) message.get_content();
			//parent_node.remove_participant(leaving_node);
			return;
			
		}
		else if (message.get_type().equals("JOIN"))
		{
			// If we've received a join message, add the chat node 
			// to our participants table and send a message to the 
			// others in the chat
			
			//System.out.println("Someone has joined the chat.");
			ChatNode incoming_node = (ChatNode) message.get_content();

			parent_node.add_participants(incoming_node);
			//System.out.println(parent_node.participants.keySet());
			
			// Set up the message that contains the participants table
			Message setup_return = new Message("SETUP", parent_node.participants, "Establishing participants.");
			
			String joined_message = Integer.toString(incoming_node.get_port()) + " joined.";
			Message join_message = new Message("TEXT", joined_message, joined_message);
			try 
			{	
				// Set up the socket that connects back to the incoming node
				
				//System.out.println("Sending setup to : ");
				/*
				Socket return_socket = new Socket(incoming_node.get_ip(), incoming_node.get_port());
				ObjectOutputStream output = new ObjectOutputStream(return_socket.getOutputStream());
				output.writeObject(setup_return);*/
				
				// For every participant in the table
				for (ChatNode entry : parent_node.participants.values())
				{
					if (entry.is_connected())
					{
						// Check that it isn't our current node
						if (!entry.equals(parent_node)) 
						{
							// Set up a socket to send the update to the participant table
							Socket setup_socket = new Socket(entry.get_ip(), entry.get_port());
							ObjectOutputStream output = new ObjectOutputStream(setup_socket.getOutputStream());
							
							// Write the update
							output.writeObject(setup_return);
							// Tell the other participants that someone joined.
							output.writeObject(join_message);
						}
					}
				}
			}
			catch (Exception e)
			{
				System.out.println("Issue connecting back to incoming node.");
				System.out.println(e);
			}
			System.out.println(message.get_msg());			
		}
		else if (message.get_type().equals("SETUP"))
		{
			// If we've received a setup message
			// set the incoming participants table as our own
			Map<Integer, ChatNode> incoming_participants = (HashMap<Integer, ChatNode>)message.get_content();
			parent_node.participants = incoming_participants;
			
		}
		else if (message.get_type().equals("TEXT"))
		{
			System.out.println(message.get_msg());
		}
		
		return;
	}

}
