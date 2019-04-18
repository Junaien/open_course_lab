import java.util.*;

/**
 * CSCI 340 Project 2
 * Class of 6:30 - 7:45 pm
 * 
 * This is a double submission, I have also done Option 1 as well.
 * This thread is visitors.
 * 
 * This thread uses no synchronized methods only uses semaphores.  The visitors forming group is 
 * done concurrently, we allow 1 visitor to go through and blocking the rest.  So the remaining
 * 8 visitor is queued up, and at same time group is formed for all.  First 3 queued is group 1, 
 * the second 3 queued is group 2 and the third 3 queued is group 3.  
 * 
 * The last visitor getting off will let 1 visitor from the queue go through and repeat.  
 * The visitors wait until visitor n-1 has left before the visitor can leave.
 * 
 * The visitors are stored in a group class where each group has a vector in storing the visitors.
 * 
 * Date Finished May 16, 2008
 * @Yachao Liu 
*/

public class Visitor extends Thread{
	
	Project2 main;//instance of Project 2
	String name;//name
	int id;//identification for each visitor -- a helper variable
	
	/**
	 * Visitor Constructor
	 * 
	 * @param m Project2 an instance
	 * @param n String the name
	 * @param i int the ID
	 */
	public Visitor(Project2 m, String n, int i){
		main = m;
		name = n;
		id = i;
	}//constructor
	
	/**
	 * This method returns the name of the visitor
	 * 
	 * @return String the name of the visitor
	 */
	public String name(){
		return name;
	}//getName
	
	public void printMessage(String message){
		startTime();
		System.out.println(this.name + ":" + message);
	}//printMessage
	
	/**
	 * This method finds the time
	 *
	 */
	public static final void startTime(){
		int minute = 0, second = 0, milSec = 0;
		Calendar current = Calendar.getInstance();
		current.setTimeInMillis(System.currentTimeMillis());
		minute = current.get(Calendar.MINUTE);
		second = current.get(Calendar.SECOND);
		milSec = current.get(Calendar.MILLISECOND);
		System.out.print(minute + ":" + second + ":" + milSec + " -- ");
	}//startTime
	
	/**
	 * Run method of the thread
	 */
	public void run(){
		Random generator = new Random();
		int sleepTime = 0;//random sleep time
		sleepTime = generator.nextInt(300) + 200;//sleep 
		
		try{
		Thread.sleep(sleepTime);
		printMessage("Arrives at the museum.");
		printMessage("Is wandering around.");
		Thread.sleep(sleepTime);
		
		//forms group
		main.group.acquire();//allow 1 visitor to go, while others queue up
		
		main.mutex.acquire();
		main.visitorCounter++;//critical section
		main.mutex.release();
		
		printMessage("Joins group #" + main.groupCounter);
		
		//group not form, visitors wait
		if(main.visitorCounter < 3){
			printMessage("group not formed...waiting");
			main.group.release();//let 1 visitor go
			main.waiting.acquire();//wait until group is form
		}//if
		
		//group is formed
		if(main.visitorCounter == 3){
			printMessage("Is the 3rd visitor, group is formed");
			main.visitorCounter = 0;//reset counter to 0
			main.waiting.release(2);//release the 2 visitors waiting
			
			Thread.sleep(sleepTime);
			main.groupReady.release();//signal that group is ready
		}//if
		
		main.g[main.groupCounter].addToGroup(this);//add each visitor to a group
		
		//wait for car is available
		main.carIsReady.acquire();
		
		//wait for visitors to be off the car
		main.visitorsOff.acquire();
		
		main.mutex.acquire();
		main.lastVisitor--;
		printMessage("Got off");
		main.mutex.release();
		
		//if is last visitor of the group
		if(main.lastVisitor == 0){
			main.groupCounter++;
			main.lastVisitor = 3;//reset counter
			printMessage("Is the last visitor and letting 1 go through");
			main.group.release();//let 1 visitor go through
		}//if
		
		Thread.sleep(sleepTime);
		printMessage("Is shopping around");
		Thread.sleep(sleepTime);
		
		//if visitor is not visitor 0
		if(this.id != 0){
			main.sem[this.id - 1].acquire();//wait for visitor n-1 to release
			Thread.sleep(50);
		}//if
		

		main.sem[this.id].release();//release current n semaphore			
				
		printMessage("Has left for home");
		
		/*
		 * testing purpose to see if the vector worked
		if(this.id == 8){
		System.out.println("group 1 has: " + main.g[1].getVisitor(0).name + " " + main.g[1].getVisitor(1).name + " " + main.g[1].getVisitor(2).name);
		System.out.println("group 2 has: " + main.g[2].getVisitor(0).name + " " + main.g[2].getVisitor(1).name + " " + main.g[2].getVisitor(2).name);
		System.out.println("group 3 has: " + main.g[3].getVisitor(0).name + " " + main.g[3].getVisitor(1).name + " " + main.g[3].getVisitor(2).name);
		}//if
		*/
		
		}//try
		catch(Exception e){
			System.out.println("Visitor thread error");
			e.printStackTrace();}
		
	}//run
	
}//Visitor