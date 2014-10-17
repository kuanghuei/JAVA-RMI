
/**
 * 
 * A sample remote object that prints messages.
 * 
 * */
public class PrintingRemoteObject extends RemoteObjectReference implements IPrintingRemoteObject {

	private static final long serialVersionUID = 1L;

	private String name;
	private int counter = 1;

	public PrintingRemoteObject(String[] args) {
		super(args);
		this.name = args[0];
	}
	
	@Override
	public void printAndIncrement() {
		System.out.println(name + " printAndIncrement called: " + counter);
		counter++;
	}

	@Override
	public void printStringNTimes(String string, int repeatCount) {
		for (int i = 0; i < repeatCount; i++) {
			System.out.println(name + ": " + string);
		}
	}

	@Override
	public int getCurrentCounter() {
		return counter;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getClassName() {
		return PrintingRemoteObject.class.getName();
	}
}
