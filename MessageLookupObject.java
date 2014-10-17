

/**
 * Message sent to server to get an object, server will reply with the
 * serialized file name.
 **/
public class MessageLookupObject extends MessageBase {

	private static final long serialVersionUID = 1L;

	public String objectName;

	public MessageLookupObject() {
		super();
		this.type = Type.LOOK_UP_OBJECT;
	}
}
