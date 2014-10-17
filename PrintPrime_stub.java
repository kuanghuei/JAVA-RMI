@SuppressWarnings("rawtypes")

/**
 * 
 * A sample remote object stub class for PrintPrime class.
 * 
 * */

public class PrintPrime_stub extends Stub implements IPrintPrime {

	Class<PrintPrime> objectClass = PrintPrime.class;

	public PrintPrime_stub(String _objectName, Client _client) {
		super(_objectName, _client);
	}

	@Override
	public int nextPrime() {
		Object[] functionArgs = new Object[] {};
		Class[] argumentTypes = new Class[0];
		ServerReply reply = callFunction(objectName, "nextPrime", functionArgs,
				objectClass, argumentTypes);
		UserInteraction.displayReply(reply);

		if (reply.success) {
			return (Integer) reply.functionReturnValue;
		}
		return -1;
	}

}
