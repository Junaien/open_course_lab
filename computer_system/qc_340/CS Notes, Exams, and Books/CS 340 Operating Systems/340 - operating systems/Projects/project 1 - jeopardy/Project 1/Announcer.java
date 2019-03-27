import java.util.*;

/**
 * CS340 Project 1
 * Evening session: 6:30-7:45
 * 
 * This class is for the Announcer thread.
 * Describe what the announcer does........
 * 
 * Due: 4-30-09
 * @author - Angel Guevara, Jr.
 */
public class Announcer extends Thread
{
	static String name = "Announcer";
	static boolean contestantSignal=false;
	static boolean hostSignal=false;
	
	/**
	 * Announcer Constructor
	 * 
	 * @param - 
	 */
	public Announcer()
	{
		
	}//Announcer constructor
	
	/**
	 * Name Method:
	 * Assigns and returns the name of the Announcer thread.
	 * 
	 * @return name The name of the thread
	 */
	public static String name()
	{
		return name;
	}
	
	/**
	 * Print Speech Method: 
	 * Prints out a Message of the Announcer Thread.
	 */
	public static void printSpeech(String message)
	{
		age();	//print out the current time
		System.out.println(name() + ": " + message);		//print out the message
	}//printMessage
	
	/**
	 * Print Action Method:
	 * Prints out an action of the Announcer Thread.
	 */
	public static void printAction(String message)
	{
		age();	//print out the current time
		System.out.println(name() + " " + message);	//print out the message
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
			hr=12;
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
		while(!Project1.getAnnouncerSignal())
		{
			/*Busy Wait*/
		}
		printAction("has stopped busy waiting.");
	}
	
	/**
	 * Start Host Method:
	 * Starts the Host thread
	 */
	public static void startHost()
	{
		printSpeech("Give it up for your host, Alex Trebek!");
		Host host = new Host();
		host.start();
	}
	
	/**
	 * Run Method:
	 * 
	 * This is the run method for the Announcer thread.
	 */
	public void run()
	{
		try
		{
			//print welcome message
			printSpeech("Welcome to Jeopardy!");
			
			//start the host
			startHost();
			
			//tell the contestants to get ready
			
			printAction("About to tell Contestants to get ready. Signal: " + contestantSignal);
			contestantSignal=true;
			Project1.setContestantSignal(contestantSignal);
			printAction("Told Contestants to get ready. Signal: " + contestantSignal);
			
			//wait for the contestants to finish generating their random numbers
			busyWait();
			
			//pick contestants
			printSpeech("It's time to choose our contestants!");

			//wake up contestant threads
			
			//say who stays
			
			//start intros
			printSpeech("Let's meet our contestants!");
			
			//busy wait until contestants are done with their intros
			
			//start the game
			printSpeech("It's now time to start the game!");
			
			
			//terminate
		}
		catch(Exception e)
		{
			System.out.println("Problem with Announcer thread!");
			e.printStackTrace();
		}		
	}//run
}//Announcer class
