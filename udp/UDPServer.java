/*
 * Created on 01-Mar-2016
 */
package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;

import common.MessageInfo;

public class UDPServer {

	private DatagramSocket recvSoc;
	private int totalMessages = -1;
	private int[] receivedMessages;
	private boolean close;

	private void run() {
		int				pacSize;
		byte[]			pacData;
		DatagramPacket 	pac;

		// TO-DO: Receive the messages and process them by calling processMessage(...).
		//        Use a timeout (e.g. 30 secs) to ensure the program doesn't block forever
		try{
			recvSoc.setSoTimeout(30000);
			recvSoc.receive(pac);
			processMessage(new String(pac.getData()));
		}
		catch(SocketTimeException e){
			System.out.println("Error: Timeout Exception");
			//should print out a summary of what was received before timeout exception?
		}

		

	}

	public void processMessage(String data) {

		MessageInfo msg = null;

		// TO-DO: Use the data to construct a new MessageInfo object
		try{
			msg = new MessageInfo(data);
		}
		catch(Exception e) {
			System.out.println("Error creating MessageInfo object, exception: " + e);
			System.exit(-1);
		}

		// TO-DO: On receipt of first message, initialise the receive buffer
		if(receivedMessages == null) {
			totalMessages = 0;
			// creates array of 'totalMessages' zeros
			receivedMessages = new int[msg.totalMessages];
		}

		// TO-DO: Log receipt of the message
		totalMessages++;
		receivedMessages[msg.messageNum] = 1;

		// TO-DO: If this is the last expected message, then identify
		//        any missing messages
		if (totalMessages == msg.totalMessages) {
			if (receivedMessages == null || totalMessages <= 0) {
				return; // do nothing because no messages
			}
			String MissingMessages = "";
			for (int i = 0; i <receivedMessages.length; i++) {
				if (receivedMessages[i] == 0) {
					MissingMessages += i + ", ";
				}
			}	
			System.out.println("******* SUMMARY *******");
			Systen.out.println("Number of messages received: " + totalMessages);
			if (totalMessages == receivedMessages.length) {
				System.out.println("No missing messages!");
			}
			else {
				System.out.println("Lost Messages: " + missingMessages);
			}
			receivedMessages = null;
			totalMessages = -1;
		}

	}


	public UDPServer(int rp) {
		// TO-DO: Initialise UDP socket for receiving data
		try {
			recvSoc = new DatagramSocket(rp);
		}
		catch (SocketException e) {
			System.out.println("Error creating socket on port: " + rp);
			System.exit(-1);
		}

		// Done Initialisation
		System.out.println("UDPServer ready");
	}

	public static void main(String args[]) {
		int	recvPort;

		// Get the parameters from command line
		if (args.length < 1) {
			System.err.println("Arguments required: recv port");
			System.exit(-1);
		}
		recvPort = Integer.parseInt(args[0]);

		// TO-DO: Construct Server object and start it by calling run().
		UDPServer serverport = new UDPServer(recvPort);
		serverport.run();

	}

}
