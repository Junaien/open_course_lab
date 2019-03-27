package javaThreads;
import java.lang.Thread;
public class TheAdventureDemo  {
	
	public static void main(String[]args) throws InterruptedException
	{
		//create a adventure instance
		TheAdventure adv= new TheAdventure();
		Clerk service = new Clerk("Clerk");
		
		
		//Create 8 adventures
		new Thread(new Adventurer("Lonique", adv, service)).start();
		new Thread(new Adventurer("Alton", adv, service)).start();
		//new Thread(new Adventurer("son gohan", adv, service, fightOn)).start();
		new Thread(new Adventurer("Meenal", adv, service)).start();
		//new Thread(new Adventurer("Dr. Fluture", adv, service, fightOn)).start();
		//new Thread(new Adventurer("son goku", adv, service, fightOn)).start();
		//new Thread(new Adventurer("Super man", adv, service, fightOn)).start();
		//new Thread(new Adventurer("bat man", adv, service, fightOn)).start();
		//new Thread(new Dragon("baby Dragon"));
		//ok, now we need wait a bit
		System.out.println("The adventure is about to begain where 8 lucky adventurer will search for magical item");
		Thread.sleep(1000);
		System.out.println("All adventurers are off to find items...");
		adv.startAdvanture();
		
	}

}
