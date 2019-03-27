package javaThreads;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Clerk extends StackUser{
	private TheAdventure advent;
	private Adventurer player;
	private long time=0;
	private Random rand = new Random(System.currentTimeMillis());
	private static Semaphore mutex;

	Clerk(String threadName, TheAdventure advent, StackClass stack) {
		super(threadName, stack);
		this.advent=advent;	
		time=System.currentTimeMillis();	//get the birth time

	}
	
	public synchronized void callForClerk(Adventurer player)throws InterruptedException
	{
		this.player=player;
		long duration = Math.abs((this.rand.nextLong()%2000)+1000);
		Thread.sleep(duration);
	}
	
	public void msg(String m) {
		System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
	}
	
	public void run() {
		try{	
		System.out.println("The Clerk is waiting to provide a sevice (cough... rob an adventurer)");

		while(!advent.isGameOver()){
			//busy waiting for adventure 

			while(!advent.getNeed_assistance()&& !advent.isGameOver()){}
			
			if(advent.isGameOver())break;	//if game over get out
			
			//synchronized(advent){
			advent.getclerkSem();
				advent.setIsSeving(true);
				msg("is serving adventurer "+player.getName());
				
				Thread.sleep(5000);
				stack.pop();
				//advent.notify();

				advent.setIsSeving(false);
			//}
			advent.getclerkSem().release();
			
		}//end of while loop
		
		msg("has gone home");
		}catch(InterruptedException ie){
			System.out.println("Error clerk died");
		}
	}
}