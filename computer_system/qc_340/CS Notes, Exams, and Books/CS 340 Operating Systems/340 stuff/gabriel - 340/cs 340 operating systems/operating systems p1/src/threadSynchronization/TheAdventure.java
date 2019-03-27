package threadSynchronization;

public class TheAdventure {
	
	private int count =0;
	private boolean isDragonBusy = false;			//make the dragon wait
	private boolean isInBattle=false;			//let the adventurer know he has to wait
	private boolean isGameOver=false;			//tell the clerk and dragon to go home
	private boolean isServing=false;			//serving a player
	
	//setters, when changing data should be synchronized 
	public synchronized void setAvailable(boolean tf){isDragonBusy=tf;}
	
	public synchronized void setIsInBattle(boolean tf){isInBattle=tf;}
	
	public synchronized void setIsSeving(boolean tf){isInBattle=tf;}
	
	public synchronized void setIsGameOver(boolean tf){isGameOver=tf;}
	
	//getter
	public boolean getAvailable(){return isDragonBusy;}
	
	public boolean getIsInBattle(){return isInBattle;}
	
	public boolean getIsGameOver(){return isGameOver;}
	
	public boolean getIsServing(){return isServing;}
	
	//every 
	public synchronized void getReadToStart()throws InterruptedException
	{
		this.wait();
	}
	
	public synchronized void startAdvanture(){
		this.notifyAll();
	}
	
	public synchronized int numberOfThreads(){
		return ++count;
	}
	
}
