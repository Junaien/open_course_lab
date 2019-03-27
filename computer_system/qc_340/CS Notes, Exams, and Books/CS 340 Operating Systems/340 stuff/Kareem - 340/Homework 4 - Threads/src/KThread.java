import java.util.Random;

/**
 * @author Kareem Francis  * CS 340  * Fall '11  * 11/6/11
 * Implements threads in Java
 *
 */
public class KThread implements Runnable {
	
	private Thread t;
	private Random rand;
	
	public KThread(String threadName){
		t = new Thread(this, threadName);
		System.out.println("@Time: " + HW4Main.age() + 
				" Creating thread: " + t.getName());
		t.start();	
	}
	
	public void  run() {
		rand = new Random();
		int napTime;
		while(true){
			try {
				napTime = rand.nextInt(1001); 
				System.out.println("@Time: " + HW4Main.age() +
						", executing " + t.getName() + ", going to nap for: " + napTime);
				Thread.sleep(napTime);
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}
	}
}
