/*
 * Created on 01-Mar-2016
 */
package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import common.MessageInfo;

public class RMIClient {

	public static void main(String[] args) {

		RMIServerI iRMIServer = null;

		// Check arguments for Server host and number of messages
		if (args.length < 2){
			System.out.println("Needs 2 arguments: ServerHostName/IPAddress, TotalMessageCount");
			System.exit(-1);
		}

		String urlServer = new String("rmi://" + args[0] + "/RMIServer");
		int numMessages = Integer.parseInt(args[1]);

		try {
			// TO-DO: Initialise Security Manager
			if (System.getSecurityManager() == null){
				System.setSecurityManager(new SecurityManager());
				System.out.println("Initlised Security Manager");
			}

			// TO-DO: Bind to RMIServer

			try {
				iRMIServer = (RMIServerI) Naming.lookup(urlServer);
				System.out.println("Bound to RMIServer");
				// for (int i = 0; i < numMessages; i++){
				// 	MessageInfo new_message = new MessageInfo(numMessages, i);
				// 	iRMIServer.receiveMessage(new_message);
				// }

			}
			// catch (RemoteException e){ 
			// 	System.out.println("Remote exception error: " + e);
			// }
			catch (NotBoundException e){ 
				System.out.println("Not bound exception error: " + e);
			}
			catch (MalformedURLException e) {
				System.out.println("Malformed URL Exception error: " + e);
			}

			// TO-DO: Attempt to send messages the specified number of time
			for (int n = 0; n < numMessages; n++) {
				System.out.println("Sending message: " + n);
				iRMIServer.receiveMessage(new MessageInfo(numMessages,n));
			}
		}
		catch (RemoteException e){ 
			System.out.println("Remote exception error in main: " + e);
		}

	}
}
