/**
 * @author Kareem Francis  * CS 340  * Fall '11  * 11/6/11
 * Implements threads in Java
 *
 */
public class HW4Main {
	
	private static final long startTime = System.currentTimeMillis();
	
	protected static final long age() {
		return System.currentTimeMillis() - startTime;
	}
	
	public static void main(String[] args) {
		new KThread("t0");
		new KThread("t1");
		new KThread("t2");
	
	}
}
