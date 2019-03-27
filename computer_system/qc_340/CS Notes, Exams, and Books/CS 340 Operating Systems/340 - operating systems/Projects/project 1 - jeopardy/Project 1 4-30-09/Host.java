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
	boolean correctAns=false;
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
	 * @param message - the message to be printed.
	 */
	public void printSpeech(String message)
	{
		System.out.println(p.age() + name() + ": " + message);
		//example:
		//12:01:10 - Trebek: Here is your question:
	}//printSpeech
	
	/**
	 * Print Action Method:
	 * Prints out what the Host is doing.
	 * 
	 * @param message - the message to be printed.
	 */
	public void printAction(String message)
	{
		System.out.println(p.age() + name() + " " + message);
		//example:
		//12:01:10 - Trebek appears on stage
	}//printAction
	
	/**
	 * Busy Wait for Announcer Method:
	 * Makes the Host thread busy wait until the Announcer sends his signal
	 */
	public void busyWaitAnnouncer()
	{
		printAction("is now busy waiting for the Announcer.");
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
		printAction("has stopped busy waiting for the Annoucer.");
	}//busyWaitAnnouncer
	
	/**
	 * Busy Wait for Contestants Method:
	 * Makes the Host thread busy wait until all contestants send their signal through.
	 */
	public void busyWaitContestants()
	{
		printAction("is now busy waiting for the Contestants.");
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
		printAction("has stopped busy waiting for the Contestants.");
	}//busyWaitContestants
	
	/**
	 * Busy Wait for Playing Contestants:
	 * BW until counter=0
	 * RESET SIGNAL
	 */
	public void busyWaitPlayingContestants()
	{
		printAction("is now busy waiting for the Playing Contestants.");
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
		printAction("has stopped busy waiting for the Playing Contestants.");
	}
	
	/**
	 * Born Method:
	 * APPEAR, 
	 * SAY INTRO, 
	 * SIGNAL ANNOUNCER, 
	 * BWA(Start Game)
	 */
	public void born()
	{
		printAction("appears on stage.");
		printSpeech("Good evening, everyone, I'm Alex Trebek.");
		p.ha_Signal=true;
		printAction("BWA for startGame");
		busyWaitAnnouncer();
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
	 */
	public void askQuestion()
	{
		//say question
		printSpeech("Contestants, here is question number " + questionNum + ": ");
		
		//sleep for a random amount of time
		sleep = nextRand(2000);
		try{sleep(sleep);}
		catch(InterruptedException e){}
		
		//pick a random contestant
		int randContestant = nextRand(p.playingContestants);
		printAction("has picked " + p.playingContestant[randContestant].name);
		printSpeech(p.playingContestant[randContestant].name + ", what is your answer?");
		p.playingContestant[randContestant].interrupt();
		
		//BWC(until question is answered)
		printAction("is waiting for an answer.");
		while(!p.gaveAnswer)
		{
			try{sleep(500);}
			catch(InterruptedException e){}
		}
		p.gaveAnswer=false;
		printAction("has finished waiting for an answer");
		
		//grade answer randomly
		if(nextRand(1)==1)
		{
			correctAns=true;
		}
		else if(nextRand(1)==0)
		{
			correctAns=false;
		}
		
		//print whether right or wrong
		if(correctAns=true)
		{
			printSpeech(p.playingContestant[randContestant].name + ", you are correct!");
			//update score
			increaseScore(randContestant);
		}
		else if(correctAns==false)
		{
			printSpeech(p.playingContestant[randContestant].name + ", I'm sorry but you were incorrect.");
			//update score
			decreaseScore(randContestant);
		}
		
		if(questionNum==totQuestions)
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
	 * This is where the Host will increase the current Contestant's score, if the Contestant got the right answer.
	 */
	public void decreaseScore(int rc)
	{
		p.playingContestant[rc].score -= p.questionValues;
		p.playingContestant[randContestant].printAction("now has a score of: " + p.playingContestant[randContestant].score);
	}//decreaseScore
	
	/**
	 * Get Score Method:
	 * This is where the Host can view a Contstant's current score.
	 * 
	 * @return score - int value of the score of a given contestant.
	 */
	public int getScore()
	{
		return p.contestant[0].score;
	}
	
	/**
	 * Play FJ Method:
	 * Takes the current game into the Final Jeopardy section.
	 * 
	 * PREPARE SLEEP TIMES
	 * SAY FJ
	 * SET TIMES
	 * BWC (until bets are placed)
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
			printAction("has set " + p.playingContestant[i].name + "'s sleep time to: " + p.playingContestant[i].sleepTime);
		}//for
		
		//say final jeopardy
		printSpeech("It's now time for final jeopardy!");
		
		//BWC(until bets are placed)
		busyWaitContestants();
	}//playFJ
	
	/**
	 * Ask FJ Question Method:
	 * SAY FJQ
	 * BWC (until C's answer question)
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
		busyWaitContestants();
	}
	
	/**
	 * End Game Method:
	 * This is where the ending sequence will take place.
	 * PICK RANDOM WINNER, 
	 * PRINT SCORES
	 * LEAVE
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
		printSpeech("Congratulations, " + p.playingContestant[winner].name + ", you've won the game!");
		
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
		printAction("has left the stage");
	}//leave
	
	/**
	 * Run Method:
	 * 
	 * This is the run method for the Host thread.
	 */
	public void run()
		{
			try
			{
				//introduce yourself
				born();

				printAction("is signaling the contestants to exit BW and sleep");
				for(int i=0; i<p.playingContestants; i++)
				{
					p.playingContestant[i].hostSignal=true;
				}
				
				busyWaitContestants();
				
				for(int i=0; i<p.numRounds; i++)
				{
					for(int j=0; j<p.numQuestions; j++)
					{
						printAction("is about to ask a question");
						askQuestion();
						questionNum++;
					}
					roundNum++;
					questionNum=1;
					
					if(roundNum<=p.numRounds)
					{
						printAction("in round number: " + roundNum);
					}
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