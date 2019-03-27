import java.util.*;

/**
 * Project 2 - Jeopardy Simulation with Semaphores
 * 
 * 
 * Due - 5-19-09
 * Evening Section - 6:30pm-7:45pm
 * @author Angel Guevara, Jr.
 */
public class Announcer extends Thread
{
	public String name = "Announcer";
	public long startTime = System.currentTimeMillis();
	Random rand = new Random();
	int highest=0;
	
	/**
	 * Returns the millisecond value of the age of the current thread.
	 * It is calculated by taking the current time, and subtracting the thread's start time.
	 * 
	 * @return The age of the thread
	 */
	public long age(){return (System.currentTimeMillis() - startTime);}
	
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
	 * Generates a random number from 0 to the limit.
	 * 
	 * @param limit - the highest possible random number
	 * @return The randomly generated number
	 */
	public int nextRand(int limit){return rand.nextInt(limit);}
	
	/**
	 * Announcer appears on stage and waits for the room to be filled.
	 * 
	 * @throws InterruptedException  If the specified thread gets interrupted while blocking
	 */
	public void born() throws InterruptedException
	{
		printAction("appeared on stage");
		printSpeech("Good evening, viewers, I hope you're ready for tonight's Jeopardy!");
		Project2.readyRoom.acquire();
	}//born
	
	/**
	 * Starts the exam, generates random grades, sorts the grades from highest to lowest,
	 * accepts the first three contestants, and rejects the rest.
	 * 
	 * @throws InterruptedException If the specified thread gets interrupted while blocking
	 */
	public void startExam() throws InterruptedException
	{
		//Start the exam
		System.out.println();
		printSpeech("Contestants, it's time to start the exam.");
		Project2.startExam.release(Project2.initial_num_contestants);
		sleep(Project2.exam_time);
		Project2.examDone.acquire(Project2.initial_num_contestants);
		
		//Generate random grades
		System.out.println();
		for(int i=0; i<Project2.initial_num_contestants; i++)
		{
			Project2.contestant[i].examGrade = nextRand(400);
			printAction("gave " + Project2.contestant[i].name + " an exam grade of " + Project2.contestant[i].examGrade);
		}
		
		//Sort the array
		for(int pp=0; pp<Project2.initial_num_contestants; pp++)
		{
			for(int c=0; c<Project2.initial_num_contestants; c++)
			{
				if(Project2.player[pp].examGrade < Project2.contestant[c].examGrade)
				{
					Project2.player[pp] = Project2.contestant[c];
					highest = Project2.contestant[c].examGrade;
				}//if
			}//1st inner for
			for(int i=0; i<Project2.initial_num_contestants; i++)
			{
				if(Project2.contestant[i].examGrade==highest)
				{
					Project2.contestant[i].examGrade=0;
				}//if
			}//2nd inner for
		}//outer for
		
		//Say who stays
		System.out.println();
		for(int i=0; i<Project2.num_of_players; i++)
		{
			printSpeech("Congratulations, " + Project2.player[i].name + ", you passed the exam!");
			Project2.player[i].passedExam = true;
		}//for
		//Say who leaves
		System.out.println();
		for(int i=Project2.num_of_players; i<Project2.initial_num_contestants; i++)
		{
			printSpeech("I'm sorry, " + Project2.player[i].name + ", but it's time for you to go home.");
			Project2.player[i].passedExam = false;
		}//for
	}//startExam
	
	/**
	 * Starts the introduction process, and blocks until all the contestants are finished.
	 * 
	 * @throws InterruptedException If the specified thread gets interrupted while blocking
	 */
	public void startIntros() throws InterruptedException
	{
		System.out.println();
		printSpeech("Contestants, tell the viewers a little bit about yourselves.");
		Project2.startIntros.release(Project2.initial_num_contestants);
		Project2.introsDone.acquire(Project2.num_of_players);
	}
	
	/**
	 * Starts the host and blocks until the host finishes the introduction.
	 * 
	 * @throws InterruptedException If the specified thread gets interrupted while blocking
	 */
	public void startHost() throws InterruptedException
	{
		System.out.println();
		printSpeech("Now let's meet our host, Alex Trebek!");
		Host host = new Host();
		host.start();
		
		Project2.hostIntro.acquire();
	}
	
	/**
	 * Announces that the game will start, and releases the Host from blocking. 
	 */
	public void startGame()
	{
		System.out.println();
		//Announce that the game will start
		printSpeech("It's time to start the game!");
		//Release the host
		Project2.startGame.release();
	}
	
	/**
	 * Goes through the sequence of the Announcer thread.
	 */
	public void run()
	{
		try
		{
			//Appear on stage
			born();
			//Start the exam
			startExam();
			//Start the Contestants' introduction process
			startIntros();
			//Start the host
			startHost();
			//Start the game
			startGame();
			//Leave the stage
			printAction("has left the stage.\n");
		}//try
		catch(Exception e)
		{
			System.out.println("Problem with Announcer thread!");
			e.printStackTrace();
		}//catch
	}//run
}//Announcer
