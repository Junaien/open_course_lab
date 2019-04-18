import java.util.concurrent.*;

public class Project2
{
	//Command Line Arguments' Variables
	public static int numRounds=2;
	public static int numQuestions=5;
	public static int questionValues=200;
	public static double rightPercent=0.65;
	public static int room_capacity=4;
	public static int initial_num_contestants=12;
   //---------------------------------------------------------------------------
	//Class variables:
	public static Contestant[] contestant = new Contestant[initial_num_contestants];
    public static int num_of_players=3;
    public static Contestant[] player = new Contestant[initial_num_contestants];
    public static int exam_time=200;
    //--------------------------------------------------------------------------
	//Counters:
	public static int contestantCounter = 0;
    public static int roomCounter = 0;
    public static int groupNum = 0;
    public static int waiting = 0;
    public static int inRoom = 0;
    public static int isAwake = 0;
    //--------------------------------------------------------------------------
	//Semaphores:
	//Binary:
    public static Semaphore mutex = new Semaphore(1,true);
    public static Semaphore group = new Semaphore(0,true);
    public static Semaphore roomReady = new Semaphore(0,true);
    public static Semaphore takeExam = new Semaphore(0,true);
    public static Semaphore finishedExam = new Semaphore(0,true);
    public static Semaphore examResults = new Semaphore(0,true);
    public static Semaphore startIntros = new Semaphore(0,true);
    public static Semaphore hostIntro = new Semaphore(0,true);
    public static Semaphore startGame = new Semaphore(0,true);
    public static Semaphore answeredQuestion = new Semaphore(0,true);
    public static Semaphore placeBets = new Semaphore(0,true);
    public static Semaphore fjqAsked = new Semaphore(0,true);
    //Counting:
    //public static Semaphore enterRoom = new Semaphore(initial_num_contestants,true);
    public static Semaphore finishIntros = new Semaphore(num_of_players,true);
    public static Semaphore answerQuestion = new Semaphore(num_of_players,true);
    public static Semaphore betPlaced = new Semaphore(num_of_players,true);
    public static Semaphore finalAnswered = new Semaphore(num_of_players,true);
    //--------------------------------------------------------------------------
	public static void main(String[] args)
	{
		if(args.length==6)
		{
			try
			{
				numRounds = Integer.parseInt(args[0]);
				numQuestions = Integer.parseInt(args[1]);
				questionValues = Integer.parseInt(args[2]);
				rightPercent = Double.parseDouble(args[4]);
				room_capacity = Integer.parseInt(args[4]);
				initial_num_contestants = Integer.parseInt(args[5]);
			}//try
			catch(Exception e)
			{
				System.out.println("Problem with arguments!");
				System.out.println("Must be rounds questions values percentage capacity contestants.");
				e.printStackTrace();
			}//catch
		}//if
		System.out.println("********STARTING GAME********");
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