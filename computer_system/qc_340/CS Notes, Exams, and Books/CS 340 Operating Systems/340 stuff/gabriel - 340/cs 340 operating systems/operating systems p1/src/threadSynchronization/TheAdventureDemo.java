package threadSynchronization;
import java.lang.Thread;

public class TheAdventureDemo  {
	
	public static void main(String[]args) throws InterruptedException
	{

		TheAdventure advent = new TheAdventure();
		Clerk service= new Clerk("clerk", advent);
		Dragon battle= new Dragon("Dragon", advent);

		//Adventurer waiter = new Adventurer("Adventurer 1",adv);
		//Thread waiterThread = new Thread(waiter);
		//waiterThread.start();
		
		new Thread(new Adventurer("adventure 1", advent, service, battle)).run();
		new Thread(new Adventurer("adventure 2", advent, service,battle)).run();

		//Dragon notifier = new Dragon(advent);
		//Thread notifierThread = new Thread(battle);
		//notifierThread.start();
		
		System.out.println("The adventure is about to begain where ");
		Thread.sleep(2000);
		System.out.println("Everyone go!!");
		advent.startAdvanture();
		System.out.println("frar");
	}

}
