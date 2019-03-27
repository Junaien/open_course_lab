import java.util.*;

/**
 * Announcer Class:
 * 
 * @author Angel Guevara, Jr.
 */
public class Announcer extends Thread
{
	String name="Announcer";
	boolean contestantSignal=false;
	boolean hostSignal=false;
	Project1 p;
	boolean terminate=false;
	boolean isReady=false;
	
	/**
	 * Announcer Constructor:
	 * Initializes variables for the Announcer thread
	 */
	public Announcer(Project1 p1)
	{
		p=p1;
	}//Announcer constructor
	
	/**
	 * Name Method DONE:
	 * Returns the name of the Announcer thread.
	 * 
	 * @return name - the name of the Announcer
	 */
	public String name()
	{
		return name;
	}//name
	
	/**
	 * Print Speech Method DONE:
	 * Prints out what the Announer is saying.
	 * 
	 * @param message - the message to be said
	 */
	public void printSpeech(String message)
	{
		System.out.println(p.age() + name() + ": " + message);
	}//printMessage
	
	/**
	 * Print Action Method DONE:
	 * Prints out what the Announcer is doing
	 * 
	 * @param message - the message to be acted out
	 */
	public void printAction(String message)
	{
		System.out.println(p.age() + name() + " " + message);
	}//printMessage
	
	/**
	 * Busy Wait for Host Method DONE:
	 * BWH for HA_SIGNAL=TRUE, 
	 * RESET.
	 */
	public void busyWaitHost()
	{
		printAction("is now busy waiting for the Host-to-Announcer signal.");
		while(!p.ha_Signal)
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
		printAction("has finished busy waiting for the Host.");
		//reset host-to-announcer signal
		p.ha_Signal=false;
	}//busyWaitHost
	
	/**
	 * Busy Wait for Contestants Method DONE:
	 * BWC for contestantCounter=0, 
	 * RESET.
	 */
	public void busyWaitContestants()
	{
		printAction("is now busy waiting for the Contestants' singals");
		while(p.getCount()!=0)
		{
			try{Thread.sleep(500);}
			catch(InterruptedException ie){ie.printStackTrace();}
		}
		//reset the contestant counter
		p.contestantCounter=p.numContestants;
		printAction("has finished busy waiting for the Contestants' signals");
	}//busyWaitContestants
	
	public void busyWaitPlayingContestants()
	{
		printAction("is now busy waiting for the Contestant's signals");
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
		}
		//reset contestant counter
		p.contestantCounter=p.playingContestants;
		printAction("has finished busy waiting for the Contestant's signals");
	}
	
	/**
	 * Born Method DONE:
	 * BORN,  
	 * BWC (ALL C'S BIRTH).
	 */
	public void born()
	{
		//born
		printAction("is created.");
		printSpeech("Welcome to Jeopardy!");
		//bwc
		busyWaitContestants();
	}//born
	
	/**
	 * Start Host Method DONE:
	 * INTRODUCE HOST, 
	 * START HOST, 
	 * WAKE HOST, 
	 * BWH (INTRO).
	 */
	public void startHost()
	{
		//introduce host
		printSpeech("Give it up for your host, Alex Trebek!");
		//start host
		Host host = new Host(p);
		host.start();
		//wake host
		//p.ah_Signal=true;
		//printAction("has signaled HOST to wake.");
		//BWH for the host to finish his intro
		busyWaitHost();
	}//startHost
	
	/**
	 * Transfer method DONE:
	 * WAKE CONTESTANTS (HOST FINISHED INTRO), 
	 * BWC (for all ownNumbers to finish)
	 */
	public void transfer()
	{
		//tell contestants that host has finished his intro
		for(int i=0; i<p.numContestants; i++)
		{
			p.contestant[i].announcerSignal=true;
		}
		printAction("has woken up contestants.");
		//BUSY WAIT for ownNumber to be generated.
		busyWaitContestants();
	}//transfer
	
	/**
	 * Pick Top Three Method DONE:
	 * This is where the Announcer picks the 3 Contestants.
	 * SORT ARRAY, 
	 * DISPLAY NEW ARRAY, 
	 * acceptReject()
	 */
	public void pickPlayers()
	{
		//sort the array
		int highest=0;
	
		for(int pp=0; pp<p.numContestants; pp++)
		{
			for(int c=0; c<p.numContestants; c++)
			{
				if(p.playingContestant[pp].ownNumber < p.contestant[c].ownNumber)
				{
					p.playingContestant[pp]=p.contestant[c];
					highest = p.contestant[c].ownNumber;
				}//if
			}//first inner for
			
			for(int i=0; i<p.numContestants; i++)
			{
				if(p.contestant[i].ownNumber == highest)
				{
					p.contestant[i].ownNumber=0;
				}//if
			}//second inner for
		}//outer for

		//accept/reject
		acceptReject();
	}//pickTopThree
	
	/**
	 * Accept/Reject Method DONE:
	 * This is where the Announcer will say who's playing and who's going home.
	 * ANNOUNCE STAY,
	 * ANNOUNCE LEAVE.
	 */
	public void acceptReject()
	{
		//announce stay
		for(int i=0; i<p.playingContestants; i++)
		{
			printSpeech("Congratulations to: " + p.playingContestant[i].name);
			p.playingContestant[i].accepted=true;
		}//"accepted" for
		
		//announce leave
		for(int i=p.playingContestants; i<p.numContestants; i++)
		{
			printSpeech("I'm sorry, " + p.playingContestant[i].name + " but it's time for you to go home.");
			p.playingContestant[i].terminate=true;
		}//"rejected" for
		
		printAction("has finished accepting and rejecting contestants.");
	}//acceptReject
	
	/**
	 * Start Intros Method DONE:
	 * This is where the Announcer starts the introductions.
	 * ANNOUNCE, 
	 * WAKE CONTESTANTS, 
	 * BW_PC(for P-C's to finish their intros) {MAKE BWPC METHOD}
	 */
	public void startIntros()
	{
		//announce intros
		printSpeech("Now it's time to meet our contestants!");
		//wake contestants
		p.contestantCounter=p.playingContestants;
		for(int i=0; i<p.numContestants; i++)
		{
			p.playingContestant[i].announcerSignal=true;
		}
		//busy wait until contestants are finished with their intros
		busyWaitPlayingContestants();
	}//startIntros
	
	/**
	 * Start Game Method DONE:
	 * This is where the ANnouncer starts the game and notifies the Host.
	 * (The host is BW for the start of the game, after his introduction)
	 * ANNOUNCE START, 
	 * WAKE HOST.
	 */
	public void startGame()
	{
		//announce game is starting
		printSpeech("Now it's time to start the game. Have fun, everyone!!");
		
		//wake the host
		p.ah_Signal=true;
	}//startGame
	
	/**
	 * Leave Method DONE:
	 * This is where the Announcer leaves the stage.
	 * ANNOUNCE LEAVE
	 */
	public void leave()
	{
		//announce leave
		printAction("is leaving the stage.");
	}//leave
	
	/**
	 * Run Method:
	 * This is the run method for the Announcer thread.
	 */
	public void run()
	{
		try
		{
			born();
			
			startHost();
			
			transfer();	

			pickPlayers();

			startIntros();

			startGame();
			
			leave();
			
			printAction("has terminated.");
		}//try
		catch(Exception e)
		{
			System.out.println("Problem with Announcer thread!");
			e.printStackTrace();			
		}//catch
	}//run
}//Announcer class