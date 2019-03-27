import java.util.*;
public class Assignment5 implements Runnable
{
	static void printMessage(String message)
	{
		String threadName = Thread.currentThread().getName();	
		System.out.println(threadName + ":" + message);
	}//printMessage
	protected static final long age(long startTime)
	{
		//return System.currentTimeMillis() - startTime;
		int second = 0;
		Calendar current = new Calendar();
		current.setTimeInMillis(System.currentTimeMillis());
		second = current.get(SECOND);
	}//age
	public void run()
	{
		Random generator = new Random();
		int sleepTime = generator.nextInt(4000) + 1000;
		int i = 1;
		try
		{
			for(i = 1; i <= 4; i++)
			{
				printMessage("I am talking");
				Thread.sleep(sleepTime);
			}//for
		}//try
		catch(Exception e){};
	}//run
	public static void main(String args[])
	{
		Thread one = new Thread(new Assignment5());
		Thread two = new Thread(new Assignment5());
		Thread three = new Thread(new Assignment5());
		one.start();
		two.start();
		three.start();
	}//main
}//Assignment5