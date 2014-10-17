
/**
 * 
 * A Stub is a abstract class to be extended by any stub class, responsible for 
 * sending remote method invocation request to server
 * 
 * 
 */


public abstract class Stub {
	
    public String objectName = null;
    public Client client = null;
	
	public Stub (String _objectName, Client _client) {
		objectName = _objectName;
		client = _client;
	}
	
	public ServerReply callFunction(String objectName, String functionName, 
			Object[] functionArgs, Class<?> objectClass, Class<?>[] argumentTypes) {
		MessageCallFunction callFunctionMessage = new MessageCallFunction();
		callFunctionMessage.objectClass = objectClass;
		callFunctionMessage.objectName = objectName;
		callFunctionMessage.functionName = functionName;
		callFunctionMessage.functionArgs = functionArgs;
		callFunctionMessage.argumentTypes = argumentTypes;
		ServerReply response = (ServerReply)client.sendMessage(callFunctionMessage);
		return response;
	}
	
}
