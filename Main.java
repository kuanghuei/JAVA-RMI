import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * Class containing main method
 * 
 */


public class Main {

	// HOW TO USE???
	public static void main(String[] args) throws Exception {
		if (args.length == 1) {
			server(Integer.parseInt(args[0]));
		} else if (args.length == 2) {
			client(args[0], Integer.parseInt(args[1]));
		}
		System.out.println("end");
	}

	private static void server(int port) {

		Server server = new Server();
		
		System.out.println("Started");

		try {
			ServerSocket serverSocket = new ServerSocket(port);
			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("Get connection");
				ServerConnection process = new ServerConnection(clientSocket,
						server);
				new Thread(process).start();
			}
		} catch (IOException e) {
			// Do nothing, service is not closed
		}

		System.out.println("Server socket process terminated.");
	}

	private static void client(String host, int port) {

		Client client = new Client(host, port);
		client.connect();

		UserInteraction.beginUserInteraction(client);

		// Test create
		// Test duplicate create
		// Call function no args
		// Call function with args
		// !! Call function with exception
		// Call function to get result

		// Store client created object
		// System.out.println("\nTEST STORE CLIENT-CREATED OBJECT");
		// String clientObjectName = "client-created";
		// String[] args = new String[] { clientObjectName };
		// PrintingRemoteObjectImpl clientCreated = new
		// PrintingRemoteObjectImpl(
		// args);
		// client.storeObject(clientObjectName, clientCreated);

		// Try calling remote client created object
		// Lookup object
		// Delete object
		// Test deleting non-existing
	}
}
