import java.util.concurrent.*;

/**
 * Project 2 - Jeopardy Simulation with Semaphores
 * 
 * 
 * Due - 5-19-09
 * Evening Section - 6:30pm-7:45pm
 * @author Angel Guevara, Jr.
 */
public class Project2 
{
	//Command Line Arguments
	public static int numRounds = 2;
	public static int numQuestions = 5;
	public static int questionValues = 200;
	public static double rightPercent = 0.65;
	public static int room_capacity = 4;
	public static int initial_num_contestants = 12;
	
	//Variables
	public static int num_of_players = 3;
	public static int exam_time = 200;
	public static Contestant[] contestant = new Contestant[initial_num_contestants];
	public static Contestant[] player = new Contestant[initial_num_contestants];
	public static boolean finalJeopardy = false;
	
	//Counters
	public static int contestantCounter = 0;
	public static int roomCounter = 0;
	public static int groupNumber = 0;
	public static int waiting = 0;
	public static int inRoom = 0;
	public static int isAwake = 0;
	public static int remaining = num_of_players;
	public static int questionNumber = 1;
	public static int roundNumber = 1;
	
	//Semaphores
	public static Semaphore mutex = new Semaphore(1,true);
	public static Semaphore readyRoom = new Semaphore(0,true);
	public static Semaphore waitingGroup = new Semaphore(0,true);
	public static Semaphore startExam = new Semaphore(0,true);
	public static Semaphore examDone = new Semaphore(0,true);
	public static Semaphore startIntros = new Semaphore(0,true);
	public static Semaphore introsDone = new Semaphore(0,true);
	public static Semaphore answerQuestion = new Semaphore(0,true);
	public static Semaphore hostIntro = new Semaphore(0,true);
	public static Semaphore startGame = new Semaphore(0,true);
	public static Semaphore questionAnswered = new Semaphore(0,true);
	public static Semaphore placeBet = new Semaphore(0,true);
	public static Semaphore betPlaced = new Semaphore(0,true);
	public static Semaphore answerFJQ = new Semaphore(0,true);
	public static Semaphore fjqAnswered = new Semaphore(0,true);
	public static Semaphore done = new Semaphore(0,true);
	
	/**
	 * This method checks the number of Command Line Arguments.
	 * If 6, the arguments will replace the default values.
	 * If less than 6, an exception will be thrown.
	 * If none, default values will be used.
	 * 
	 * After that, it will start the Announcer and Contestant threads.
	 * 
	 * @param args[0] - the number of rounds
	 * @param args[1] - the number of questions
	 * @param args[2] - the weight of each question
	 * @param args[3] - the chance of giving a right answer
	 * @param args[4] - the size of a group
	 * @param args[5] - the number of contestants starting the game
	 */
	public static void main(String[] args) 
	{
		if(args.length==6)
		{
			try
			{
				numRounds = Integer.parseInt(args[0]);
				numQuestions = Integer.parseInt(args[1]);
				questionValues = Integer.parseInt(args[2]);
				rightPercent = Double.parseDouble(args[3]);
				room_capacity = Integer.parseInt(args[4]);
				initial_num_contestants = Integer.parseInt(args[5]);
			}//try
			catch(Exception e)
			{
				System.out.println("Problem with arguments! Must be entered as follows:");
				System.out.println("rounds questions values percent capaciy contestants");
				e.printStackTrace();
			}//catch
		}//if
		
		//Start the threads
		Announcer announcer = new Announcer();
		announcer.start();
		for(int i=0; i<initial_num_contestants; i++)
		{
			contestant[i] = new Contestant(i);
			contestant[i].start();
			player[i] = new Contestant(i);
		}//for		
	}//main
}//Project2
