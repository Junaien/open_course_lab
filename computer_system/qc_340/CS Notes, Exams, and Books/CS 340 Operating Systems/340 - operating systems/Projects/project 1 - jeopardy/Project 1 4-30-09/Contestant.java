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
	 * Prints out the current Contestant's name
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
	 * @param cName
	 */
	public void makeName(String cName)
	{
		name=cName;
	}//makeName
	
	/**
	 * Print Speech Method:
	 * Prints out a given message as speech.
	 * 
	 * @param message - the message to be printed
	 */
	public void printSpeech(String message)
	{
		System.out.println(p.age() + name() + ": " + message);
	}//printSpeech
	
	/**
	 * Print Action Method:
	 * Prints out a given action.
	 * 
	 * @param message - the message to be printed
	 */
	public void printAction(String message)
	{
		System.out.println(p.age() + name + " " + message);
	}//printAction
	
	/**
	 * Born Method:
	 * Shows that the current Contestant has been created.
	 */
	public void born()
	{
		printAction("has been created.");
		accessCounter();
		busyWaitAnnouncer();
	}//born
	
	/**
	 * Generate ownNum Method:
	 * GENERATE RANDOM OWNNUM, 
	 * DEC COUNTER, 
	 * BWA (while announcer chooses players)
	 */
	public void genOwnNum()
	{
		//generate ownNum
		nextRand(400);
		//decCounter
		p.decCounter();
		//BWA (playing Contestant's intro)
		busyWaitAnnouncer();
	}
	
	/**
	 * Say Intro Method:
	 * SAY INTRO, 
	 * DEC COUNTER (announcer is waiting)
	 * BWH (to answer the question)
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

		//bwh (to answer the question)
		busyWaitHost();
	}//sayIntro
	
	/**
	 * Generate Answer Method:
	 * RANDOM ANSWER (0-2)
	 * PRINT ANSWER
	 * BWH (for next answer)
	 */
	public void genAnswer()
	{
		//generate answer (between 0 and 2)
		answer = nextRand(2);
		
		//print answer
		printSpeech("Alex, my answer is: " + answer);
		p.gaveAnswer=true;
	}
	
	/**
	 * Generate Bet Method:
	 * RANDOM BET (0-score)
	 * PRINT BET
	 * DEC COUNTER
	 * BWH (for final question)
	 */
	public void genBet()
	{
		//pickBet (between 0 and score)
		bet = nextRand(score);
		
		//print bet
		printSpeech("Alex, my bet is: " + bet);
		
		//decCounter
		p.decCounter();
		
		//bwh(final question)
		busyWaitHost();
	}
	
	/**
	 * Busy Wait for Announcer method:
	 * Busy Waits until the Announcer sends his signal.
	 */
	public void busyWaitAnnouncer()
	{
		printAction("is now busy waiting for the Announcer.");
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
		printAction("has stopped busy waiting for the Announcer.");
	}//busyWaitAnnouncer
	
	/**
	 * Busy Wait for Host Method:
	 * Busy Waits until the Host sends his signal.
	 */
	public void busyWaitHost()
	{
		printAction("is now busy waiting for the Host.");
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
		printAction("has stopped busy waiting for the Host.");
	}//busyWaitHost
		
	/**
	 * Busy Wait for Accept/Reject Method:
	 * Makes the Contestant busy wait until the Announcer says to go home or stay
	 */
	public void busyWaitAcceptReject()
	{
		printAction("is now busy waiting for the Announcer.");
		while(!p.getContestantSignal(name))
		{
			//if Contestant is accepted, go to sleep
			if(accepted){break;}
			else if(rejected){terminate=true;break;}
		}//while
		printAction("has stopped busy waiting for the Announcer.");
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
		ownNumber = randGenerator.nextInt(limit)+1;
		return ownNumber;
	}//nextRand
	
	/**
	 * Access Counter Method:
	 * This is where the Contestant will access the contestantCounter
	 */
	public void accessCounter()
	{
		p.decCounter();
	}//accessCounter
	
	/**
	 * Question Process:
	 * This is where all the Contestants get into the first part of the game.
	 */
	public void questionProcess()
	{
		printAction("has begun the question process.");	
		accessCounter();
		printAction("round number: " + Host.roundNum + "  total rounds: " + Host.totRounds);
		while(Host.roundNum <= Host.totRounds)
		{
			//sleep intil the host interrupts
			printAction("is going to sleep");
			try{sleep(99999999);}
			catch(InterruptedException ie){}
		
			//GENERATE ANSWER
			genAnswer();
		}		
		printAction("is about to finish the question process.");
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
		printAction("has begun Final Jeopardy");
		
		if(score<=0)
		{
			printAction("has a score that is too low.");
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
			printSpeech("I have an answer. ");
			accessCounter();
		}

		printAction("is about to finish Final Jeopardy.");
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
