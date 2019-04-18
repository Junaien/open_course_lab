import java.util.concurrent.*;

/**
 * CSCI 340 Project 2 -- Option 2
 * This is a double submission, I have also done Option 1 as well.
 * Class of 6:30 - 7:45 pm
 * 
 * This class is mainly for testing the synchronization of
 * Visitor, Car, Controller Threads.
 * 
 * This project is similar to Project1 except that it uses Semaphores and there
 * is no handicap visitors.
 * 
 * 
 * Date Finished May 16, 2008
 * @Yachao Liu 
*/

public class Project2{
	
	int numVisitors;//number of visitors specified by the command line argument
	int numCar;//number of cars specified by the command line argument
	int groupSize;//number of visitors per group is allowed
	

	Visitor[] visitors;//Visitor threads
	
	//Visitor Semaphore variables and helper variables
	Semaphore mutex;//provide mutual exclusion to the critical section
	Semaphore group;//to signal to let visitor come in if group not formed
	Semaphore waiting;//waiting to see if a group is formed
	Semaphore groupReady;//signal that a group is ready or not
	
	int visitorCounter;//a counter to determine the # of visitor in the current group
	int lastVisitor;//a counter to determine the last visitor getting off
	int groupCounter;//a counter to determine the group number
	
	Semaphore[] sem;//array of semaphores
	Group[] g;//array of groups
	
	//Car Semaphore variables
	Semaphore carIsReady;//signal if a car is available or not
	Semaphore carQueue;//signal to get in car queue
	Semaphore visitorsOff;//signal that the car has dropped its visitors off or not
	
	//Controller Semaphore variables
	Semaphore controller;//signal that a controller is available or not
	Semaphore waitsForCar;//signal that is waiting for a car
	Semaphore permission;//signal permission to depart
	
	public Project2(){
		numVisitors = 0;
		numCar = 0;
		groupSize = 0;
		
		//visitor variables initialization
		mutex = new Semaphore(1, true);
		group = new Semaphore(1, true);
		waiting = new Semaphore(0, true);
		groupReady = new Semaphore(0, true);
		
		visitorCounter = 0;
		lastVisitor = 3;
		groupCounter = 1;
		
		//car variables initialization
		carIsReady = new Semaphore(0, true);
		carQueue = new Semaphore(1, true);
		visitorsOff = new Semaphore(0, true);
		
		//controller variables initialization
		controller = new Semaphore(0, true);
		waitsForCar = new Semaphore(0, true);
		permission = new Semaphore(0, true);

	}//constructor
	
	/**
	 * Main Method for initializng and starting the Threads
	 * User must supply command line arguments, if none are given, exception is thrown
	 * 
	 * @param args[0] number of visitors
	 * @param args[1] number of cars
	 * @param args[2] group size
	 * 
	 */
	public static void main(String args[]){
	
		try{
			Project2 main = new Project2();
				
			main.numVisitors = Integer.parseInt(args[0]);
			main.numCar = Integer.parseInt(args[1]);
			main.groupSize = Integer.parseInt(args[2]);
			
			//initialize and start threads
			main.initSem();//initialize the 9 semaphores
			main.initVisitors(main.numVisitors, main);
			main.initCar(main);
			Controller controller = new Controller(main, "Controller");
			controller.start();
			
		}//try
		catch(Exception e){
				System.out.println("Must supply argument values");
		}//catch
	
	
	}//main
	
	/**
	 * Method to initialize the 9 semaphores to coordinate with the 
	 * visitors n-1 leaving.
	 * 
	 */
	public void initSem(){
		sem = new Semaphore[9];
		
		for(int i = 0; i < sem.length; i++){
			sem[i] = new Semaphore(0, true);
		}//for
		
	}//initSem
	
	/**
	 * Method to initialize visitors and starting the thread
	 * 
	 * @param numVisitors int number of visitors specify by the user
	 * @param p 		  Project2 an instance of the main method
	 */
	public void initVisitors(int numVisitors, Project2 p){
		visitors = new Visitor[numVisitors];
		
		for(int i = 0; i < numVisitors; i++){
			visitors[i] = new Visitor(p,"Visitor" + i, i);
			visitors[i].start();
		}//for
		
		//initialize the groups
		g = new Group[4];//make 3 groups-- array is 1 based for convience
		for(int i = 1; i < g.length; i++){
			g[i] = new Group(i);
		}//for
		
	}//initVisitors
	
	/**
	 * Method to intialize car and starting the thread
	 * 
	 * @param p Project2 an instance the main method
	 */
	public void initCar(Project2 p){
		Car Cars[] = new Car[numCar];
		
		for(int i = 0; i < p.numCar; i++){
			Cars[i] = new Car(p, "Car " + i);
			Cars[i].start();
		}//for
	}//initCar
	
}//Project2