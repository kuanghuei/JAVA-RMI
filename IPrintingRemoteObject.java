/**
 * 
 * interface for PrintRemoteObject and its stub
 * 
 */


public interface IPrintingRemoteObject {
	public void printAndIncrement();

	public void printStringNTimes(String string, int repeatCount);

	public int getCurrentCounter();
}
