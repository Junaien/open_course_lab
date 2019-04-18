package javaThreads;

public class TheAdventure {
	private int rank=0;
	
	//every 
	public synchronized void getReadToStart()throws InterruptedException
	{
		this.wait();
	}
	
	public synchronized void startAdvanture(){
		this.notifyAll();
	}
	
	public synchronized int RequiredItem(){
		return ++rank;
	}

}
