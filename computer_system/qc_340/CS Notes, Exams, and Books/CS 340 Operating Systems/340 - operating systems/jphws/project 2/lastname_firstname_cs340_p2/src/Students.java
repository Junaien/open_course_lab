import java.util.Random;
import java.util.concurrent.Semaphore;


public class Students extends Thread
{
	//Variables Pertaining to Each Student
	static int id = 1;
	int MyGroup = 0;
	int TotalClassesTaken = 0;
	long StartClassTime[] = {0, 0, 0 , 0};
	long EndClassTime[] = {0, 0, 0 , 0};
	String ClassesAttended[]= {"Pending", "Pending", "Pending", "Pending"};
	
	//Variables Pertaining to the Bathroom
	public static Semaphore BathroomMutex = new Semaphore(1, true);
	
	//Variables Pertaining to the Classes
	static String Classes [] = {"Bioinformatics", "Math 241", "Quantum Computing", "Operating Systems"};
	public static Semaphore ClassMutex = new Semaphore(1, true), 
							StartClass = new Semaphore(0, true), 
							InClass = new Semaphore(0, true);
	public static int waitingForClass[] = {0,0,0,0};	
	
	//Variables Pertaining to the Group
	public static Semaphore GroupFormed = new Semaphore (0, true);
	static int numStudents = 0;
	static int groupsize = 0;
	static int groupNum = 0;
	
	//Variables Pertaining to the Tables
	public static Semaphore TableMutex = new Semaphore (1, true);
	static int numTables = 5;
	public static Semaphore TablesAvailable = new Semaphore (numTables, true);
	static int TableCapacity = 3;
	public static Semaphore TableGroup = new Semaphore (0, true);

	//Variables Pertaining to the Eating
	static int peopleeating = 0;
	public static Semaphore ReadyToEatMutex = new Semaphore (1, true);
	public static Semaphore WaitingToEat = new Semaphore (0, true);
	
	//Variables Pertaining to the Dorms
	public static Semaphore DormsMutex = new Semaphore (1, true);
	static int studentsInDorms = 0;

	//Variables Pertaining to the Student Schedule
	public static Semaphore StudentSchMutex = new Semaphore (0, true);
	

	/**
	 * Constructor for EACH Student
	 */
	public Students()
	{
		//Sets the name of EACH student and assigns them a unique id (never duplicated).
		//Starts the thread.
		numStudents = id;
		setName("Student-" + id++); 
		start();
	}
	
	/**
	 * A simple function that displays the current time in milliseconds
	 * @return
	 */
	public long currentTime()
	{
		return System.currentTimeMillis()-GlobalVariables.time;
	}
	
	/**
	 * Messages that occur throughout the day come from this function.
	 * @param m
	 */
	public void msg(String m) 
	{
		System.out.println("["+(currentTime())+"] "+getName()+":"+m);
	}
	
	/**
	 * This function is used to generate a random number that has a max
	 * no greater than 5000 milliseconds.
	 * @return
	 */
	public static int RandomNumber()
	{
		Random rand = new Random();
		int timeToSleep = rand.nextInt(5000);
		return timeToSleep;
	}
	
	/**
	 * The HaveFun function takes the Thread that calls on it and sets the Priority
	 * higher than the default (5).  Then sleeps for random time to simulate
	 * having fun.  After it wakes up it sets the priority of the Thread
	 * back to the default.
	 */
	public void HaveFun()
	{
		try 
		{
			setPriority(10);
			sleep(RandomNumber());
		} 
		catch (InterruptedException e) 
		{
			setPriority(5);
		}
		return;
	}
	
	/**
	 * The Errands function takes the Thread that calls on it and puts it to 
	 * sleeps for random time to simulate running errands.
	 */
	public void Errands()
	{
		try 
		{
			sleep(RandomNumber());
		} 
		catch (InterruptedException e)
		{}
		return;
	}
	
	/**
	 * Runs a Thread that has been started
	 */
	public void run()
	{
		//Rise and Shine
		//Sleeps for random time and wakes up this simulates
		//waking up in the morning.
		try {sleep(RandomNumber());}
		catch (InterruptedException e) 
		{}
		//Thread is awake
		
		//Each thread uses the bathroom in the order in which they woke up
		//Sleep is used to simulate the time inside the bathroom
		try 
		{
			BathroomMutex.acquire();
			msg(" is in the bathroom");
			sleep(RandomNumber());
		} 
		
		catch (InterruptedException e1) 
		{}
		
		msg(" is out of the bathroom.");
		BathroomMutex.release();
		//Finished using the Bathroom
		
		
		/**
		 * This is a for loop that runs through every class for each student
		 */
		for(int i = 0; i < Classes.length; i++)
		{
			/**
			 * Simple enough if the Teacher has not signaled that class has
			 * started then they go into the auditorium and wait for the teacher
			 * to arrive. Once the Teacher arrives they stay in class till class
			 * is over and then go have fun before the next class begins.
			 */
			if (Teacher.ClassStarted[i] == "No")
			{
				try 
				{
					ClassMutex.acquire();
					ClassesAttended[i] = "Attended";
					StartClassTime[i] = currentTime();
					waitingForClass[i]++;
					TotalClassesTaken++;
					ClassMutex.release();
				
					//msg(" is waiting for " + Classes[i]);
					StartClass.acquire();
					msg(" is in " + Classes[i]);
					InClass.acquire();
					msg(" is out of " + Classes[i] + " and having fun.");
					EndClassTime[i] = currentTime();
					HaveFun();
				}
				catch (InterruptedException e) 
				{}
			}
			else
			{	
				/**
				 * If the class is over they might as well wait for the next
				 * class to begin
				 */
				if (Teacher.ClassStarted[i] == "Over")
				{
					ClassesAttended[i] = "Did Not Attend";
				}
				/**
				 * If the class is in session and they missed class they
				 * try to run errands before the next class starts.
				 */
				else
				{
					ClassesAttended[i] = "Did Not Attend";
					msg(" missed " + Classes[i] + " and doing errands");
					Errands();
				}
			}
		}
		
		/**
		 * Cafeteria Starts Here! After all classes are over
		 * each student steps into the cafeteria waiting for two other people
		 * to pair up with. Once in a group of three they let the teacher know
		 * and the teacher places them into a table.  If no table is available
		 * they will have to wait until a whole group leaves 1 table.  When the
		 * group leaves the table the Teacher is notified that there is a table
		 * available.  The teacher then releases the next group onto that table.
		 */
		try 
		{
			TableMutex.acquire();
			groupsize++;
			if (groupsize % TableCapacity != 0 && groupsize != numStudents)
			{
				msg(" is waiting for fellow students");
				TableMutex.release();
				TableGroup.acquire();
				MyGroup = groupNum;
				WaitingToEat.acquire();
			}
			else
			{
				groupNum++;
				msg(" formed Group " + groupNum);
				MyGroup = groupNum;
				TableMutex.release();
				for (int i = 1; i < 3; i ++)
				{
					TableGroup.release();
				}
				GroupFormed.release();
				WaitingToEat.acquire();
			}
			
			ReadyToEatMutex.acquire();
			peopleeating++;
			sleep(RandomNumber());
			if(peopleeating % TableCapacity == 0 || peopleeating == numStudents)
			{
				TablesAvailable.release();
			}
			msg (" finished eating and heads to the dorms.");
			ReadyToEatMutex.release();
			
			
			/**
			 * Once a student finishes eating he/she heads back to the dorms.
			 * The last person that heads to the dorms lets the teacher know that
			 * everyone has finished eating and the Teacher goes home.
			 */
			DormsMutex.acquire();
			studentsInDorms++;
			msg(" is in the dorms");
			if(studentsInDorms == numStudents)
			{
				GroupFormed.release();
				Teacher.TeacherWaitsForStudent.release();
				StudentSchMutex.release();
			}
			DormsMutex.release();
			
			/**
			 * Once everyone has arrived at the dorms the Daily Report Of Classes
			 * for each student is displayed.
			 */
			StudentSchMutex.acquire();
			System.out.println();
			System.out.println("Daily Report Of Classes");
			System.out.println("Name: " + getName());
			if (ClassesAttended[0] == "Attended")
			{
				System.out.println(Classes[0] +": " + ClassesAttended[0] +" from " + StartClassTime[0] + " to " + EndClassTime[0]);
			}
			else
			{
				System.out.println(Classes[0] +": " + ClassesAttended[0]);
			}
			if (ClassesAttended[1] == "Attended")
			{
				System.out.println(Classes[1] +": " + ClassesAttended[1] +" from " + StartClassTime[1] + " to " + EndClassTime[1]);
			}
			else
			{
				System.out.println(Classes[1] +": " + ClassesAttended[1]);
			}
			if (ClassesAttended[2] == "Attended")
			{
				System.out.println(Classes[2] +": " + ClassesAttended[2] +" from " + StartClassTime[2] + " to " + EndClassTime[2]);
			}
			else
			{
				System.out.println(Classes[2] +": " + ClassesAttended[2]);
			}
			if (ClassesAttended[3] == "Attended")
			{
				System.out.println(Classes[3] +": " + ClassesAttended[3] +" from " + StartClassTime[3] + " to " + EndClassTime[3]);
			}
			else
			{
				System.out.println(Classes[3] +": " + ClassesAttended[3]);
			}
			System.out.println("Total Classes Attended: " + TotalClassesTaken);
			System.out.println(getName() + " was a member of Group " + MyGroup);
			StudentSchMutex.release();
		} 
		catch (InterruptedException e) 
		{}
	}// ends run
}//ends Students class


