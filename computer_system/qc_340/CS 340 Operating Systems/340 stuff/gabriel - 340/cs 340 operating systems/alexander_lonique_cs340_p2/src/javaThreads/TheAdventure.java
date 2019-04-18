package javaThreads;

import java.util.concurrent.Semaphore;

public class TheAdventure {
	private int count =0;
	private boolean isDragonBusy = false;		//make the dragon wait
	private boolean isInBattle=false;			//let the adventurer know he has to wait
	private boolean isGameOver=false;			//tell the clerk and dragon to go home
	private boolean need_assistance =false;		//clerk wait for adventurer 
	private boolean isServing=false;			//serving a player
	private boolean isItheWinner=false;
	private static Semaphore dragonMutex= new Semaphore(1);
	private static Semaphore adventMutex= new Semaphore(1);
	private static Semaphore clerkSem= new Semaphore(2);
	private static Semaphore startGame; 
	private static Semaphore dragonResponseMutex= new Semaphore(1);
	public String helperArray[]= new String[]{"stone", "ring","necklaces", "magical ring","magical necklace" };

	
	//setters, when changing data should be synchronized 
	public void setAvailable(boolean tf){isDragonBusy=tf;}
	
	public void setIsInBattle(boolean tf){isInBattle=tf;}
	
	public void setIsSeving(boolean tf){isServing=tf;}
	
	public void setIsGameOver(boolean tf){isGameOver=tf;}
	
	public void setIsItheWinner(boolean tf){isItheWinner=tf;}
	
	public void setNeed_assistance(boolean tf){need_assistance=tf;}
	
	//getter
	public boolean getAvailable(){return isDragonBusy;}
	
	public boolean isInBattle(){return isInBattle;}
	
	public boolean isGameOver(){return isGameOver;}
	
	public boolean isServing(){return isServing;}
	
	public boolean isItheWinner(){return isItheWinner;}
	
	public boolean getNeed_assistance(){return need_assistance;}

	public int numberOfThreads(){
		return ++count;
	}
	//only method that allowed to be synchronize are the sem
	public synchronized Semaphore getDragonSemaphore(){return dragonMutex;}
	
	public synchronized Semaphore getdragonResponseMutex(){return dragonResponseMutex;}
	
	public synchronized Semaphore getadventMutex(){return adventMutex;}
	
	public synchronized Semaphore getclerkSem(){return clerkSem;}

	

}
