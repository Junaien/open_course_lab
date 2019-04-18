import java.util.*;

/**
 * CSCI 340 Project 1
 * Class of 6:30 - 7:45 pm
 * 
 * This thread is car.
 * The car arrives and stores in a vector located in Controller for FCFS order
 * The car has a vector as well, to store/load each group
 * 
 * 
 * Date Finished April 24, 2008
 * @Yachao Liu 
*/


public class Car extends Thread{
	
	boolean departure;//determine if the car left or not
	Project1 main;//instance of Project 1
	String name;//name of the car
	Vector <Group> seat;//storing a group of visitors
	
	/**
	 * Car Constructor
	 * 
	 * @param m Project1 an instance of Project1
	 * @param n String name of the car
	 */
	public Car(Project1 m, String n){
		departure = false;
		main = m;
		name = n;
		seat = new Vector <Group>();
	}//constructor
	
	/**
	 * This method returns the name of the car
	 * 
	 * @return name String the name of the car
	 */
	public String name(){
		return name;
	}//getName
	
	public void printMessage(String message){
		startTime();
		System.out.print(this.name + ":" + message);
		System.out.println();
	}//printMessage
	
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
	 * The run method for the car
	 */
	public void run(){
		Random generator = new Random();
		int sleepTime = 0;//random sleep time
		int h = 0;//int variable storing the priority
		sleepTime = generator.nextInt(300) + 200;//sleep 
		
		try{
		while(true){//keep on looping
			
		if(main.getGroupNum() > 3)
			break;	
			
		Thread.sleep(sleepTime);
		printMessage("Arrives at the depot.");
		main.carArrives(this);//add in the vector
		Thread.sleep(sleepTime);
		
		//only let 1 car go at a time
		while(main.getLock() > 0){
			if(main.getGroupNum() > 3)
				break;
			//while 1 car is already out
			printMessage("Not the first car, waiting...");
			Thread.sleep(sleepTime);
		}//while
		
		while(this == main.carsLoaded.elementAt(1)){
			if(main.getGroupNum() > 3)
				break;
			printMessage("Not the first car, waiting...");
			Thread.sleep(sleepTime);
		}//while
		
		main.setLock();//lock the other two
		printMessage("Is ready");
			while(!main.group){
				printMessage("waiting for groups");
				Thread.sleep(sleepTime);
			}//while
		
		if(main.getGroupNum() > 3)
			break;	
		printMessage("signaling group...");

		//load group into car
		main.carsLoaded.elementAt(0).seat.add(main.groups.elementAt(main.getGroupNum()));
		printMessage("Group " + Integer.toString(main.getGroupNum()+1) + " is loaded in");
		
		if(main.groups.elementAt(main.getGroupNum()).hasHandicap){
			printMessage(main.groups.elementAt(main.getGroupNum()).getHandiVisitor().name + " announced first since he is handicapped.");
			h = main.groups.elementAt(main.getGroupNum()).getHandiVisitor().getPriority();
			main.groups.elementAt(main.getGroupNum()).getHandiVisitor().setPriority(h+1);
			main.groups.elementAt(main.getGroupNum()).getHandiVisitor().sleep(sleepTime);//boards and sleep
			main.groups.elementAt(main.getGroupNum()).getHandiVisitor().setPriority(h+1);//reset prirotiy			
			printMessage("He is onboard, got a seat -- Handicap");
			main.groups.elementAt(main.getGroupNum()).getHandiVisitor().onBoard = true;

			if(!main.groups.elementAt(main.getGroupNum()).getVisitor(0).handicap){
				printMessage(main.groups.elementAt(main.getGroupNum()).getVisitor(0).name + " anounced next -- regular visitor");
				printMessage(main.groups.elementAt(main.getGroupNum()).getVisitor(0).name + " on board, got seat");
				main.groups.elementAt(main.getGroupNum()).getVisitor(0).onBoard = true;
			}//if
			if(!main.groups.elementAt(main.getGroupNum()).getVisitor(1).handicap){
				printMessage(main.groups.elementAt(main.getGroupNum()).getVisitor(1).name + " anounced next -- regular visitor");
				printMessage(main.groups.elementAt(main.getGroupNum()).getVisitor(1).name + " on board, got seat");
				main.groups.elementAt(main.getGroupNum()).getVisitor(1).onBoard = true;
			}//if
			if(!main.groups.elementAt(main.getGroupNum()).getVisitor(2).handicap){
				printMessage(main.groups.elementAt(main.getGroupNum()).getVisitor(2).name + " anounced next -- regular visitor");
				printMessage(main.groups.elementAt(main.getGroupNum()).getVisitor(2).name + " on board, got seat");
				main.groups.elementAt(main.getGroupNum()).getVisitor(2).onBoard = true;
			}//if
		}//if
		else{
			printMessage("Group has no handicap, boarding regular visitors");
			if(!main.groups.elementAt(main.getGroupNum()).getVisitor(0).handicap){
				printMessage(main.groups.elementAt(main.getGroupNum()).getVisitor(0).name + " anounced first -- regular visitor");
				printMessage(main.groups.elementAt(main.getGroupNum()).getVisitor(0).name + " on board, got seat");
				main.groups.elementAt(main.getGroupNum()).getVisitor(0).onBoard = true;
			}//if
			if(!main.groups.elementAt(main.getGroupNum()).getVisitor(1).handicap){
				printMessage(main.groups.elementAt(main.getGroupNum()).getVisitor(1).name + " anounced second -- regular visitor");
				printMessage(main.groups.elementAt(main.getGroupNum()).getVisitor(1).name + " on board, got seat");
				main.groups.elementAt(main.getGroupNum()).getVisitor(1).onBoard = true;
			}//if
		}//else
		
		//waits for controller using the wait();
		this.departure = true;
		printMessage("Signals controller all visitor is boarded");
		main.waitForController();
		
		//controller signals ok to depart
		printMessage("Receives signal to depart");
		printMessage("Departs.");

		//rides around the park
		printMessage("Riding around the park");
		Thread.sleep(sleepTime);
		
		//visitors gets off, car gets gass
		printMessage("Lets visitors off");
		
		if(main.groups.elementAt(main.getGroupNum()).hasHandicap){
			main.groups.elementAt(main.getGroupNum()).getVisitor(0).onBoard = false;
			main.groups.elementAt(main.getGroupNum()).getVisitor(1).onBoard = false;
			main.groups.elementAt(main.getGroupNum()).getVisitor(2).onBoard = false;
		}//if
		else{
			main.groups.elementAt(main.getGroupNum()).getVisitor(0).onBoard = false;
			main.groups.elementAt(main.getGroupNum()).getVisitor(1).onBoard = false;
		}//else


		printMessage("Leaves and gets gas");
		
		//allows other thread to execute
		yield();
		
		//wakes up or finish getting gas
		//car available for another ride
		printMessage("Got gas going back to depot");
		main.carsLoaded.removeElementAt(0);

		//this.departure = false;
		main.incrGroupNum();//incr group num
		main.releaseLock();//release lock
		
		}//while
		
		printMessage("Cars have served all the customer");
		
		}//try
		catch(Exception e){
			System.out.println("Car thread error");
			e.printStackTrace();
		}//catch
		
	}//run
	
}//Car