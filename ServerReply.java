import java.io.Serializable;

/** Represents a reply message from the server. Defaults to success. **/
public class ServerReply implements Serializable {

	private static final long serialVersionUID = 1L;

	public boolean success;
	public boolean hasException;
	public String exceptionMessage;

	// The file name for the serialized lookup object
	public String serializedLookupObjectFileName;

	// The return value for remote procedure calls
	public Object functionReturnValue;

	// The class of the remote object
	public String className;

	// String displaying available objects on server
	public String objectList;

	public ServerReply() {
		success = true;
		hasException = false;
		exceptionMessage = null;
		serializedLookupObjectFileName = null;
		functionReturnValue = null;
		className = null;
		objectList = null;
	}

	public void setException(String error) {
		success = false;
		hasException = true;
		exceptionMessage = error;
	}

	public static ServerReply success() {
		return new ServerReply();
	}

}
