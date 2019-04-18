import java.util.*;

/**
 * CS340 Project I - Jeopardy
 * Evening Session: 6:30-7:45
 * 
 * This class is for the Contestant thread.
 * Describ what the contestant does.........
 * 
 * Due: 4-30-09
 * @author - Angel Guevara, Jr.
 */
public class Contestant extends Thread
{
	//Member Variables:
	static String name;	//will hold the current contestant's name
	int pickNum;	//will hold all contestants' random number to be picked by announcer
	static boolean announcerSignal=false;
	static boolean hostSignal=false;
	static Random randGenerator = new Random();
	static int randNum=0;
	
	/**
	 * Contestant Constructor:
	 * 
	 * @param - 
	 */
	public Contestant()
	{
		
	}//Contestant constructor
	
	/**
	 * Name Method:
	 * Prints out the current Contestant's name
	 * 
	 * @return name The name to be printed
	 */
	public static String name()
	{
		return name;
	}
	
	/**
	 * Sets the name for a given contestant
	 * @param cName
	 */
	public static void makeName(String cName)
	{
		name = cName;
	}
	
	/**
	 * Print Speech Method:
	 * Prints out any Contestants' speech
	 * 
	 * @param message The message to be printed
	 */
	public static void printSpeech(String message)
	{
		age();	//print out the current time
		System.out.println(name() + " : " + message);		//print out the message
	}//printMessage
	/**
	 * Print Action Method:
	 * Prints out any Contestants' actions
	 * 
	 * @param message The action to be printed
	 */
	public static void printAction(String message)
	{
		age();	//print out the current time
		System.out.println(name() + " " + message);	//print out the action
	}//printAction
	
	/**
	 * Age Method:
	 * Finds the current time.
	 */
	public static final void age()
	{
		//initialize time variables and get the current time
		int hr=0, min=0, sec=0;
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(System.currentTimeMillis());
		
		//set the current time to variables
		hr = now.get(Calendar.HOUR);
		if(hr==0)
		{
			hr=12;	//change "0" to be displayed as "12"
		}
		min = now.get(Calendar.MINUTE);
		sec = now.get(Calendar.SECOND);
		
		//display the current time
		if(min<10 && sec<10)
		{
			System.out.print(hr + ":0" + min + ":0" + sec + " -");	//12:01:01
		}
		else if (min<10 && sec>=10)
		{
			System.out.print(hr + ":0" + min + ":" + sec + " - ");	//12:01:10
		}
		else if (min>=10 && sec<10)
		{
			System.out.print(hr + ":" + min + ":0" + sec + " - ");	//12:10:01
		}
		else if (min>=10 && sec>=10)
		{
			System.out.print(hr + ":" + min + ":" + sec + " - ");	//12:10:10
		}
	}//age
	
	/**
	 * Busy Wait Method:
	 * Makes the thread busy wait until the signal is true.
	 */
	public static void busyWait()
	{
		printAction("is now busy waiting.");
		while(!Project1.getContestantSignal(name))
		{
			/*Busy Wait*/
		}//while
		printAction("has stopped busy waiting.");
	}//busyWait

	/**
	 * Next Rand Method:
	 * Generates a random number between 0 and the limit.
	 * 
	 * @param limit The highest possible random number
	 * @return randNum The new random number
	 */
	public static int nextRand(int limit)
	{
		return randNum = randGenerator.nextInt(limit);
	}//nextRand
	
	/**
	 * Run Method:
	 * 
	 * This is the run method for the Contestant thread.
	 */
	public void run()
	{
		try
		{
			printAction("has been created.");
			//contestant sleeps until Announcer's signal
			busyWait();
			printAction("is finished busy waiting");
			
			//generate a random number for each contestant
			printAction("generating random numbers");
			nextRand(400);
			printAction("has finished generating random numbers");
		
			//update or access the counter
			printAction("is updating the counter");
		
			//announcer picks 3 contestants, terminate or sleep when appropriate
				
			//announcer starts intro process
		
			//INTRODUCTION - Cn increases priority
			printAction("has increased priority");
		
			//Cn says introduction
			printSpeech("Hello, my name is: ");
		
			//	Cn decreases priority
			printAction("has decreased priority");
		
			//Cn yields to C2
			printAction("has now yielded to next contestant");
		
			//QUESTIONING - Cn busy waits, host starts question process
			printAction("is now busy waiting");
		
			//Host picks random contestant for question
			//that contestant exits busy waiting
			printAction("has stopped busy waiting");
		
			//then generates a random number between 0 and 2
			printAction("is now generating a random number");
		
			//Prints a message for the answer
			printSpeech("Alex, I have the answer");
		
			//busy wait until the next question
			printAction("has started busy waiting again");
		
			//FJ - 3 contestants will pick random bets, between 0 and their current score
			printAction("is now picking a random bet.");
		
			//if any contestant doesn't have enough, that contestant will terminate
			printAction("is now terminating due to lack of funds.");
			
			//busy wait for the final question
			printAction("is now busy waiting for the final question.");
			
			//host asks final question,
			//contestants stop busy waiting
			printAction("has stopped busy waiting.");
		
			//contestants will sleep for 3 different, fixed, amounts of time
			printAction("is sleeping for 1");
		
			//1st contestant wakes up
			printAction("is the first contestant to wake up");
		
			//tells host their question is answered
			printSpeech("Alex, I have the answer");
		
			//second contestant wakes up
			printAction("is the second contestant to wake up");
		
			//tells host that the question is answered
			printSpeech("Alex, I have the answer");
		
			//first contestant leaves
			printAction("has left the stage.");
		
			//first contestant terminates
			printAction("has terminated");
		
			//third contestant wakes up
			printAction("is the third contestant to wake up");
		
			//third contestant simply leaves
			printAction("has left the stage");
		
			//second contestant leaves the stage
			printAction("has left the stage");
		
			//second contestant terminates
			printAction("has terminated");
		
			//all three contestants should be terminated
		}
		catch(Exception e)
		{
			System.out.println("Problem with Contestant thread!");
			e.printStackTrace();
		}
	}//run
}//Contestant class
