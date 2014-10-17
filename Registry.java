import java.lang.reflect.Constructor;
import java.util.Hashtable;

/**
 * 
 * A registry responsible for creating and retrieving remote objects.
 * 
 * */
public class Registry {

	private Hashtable<String, Object> registry;

	/**
	 * Constructor
	 * */
	public Registry() {
		registry = new Hashtable<String, Object>();
	}

	/**
	 * Method to create and register object
	 * */
	public void createObject(String className, String[] args, String objectName)
			throws RemoteException {
		Object[] constructorArgs = { (Object[]) args };

		// Check if object already exists
		if (registry.containsKey(objectName)) {
			throw new RemoteException(objectName, "Already exists.");
		}

		try {
			Constructor<?> constructor = Class.forName(className)
					.getConstructor(String[].class);

			// Create remote object and store it
			RemoteObjectReference object = (RemoteObjectReference) constructor
					.newInstance(constructorArgs);
			registry.put(objectName, object);

			System.out.println("Created: " + objectName);
		} catch (Exception e) {
			throw new RemoteException(objectName, e.toString());
		}
	}

	/**
	 * Method to retrieve object
	 * */
	public RemoteObjectReference getObject(String objectName)
			throws RemoteException {
		if (registry.containsKey(objectName)) {
			return (RemoteObjectReference) registry.get(objectName);
		} else {
			throw new RemoteException(objectName, "Does not exist.");
		}
	}

	/**
	 * Method to store client-created object
	 * */
	public void storeObject(String objectName, Object object) {
		registry.put(objectName, object);
		System.out.println("Stored: " + objectName);
	}

	/**
	 * Method to delete an object
	 * */
	public void deleteObject(String objectName) throws RemoteException {
		if (registry.containsKey(objectName)) {
			registry.remove(objectName);
			System.out.println(objectName + " deleted.");
		} else {
			throw new RemoteException(objectName,
					"Does not exist, cannot delete.");
		}
	}

	/**
	 * Method that lists the objects in registry and their class names
	 * */
	public String getObjectList() {
		StringBuilder builder = new StringBuilder();

		for (String objectName : registry.keySet()) {
			RemoteObjectReference object = (RemoteObjectReference) registry
					.get(objectName);
			builder.append(object.getName() + "\t" + object.getClassName()
					+ "\n");
		}

		return builder.toString();
	}

	/**
	 * Method that returns the class name of an object
	 * */
	public String getClassName(String objectName) throws RemoteException {
		if (registry.containsKey(objectName)) {
			RemoteObjectReference object = (RemoteObjectReference) registry
					.get(objectName);
			return object.getClassName();
		} else {
			throw new RemoteException(objectName, "Does not exist.");
		}
	}
}
