

/**
 * Represents a remote exception. Contains name of the remote object that caused
 * the exception.
 * */
public class RemoteException extends Exception {

	private static final long serialVersionUID = 1L;

	private String objectName;
	private String exceptionMessage;

	public RemoteException(String objectName, String exceptionMessage) {
		this.objectName = objectName;
		this.exceptionMessage = exceptionMessage;
	}

	public String toString() {
		return "Remote Exception (" + objectName + "): " + exceptionMessage;
	}
}
