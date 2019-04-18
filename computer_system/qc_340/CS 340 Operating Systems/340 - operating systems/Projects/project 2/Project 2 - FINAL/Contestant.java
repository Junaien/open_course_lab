import java.util.*;

/**
 * Project 2 - Jeopardy Simulation with Semaphores
 * 
 * 
 * Due - 5-19-09
 * Evening Section - 6:30pm-7:45pm
 * @author Angel Guevara, Jr.
 */
public class Contestant extends Thread 
{
	public String name = "";
	private long startTime = System.currentTimeMillis();
	public boolean passedExam = false;
	public int inGroup = 0;
	public int examGrade = 0;
	public int score = 0;
	public boolean picked = false;
	public boolean right = false;
	public boolean wrong = false;
	Random rand = new Random();
	public int bet = 0;
	public boolean winner = false;
	
	/**
	 * 
	 * @param n
	 */
	public Contestant(int n)
	{
		name = ("Contestant " + (n+1));
	}
	
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
	 * Increases the score of a contestant by the amount of points a question is worth
	 */
	public void increaseScore(){score += Project2.questionValues;}
	
	/**
	 * Decreases the score of a contestant by the amount of points a quesiton is worth
	 */
	public void decreaseScore(){score -= Project2.questionValues;}
	
	/**
	 * The contestant will check the amount of other contestants waiting to form a group.
	 * If equal to the size of the group, then the other waiting contestants will enter the room.
	 * If less than the size of the group, then this contestant will wait until the waitingGroup semaphore is released.
	 * 
	 * @throws InterruptedException If the specified thread gets interrupted while blocking
	 */
	public void enterRoom() throws InterruptedException
	{		
		printAction("is born");
		//Get the Mutex
		Project2.mutex.acquire();
		//Add another person to the waiting list
		Project2.waiting++;
		//Check how many contestants are waiting
		if((Project2.waiting%Project2.room_capacity)!=0)
		{
			//If the right amount, form a group
			Project2.mutex.release();
			Project2.waitingGroup.acquire();
		}
		//Otherwise, wait until someone else releases the semaphore
		else
		{
			Project2.waitingGroup.release(Project2.room_capacity-1);
			Project2.groupNumber++;
		}
		Project2.groupNumber = inGroup;
		Project2.mutex.release();
	}//enterRoom
	
	/**
	 * Releases the Announcer if the current Contestant is the last to enter the room,
	 * take the exam, and blocks until the introduction process begins.
	 * 
	 * @throws InterruptedException
	 */
	public void takeExam() throws InterruptedException
	{
		//Get the Mutex
		Project2.mutex.acquire();
		//If this contestant is the last one to enter,
		if(Project2.waiting == Project2.initial_num_contestants)
		{
			//Release the Announcer
			Project2.readyRoom.release();
		}
		//Release the Mutex
		Project2.mutex.release();
		//Wait until the exam starts
		Project2.startExam.acquire();
		//Take the exam
		printAction("is taking the exam");
		sleep(Project2.exam_time);
		printAction("has finished taking the exam");
		//Release the Announcer
		Project2.examDone.release();
		//Block until the introductions begin
		Project2.startIntros.acquire();
	}//takeExam
	
	/**
	 * Increases the contestant's priority, says introduction,
	 * releases the introsDone semaphore, decreases priority, and blocks until it's time to answer a question.
	 * 
	 * @throws InterruptedException If the thread is interrupted while blocking
	 */
	public void introduction() throws InterruptedException
	{
		this.setPriority(this.getPriority()+1);
		printSpeech("Hello, my name is " + name);
		Project2.introsDone.release();
		this.setPriority(this.getPriority()-1);
		Project2.answerQuestion.acquire();		
	}//introduction
	
	/**
	 * Contestants will repeat the following until it's time for the Final Jeopardy section:
	 * If they've been picked to answer a question, they will say their answer and go back to blocking, otherwise 
	 * they'll simply go back to blocking
	 * 
	 * @throws InterruptedException If the thread is interrupted while blocking
	 */
	public void answerQuestion() throws InterruptedException
	{
		//While the game hasn't reached Final Jeopardy,
		while(!Project2.finalJeopardy)
		{
			//Check if randomly chosen to answer a question
			if(picked)
			{
				//Say answer
				printSpeech(Host.name + ", my answer is......");
				//Reset picked variable for the next question
				picked=false;
				//Signal that the question has been answered
				Project2.questionAnswered.release();
			}//if
			//If the game hasn't reached Final Jeopardy,
			if(!Project2.finalJeopardy)
			{
				//Block until the next question
				Project2.answerQuestion.acquire();
			}
		}//while
	}//answerQuestion
	
	/**
	 * The contestant will randomly generate a bet and answer the Final Jeopardy question,
	 * if and only if the contestant has a sufficient score.
	 * Otherwise, the contestant will have to leave the game.
	 * 
	 * @throws InterruptedException If the thread is interrupted while blocking
	 */
	public void finalJeopardy() throws InterruptedException
	{
		//Block until it's time to place a bet
		Project2.placeBet.acquire();
		//If contestant has a sufficient score,
		if(score>0)
		{
			//Randomly generate a bet
			bet = nextRand(score);
			printSpeech("I'm going to bet " + bet);
			//Signal that a bet has been placed
			Project2.betPlaced.release();
			
			//Block until it's time to answer the final question
			Project2.answerFJQ.acquire();
			//Answer the question
			printSpeech(Host.name + ", my answer is......");
			//Signal that it's been answered
			Project2.fjqAnswered.release();
			//Block until the game is over
			Project2.done.acquire();
		}//if
		//Otherwise if contestant has an insufficient score,
		else
		{
			//Say bye,
			printSpeech("I don't have enough to bet. Good night!");
			//Release the semaphores
			Project2.betPlaced.release();
			Project2.answerFJQ.release();
			Project2.remaining--;
		}//else
	}//finalJeopardy
	
	/**
	 * Goes through the sequence of the Contestant thread.
	 */
	public void run()
	{
		try
		{
			//Enter the examination room
			enterRoom();
			//Take the exam
			takeExam();
			//If the contestant passed, continue on with the rest of the game
			if(passedExam)
			{		
				//Say introduction,
				introduction();
				//Go into the rounds
				answerQuestion();
				//Go into the Final Jeopardy
				finalJeopardy();
			}
			//Leave
			printAction("has left the stage.");
		}//try
		catch(Exception e)
		{
			System.out.println("Problem with Announcer thread!");
			e.printStackTrace();
		}//catch
	}//run
}//Contestant
