@SuppressWarnings("rawtypes")

/**
 * 
 * A sample remote object stub class for PrintingRemoteObject class.
 * 
 * */

public class PrintingRemoteObject_stub extends Stub implements IPrintingRemoteObject {

	Class<PrintingRemoteObject> objectClass = PrintingRemoteObject.class;

	public PrintingRemoteObject_stub(String _objectName, Client _client) {
		super(_objectName, _client);
	}

	@Override
	public void printAndIncrement() {
		Object[] functionArgs = new Object[] {};
		Class[] argumentTypes = new Class[0];
		ServerReply reply = callFunction(objectName, "printAndIncrement",
				functionArgs, objectClass, argumentTypes);
		UserInteraction.displayReply(reply);
	}
	
	@Override
	public void printStringNTimes(String string, int repeatCount) {
		Object[] functionArgs = new Object[] { string, repeatCount };
		Class[] argumentTypes = new Class[] { String.class, int.class };
		ServerReply reply = callFunction(objectName, "printStringNTimes",
				functionArgs, objectClass, argumentTypes);
		UserInteraction.displayReply(reply);
	}

	@Override
	public int getCurrentCounter() {
		Object[] functionArgs = new Object[] {};
		Class[] argumentTypes = new Class[0];
		ServerReply reply = callFunction(objectName, "getCurrentCounter",
				functionArgs, objectClass, argumentTypes);
		UserInteraction.displayReply(reply);

		if (reply.success) {
			return (Integer) reply.functionReturnValue;
		}
		return -1;
	}

}
