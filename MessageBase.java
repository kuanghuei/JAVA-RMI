

import java.io.Serializable;

/**
 * 
 * Represents a message sent between clients and servers for various RMI
 * functionalities.
 * 
 * */
public class MessageBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum Type {
		CREATE_OBJECT, CALL_FUNCTION, STORE_OBJECT, LOOK_UP_OBJECT, DELETE_OBJECT, GET_OBJECT_LIST
	}

	public Type type;
}
