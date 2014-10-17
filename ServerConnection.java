import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 
 * Responsible for handling TCP connection and Object IO Stream to 1 client
 * 
 */

public class ServerConnection implements Runnable {
	
	private Socket clientSocket;
	private ObjectInputStream objectIS;
	private ObjectOutputStream objectOS;
	private Server server;
	private boolean suspended = false;

	public ServerConnection(Socket socket, Server _server) {
		clientSocket = socket;
		server = _server;
		try {
			objectOS = new ObjectOutputStream(clientSocket.getOutputStream());
			objectOS.flush();
			objectIS = new ObjectInputStream(clientSocket.getInputStream());
		} catch (IOException e) {
			System.out.println("Connection issue: " + e.toString());
		}		
	}

	public void suspend() {
		suspended = true;
	}

	@Override
	public void run() {
		while (!suspended) {
			try {
				MessageBase message = (MessageBase) objectIS.readObject();
				ServerReply response = server.receiveMessage(message);
				objectOS.writeObject(response);
			} catch (Exception e) {
				break;
			}
		}
	}
	
}
