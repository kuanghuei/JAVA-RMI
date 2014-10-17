import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * Class for handling user interactions in demo.
 * 
 * Nothing interesting to see here.
 * 
 * */
public class UserInteraction {

	// TODO: Demo store locally created object?

	// Possible commands:

	// listObjects

	// create printing AA
	// get AA
	// delete AA
	// call AA printAndIncrement
	// call AA printStringNTimes testString 10
	// call AA getCurrentCounter

	// create prime BB 100
	// call BB nextPrime

	// exit

	public static void beginUserInteraction(final Client client) {
		InputStreamReader isReader = new InputStreamReader(System.in);
		BufferedReader bufferRead = new BufferedReader(isReader);
		String line = "";

		// Just an infinite loop that reads lines and parses them
		while (line != null && !line.equalsIgnoreCase("exit")) {
			try {
				line = bufferRead.readLine();

				if (line != null) {

					// Run in a separate thread so user input is not blocked
					final String fline = line;
					new Thread(new Runnable() {
						@Override
						public void run() {
							parseCommand(fline, client);
							System.out.println("");
						}
					}).start();
				}
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
	}

	private static void parseCommand(String line, Client client) {

		String[] tokens = line.split(" ");

		if (tokens[0].equalsIgnoreCase("listObjects") && tokens.length == 1) {
			ServerReply reply = client.listObjects();
			if (reply.success) {
				System.out.println("Remote Objects:");
				System.out.println(reply.objectList);
			} else {
				displayReply(reply);
			}
		} else if (tokens[0].equalsIgnoreCase("create")
				&& tokens[1].equalsIgnoreCase("printing") && tokens.length == 3) {
			// Create a printing object on server
			System.out.println("Creating " + tokens[2] + " on server");
			String objectName = tokens[2];
			String className = PrintingRemoteObject.class.getName();
			String[] constructorArgs = new String[] { objectName };
			ServerReply reply = client.createObject(className, constructorArgs,
					objectName);
			displayReply(reply);
		} else if (tokens[0].equalsIgnoreCase("create")
				&& tokens[1].equalsIgnoreCase("prime") && tokens.length == 4) {
			// Create a print prime object on server
			System.out.println("Creating " + tokens[2] + " on server");
			String objectName = tokens[2];
			String className = PrintPrime.class.getName();
			int limit = Integer.parseInt(tokens[3]);
			String[] constructorArgs = new String[] { objectName,
					Integer.toString(limit) };
			ServerReply reply = client.createObject(className, constructorArgs,
					objectName);
			displayReply(reply);
		} else if (tokens[0].equalsIgnoreCase("get") && tokens.length == 2) {
			// Lookup an object on server, if it exists, create stub
			lookupAndCreateStub(tokens[1], client);
		} else if (tokens[0].equalsIgnoreCase("delete") && tokens.length == 2) {
			// Delete an object on server
			System.out.println("Deleting " + tokens[1] + " on server");
			String objectName = tokens[1];
			ServerReply reply = client.delete(objectName);
			displayReply(reply);

			if (reply.success && client.containsStub(objectName)) {
				client.deleteStub(objectName);
				System.out.println("Stub deleted");
			}
		} else if (tokens[0].equalsIgnoreCase("call") && tokens.length == 3
				&& tokens[2].equalsIgnoreCase("printAndIncrement")) {
			// Print and increment
			String objectName = tokens[1];
			boolean success = true;

			if (!client.containsStub(objectName)) {
				success = lookupAndCreateStub(tokens[1], client);
			}
			if (success) {
				System.out.println("Calling print and increment on server.");
				PrintingRemoteObject_stub stub = (PrintingRemoteObject_stub) client
						.getStub(objectName);
				stub.printAndIncrement();
			}
		} else if (tokens[0].equalsIgnoreCase("call") && tokens.length == 5
				&& tokens[2].equalsIgnoreCase("printStringNTimes")) {
			// Print a string N times on server
			String objectName = tokens[1];
			boolean success = true;

			if (!client.containsStub(objectName)) {
				success = lookupAndCreateStub(tokens[1], client);
			}
			if (success) {
				System.out.println("Calling print string n times on server.");
				PrintingRemoteObject_stub stub = (PrintingRemoteObject_stub) client
						.getStub(objectName);
				stub.printStringNTimes(tokens[3], Integer.parseInt(tokens[4]));
			}
		} else if (tokens[0].equalsIgnoreCase("call") && tokens.length == 3
				&& tokens[2].equalsIgnoreCase("getCurrentCounter")) {
			// Get object's current counter
			String objectName = tokens[1];
			boolean success = true;

			if (!client.containsStub(objectName)) {
				success = lookupAndCreateStub(tokens[1], client);
			}
			if (success) {
				System.out.println("Calling get current counter on server.");
				PrintingRemoteObject_stub stub = (PrintingRemoteObject_stub) client
						.getStub(objectName);
				int result = stub.getCurrentCounter();
				if (result != -1) {
					System.out.println("Current counter is: " + result);
				}
			}
		} else if (tokens[0].equalsIgnoreCase("call") && tokens.length == 3
				&& tokens[2].equalsIgnoreCase("nextPrime")) {
			// Get next prime number
			try {
				String objectName = tokens[1];
			
				boolean success = true;
	
				if (!client.containsStub(objectName)) {
					success = lookupAndCreateStub(tokens[1], client);
				}
				if (success) {
					System.out.println("Calling get next prime on server.");
					PrintPrime_stub stub = (PrintPrime_stub) client
							.getStub(objectName);
					int result = stub.nextPrime();
					if (result != -1) {
						System.out.println("Prime is: " + result);
					}
				}
			} catch(ClassCastException e) {
				System.out.println("Error: Calling a funciton not supported "
						+ "by this funciton");
			}
		} else if (tokens[0].equalsIgnoreCase("exit") && tokens.length == 1) {
			// Exit
			System.out.println("Exiting program");
		} else {
			System.out.println("Invalid command");
		}
	}

	private static boolean lookupAndCreateStub(String objectName, Client client) {
		System.out.println("Looking up " + objectName + " on server");
		ServerReply reply = client.lookup(objectName);	
		displayReply(reply);
		
		if (reply == null) {
			return false;
		}

		// Create stub based on the object's class
		if (reply.success) {
			if (reply.className.equalsIgnoreCase(PrintingRemoteObject.class
					.getName())) {
				PrintingRemoteObject_stub stub = new PrintingRemoteObject_stub(
						objectName, client);
				client.storeStub(objectName, stub);
			} else if (reply.className.equalsIgnoreCase(PrintPrime.class
					.getName())) {
				PrintPrime_stub stub = new PrintPrime_stub(objectName, client);
				client.storeStub(objectName, stub);
			}
			System.out.println("Stub created");
		}
		return reply.success;
	}

	public static void displayReply(ServerReply reply) {
		if (reply != null) {
			if (reply.success) {
				System.out.println("Success");
			} else {
				System.out.println(reply.exceptionMessage);
			}
		}
	}
}
