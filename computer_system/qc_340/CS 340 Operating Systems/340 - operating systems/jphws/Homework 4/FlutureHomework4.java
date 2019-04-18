import java.util.Random;

public class FlutureHomework4 implements Runnable
{	
	public static long startTime = System.currentTimeMillis();
	
	public void run() 
	{
		/**
		 * Puts a thread to sleep at a random time (no more than 5 seconds) and 
		 * prints the name of the thread and the age of the thread
		 */
		
		while(true)
		{
			System.out.println(Thread.currentThread().getName() + " is: " + age() + " milliseconds old");
			try
			{
				Thread.sleep(RandomNumber());
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}	
	
	protected static final long age() 
	{
		/**
		 * Returns the age of the thread
		 */
		
		return (System.currentTimeMillis() - startTime);
	}
	
	
	public static int RandomNumber()
	{
		/**
		 * Returns a random number no more than 5000
		 */
		
		Random rand = new Random();
		int TimeToSleep = rand.nextInt(5000);
		return TimeToSleep;
	}
	
	public static void main(String[] args) throws InterruptedException
	{	
		/**
		 * Creates three threads and starts them 
		 */
		
		Thread T0 = new Thread(new FlutureHomework4());
		Thread T1 = new Thread(new FlutureHomework4());
		Thread T2 = new Thread(new FlutureHomework4());
		T0.start();
		T1.start();
		T2.start();
	}
}	

