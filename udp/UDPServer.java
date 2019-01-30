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


		

	}

	public void processMessage(String data) {

		MessageInfo msg = null;

		// TO-DO: Use the data to construct a new MessageInfo object
		try {
			msg = new MessageInfo(data);
		}
		catch (Exception e) {
			System.out.println("Error: ");
		}

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
		//totalMessages++; agrim: do we need this?
		receivedMessages[msg.messageNum] = 1;

		// TO-DO: If this is the last expected message, then identify
		//        any missing messages
		if (totalMessages == msg.totalMessages) {

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
