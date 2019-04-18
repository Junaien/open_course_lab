package javaThreads;
import java.lang.Thread;

public class TheAdventureDemo{
	
	public static void main(String[]args) throws InterruptedException
	{
		//stack for clerk and Dragon
		StackClass stackA = new StackClass(100);
		StackClass stackB = new StackClass(100);
		
		//create a adventure instance
		TheAdventure adv= new TheAdventure();
		
		//clerk and dragon instance 
		Clerk service = new Clerk("Clerk", adv, stackA);
		Dragon fightOn = new Dragon("Dragon", adv, stackB);
		
		//Create 2 adventures service threads 
		new Thread(service, "Clerk").start();
		new Thread(fightOn, "Dragon").start();
		
		//Create 8 adventures threads
		new Thread(new Adventurer("Thread0", adv, service, fightOn, stackA)).start();
		new Thread(new Adventurer("Thread1", adv, service, fightOn, stackA)).start();
		new Thread(new Adventurer("Thread2", adv, service, fightOn, stackA)).start();
		new Thread(new Adventurer("Thread3", adv, service, fightOn, stackA)).start();
		new Thread(new Adventurer("Thread4", adv, service, fightOn, stackA)).start();
		new Thread(new Adventurer("Thread5", adv, service, fightOn, stackA)).start();
		new Thread(new Adventurer("Thread6", adv, service, fightOn, stackA)).start();
		new Thread(new Adventurer("Thread7", adv, service, fightOn, stackA)).start();
	}

}
