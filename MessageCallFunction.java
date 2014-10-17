

/**
 * Message used to invoke a function on a remote object
 * */
public class MessageCallFunction extends MessageBase {

	private static final long serialVersionUID = 1L;

	
	public String objectName;
	public String functionName;
	public Object[] functionArgs;
	
	
	@SuppressWarnings("rawtypes")
	public Class objectClass;
	
	@SuppressWarnings("rawtypes")
	public Class[] argumentTypes;

	public MessageCallFunction() {
		super();
		this.type = Type.CALL_FUNCTION;
	}
}
