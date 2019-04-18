package threadSynchronization;
import java.util.Date;
import java.util.Random;
public class Clerk implements Runnable{
	private String name;
	private TheAdventure advent;
	private Random rand = new Random(System.currentTimeMillis());

	public Clerk(String name, TheAdventure advent){
		this.name = name;
		this.advent=advent;
	}
	
	public synchronized long getItem() throws InterruptedException
	{
		//
		long duration = Math.abs((this.rand.nextLong()%2000)+1000);
		Thread.sleep(duration);
		return duration;
	}
	
	//@Override
	public void run() {
		while(true){
			//busy waiting for adventure 
			while(!advent.getIsServing()){;}
			
			advent.setIsSeving(true);
			
			synchronized (advent) {
				System.out.println("The Dragon is notifying to come battle " + new Date());
				//startBattle();
			}
			
			
			
			if(advent.getIsGameOver()){
				System.out.println("Clerk is gone home");
				return;
			}
			
		}
	}
	
}
