import java.util.*;

/**
 * Project 2 - Jeopardy Simulation with Semaphores
 * 
 * 
 * Due - 5-19-09
 * Evening Section - 6:30pm-7:45pm
 * @author Angel Guevara, Jr.
 */
public class Host extends Thread
{
	public long startTime = System.currentTimeMillis();
	public static String name = "Alex";
	Random rand = new Random();
	public static int randomContestant = 0;
	public int highest=0;
	public int grade=0;
	public double chance = 100*Project2.rightPercent;
	
	/**
	 * Returns the millisecond value of the age of the current thread.
	 * It is calculated by taking the current time, and subtracting the thread's start time.
	 * 
	 * @return The age of the thread
	 */
	public long age(){return (System.currentTimeMillis() - startTime);}
	
	/**
	 * Generates a random number from 0 to the limit.
	 * 
	 * @param limit - the highest possible random number
	 * @return The randomly generated number
	 */
	public int nextRand(int limit){return rand.nextInt(limit);}
	
	/**
	 * Prints out an action of the specified thread.
	 * 
	 * @param action - the action to be printed.
	 */
	public void printAction(String action){System.out.println(age() + " " + name + " " + action);}
	
	/**
	 * Prints out dialogue of the specified thread.
	 * 
	 * @param speech - the dialogue to be spoken
	 */
	public void printSpeech(String speech){System.out.println(age() + " " + name + ": " + speech);}
	
	/**
	 * Initiates the Host thread and blocks until the Host finishes his introduction.
	 */
	public void hostIntro()
	{
		printAction("appeared on stage");
		printSpeech("Good evening, viewers and contestants, my name is " + name + " and I'm tonight's host.");
		Project2.hostIntro.release();
	}
	
	/**
	 * The host asks a question, picks a random contestant,
	 * blocks until the answer is given, randomly decides whether or not the answer is correct,
	 * updates the contestant's score, and loops until the rounds are finished.
	 * 
	 * @throws InterruptedException If the thread gets interrupted while blocking
	 */
	public void playGame() throws InterruptedException
	{
		Project2.startGame.acquire();
		for(int r=0; r<Project2.numRounds; r++)
		{
			for(int q=0; q<Project2.numQuestions; q++)
			{
				//ask question
				printSpeech("Here is question " + Project2.questionNumber + "......");
				Project2.questionNumber++;
				
				//pick random contestant
				randomContestant = nextRand(Project2.num_of_players);
				Project2.player[randomContestant].picked=true;
				printSpeech(Project2.player[randomContestant].name + ", what is your answer?");
				
				//question gets answered
				Project2.answerQuestion.release(Project2.num_of_players);
				Project2.questionAnswered.acquire();
				
				//grade the answer
				grade = nextRand(100);
				
				if(grade<chance)
				{
					printSpeech("Yes, " + Project2.player[randomContestant].name + "! That's correct!");
					Project2.player[randomContestant].increaseScore();
					printAction("increased " + Project2.player[randomContestant].name + "'s score to " + Project2.player[randomContestant].score + "\n");
				}//if right
				else if(grade>chance)
				{
					printSpeech("I'm sorry, " + Project2.player[randomContestant].name + ", but that's the wrong answer.");
					Project2.player[randomContestant].decreaseScore();
					printAction("decreased " + Project2.player[randomContestant].name + "'s score to " + Project2.player[randomContestant].score + "\n");
				}//else if wrong
			}//for
			
			Project2.roundNumber++;
			if(Project2.roundNumber<=Project2.numRounds)printSpeech("It's time for round " + Project2.roundNumber);
			Project2.questionNumber = 1;
		}//for
		Project2.finalJeopardy = true;
	}
	
	/**
	 * The host will release the Answer Question semaphore, allowing the contestants to enter
	 * the Final Jeopardy method.
	 * @throws InterruptedException
	 */
	public void finalJeopardy() throws InterruptedException
	{
		//Send contestants to FJ
		Project2.answerQuestion.release(Project2.num_of_players);
		//Announce FJ
		printSpeech("It's time for tonight's Final Jeopardy!");
		
		//Print out the contestants' scores
		for(int i=0; i<Project2.num_of_players; i++)
		{
			printSpeech(Project2.player[i].name + ", your score is " + Project2.player[i].score);
		}
		//Tell the contestants to place bets
		printSpeech("Contestants, place your bets!");
		Project2.placeBet.release(Project2.num_of_players);
		
		//Block until all bets have been placed
		Project2.betPlaced.acquire(Project2.num_of_players);
		//Ask the final question
		printSpeech("Here's your Final Jeopardy Question......");
		Project2.answerFJQ.release(Project2.remaining);
		
		//Block until all contestants have given their answers
		Project2.fjqAnswered.acquire(Project2.remaining);
		
		//Grade answers and print out updated scores, while picking a winner
		for(int i=0; i<Project2.remaining; i++)
		{
			grade = nextRand(100);
			if(grade<=50)
			{
				printSpeech("Yes, " + Project2.player[i].name + "! That's correct!");
				Project2.player[i].score+=Project2.player[i].bet;
			}//if right
			else if(grade>50)
			{
				printSpeech("I'm sorry, " + Project2.player[i].name + ", but that's the wrong answer.");
				Project2.player[i].score-=Project2.player[i].bet;
			}//else if wrong
			
			printSpeech(Project2.player[i].name + ", your score is " + Project2.player[i].score);
			
			if(highest < Project2.player[i].score)
			{
				Project2.player[i].winner = true;
				if((i-1)>=0)
				{
					Project2.player[i-1].winner=false;
				}//if
			}//if
			grade = 0;
		}//for
		//Announce the winner
		for(int i=0; i<Project2.remaining; i++)
		{
			if(Project2.player[i].winner)
			{
				printSpeech("Congratulations, " + Project2.player[i].name + " you won tonight's game!");
			}//if
		}//for
		Project2.done.release(Project2.num_of_players);
	}//finalJeopardy
	
	/**
	 * Goes through the sequence of the Host thread.
	 */
	public void run()
	{
		try
		{
			//Say introduction,
			hostIntro();
			//Ask questions
			playGame();
			//Get into the final jeopardy mode
			finalJeopardy();
			//Say goodnight
			printSpeech("Good night, everyone, hope you all had fun!");
			printAction("has left the stage.");
		}//try
		catch(Exception e)
		{
			System.out.println("Problem with Announcer thread!");
			e.printStackTrace();
		}//catch
	}//run
}//host
