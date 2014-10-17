

/**
 * Message used to create new objects on server.
 * */
public class MessageCreate extends MessageBase {

	private static final long serialVersionUID = 1L;

	public String className;
	public String[] constructorArgs;
	public String nameToRegister;

	public MessageCreate() {
		super();

		this.type = Type.CREATE_OBJECT;
	}
}
