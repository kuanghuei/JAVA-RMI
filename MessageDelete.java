

/**
 * Message used to delete objects on server.
 * */
public class MessageDelete extends MessageBase {

	private static final long serialVersionUID = 1L;

	public String objectName;

	public MessageDelete() {
		super();
		this.type = Type.DELETE_OBJECT;
	}
}