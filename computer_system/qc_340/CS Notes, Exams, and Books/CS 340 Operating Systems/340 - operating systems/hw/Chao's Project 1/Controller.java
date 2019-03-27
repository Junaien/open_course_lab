import java.util.*;

/**
 * CSCI 340 Project 1
 * Class of 6:30 - 7:45 pm
 * 
 * This thread is Controller.
 * 
 * The controller will keep checking if checking the car is available
 * 
 * Date Finished April 24, 2008
 * @Yachao Liu 
*/


public class Controller extends Thread{
	
	Project1 main;//instance of Project 1
	String name;//name of the controller
	
	/**
	 * Controller Constructor
	 * 
	 * @param m	Project1 instance of project1
	 * @param n String the name of the controller
	 */
	public Controller(Project1 m, String n){
		main = m;
		name =  n;
	}//constructor
	
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
	 * The run method for controller
	 * 
	 */
	public void run(){
		Random generator = new Random();
		int sleepTime = 0;//random sleep time
		sleepTime = generator.nextInt(300) + 200;//sleep 
		
		try{
		printMessage("Arrives at the depot and ready to work");
		while(main.carsLoaded.size() != main.numCar){
			//waits while cars are loaded in controllers vector
			printMessage("Waiting for available cars...");
			Thread.sleep(500);//sleep for 1 sec
		}//while
			
		//cars are ready and loaded in the vector in FCFS order
		printMessage("The available cars in FCFS order:");
		for(int i = 0; i < main.carsLoaded.size(); i++){
			printMessage("Car" + i + " is: " + main.carsLoaded.elementAt(i).name);
		}//for
		
		while(main.groupNum < 4){	
		//waiting for the car to depart
		while(!main.carsLoaded.elementAt(0).departure && main.groupNum < 4){
			printMessage("Waiting for a Car to load visitors");
			Thread.sleep(sleepTime);
		}//while
		
		if(main.getGroupNum() > 3)
			break;
		
		main.carsLoaded.elementAt(0).departure = false;
		//car is ready to departure and waiting for controller to check tickets
		printMessage("Checking each visitor for their tickets...");
		Thread.sleep(sleepTime);
		printMessage("Signals to Car, it can depart");
		main.controllerReady();
		}//while

		printMessage("Has left");
		
		}//try
		catch(Exception e){
			System.out.println("Controller thread error");
			e.printStackTrace();
		}//catch
		
	}//run
	
}//Controller