import java.util.*;

/**
 * CSCI 340 Project 1
 * Class of 6:30 - 7:45 pm
 * 
 * This thread is visitors.
 * For testing purposes, I put 1 handicap and the rest are regular visitors 
 * so this way, it can test for both scenarios working.  I did not want to leave any
 * visitors waiting because of uneven groups.  I choose randomly 1 visitor to be handicap
 * so the group can be 3,2,2,2.
 * 
 * 
 * Date Finished April 24, 2008
 * @Yachao Liu 
*/

public class Visitor extends Thread{
	
	boolean handicap;//if the visitor is handicap or not
	Project1 main;//instance of Project 1
	String name;//name
	int id;//identification for each visitor -- a helper variable
	boolean onBoard;//tests to see if visitor is on the car or not -- a helper variable
	
	/**
	 * Visitor Constructor
	 * 
	 * @param m Project1 an instance
	 * @param n String the name
	 * @param i int the ID
	 */
	public Visitor(Project1 m, String n, int i){
		handicap = false;
		main = m;
		name = n;
		onBoard = false;
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
		System.out.print(this.name + ":" + message);
			if(this.handicap == true){
				System.out.print(" This visitor is handicapped.");
			}//if
		System.out.println();
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
		int detHandicap = 0;//this determines if visitor is handicap or not
		
		sleepTime = generator.nextInt(300) + 200;//sleep 
		detHandicap = generator.nextInt(10);//generate 0-10
		
		
		/* This is the method to choose handicap randomly
		if((detHandicap%2) == 1){
			//visitor is handicap if it is odd
			this.handicap = true;
		}//if
		*/
		
		//this is for testing purposes making 1 visitor handicap
		//to form groups 3-2-2-2 without any visitors left behind
		if(this.name.equalsIgnoreCase("visitor4"))
			this.handicap = true;
		
		try{
		Thread.sleep(sleepTime);
		printMessage("Arrives at the museum.");
		printMessage("Is wandering around.");
		Thread.sleep(sleepTime);
		int result = main.getTogether(this);
		while(true){
			if(result == 1){//group is full
				//got reject try again later
				printMessage("Got rejected, will try again soon...");
				Thread.sleep(sleepTime);//sleep
				result = main.getTogether(this);//trying again
			}//if
			
			if(result == 2){//missing either visitor or handicap
				if(this.handicap){//if handicap then is
					//waiting for visitor
					printMessage("Has 1 Handicap, waiting for 1 or 2 more visitor...");
					
					while(!main.isFormed()){
						printMessage("Waiting...");
						Thread.sleep(sleepTime);
					}//while
					result=4;//once awake will form group
				}//if
				else{					
					printMessage("1 visitor , waiting for visitor or Handicap");
					//callTimeout();//wait
					while(!main.isFormed()){
						printMessage("Waiting...");
						Thread.sleep(sleepTime);
					}//while
					result=4;//group is formed
				}//else
			}//if
			
			if(result == 3){
					printMessage("Has 2 visitor and no handicap, waiting for Handicap for a fixed interval");
					printMessage("Waiting time starts ---------------");
					while(!main.isFormed()){
						Thread.sleep(main.hTime);
						break;
					}//while
					printMessage("Waiting time ends   ---------------");
					main.formGroup();//form group
					result=4;
			}//if
			
			if(result == 4){//group is formed	
				main.group = true;
				break;
			}//if
		}//while	

		//busy waits until Car signals
		while(!this.onBoard){
			printMessage("Waiting to be boarded");
			Thread.sleep(sleepTime);
		}//while
		
		//busy waits if still on the car
		while(this.onBoard){
			printMessage("On board, waiting ride to finish");
			Thread.sleep(sleepTime);
		}//while
		
		//shows status that it got off
		printMessage("Got off board");
		
		//means visitors got off the car
		//visitors will shop
		printMessage("Is shopping around...");
		Thread.sleep(sleepTime);
		
		//waiting for visitor-i to leave first
		if(this.id != 0){
			//only for visitors 1 and above
			//visitor 0 leave without anyone waiting for him
			if(main.visitors[this.id - 1].isAlive())
				main.visitors[this.id - 1].join();
		}//if
		
		printMessage("Has left for home");

		}//try
		catch(Exception e){
			System.out.println("Visitor thread error");
			e.printStackTrace();}
		
	}//run
	
}//Visitor