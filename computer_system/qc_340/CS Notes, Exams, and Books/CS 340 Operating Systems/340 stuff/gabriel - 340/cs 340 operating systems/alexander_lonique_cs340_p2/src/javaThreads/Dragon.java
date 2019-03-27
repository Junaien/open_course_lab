package javaThreads;
import java.util.Random;
import java.util.concurrent.Semaphore;
public class Dragon extends StackUser{
	private long time=0;
	TheAdventure advent;			//dragon interface with shared data
	Adventurer player;				//dragon interface with adventurer 
	private static Semaphore mutex;

	private Random rand = new Random(System.currentTimeMillis());

	//Dragon interface with two threads
	public Dragon(String name, TheAdventure advent,StackClass stack) throws InterruptedException{
		super(name, stack);
		this.advent = advent;
		time=System.currentTimeMillis();	//get the birth time
		advent.getDragonSemaphore().acquire();
    	//advent.getadventMutex().acquire();
	}
	
	//dices game 
	public boolean gameOfDice() throws InterruptedException
	{
		msg(" is in battle "+ player.getName());
		int drag = Math.abs(this.rand.nextInt(6));
		int adve = Math.abs(this.rand.nextInt(6));
		msg(" rolled "+ drag+" and the adventurer "+ player.getName()+" rolled "+adve); 

		Thread.sleep(2000);
		if(adve>drag)return true;
		return false;
	}
	
	public int findItem(Adventurer player) throws InterruptedException
	{
		this.player =player;
		long duration = Math.abs((this.rand.nextLong()%2000)+1000);
		msg(" is battle "+player.getName()+" fight one!!");
		Thread.sleep(duration);		
		
		int ranItem = Math.abs(this.rand.nextInt(3));
		player.setItem(ranItem);
		Thread.sleep(1000);
		return ranItem;
	}
	
	public void goToCave(Adventurer player)throws InterruptedException
	{
		this.player =player;
		long duration = Math.abs((this.rand.nextLong()%2000)+1000);
		Thread.sleep(duration);
	}
	
	public void msg(String m) {
		System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
	}
	
	public void run() {
		try{
			mutex= new Semaphore(1);
			System.out.println("The Dragon is waiting for a adventurer (cough... victim)");
			while(!advent.isGameOver()){

				//busy waiting for adventure to call for the dragon 
				//while(!advent.getAvailable()&&!advent.isGameOver()){System.out.print(" ");}
				advent.getDragonSemaphore().acquire();	//busy wait
				if(advent.isGameOver())break;	//if game over get out

				//synchronized (advent){
				//mutex.acquire();
				advent.setIsInBattle(true);
				
					msg(" you may enter adventure "+player.getName()+" the battle area");
					
					if(this.gameOfDice()){
						int randItem = findItem(this.player);
						advent.setIsItheWinner(true);
						msg(" has lose "+player.getName()+ " won the following item from the dragon "+advent.helperArray[randItem]);
					}		
					else{
						player.setCounter(1);		//how many time player lose to the dragon

						if(player.getCounter()==2){
							player.getThread().setPriority(5);
							msg(player.getName()+ " you fail twice you priority is normal "+ player.getThread().getPriority());
							player.setCounter(0);
							
							Thread.yield();
						}
						else{
							advent.setIsItheWinner(false);
							msg(player.getName()+ " you have lose to the to me twice "+
									" and your current priority is back to normal "+ player.getThread().getPriority());
							player.getThread().setPriority(10);
							msg( player.getName()+" I shall grant you another chance, your priority is updgraded "+ player.getThread().getPriority());
						}
						
					}//end of outer else
			    	//advent.getadventMutex().release();
					//mutex.release();
					//advent.setAvailable(false);
					//advent.notify();
					//advent.setIsInBattle(false);
			//}
					advent.getDragonSemaphore().release();
			}//End of while loop
			msg("has gone home");
		
		}catch(InterruptedException ie){
			System.out.println("Error the dragon died");
		}
	}
}