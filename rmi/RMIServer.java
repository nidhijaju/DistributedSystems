/*
 * Created on 01-Mar-2016
 */
package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

import common.*;

public class RMIServer extends UnicastRemoteObject implements RMIServerI {

	private int totalMessages = -1;
	private int[] receivedMessages;

	public RMIServer() throws RemoteException {
	}

	public void receiveMessage(MessageInfo msg) throws RemoteException {

		// TO-DO: On receipt of first message, initialise the receive buffer
		if(receivedMessages == null) {
			totalMessages = 0;
			receivedMessages = new int[msg.totalMessages];
		}

		// TO-DO: Log receipt of the message
		totalMessages++;
		receivedMessages[msg.messageNum] = 1;

		// TO-DO: If this is the last expected message, then identify
		//        any missing messages

	}


	public static void main(String[] args) {

		RMIServer rmis = null;

		// TO-DO: Initialise Security Manager
		if(System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}

		// TO-DO: Instantiate the server class
		try {
			rmis = new RMIServer();
		}
		catch (Exception e) {
			
		}

		// TO-DO: Bind to RMI registry
		try {

			iRMIServer = (RMIServerI) Naming.lookup(urlServer);

			for (int i = 0; i < numMessages; i++){
				MessageInfo new_message = new MessageInfo(numMessages, i);
				iRMIServer.receiveMessage(new_message);
			}

		}
		catch (RemoteException e){ 
			System.out.println("Remote exception error: " + e);
		}
		catch (NotBoundException e){ 
			System.out.println("Not bound exception error: " + e);
		}
		catch (MalformedURLException e) {
			System.out.println("Malformed URL Exception error: " + e);
		}
	}

	protected static void rebindServer(String serverURL, RMIServer server) {

		// TO-DO:
		// Start / find the registry (hint use LocateRegistry.createRegistry(...)
		// If we *know* the registry is running we could skip this (eg run rmiregistry in the start script)

		// TO-DO:
		// Now rebind the server to the registry (rebind replaces any existing servers bound to the serverURL)
		// Note - Registry.rebind (as returned by createRegistry / getRegistry) does something similar but
		// expects different things from the URL field.
	}
}
