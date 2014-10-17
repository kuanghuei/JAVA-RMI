import java.util.Arrays;

/**
 * 
 * A sample remote object that prints prime numbers.
 * 
 * */

public class PrintPrime extends RemoteObjectReference implements IPrintPrime {

	private static final long serialVersionUID = 1L;

	private String name;
	private boolean[] primes;
	private int n;
	private int i;

	public PrintPrime(String args[]){
		super(args);
		this.name = args[0];
		this.n = Integer.parseInt(args[1]);
		reset(n);
	}

	@Override
	public int nextPrime() {
		if (i > n) return n;
		while (primes[i] == false) {
			i++;
			if (i > n) return n;
		}
		int ret = i;
		for (int j = 2; i * j < primes.length; j++) {
			primes[i * j] = false;
		}
		i++;
		return ret;
	}
	
	private void reset(int _n){
		n = _n;
		primes = new boolean[n + 1];
		Arrays.fill(primes, true);
		primes[0] = primes[1] = false;
		i = 2;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String getClassName() {
		return PrintPrime.class.getName();
	}
}