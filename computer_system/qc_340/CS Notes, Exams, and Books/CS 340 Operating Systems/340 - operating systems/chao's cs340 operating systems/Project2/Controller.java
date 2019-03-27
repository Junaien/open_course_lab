import java.util.*;

/**
 * CSCI 340 Project 2
 * Class of 6:30 - 7:45 pm
 * 
 * This is a double submission, I have also done Option 1 as well.
 * This thread is Controller.
 * 
 * The controller will keep checking if checking the car is available.
 * 
 * Date Finished May 16, 2008
 * @Yachao Liu 
*/


public class Controller extends Thread{
	
	Project2 main;//instance of Project 2
	String name;//name of the controller
	
	/**
	 * Controller Constructor
	 * 
	 * @param m	Project2 instance of project1
	 * @param n String the name of the controller
	 */
	public Controller(Project2 m, String n){
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
			Thread.sleep(sleepTime);
			printMessage("Arrives at the depot and ready to work");
		while(true){
			//signal that the controller is available
			main.controller.release();
			
			//waits for car
			main.waitsForCar.acquire();
			
			//checks tickets
			printMessage("Is checking the visitor's tickets");
			Thread.sleep(sleepTime);
			
			//gives permission
			printMessage("Gives permission to depart");
			main.permission.release();
			Thread.sleep(sleepTime);
			
			//if no more groups, then controller is no longer needed
			if(main.groupCounter == 3)break;
		}//while

		printMessage("Has left");
		
		}//try
		catch(Exception e){
			System.out.println("Controller thread error");
			e.printStackTrace();
		}//catch
		
	}//run
	
}//Controller