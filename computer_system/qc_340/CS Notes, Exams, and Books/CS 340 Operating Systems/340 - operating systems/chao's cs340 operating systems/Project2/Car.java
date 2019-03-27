import java.util.*;

/**
 * CSCI 340 Project 2
 * Class of 6:30 - 7:45 pm
 * 
 * This is a double submission, I have also done Option 1 as well.
 * This thread is car.
 * 
 * The car thread works as queue.  Since only 1 car is allowed to run at a time, the remaining
 * 2 is queued up.  Once the car is done, it will release the next available car and the 
 * car will queue up.
 * 
 * Inside a car has a vector called seat to place a group of visitors, once the ride is done
 * the car will empty out the group.
 * 
 * Date Finished May 16, 2008
 * @Yachao Liu 
*/


public class Car extends Thread{
	
	boolean departure;//determine if the car left or not
	Project2 main;//instance of Project 1
	String name;//name of the car
	Vector <Group> seat;//storing a group of visitors
	
	/**
	 * Car Constructor
	 * 
	 * @param m Project2 an instance of Project2
	 * @param n String name of the car
	 */
	public Car(Project2 m, String n){
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
		System.out.println(this.name + ":" + message);
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
		sleepTime = generator.nextInt(300) + 200;//sleep 
		
		try{
			Thread.sleep(sleepTime);
			printMessage("Has arrived from the depot");
			
		while(true){//keep on looping
			main.carQueue.acquire();//allow the first car to go through and queue the remaining 2 cars
			
			if(main.groupCounter == 4) break;//lets the 2 car go if is not needed then breaks out
			
			printMessage("Is available");
			main.carIsReady.release(3);//signal that a car is available
			main.groupReady.acquire();//wait for a group to be ready
			
			printMessage("Loading the visitors on to the car");
			this.seat.add(main.g[main.groupCounter]);//add the group in 
		
			Thread.sleep(sleepTime);
			printMessage("Visitors are loaded");
			
			//wait for controller
			main.controller.acquire();
			
			//signal that a car is ready to be checked
			main.waitsForCar.release();
			
			//wait for permission
			main.permission.acquire();
			
			printMessage("Rides around the park");
			Thread.sleep(sleepTime);
			
			printMessage("Let's visitors off");
			this.seat.removeAllElements();//remove the group of visitors
			Thread.sleep(sleepTime);
			main.visitorsOff.release(3);
			
			Thread.sleep(sleepTime);
			printMessage("Refueling...");
			
			//if this is the last serving car
			if(main.groupCounter == 4){
				main.carQueue.release(2);//let the other 2 car get out
				break;//break out
			}//if
			else
				main.carQueue.release();//let next car go
			
		}//while
		
		printMessage("Cars have served all the customer");
		
		}//try
		catch(Exception e){
			System.out.println("Car thread error");
			e.printStackTrace();
		}//catch
		
	}//run
	
}//Car