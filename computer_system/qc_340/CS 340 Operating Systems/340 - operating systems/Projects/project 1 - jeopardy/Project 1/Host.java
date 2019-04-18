import java.util.*;

/**
 * CS340 Project I - Jeopardy
 * Evening Session: 6:30-7:45
 * 
 * This class is for the Host thread.
 * Describe what the host does..........
 * 
 * Due: 4-30-09
 * @author - Angel Guevara, Jr.
 */
public class Host extends Thread
{
	//Member variables
	static String name = "Trebek";
	static boolean announcerSignal=false;
	static boolean contestantSignal=false;
	static Random randGenerator = new Random();
	static int randNum=0;
	
	/**
	 * Host constructor:
	 * 
	 * @param - 
	 */
	public Host()
	{
		
	}//Host constructor
	
	/**
	 * Name Method:
	 * Returns the name of the Host thread.
	 * 	
	 * @return name The name of the thread
	 */
	public static String name()
	{
		return name;
	}//name
	
	/**
	 * Print Message Method:
	 * Prints out what the Host is saying
	 * 
	 * @param message - The message to be printed
	 */
	public static void printSpeech(String message)
	{
		age();	//print out the current time
		System.out.println(name() + ": " + "'" + message + "'");		//print out the message
		//example:
		//12:01:10 - Host : 'Alright, guys, here is your question:
	}//printMessage
	
	/**
	 * Print Action Method:
	 * Prints out what the Host is doing
	 * 
	 * @param message - The action to be printed
	 */
	public static void printAction(String message)
	{
		age();	//print out the current time
		System.out.println(name() + " " + message);	//print out the action
		//example:
		//12:01:10 - Host appears on stage.
	}//printAction
	
	/**
	 * Age Method:
	 * Finds the current time.
	 */
	protected static final void age()
	{
		//initialize time variables and get the current time
		int hr=0, min=0, sec=0;
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(System.currentTimeMillis());
		
		//set the current time to variables
		hr = now.get(Calendar.HOUR);
		if(hr==0)
		{
			hr=12;
		}//if
		min = now.get(Calendar.MINUTE);
		sec = now.get(Calendar.SECOND);
		
		//display the current time
		if(min<10 && sec<10)
		{
			System.out.print(hr + ":0" + min + ":0" + sec + " -");	//12:01:01
		}//if
		else if (min<10 && sec>=10)
		{
			System.out.print(hr + ":0" + min + ":" + sec + " - ");	//12:01:10
		}//else if
		else if (min>=10 && sec<10)
		{
			System.out.print(hr + ":" + min + ":0" + sec + " - ");	//12:10:01
		}//else if
		else if (min>=10 && sec>=10)
		{
			System.out.print(hr + ":" + min + ":" + sec + " - ");	//12:10:10
		}//else if
	}//age
	
	/**
	 * Busy Wait Method:
	 * Makes the thread busy wait until the signal is true.
	 */
	public static void busyWait()
	{
		printAction("is now busy waiting.");
		while(!Project1.getHostSignal())
		{
			/*Busy Wait*/
		}
		printAction("has stopped busy waiting.");
	}
	
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
	}
	
	/**
	 * Run Method:
	 * 
	 * This is the run method for the Host thread.
	 */
	public void run()
	{
		try
		{
			//born
			printAction("appears on stage.");
			printSpeech("Good evening, everyone!  I'm tonight's host, Alex Trebek.");
			
			//sleep until announcer says to wake up
			busyWait();
						
			//announcer says to wake up
			printAction("wakes up.");
			
			//ask question
			printSpeech("Alright, guys, here is your question: ");
			
			//sleep randomly
			printAction("is generating a random number.");
			randNum=randGenerator.nextInt(400);
			printAction("is now sleeping for a random amount of time");
			
			//wake up and pick a random contestant to answer the question
			
			//busy wait until the question is answered
			printAction("is now busy waiting until the question is answered");
			
			//exit the busy wait
			printAction("has now finished busy waiting");
			
			//generate a random number to see if the answer is right
			printAction("is now checking to see if the answer is correct.");
			
			//print appropriate message for the contestant
			
			//update the score (+ or -)
			printAction("is now updating the score.");
			
			//check current number of questions, if OK, go to next question
			printAction("is now checking the current question number.");
			
			//check current round number, if OK, go to next round
			printAction("is now checking current round number..");
			
			//OR go to the final jeopardy
			printAction("is now going into Final Jeopardy..");
					
			//FINAL JEOPARDY - announce final jeopardy
			printSpeech("Alright, the game is almost over!  It's time for Final Jeopardy!");
			//ask final question
			
			
			//END - pick a winner at random
			printSpeech("Okay, now it's time to find tonight's winner!");
			
			//print all 3 contestants' scores
			
			
			//announce the winner
			printSpeech("and the winner is");
			
			//say bye and terminate
			printSpeech("I hope you enjoyed tonight's game of Jeopardy!  See you later!");
			printAction("has now left the stage.");
		}
		catch(Exception e)
		{
			System.out.println("Problem with Host thread!");
			e.printStackTrace();
		}
	}//run
}//Host class
