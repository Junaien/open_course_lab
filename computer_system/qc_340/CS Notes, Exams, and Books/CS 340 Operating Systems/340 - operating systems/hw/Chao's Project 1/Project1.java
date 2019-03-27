import java.util.*;

/**
 * CSCI 340 Project 1
 * Class of 6:30 - 7:45 pm
 * 
 * This class is mainly for testing the synchronization of
 * Visitor, Car, Controller Threads.
 * 
 * All the synchronized method is implemented here and called here.
 * 
 * 
 * Date Finished April 24, 2008
 * @Yachao Liu 
*/

public class Project1{
	
	int numVisitors;//number of visitors specified by the command line argument
	int numCar;//number of cars specified by the command line argument
	int groupSize;//number of visitors per group is allowed
	int hTime;//time to wait for handicap person

	Visitor[] visitors;//Visitor threads
	
	//visitor helper variables
	int visitor;//a counter for visitors
	int handiVisitor;//a counter for handicap
	boolean hasGroupFormed;//check if group is formed within
	int groupID;//ID to keep track of each group
	Group g;//a group vector to store each visitor
	Vector <Group> groups;//store each group in a Vector
	boolean group;//boolean variable to check if group is formed outer
	boolean waiting;//check to see if visitor are waiting
	
	//car helper variables
	Vector <Car> carsLoaded;//vector for the loaded cars in FCFS order
	//boolean carLeft;
	int lock;//needed so only one car can be out at a time -- locking the remaining two cars
	int groupNum;//counter for which group is next to be loaded on

	/**
	 * Constructor Method for Project1
	 * 
	 * basic initialization of variables
	 * 
	 */
	public Project1(){
		numVisitors = 0;
		numCar = 0;
		groupSize = 0;
		hTime = 0;
		
		//variables for visitor thread
		visitor = 0;
		handiVisitor = 0;
		hasGroupFormed = false;
		groupID = 1;//initialize first group
		groups = new Vector <Group>();//create a new vector to store the group
		group = false;
		waiting = false;
		
		//variables for car thread
		carsLoaded = new Vector <Car>();
		//boolean carLeft = false;
		int lock = 0;
		int groupNum = 0;		
	}//constructor
	
	/**
	 * Main Method for initializng and starting the Threads
	 * User must supply command line arguments, if none are given, exception is thrown
	 * 
	 * @param args[0] number of visitors
	 * @param args[1] number of cars
	 * @param args[2] group size
	 * @param args[3] waiting time for handicap
	 * 
	 */
	public static void main(String args[]){
		
	try{
		Project1 main = new Project1();
			
		main.numVisitors = Integer.parseInt(args[0]);
		main.numCar = Integer.parseInt(args[1]);
		main.groupSize = Integer.parseInt(args[2]);
		main.hTime = Integer.parseInt(args[3]);
		
		//initialize and start threads
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
	 * This method is for visitors trying to form a group.
	 * It will return a result of a visitor trying to join.
	 * 
	 * If it finds a place in a group, it will be added into a group vector of visitors
	 * and once a group is done it will also be placed in a Group vector of groups.
	 * 
	 * @param v Visitor that is trying to form a group
	 * @return integer 1-4 if it is 1 - group is full, it gets reject
	 * 					   if it is 2 - waiting to form a group
	 * 					   if it is 3 - it is waitng for a handicap
	 * 					   if it is 4 - the group is considered form	
	 */
	public synchronized int getTogether(Visitor v){
		
		v.printMessage("Enters the group");
		System.out.println("Currently -- #Visitor: " + visitor + "    #Handicap: " + handiVisitor);
		
		if(v.handicap){
			if(handiVisitor == 1)//already have a handicap
				return 1;//reject
			else if(visitor < 2){				
				if(visitor == 0)
					g = new Group(groupID);//create new group					
				g.setHandiVisitor(v);//add handicap to the group					
				handiVisitor++;							
				return 2;//waiting for visitors to form group
			}//if
		}//if
		else{//visior not handicap
			if(visitor == 2)//seats are filled
				return 1;//reject
			else{
				if(handiVisitor == 0){
				//there are no handicap present
					if(visitor == 0)
						g = new Group(groupID);//create new group						
					g.addToGroup(v);//add visitor to group
					visitor++;//forms group

					if(visitor == 2)//if is 2 visitors
						return 3;
					else{
						hasGroupFormed = false;
						return 2;//waiting to form group
					}//else
					
				}//if
				else{
					if(visitor == 0){
						//if first visior
						g.addToGroup(v);//add visitor to group
						visitor++;
						return 2;//waiting another visitor to form group
					}//if
				}//else
			}//else
		}//else			
			
		if(v.handicap){
			g.setHandiVisitor(v);
		}//if
		else{
			g.addToGroup(v);
		}//else
		
		hasGroupFormed = true;//group formed	
		System.out.println("Group " + groupID + " has formed with:");
		System.out.println(g.getVisitor(0).name());
		System.out.println(g.getVisitor(1).name());
		System.out.println(g.getVisitor(2).name());
		waitingTrue();//group is formed -- setting waiting true
		//clear vars counters
		visitor = 0;
		handiVisitor = 0;
		groups.add(g);//add to a vector of groups
		groupID++;//increment for next group
		return 4;//group is formed
	}//getTogether
	
	/**
	 * This method is to form a group if there is no handicap present.
	 * If it detects that there are two visitors and no handicap, it will 
	 * wait the designated time and call this method to form without handicap
	 *
	 */
	public void formGroup(){ //form group with no handicap
		if(!hasGroupFormed){
		hasGroupFormed = true;//group form
		System.out.println("Group " + groupID + " has formed :");
		System.out.println(g.getVisitor(0).name());
		System.out.println(g.getVisitor(1).name());
		groups.add(g);//add to a vector of groups
		groupID++;
		visitor = 0;
		handiVisitor = 0;	
		waitingTrue();//group is formed -- setting waiting true
		}//if
		else
			System.out.println("Group" + groupID + "already formed by a handicap:");
	}//formGroup
	
	/**
	 * Method to initialize visitors and starting the thread
	 * 
	 * @param numVisitors int number of visitors specify by the user
	 * @param p 		  Project1 an instance of the main method
	 */
	public void initVisitors(int numVisitors, Project1 p){
		visitors = new Visitor[numVisitors];
		
		for(int i = 0; i < numVisitors; i++){
			visitors[i] = new Visitor(p,"Visitor" + i, i);
			visitors[i].start();
		}//for
	}//initVisitors
	
	/**
	 * Method to intialize car and starting the thread
	 * 
	 * @param p Project1 an instance the main method
	 */
	public void initCar(Project1 p){
		Car Cars[] = new Car[numCar];
		
		for(int i = 0; i < p.numCar; i++){
			Cars[i] = new Car(p, "Car " + i);
			Cars[i].start();
		}//for
	}//initCar
	
	/**
	 * Synchronized method for visitor thread
	 * Setting the waiting to true
	 *
	 */
	public synchronized void waitingTrue(){
		waiting = true;
	}//waitingTrue
	
	/**
	 * Synchronized method for visitor thread
	 * @return boolean if the group is formed
	 */
	public synchronized boolean isFormed(){
		return waiting;
	}//isFormed
	
	/**
	 * Synchronized method for Car thread
	 * It will add it to the vector in a FCFS order
	 * 
	 * @param c Car adding the arriving car to a vector
	 */
	public synchronized void carArrives(Car c){
		carsLoaded.add(c);
	}//carArrives	
	
	/**
	 * This method returns the value of lock to see if locking the cars is needed
	 * 
	 * @return int the integer value of the lock
	 */
	public synchronized int getLock(){
		return lock;
	}//getLock
	
	/**
	 * This method locks the other two cars so only one car can leave
	 * It increments the lock.
	 * 
	 */
	public synchronized void setLock(){
		lock++;
	}//setLock
	
	/**
	 * This method releases the lock and allows the next available car to exit
	 * 
	 */
	public synchronized void releaseLock(){
		lock--;
	}//releaseLock
	
	/**
	 * This method increases the next group to be loaded on to the car
	 */
	public synchronized void incrGroupNum(){
		groupNum++;
	}//incrGroupNum
	
	/**
	 * This method returns the current group of visitors
	 * @return
	 */
	public int getGroupNum(){
		return groupNum;
	}//getGroupNum
	
	/**
	 * Synchronized method for the controller
	 * 
	 * This method causes the car to wait for the controller
	 */
	public synchronized void waitForController(){
		try{
			wait();
		}//try
		catch(Exception e){
			System.out.println("Exception caused by wait()--waiting for controller");
		}//catch
	}//waitForController
	
	/**
	 * This method notifys the waiting car
	 */
	public synchronized void controllerReady(){
		try{
			notifyAll();
		}//try
		catch(Exception e){
			System.out.println("Exception caused by notifyAll()--controller ready");
		}//catch
	}//waitForController

}//Project 1