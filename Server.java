import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;

/**
 * 
 * Represents a remote server. Responsible for parsing messages and relaying
 * to/from Registry.
 * 
 * */
public class Server {

	private Registry registry;

	public Server() {
		registry = new Registry();
	}

	/** Delegate function that handles different messages **/
	public ServerReply receiveMessage(MessageBase message) {
		try {
			if (message.type == MessageBase.Type.CREATE_OBJECT) {
				onCreateObject(message);
				return ServerReply.success();
			} else if (message.type == MessageBase.Type.CALL_FUNCTION) {
				// Include function return value in reply
				Object returnValue = onCallFunction(message);
				ServerReply reply = ServerReply.success();
				reply.functionReturnValue = returnValue;

				if (returnValue != null) {
					System.out.println("Returned: " + returnValue);
				}
				return reply;
			} else if (message.type == MessageBase.Type.STORE_OBJECT) {
				onObjectStore(message);
				return ServerReply.success();
			} else if (message.type == MessageBase.Type.LOOK_UP_OBJECT) {
				// Include serialized file name in reply
				String serializedFileName = onObjectLookup(message);
				ServerReply reply = ServerReply.success();
				reply.serializedLookupObjectFileName = serializedFileName;

				// Include class name
				MessageLookupObject lookupMessage = (MessageLookupObject) message;
				reply.className = registry
						.getClassName(lookupMessage.objectName);
				return reply;
			} else if (message.type == MessageBase.Type.DELETE_OBJECT) {
				onObjectDelete(message);
				return ServerReply.success();
			} else if (message.type == MessageBase.Type.GET_OBJECT_LIST) {
				String objectList = registry.getObjectList();
				ServerReply reply = ServerReply.success();
				reply.objectList = objectList;
				return reply;
			}

			// Unrecognized message
			ServerReply error = new ServerReply();
			error.setException("Unrecognized message type: " + message.type);
			return error;
		} catch (RemoteException e) {
			System.out.println(e.toString());

			// Return error response
			ServerReply error = new ServerReply();
			error.setException(e.toString());
			return error;
		}
	}

	/** Create object handler **/
	private void onCreateObject(MessageBase message) throws RemoteException {
		MessageCreate createMessage = (MessageCreate) message;
		registry.createObject(createMessage.className,
				createMessage.constructorArgs, createMessage.nameToRegister);
	}

	/**
	 * Call function handler, uses reflection. Returns the function's return
	 * value which can be null.
	 **/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object onCallFunction(MessageBase message) throws RemoteException {
		MessageCallFunction callMessage = (MessageCallFunction) message;

		try {
			Class objectClass = callMessage.objectClass;
			String functionName = callMessage.functionName;
			Class[] parameterTypes = callMessage.argumentTypes;
			Method method = objectClass.getDeclaredMethod(functionName,
					parameterTypes);

			Object object = registry.getObject(callMessage.objectName);
			Object result = method.invoke(object, callMessage.functionArgs);
			return result;
		} catch (Exception e) {
			throw new RemoteException(callMessage.objectName, e.toString());
		}
	}

	/** Handler for storing client-created object **/
	private void onObjectStore(MessageBase message) throws RemoteException {
		MessageStoreObject storeMessage = (MessageStoreObject) message;

		try {
			registry.storeObject(storeMessage.objectName,
					storeMessage.objectToStore);
		} catch (Exception e) {
			throw new RemoteException(storeMessage.objectName, e.toString());
		}
	}

	/**
	 * Handler to lookup an object, it will be serialized if it exists. The
	 * serialized file name is returned.
	 **/
	private String onObjectLookup(MessageBase message) throws RemoteException {
		MessageLookupObject lookupMessage = (MessageLookupObject) message;

		try {
			RemoteObjectReference object = registry
					.getObject(lookupMessage.objectName);

			FileOutputStream fileOS = new FileOutputStream(object.getName(),
					false);
			ObjectOutputStream objectOS = new ObjectOutputStream(fileOS);
			objectOS.writeObject(object);
			objectOS.close();
			fileOS.close();
			// System.out.println(object.getName() + " found and serialized.");

			return object.getName();
		} catch (Exception e) {
			throw new RemoteException(lookupMessage.objectName, e.toString());
		}
	}

	/** Handler to delete an object, throws exception if it does not exist. **/
	private void onObjectDelete(MessageBase message) throws RemoteException {
		MessageDelete deleteMessage = (MessageDelete) message;
		registry.deleteObject(deleteMessage.objectName);
	}
}
