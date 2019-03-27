import java.util.*;

/**
 * Host Class:
 * 
 * @author Angel Guevara, Jr.
 */
public class Host extends Thread
{
	//Member variables:
	String name="Trebek";
	boolean announcerSignal=false;
	boolean contestantSignal = false;
	Random randGenerator = new Random();
	Random percentage = new Random();
	int correct=0;
	int randNum=0;
	int[] cSleep;
	int sleep=0;
	static int questionNum=1;
	static int roundNum=1;
	static int totQuestions;
	static int totRounds;
	int randContestant=0;
	int winner=0;
	
	Project1 p;
	boolean terminate=false;
	
	/**
	 * Host Constructor:
	 * Initializes variables for the Host thread
	 */
	public Host(Project1 p1)
	{
		p=p1;
		totQuestions=p.numQuestions;
		totRounds=p.numRounds;
	}//Host Constructor
	
	/**
	 * Name Method:
	 * Returns the name of the Host thread.
	 * 
	 * @return name - the name of the Host.
	 */
	public String name()
	{
		return name;
	}//name
	
	/**
	 * Print Speech Method:
	 * Prints out what the Host is saying.
	 * 
	 * @param message - the dialog to be printed.
	 */
	public void printSpeech(String message)
	{
		System.out.println(p.age() + name() + ": " + message);
	}//printSpeech
	
	/**
	 * Print Action Method:
	 * Prints out what the Host is doing.
	 * 
	 * @param message - the action to be printed.
	 */
	public void printAction(String message)
	{
		System.out.println(p.age() + name() + " " + message);
	}//printAction
	
	/**
	 * Busy Wait for Announcer Method:
	 * Makes the Host thread busy wait until the Announcer sends his signal
	 */
	public void busyWaitAnnouncer()
	{
		//printAction("is now busy waiting.");
		while(!p.ah_Signal)
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
		p.ah_Signal=false;
		//printAction("has finished busy waiting.");
	}//busyWaitAnnouncer
	
	/**
	 * Busy Wait for Contestants Method:
	 * Makes the Host thread busy wait until all contestants send their signal through.
	 */
	public void busyWaitContestants()
	{
		//printAction("is now busy waiting.");
		while(p.getCount()!=0)
		{
			try
			{
				Thread.sleep(500);
			}
			catch(InterruptedException ie)
			{
				ie.printStackTrace();
			}
		}//while
		//reset signal
		p.contestantCounter=p.playingContestants;
		//printAction("has stopped busy waiting.");
	}//busyWaitContestants
		
	/**
	 * Born Method:
	 * This is where the Host makes his introduction, then goes to sleep.
	 */
	public void born()
	{
		printSpeech("Good evening, everyone, I'm Alex Trebek.");
		p.ha_Signal=true;
		try{Thread.sleep(99999999);}
		catch(InterruptedException ie){}
	}
	
	/**
	 * Next Random Number Method:
	 * Generates a random number between 0 and the limit.
	 * 
	 * @param limit - the highest possible random number.
	 * @return randNum - the new random number
	 */
	public int nextRand(int limit)
	{
		return randNum=randGenerator.nextInt(limit);
	}//nextRand
	
	/**
	 * Play Game Method:
	 * Goes through the motions for Jeopardy.
	 * 
	 * The Host asks a question, goes to sleep for a random time,
	 * picks a random contestant, randomly decides if the contestant is correct,
	 * and updates that contestant's score accordingly.
	 */
	public void askQuestion()
	{
		//say question
		printSpeech("Contestants, here is question number " + questionNum + "...");
		
		//sleep for a random amount of time
		sleep = nextRand(2000);
		try{sleep(sleep);}
		catch(InterruptedException e){}
		
		//pick a random contestant
		int randContestant = nextRand(p.playingContestants);
		printSpeech(p.playingContestant[randContestant].name + ", what is your answer?");
		p.playingContestant[randContestant].interrupt();
		
		//BWC(until question is answered)
		printAction("is busy waiting.");
		while(!p.gaveAnswer)
		{
			try{sleep(500);}
			catch(InterruptedException e){}
		}
		p.gaveAnswer=false;
		printAction("has finished busy waiting.");
		
		double conversion = 100*p.rightPercent;
		int percent = (int)conversion;
		correct = nextRand(100);
		//grade answer randomly
		if(correct<percent)
		{
			printSpeech(p.playingContestant[randContestant].name + ", you are correct!");
			//update score
			increaseScore(randContestant);
		}
		else if(correct>=percent)
		{
			printSpeech(p.playingContestant[randContestant].name + ", I'm sorry but you were incorrect.");
			//update score
			decreaseScore(randContestant);
		}
		
		//announce the new round, if necessary
		if(questionNum==totQuestions && roundNum+1<=totRounds)
		{
			printSpeech("It's now time for round " + (roundNum+1) + "!");
		}
		
	}//playGame
	
	/**
	 * Increase Score Method:
	 * This is where the Host will increase the current Contestant's score, if the Contestant got the right answer.
	 */
	public void increaseScore(int rc)
	{
		p.playingContestant[rc].score += p.questionValues;
		p.playingContestant[rc].printAction("now has a score of: " + p.playingContestant[rc].score);
	}//increaseScore
	
	/**
	 * Decrease Score Method:
	 * This is where the Host will decrease the current Contestant's score, if the Contestant got the right answer.
	 */
	public void decreaseScore(int rc)
	{
		p.playingContestant[rc].score -= p.questionValues;
		p.playingContestant[rc].printAction("now has a score of: " + p.playingContestant[rc].score);
	}//decreaseScore
		
	/**
	 * Play FJ Method:
	 * Takes the current game into the Final Jeopardy section.
	 * 
	 * This is where the Host will interrupt sleeping Contestants,
	 * then set their fixed sleep times, and initiate the Final Jeopardy section.
	 */
	public void startFJ()
	{	
		//interrupt the sleeping contestants
		for(int i=0; i<p.playingContestants; i++)
		{
			p.playingContestant[i].interrupt();
		}
		
		//prepare sleep times
		for(int i=0; i<p.playingContestants; i++)
		{
			p.playingContestant[i].sleepTime=5000+(1000*i);
		}//for
		
		//say final jeopardy
		printSpeech("It's now time for final jeopardy!");
		
		//BWC(until bets are placed)
		busyWaitContestants();
	}//playFJ
	
	/**
	 * Ask FJ Question Method:
	 * This is where the Host will ask the Final Jeopardy question,
	 * and wait for everyone's answers
	 */
	public void askFJQ()
	{
		//say final question
		printSpeech("Alright, contestants, here is the final question: ");
		
		for(int i=0; i<p.playingContestants; i++)
		{
			p.playingContestant[i].hostSignal=true;
		}
		
		//BWC(until all C's answer the question)
		//busyWaitContestants();
		int remaining=0;
		for(int i=0; i<p.playingContestants; i++)
		{
			if(p.playingContestant[i].isAlive())
			{
				remaining++;
			}
		}
		p.contestantCounter=remaining;
		busyWaitContestants();
	}
	
	/**
	 * Pick Winner Method:
	 * This is where the ending sequence will take place.
	 * The Host will pick a random winner, 
	 * print out everyone's scores,
	 * then leave the stage.
	 */
	public void pickWinner()
	{
		//pick a random winner
		winner = nextRand(p.playingContestants);
		
		//print all scores
		for(int i=0; i<p.playingContestants; i++)
		{
			printSpeech(p.playingContestant[i].name + ", your final score is: " + p.playingContestant[i].score);
		}
		
		//display the winner
		printSpeech("Congratulations, " + p.playingContestant[winner].name + ", you've won tonight's game!");
		
		//leave
		leave();
	}
	
	/**
	 * Leave Method:
	 * Initiates the leaving process of the game.
	 */
	public void leave()
	{
		printSpeech("Good night, everyone! I hope you all had fun!");
		printAction("has left the stage.");
	}//leave
	
	/**
	 * Run Method:
	 * 
	 * This is the run method for the Host thread.
	 * It calls all the methods within the Host class.
	 */
	public void run()
		{
			try
			{
				//introduce yourself
				born();

				//tell contestants to exit BW, so they can sleep
				for(int i=0; i<p.playingContestants; i++)
				{
					p.playingContestant[i].hostSignal=true;
				}
				
				//busy wait for the contestants
				busyWaitContestants();
				
				//cycle through the number of rounds
				for(int i=0; i<p.numRounds; i++)
				{
					//cycle through the number of questions
					for(int j=0; j<p.numQuestions; j++)
					{
						askQuestion();
						questionNum++;
					}
					roundNum++;
					questionNum=1;
				}
				
				//play final jeopardy
				startFJ();
				askFJQ();
				
				//pick the winner
				pickWinner();

				printAction("has terminated.");
			}//try
			catch(Exception e)
			{
				System.out.println("Problem with Host thread!");
				e.printStackTrace();
			}//catch
		}//run
		
}//Host Class