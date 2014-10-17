


/**
 * Message used to store a client-created object on server.
 * */
public class MessageStoreObject extends MessageBase {

	private static final long serialVersionUID = 1L;

	public String objectName;
	public RemoteObjectReference objectToStore;
	public String className;

	public MessageStoreObject() {
		super();
		this.type = Type.STORE_OBJECT;
	}
}