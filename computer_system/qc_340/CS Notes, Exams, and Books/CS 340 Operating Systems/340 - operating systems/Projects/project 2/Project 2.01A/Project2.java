import java.util.*;
import java.util.concurrent.*;

public class Project2
{
	//Command Line Arguments' Variables
	public static int numRounds=2;
	public static int numQuestions=5;
	public static int questionValues=200;
	public static double rightPercent=0.65;
	public static int room_capacity=4;
	public static int initial_num_contestants=11;
	//Class variables
	public static Contestant[] contestant = new Contestant[initial_num_contestants];
	//General Variables
	public int contestantCounter=0;
	//Semaphores
	public static Semaphore mutex = new Semaphore(1,true);

	public static void main(String[] args)
	{
        //if the user specified command line arguments..
		if(args.length==6)
		{
			try
			{
				numRounds=Integer.parseInt(args[0]);
				numQuestions=Integer.parseInt(args[1]);
				questionValues=Integer.parseInt(args[2]);
				rightPercent=Double.parseDouble(args[3]);
				room_capacity=Integer.parseInt(args[4]);
				initial_num_contestants=Integer.parseInt(args[5]);
			}//try
			catch(Exception e)
			{
				System.out.println("Missing argument values!");
				System.out.println("Example: 2 5 200 0.65 4 11,");
                System.out.println("for rounds, questions, values, and percentage");
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
		}//for
	}//main
}//Project2