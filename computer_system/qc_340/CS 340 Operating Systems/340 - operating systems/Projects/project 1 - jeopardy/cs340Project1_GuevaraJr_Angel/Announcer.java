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
	int highest=0;
	Host host;
	
	/**
	 * Announcer Constructor:
	 * Initializes variables for the Announcer thread
	 */
	public Announcer(Project1 p1)
	{
		p=p1;
	}//Announcer constructor
	
	/**
	 * Name Method :
	 * Returns the name of the Announcer thread.
	 * 
	 * @return name - the name of the Announcer
	 */
	public String name()
	{
		return name;
	}//name
	
	/**
	 * Print Speech Method :
	 * Prints out what the Announer is saying.
	 * 
	 * @param message - the dialog to be said
	 */
	public void printSpeech(String message)
	{
		System.out.println(p.age() + name() + ": " + message);
	}//printMessage
	
	/**
	 * Print Action Method :
	 * Prints out what the Announcer is doing
	 * 
	 * @param message - the action to be acted out
	 */
	public void printAction(String message)
	{
		System.out.println(p.age() + name() + " " + message);
	}//printMessage
	
	/**
	 * Busy Wait for Host Method :
	 * This is where the Announcer will Busy Wait for the Host's signal
	 */
	public void busyWaitHost()
	{
		//printAction("is now busy waiting.");
		while(!p.ha_Signal)
		{
			try{Thread.sleep(500);}
			catch(InterruptedException ie){ie.printStackTrace();}
		}
		//printAction("has finished busy waiting.");
		//reset host-to-announcer signal
		p.ha_Signal=false;
	}//busyWaitHost
	
	/**
	 * Busy Wait for Contestants Method :
	 * This is where the Announcer will busy wait for the Contestants' signals
	 */
	public void busyWaitContestants()
	{
		//printAction("is now busy waiting.");
		while(p.getCount()!=0)
		{
			try{Thread.sleep(500);}
			catch(InterruptedException ie){ie.printStackTrace();}
		}
		//reset the contestant counter
		p.contestantCounter=p.numContestants;
		//printAction("has finished busy waiting.");
	}//busyWaitContestants
	
	/**
	 * Busy Wait for Playing Contestants Method:
	 * This is where the Announcer will busy wait for the Contestants and reset the counter
	 * to the number of remaining Contestants.
	 */
	public void busyWaitPlayingContestants()
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
		}
		//reset contestant counter
		p.contestantCounter=p.playingContestants;
		//printAction("has finished busy waiting.");
	}
	
	/**
	 * Born Method:
	 * This is where the Announcer says hello and busy waits for the Contestants to be born.
	 */
	public void born()
	{
		//born
		printSpeech("Welcome to Jeopardy!");
		//bwc
		busyWaitContestants();
	}//born
	
	/**
	 * Start Host Method :
	 * This is where the Announcer will introduce the Host and wait for him to finish.
	 */
	public void startHost()
	{
		//introduce host
		printSpeech("Give it up for your host, Alex Trebek!");
		//start host
		host = new Host(p);
		host.start();

		busyWaitHost();
	}//startHost
	
	/**
	 * Transfer method :
	 * This is where the Announcer will wake the Contestants, and then go into a busy wait for them to finish 
	 * generating their random numbers.
	 */
	public void transfer()
	{
		//tell contestants that host has finished his intro
		for(int i=0; i<p.numContestants; i++)
		{
			p.contestant[i].announcerSignal=true;
		}
		//BUSY WAIT for ownNumber to be generated.
		busyWaitContestants();
	}//transfer
	
	/**
	 * Pick Top Three Method:
	 * This is where the Announcer picks the 3 Contestants.
	 * 
	 */
	public void pickPlayers()
	{
		//sort the array
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
	 * Accept/Reject Method :
	 * This is where the Announcer will say who's playing and who's going home.
	 */
	public void acceptReject()
	{
		//announce stay
		for(int i=0; i<p.playingContestants; i++)
		{
			printSpeech("Congratulations to " + p.playingContestant[i].name);
			p.playingContestant[i].accepted=true;
		}//"accepted" for
		
		//announce leave
		for(int i=p.playingContestants; i<p.numContestants; i++)
		{
			printSpeech("I'm sorry, " + p.playingContestant[i].name + " but it's time for you to go home.");
			p.playingContestant[i].terminate=true;
		}//"rejected" for
	}//acceptReject
	
	/**
	 * Start Intros Method:
	 * This is where the Announcer starts the introductions.
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
	 * Start Game Method:
	 * This is where the ANnouncer starts the game and notifies the Host.
	 * (The host is BW for the start of the game, after his introduction)
	 */
	public void startGame()
	{
		//announce game is starting
		printSpeech("Now it's time to start the game. Have fun, everyone!!");
		
		//wake the host
		host.interrupt();
	}//startGame
	
	/**
	 * Leave Method:
	 * This is where the Announcer leaves the stage.
	 */
	public void leave()
	{
		//announce leave
		printAction("is leaving the stage.");
	}//leave
	
	/**
	 * Run Method:
	 * This is the run method for the Announcer thread.
	 * It contains all the method calls for the Announcer class.
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