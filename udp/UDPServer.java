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

//import com.sun.glass.ui.SystemClipboard;

import common.MessageInfo;

public class UDPServer {

	private DatagramSocket recvSoc;
	private static int totalMessages = -1;
	private static int[] receivedMessages = null;
	private static int messagesReceived = 0;
	private boolean close = false;

	private void run() {
		int				pacSize;
		byte[]			pacData;
		DatagramPacket 	pac;

		// TO-DO: Receive the messages and process them by calling processMessage(...).
		//        Use a timeout (e.g. 30 secs) to ensure the program doesn't block forever
		pacSize = 256;
		pacData = new byte[pacSize];

		try {
			while(!close){
				// for (int n = 0; n < pacSize; n++) {
				// 	pacData[n] = 0;
				// }
				pac = new DatagramPacket(pacData,pacSize);
				try{
					recvSoc.setSoTimeout(30000);
					recvSoc.receive(pac);
				}
				catch(SocketTimeoutException e){
					//print out a summary of what was received before timeout exception?
					if (totalMessages != -1) {
						print_results();
					}
					// receivedMessages = null;
					// totalMessages = -1;
					
					System.exit(-1);
				}

				processMessage(new String(pac.getData()));

			}
		}
		catch (SocketException e) {
			System.out.println("Socket exception: " + e);
		}
		catch (IOException e) {
			System.out.println("IO exception: " + e);
		}


		

	}

	public void processMessage(String data) {

		MessageInfo msg = null;

		// TO-DO: Use the data to construct a new MessageInfo object
		try{
			msg = new MessageInfo(data.trim());
		}
		catch(Exception e) {
			System.out.println("Error creating MessageInfo object, exception: " + e);
			System.exit(-1);
		}

		// TO-DO: On receipt of first message, initialise the receive buffer
		if(receivedMessages == null || totalMessages < 0) {
			totalMessages = msg.totalMessages;
			receivedMessages = new int[totalMessages];
		}

		// TO-DO: Log receipt of the message
		messagesReceived++;
		receivedMessages[msg.messageNum] = 1;
		// System.out.println("total messages: " + totalMessages);
		// System.out.println("message received: " + messagesReceived);

		// TO-DO: If this is the last expected message, then identify
		//        any missing messages
		if (msg.messageNum + 1 == totalMessages) {
			print_results();
			close = true;
			// receivedMessages = null;
			// totalMessages = -1;
		}

	}

	public static void print_results() {
		String MissingMessages = "";
			for (int i = 0; i <receivedMessages.length; i++) {
				if (receivedMessages[i] == 0) {
					MissingMessages += i + ", ";
				}
			}	
			System.out.println("******* SUMMARY *******");
			System.out.println("Number of Messages Received: " + totalMessages);
			if (totalMessages == receivedMessages.length) {
				System.out.println("No missing messages!");
			}
			else {
				System.out.println("Number of Lost Messages: " + (totalMessages - messagesReceived));
				System.out.println("Lost Messages: " + MissingMessages);
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
