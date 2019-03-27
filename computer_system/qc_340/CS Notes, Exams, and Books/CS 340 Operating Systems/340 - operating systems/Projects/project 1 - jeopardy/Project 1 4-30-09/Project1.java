import java.util.*;

/**
 * CS340 - Project 1 - Jeopardy
 * Evening session: 6:30-7:45
 * 
 * This class is mainly for synchronizing the Announcer, Host, and Contestant Threads.
 * 
 * All of the synchronized methods are implemented and called here.
 * 
 * Due: 4-30-09
 * @author Angel Guevara, Jr.
 *
 */
public class Project1
{
	//Game Variables
	static int numRounds;
	static int numQuestions;
	static int questionValues;
	static double rightPercent;
	
	//Thread Variables:
	int numContestants;
	int playingContestants;
	int contestantCounter;
	int playingContestantCounter;
	int[] cSleepTime;
	Contestant[] contestant;
	Contestant[] playingContestant;
	boolean[] cSignal;
	boolean ac_Signal=false; //signal from announcer to contestant
	boolean ah_Signal=false; //signal from announcer to host
	boolean[] ca_Signal; //signal from contestant to announcer
	boolean[] ch_Signal; //signal from contestant to host
	boolean ha_Signal=false; //signal from host to announcer
	boolean hc_Signal=false; //signal from host to contestant
	boolean gaveAnswer=false;
	
	/**
	 * Project1 Constructor:
	 * Initializes all variables
	 */
	public Project1()
	{	
		//Thread Variables:
		numContestants=4;
		playingContestants=3;
		cSignal=new boolean[numContestants];
		contestant=new Contestant[numContestants];
		playingContestant = new Contestant[numContestants];
		cSleepTime = new int[playingContestants];
		contestantCounter=4;
	}
	
	/**
	 * Main Method:
	 * 
	 * Initializes and starts Announcer and Contestant threads.
	 * 
	 * @param args[0] - the number of rounds per game
	 * @param args[1] - the number of questions per round
	 * @param args[2] - the number of points per question
	 * @param args[3] - the percentage for the contestants' handicap
	 */
	public static void main(String[] args)
	{
		try
		{
			System.out.println("********SARTING GAME********");
			
			numRounds=Integer.parseInt(args[0]);
			numQuestions=Integer.parseInt(args[1]);
			questionValues=Integer.parseInt(args[2]);
			rightPercent=Double.parseDouble(args[3]);
		}//try
		catch(Exception e)
		{
			System.out.println("Missing argument values!");
			System.out.println("Example: 2 5 200 0.7, for rounds, questions, values, and percentage");
			e.printStackTrace();
		}//catch
		Project1 project1=new Project1();
		project1.initThreads(project1);
	}//main
	
	/**
	 * Init Threads Method:
	 * Initializes the Announcer and Contestants Threads.
	 * 
	 * @param project1
	 */
	public void initThreads(Project1 p1)
	{
		//Start the Announcer thread:
		Announcer announcer=new Announcer(p1);
		announcer.start();
		//Start the Contestants threads:
		for(int i=0; i<numContestants; i++)
		{
			contestant[i] = new Contestant(p1, "Contestant " + (i+1));
			contestant[i].start();
		}//for
		//Initialize playing contestant array
		for(int i=0; i<numContestants; i++)
		{
			playingContestant[i] = new Contestant(p1, "Contestant " + (i+1));
		}
	}//initThreads
	
	/**
	 * Set Contestant Signal Method:
	 * Sets the signal for the Contestant Threads
	 * 
	 * @param contestantSignal
	 */
	public void setContestantSignal(boolean contestantSignal)
	{
		for(int i=0; i<numContestants; i++)
		{
			cSignal[i]=contestantSignal;
		}//for
	}//setContestantSignal
	
	/**
	 * Set Announcer Signal Method:
	 * Sets the signal for the Announcer Thread
	 * 
	 * @param announcerSignal
	 */
	/*
	public void setAnnouncerSignal(boolean announcerSignal)
	{
		aSignal=announcerSignal;
	}//announcerSignal
	*/
	
	/**
	 * Set Host Signal Method:
	 * Sets the signal for the Host Thread.
	 * 
	 * @param hostSignal
	 */
	/*
	public void setHostSignal(boolean hostSignal)
	{
		hSignal=hostSignal;
	}
	*/
	
	/**
	 * Get ContestantSignal Method:
	 * Returns the signal for a specified Contestant thread.
	 * Figures out which contestant by pulling the number from the name.
	 * 
	 * @param cName - the name of the specified Contestant thread
	 * @return cSignal - the value of that particular Contestant's signal
	 */
	public boolean getContestantSignal(String cName)
	{
		int i=cName.charAt(11);
		return cSignal[i-1];
	}//getContestantSignal
	
	/**
	 * Get Announcer Signal Method:
	 * Returns the signal for the Annoucer thread.
	 * 
	 * @return aSignal - the Announcer's signal
	 */
	/*
	public boolean getAnnouncerSignal()
	{
		return aSignal;
	}
	*/
	
	/**
	 * Get Host Signal Method:
	 * Returns the signal for the Host thread.
	 * 
	 * @return hSignal - the Host's signal
	 */
	/*
	public boolean getHostSignal()
	{
		return hSignal;
	}//getHostSignal
	*/
	
	/**
	 * Decrease Counter Method:
	 * Decreases the counter when a Contestant thread calls this method.
	 */
	public synchronized void decCounter()
	{
		contestantCounter--;
	}//decCounter
	
	/**
	 * Set Sleep Time Method:
	 * This is where the Host thread will assign sleep times for the Final Jeopardy section.
	 * @param time - the amount of time to sleep
	 * @param index - the index of the playing contestant array.
	 */
	public void setSleepTime(int time[], int index)
	{
		playingContestant[index].sleepTime=time[index];
	}
	
	/**
	 * Get Sleep Time:
	 * Returns the sleep time for a specific contesetant
	 * @return sleepTime - the amount of time to sleep
	 */
	public int getSleepTime()
	{
		int sleepTime=0;
		return sleepTime;
	}
	
	/**
	 * Get Count Method:
	 * Returns the value of the Contestant's counter.
	 * 
	 * @return contestantCounter - the value of the counter.
	 */
	public int getCount()
	{
		return contestantCounter;
	}//getCount
	
	/**Age Method:
	 * Finds the current time.
	 * 
	 * @return age - the current time in HR:MIN:SEC.MSEC format
	 */
	public String age()
	{
		//initialize time variables and get the current time:
		String age="";
		int hr=0, min=0, sec=0, msec=0;
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(System.currentTimeMillis());
		
		//set the variables to the current time
		hr=now.get(Calendar.HOUR);
		if(hr==0)
		{
			hr=12;
		}
		min=now.get(Calendar.MINUTE);
		sec=now.get(Calendar.SECOND);
		msec=now.get(Calendar.MILLISECOND);
		
		//prepare the hour
		if(hr<10)
		{
			age=("0" + hr);
		}
		else if(hr>=10)
		{
			age=(""+hr);
		}
		age=age.concat(":");
		
		//prepare the minute:
		if(min<10)
		{
			age=age.concat("0"+min);
		}
		else if(min>=10)
		{
			age=age.concat(""+min);
		}
		age=age.concat(":");
		
		//prepare the second:
		if(sec<10)
		{
			age=age.concat("0"+sec);
		}
		else if(sec>=10)
		{
			age=age.concat(""+sec);
		}
		age=age.concat(".");
		
		//prepare the millisecond
		if(msec<10)
		{
			age=age.concat("0"+msec);
		}
		else if(msec>=10)
		{
			age=age.concat(""+msec);
		}
		age=age.concat(" ");
		
		return age;
	}
}//Project1 Class