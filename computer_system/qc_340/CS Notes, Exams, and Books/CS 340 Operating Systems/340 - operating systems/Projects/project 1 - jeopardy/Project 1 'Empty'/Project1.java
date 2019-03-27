import java.util.*;

/**
 * CS340 - Project I - Jeopardy
 * Evening session: 6:30-7:45
 * 
 * This class is mainly for synchronizing Announcer, Host, and Contestant Threads.
 * 
 * All of the synchronized methods are implemented and called here.
 * 
 * Due: 4-30-09
 * @author - Angel Guevara, Jr.
 */
public class Project1 
{
	//Game's variables:
	static int numRounds;
	static int numQuestions;
	static int questionValues;
	static double rightPercent;
	
	//Contestant's variables:
	static int numContestants;	//total number of contestants
	static Contestant[] contestant;	//contestant array
	
	static boolean cSignal=false;
	static boolean aSignal=false;
	static boolean hSignal=false;
		
	/**
	 * Project1 Constructor:
	 * Initializes all variables
	 */
	public Project1()
	{
		numRounds = 0;
		numQuestions = 0;
		questionValues = 0;
		rightPercent = 0.0;
		
		//variables for Announcer thread
		
		//variables for Contestant thread
		numContestants=4;
		
	}
	
	/**
	 * Main method, initializes and starts threads.
	 * 
	 * @param args[0] The number of rounds per game
	 * @param args[1] The number of questions per round
	 * @param args[2] The number of points per question
	 * @param args[3] The percentage for the contestants' handicap
	 */
	public static void main(String[] args)
	{
		try //might take out try/catch block
		{
			Project1 project1 = new Project1();
		
			numRounds = Integer.parseInt(args[0]);
			numQuestions = Integer.parseInt(args[1]);
			questionValues = Integer.parseInt(args[2]);
			rightPercent = Double.parseDouble(args[3]);
			
			initThreads();
		}//try
		catch(Exception e)
		{
			System.out.println("Missing argument values");
			System.out.println("Example: 2 5 200 0.7, for rounds, questions, values, and percentage.");
		}//catch
	}//main
	
	/**
	 * Init Threads Method:
	 * Starts the Announcer and Contestant threads.
	 */
	public static void initThreads()
	{
		Announcer announcer = new Announcer();
		announcer.start();
		
		Contestant[] contestant = new Contestant[numContestants];
		for(int i=0; i<numContestants; i++)
		{
			contestant[i].start();
		}//for
	}//initThreads

	/**
	 * Set Contestant Signal Method:
	 * Sets the signal for the Contestant Threads.
	 * 
	 * @param contestantSignal
	 */
	public static void setContestantSignal(boolean contestantSignal)
	{
		cSignal = contestantSignal;
	}
	/**
	 * Set Announcer Signal Method:
	 * Sets the signal for the Announcer Thread.
	 * 
	 * @param announcerSignal
	 */
	public static void setAnnouncerSignal(boolean announcerSignal)
	{
		aSignal = announcerSignal;
	}
	/**
	 * Set Host Signal Method:
	 * Sets the signal for the Host Thread.
	 * 
	 * @param hostSignal
	 */
	public static void setHostSignal(boolean hostSignal)
	{
		hSignal = hostSignal;
	}
	/**
	 * Get Contestant Signal Method:
	 * Returns the signal for the Contestant Threads.
	 * 
	 * @return cSignal
	 */
	public static boolean getContestantSignal()
	{
		return cSignal;
	}
	/**
	 * Get Announcer Signal Method:
	 * Returns the signal for the Announcer Threads.
	 * 
	 * @return aSignal
	 */
	public static boolean getAnnouncerSignal()
	{
		return aSignal;
	}
	/**
	 * Get Host Signal Method:
	 * Returns the signal for the Host Thread.
	 * 
	 * @return hSignal
	 */
	public static boolean getHostSignal()
	{
		return hSignal;
	}
	
	
}//Project1 class