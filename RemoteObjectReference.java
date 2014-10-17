
import java.io.Serializable;

/**
 * 
 * An abstract base class for remote objects references.
 * 
 * For uniformity and ease of implementation, the constructor should take in an
 * array of String argument.
 * 
 * Important: all values to be returned from remote procedure calls must be
 * serializable. Otherwise they cannot be sent to the clients.
 * 
 * */
public abstract class RemoteObjectReference implements Serializable {

	private static final long serialVersionUID = 1L;

	public RemoteObjectReference(String[] args) {
	}

	public abstract String getName();

	public abstract String getClassName();
}
