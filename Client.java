import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Hashtable;

/**
 * 
 * Represent a Client host, it handles TCP connection from client to server,
 *  and it makes remote object managing requests to server 
 *
 */
public class Client {

	private String serverHostname;
	private int serverPort;
	private Socket clientSocket = null;

	private ObjectInputStream inputStream = null;
	private ObjectOutputStream outputStream = null;

	private Hashtable<String, Object> stubs = new Hashtable<String, Object>();

	public Client(String host, int port) {
		setHostPort(host, port);
	}

	public void setHostPort(String host, int port) {
		serverHostname = host;
		serverPort = port;
	}

	public String getHostname() {
		return serverHostname;
	}

	public int getPort() {
		return serverPort;
	}

	public void connect() {

		// establish connection and object stream
		try {
			clientSocket = new Socket(serverHostname, serverPort);
			System.out.println("Client: connected to server\n");
			outputStream = new ObjectOutputStream(
					clientSocket.getOutputStream());
			outputStream.flush();
			inputStream = new ObjectInputStream(clientSocket.getInputStream());
		} catch (Exception e) {
			System.out.println("Connection issue: " + e.toString());
		}

	}

	public void closeSocket() {
		if (clientSocket != null) {
			try {
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("clientSocket is already closed");
		}
	}

	public Object sendMessage(MessageBase msg) {

		Object response = null;

		try {
			outputStream.writeObject(msg);
			response = inputStream.readObject();
		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}

		return response;
	}

	public ServerReply listObjects() {
		MessageBase list = new MessageBase();
		list.type = MessageBase.Type.GET_OBJECT_LIST;
		return (ServerReply) sendMessage(list);
	}

	public ServerReply createObject(String className, String[] constructorArgs,
			String nameToRegister) {
		MessageCreate createMessage = new MessageCreate();
		createMessage.className = className;
		createMessage.constructorArgs = constructorArgs;
		createMessage.nameToRegister = nameToRegister;
		ServerReply response = (ServerReply) sendMessage(createMessage);
		return response;
	}

	public boolean storeObject(String clientObjectName,
			RemoteObjectReference objectToStore) {
		MessageStoreObject storeMessage = new MessageStoreObject();
		storeMessage.objectName = clientObjectName;
		storeMessage.objectToStore = objectToStore;
		ServerReply response = (ServerReply) sendMessage(storeMessage);
		return response.success;
	}

	public ServerReply lookup(String objectName) {
		MessageLookupObject lookup = new MessageLookupObject();
		lookup.objectName = objectName;
		ServerReply response = (ServerReply) sendMessage(lookup);
		return response;
	}

	public ServerReply delete(String objectName) {
		MessageDelete delete = new MessageDelete();
		delete.objectName = objectName;
		ServerReply response = (ServerReply) sendMessage(delete);
		return response;
	}

	public void storeStub(String name, Object stub) {
		stubs.put(name, stub);
	}

	public void deleteStub(String name) {
		stubs.remove(name);
	}

	public Object getStub(String name) {
		return stubs.get(name);
	}

	public boolean containsStub(String name) {
		return stubs.containsKey(name);
	}

	public void list() {
		// return an array containing the names of all of the registered objects
	}

}
