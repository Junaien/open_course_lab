

/**
 * Dragon and Adventurer game.  Designed to test application of
 * Thread synchronization using BUSY WAIT, WAIT(), NOTIFY(), and YIELD().
 *
 * @author Jackson Yeh
 */
public class Game
{
	private int numAdv;						// number of ADVENTURERS
	private int fortuneSize;					// size of fortune necessary for ADVENTURER to go home
	private static boolean[] needAssistance;	// if ADVENTURER[i] needs assistance
	private int clerkTurn;						// next TURN NUMBER in line for CLERK
	private int clerkNowServing;				// TURN NUMBER that CLERK is now serving
	private int clerkNowServingID;				// ADVENTURER that CLERK is now serving
	private int customersWaiting;   			// number of customers waiting for Clerk
	public Adventurer[] adv;					// ADVENTURER threads
	private boolean gameOver;					// status of game
	private static final long startTime = System.currentTimeMillis();
	private int numCurrAdv;						// number of ADVENTURERS still in game (have not terminated)





   /**
    * main driver method.  It takes two command line arguments.  If there are no arguments,
    * then a default of 7 adventurers and fortune size of 7 will be made.
    *
    * @param args[0] representing number of adventurers
    * @param args[1] representing the fortune size.
    *
    */
	public static void main(String[] args)
	{

		int m=0;
		int n=0;

		// if no command line arguments, then use default value for number of adventurers
		// and fortune size.
		if (args.length == 0)
		{
			m = 7;
			n = 4;
		}
		else
		{
			// make sure there are two command line arguments.
			try
			{
				if (args.length != 2) throw new IllegalArgumentException("\n ************************ \n syntax should be: \n\n java Game x y' \n\n where x= number of Adventurers\n where y= fortune size\n ************************");
			}
			catch (IllegalArgumentException iae)
			{
				System.out.println(iae);
				System.exit(0);
			}

			// make sure they are integers
			try
			{
				m = Integer.parseInt(args[0]);
				n = Integer.parseInt(args[1]);
			}
			catch (Exception e)
			{
				System.out.println("both command line arguments must be positive integers");
				System.exit(0);
			}

			// make sure they are positive integers
			if (m < 1 || n < 1)
			{
				System.out.println("both command line arguments must be positive integers");
				System.exit(0);
			}
		}

		Game g = new Game(m, n);
		Clerk clerk = new Clerk(g);					// create Clerk thread
		clerk.start();

		Dragon dragon = new Dragon(g);				// create Dragon thread
		dragon.start();



		Adventurer[] adv = new Adventurer[m];			// create Adventurer threads
		for (int i=0; i < m; i++)
		{
			adv[i] = new Adventurer(i,g,dragon);
			adv[i].start();

		}

		g.adv = adv;


	}


   /**
    * Constructor method for game.
    *
    * @param na int representing number of adventurer threads
    * @param fs int representing the fortune size.  This is the goal that will allow
    *				an adventurer thread to exit the game.
    *
    */
	public Game(int na, int fs)
	{
		numAdv = na;							// initialize numAdv
		numCurrAdv = na;						// initialize number of current ADVENTURERS in game
		fortuneSize = fs;						// initialize fortune_size
		needAssistance = new boolean[numAdv];	// create dynamic boolean[] for numAdv
		clerkTurn = 1;							// initialize clerkTurn = 1
		clerkNowServing = 0;					// initialize clerkNowServing = 0
		customersWaiting = 0;					// initialize customers waiting for clerk = 0
		gameOver = false;						// initialize gameOver to false
		System.out.println("GAME STARTED with Adventurers: " + numAdv + " and Fortune Size: " + fortuneSize);

	}




   /**
    * returns the elapsed time, starting from beginning of Game object creation.
    *
    * @return long the elapsed time, starting from beginning of Game object creation.
    *
    */
	public final long age()
	{
		return System.currentTimeMillis() - startTime;
	}


   /**
    * accessor method for getting the Fortune Size of the current game.
    *
    * @return int the Fortune Size of the current game.
    *
    */
	public synchronized int getFortuneSize()
	{
		return fortuneSize;
	}


   /**
    * accessor method for getting the Number of Adventurers in the current game.
    *
    * @return int the Number of Adventurers in the current game.
    *
    */
	public int getNumAdv()
	{
		return numAdv;
	}


   /**
    * accessor method for seeing if an Adventurer who is at the Clerk's office still needs assistance.
    *
    * @param i int representing the current TURN NUMBER (mod numAdv) of the Adventurer
    *
    * @return boolean 'true' if the Adventurer still needs help from Clerk, otherwise 'false'
    *
    */
	public boolean needAssistance(int i)
	{
		return needAssistance[i];
	}


   /**
    * mutator method for the Adventurer who is at the Clerk's office to say he needs assistance.
    *
    * @param i int representing the current TURN NUMBER (mod numAdv) of the Adventurer
    * @param b boolean 'true' if the Adventurer needs help from Clerk, otherwise 'false'
    *
    */
	public void setNeedAssistance(int i, boolean b)
	{
		needAssistance[i] = b;
	}



   /**
    * accessor method for getting a service ticket number or TURN number from the Clerk's office.
    * It will automatically increment once a ticket is handed out.
    *
    * @return int the next TURN number
    *
    */
	public synchronized int getClerkNextTurn()
	{
		return clerkTurn++;
	}


   /**
    * accessor method for getting the TURN number that has been called by the Clerk for servicing.
    *
    * @return int the current TURN number that is being serviced.
    *
    */
	public synchronized int getClerkNowServing()
	{
		return clerkNowServing;
	}


   /**
    * accessor method for getting the Adventurer ID that has been called by the Clerk for servicing.
    *
    * @return int the current Adventurer ID that is being serviced.
    *
    */
	public synchronized int getClerkNowServingID()
	{
		return clerkNowServingID;
	}


   /**
    * mutator method for setting the Adventurer ID that has been called by the Clerk for servicing.
    *
    */
	public synchronized void setClerkNowServingID(int i)
	{
		clerkNowServingID = i;
	}



   /**
    * mutator method for incrementing the TURN number.
    *
    */
	public synchronized void incrClerkNowServing()
	{
		clerkNowServing++;
	}


   /**
    * accessor method for getting the number of Adventurers waiting in line to be serviced by the Clerk.
    *
    * @return int the number of Adventurers waiting in line to be serviced by the Clerk.
    *
    */
	public synchronized int getCustomersWaiting()
	{
		return customersWaiting;
	}


   /**
    * mutator method for incrementing the number of Adventurers waiting in line to be serviced by the Clerk.
    *
    */
	public synchronized void incrCustomersWaiting()
	{
		customersWaiting++;
	}


   /**
    * mutator method for decrementing the number of Adventurers waiting in line to be serviced by the Clerk.
    *
    */
	public synchronized void decrCustomersWaiting()
	{
		customersWaiting--;
	}



   /**
    * accessor method for getting the number of Adventurers still actively in the game.  Does not include
    * Adventurers who have exited game or are waiting to join another Adventurer to exit.
    *
    * @return int the number of Adventurers still actively in the game.
    *
    */
	public synchronized int getRemainingAdv()
	{
		return numCurrAdv;
	}


   /**
    * mutator method for decrementing the number of Adventurers still actively in the game.
    *
    */
	public synchronized void decrRemainingAdv()
	{
		numCurrAdv--;
	}

   /**
    * mutator method for setting GameOver value to 'true', signalling that the game is over.
    *
    */
	public synchronized void setGameOver()
	{
		gameOver = true;
	}


   /**
    * is the game over?
    *
    * @return boolean 'true' if yes, 'false' if no.
    *
    */
	public synchronized boolean isGameOver()
	{
		return gameOver;
	}

}