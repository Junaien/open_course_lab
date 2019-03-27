package javaThreads;
import java.util.Random;

public class Clerk {
	private String name;
	Adventurer advent;
	private Random rand = new Random(System.currentTimeMillis());

	public Clerk(String name){this.name = name;}
	
	public synchronized long getItem() throws InterruptedException
	{
		//
		long duration = Math.abs((this.rand.nextLong()%2000)+1000);
		Thread.sleep(duration);
		return duration;
	}
	
}
