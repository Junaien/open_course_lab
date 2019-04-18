import java.util.*;

/**
 * Contestant Class:
 * Describe what the contestants do.....
 * 
 * @author Angel Guevara, Jr.
 */
public class Contestant extends Thread
{
	//Member variables:
	String name;
	int pickNum;
	boolean announcerSignal=false;
	boolean hostSignal=false;
	Random randGenerator = new Random();
	int randNum=0;
	int ownNumber=0;
	int score=0;
	int answer=0;
	int bet=0;
	int sleepTime=0;
	Project1 p;
	boolean terminate=false;
	boolean accepted=false;
	boolean rejected=false;
	
	/**
	 * Contestant Constructor:
	 * Initializes the Contestant's variables
	 */
	public Contestant(Project1 p1, String n)
	{
		p=p1;
		name=n;
	}//Contestant
	
	/**
	 * Name Method:
	 * Returns a String value of the current Contestant's name
	 * 
	 * @return name - the name to be printed
	 */
	public String name()
	{
		return name;
	}//name
	
	/**
	 * Sets the name for a given contestant
	 * 
	 * @param cName - the desired name for the current Contestant
	 */
	public void makeName(String cName)
	{
		name=cName;
	}//makeName
	
	/**
	 * Print Speech Method:
	 * Prints out a given message as speech.
	 * 
	 * @param message - the dialog to be printed
	 */
	public void printSpeech(String message)
	{
		System.out.println(p.age() + name() + ": " + message);
	}//printSpeech
	
	/**
	 * Print Action Method:
	 * Prints out a given action.
	 * 
	 * @param message - the action to be printed
	 */
	public void printAction(String message)
	{
		System.out.println(p.age() + name + " " + message);
	}//printAction
	
	/**
	 * Born Method:
	 * Decreases the counter and sends the Contestant into a busy wait,
	 * waiting for the signal from the Announcer class.
	 */
	public void born()
	{
		accessCounter();
		busyWaitAnnouncer();
	}//born
	
	/**
	 * Generate ownNum Method:
	 * This is where the Contestants will generate their random numbers, so the
	 * Announcer can pick who stays and who goes.
	 */
	public void genOwnNum()
	{
		//generate ownNum
		ownNumber=nextRand(400);
		printAction("drew number " + ownNumber);
		
		//decCounter
		accessCounter();
		
		//BWA (playing Contestant's intro)
		busyWaitAnnouncer();
	}
	
	/**
	 * Say Intro Method:
	 * This is where the Contestants will be able to introduce themselves,
	 * right before the game starts.
	 */
	public void sayIntro()
	{
		//increase priority
		this.setPriority(this.getPriority()+1);

		//say intro
		printSpeech("Hello, my name is " + name());
		
		//dec counter (announcer is busy waiting for intros)
		accessCounter();
		
		//decrease priority
		this.setPriority(this.getPriority()-1);

		//yield to other contestants
		this.yield();
		
		//bwh (to answer the question)
		busyWaitHost();
	}//sayIntro
	
	/**
	 * Generate Answer Method:
	 * This is where the Contestants will generate a
	 * random answer, between 0 and 2.
	 */
	public void genAnswer()
	{
		//generate answer (between 0 and 2)
		answer = nextRand(2);
		
		//print answer
		if(Host.roundNum <= Host.totRounds)
		{
			printSpeech("Alex, my answer is: " + answer);
		}
		p.gaveAnswer=true;
	}
	
	/**
	 * Busy Wait for Announcer method:
	 * Busy Waits until the Announcer sends his signal.
	 */
	public void busyWaitAnnouncer()
	{
		//printAction("is now busy waiting.");
		while(announcerSignal==false)
		{
			try
			{
				Thread.sleep(500);
			}
			catch(InterruptedException ie)
			{
				ie.printStackTrace();
			}
		}
		//reset signal
		announcerSignal=false;
		//printAction("has finished busy waiting.");
	}//busyWaitAnnouncer
	
	/**
	 * Busy Wait for Host Method:
	 * Busy Waits until the Host sends his signal.
	 */
	public void busyWaitHost()
	{
		//printAction("is now busy waiting for the Host.");
		while(!hostSignal)
		{
			try
			{
				Thread.sleep(500);
			}
			catch(InterruptedException ie)
			{
				ie.printStackTrace();
			}
		}
		hostSignal=false;
		//printAction("has finished busy waiting for the Host.");
	}//busyWaitHost
		
	/**
	 * Busy Wait for Accept/Reject Method:
	 * Makes the Contestant busy wait until the Announcer says to go home or stay
	 */
	public void busyWaitAcceptReject()
	{
		//printAction("is now busy waiting for the Announcer.");
		while(!p.getContestantSignal(name))
		{
			//if Contestant is accepted, go to sleep
			if(accepted){break;}
			else if(rejected){terminate=true;break;}
		}//while
		//printAction("has stopped busy waiting for the Announcer.");
	}//busyWaitAcceptReject
	
	/**
	 * Next Random Number Method:
	 * Generates a random number between 0 and the limit.
	 * 
	 * @param limit - the highest possible random number
	 * @return randNum - the new random number
	 */
	public int nextRand(int limit)
	{
		randNum= randGenerator.nextInt(limit)+1;
		return randNum;
	}//nextRand
	
	/**
	 * Access Counter Method:
	 * This is where the Contestant will decrease the contestantCounter
	 */
	public void accessCounter()
	{
		p.decCounter();
	}//accessCounter
	
	/**
	 * Question Process:
	 * This is where all the Contestants get into the first part of the game.
	 * The while loop ensures all the Contestants will remain asleep while the game is going on.  If the 
	 * Host interrupts sleep, the chosen Contestant will answer the question.
	 */
	public void questionProcess()
	{
		accessCounter();
		while(Host.roundNum <= Host.totRounds)
		{
			//sleep intil the host interrupts
			try{sleep(99999999);}
			catch(InterruptedException ie){}
		
			//GENERATE ANSWER
			genAnswer();
		}
	}//questionProcess
	
	/**
	 * Final Jeopardy Method:
	 * This is where the Contestants enter the Final Jeopardy section of the game.
	 * Here, the Contestants will generate a random bet, from 0 to their score.
	 * If their score is 0, they must terminate.
	 * 
	 * After the Host asks the final question,
	 * the remaining Contestants will sleep for 3 different, fixed, amounts of time,
	 * then they will answer the quetsion in the order they wake up.
	 */
	public void finalJeopardy()
	{		
		if(score<=0)
		{
			printAction("has a score that is too low.");
			accessCounter();
		}
		else if(score>0)
		{
			bet = nextRand(score);
			printSpeech("Alex, I'm going to bet " + bet);
			accessCounter();
			
			//BW until the host asks the final question
			busyWaitHost();
			
			//contestants will sleep for 3 different, fixed, amounts of time
			try{Thread.sleep(sleepTime);}
			catch(InterruptedException ie){ie.printStackTrace();}
			
			//Contestant wakes up and tells host their question is answered
			printSpeech("Alex, I have an answer.");
			accessCounter();
		}
	}//finalJeopardy
	
	/**
	 * Leave Method:
	 * Sequence for the Contestant to leave
	 */
	public void leave()
	{
		printAction("is leaving the stage.");
	}//leave
	
	/**
	 * Run Method:
	 * This is the Run method for the Contestant thread.
	 * It calls all of the methods within the Contestant class.
	 */
	public void run()
	{
		try
		{
			//born
			born();
			
			//GENERATE OWNNUM
			genOwnNum();
			
			if(!terminate)
			{
				//MAKE INTRO
				sayIntro();
				
				//start the question process
				questionProcess();
								
				//GENERATE BET
				finalJeopardy();
			}
			//leave
			leave();
			printAction("has terminated");
		}//try
		catch(Exception e)
		{
			System.out.println("Problem with Contestant Thread!");
			e.printStackTrace();
		}//catch
	}//run
}//Contestant class
