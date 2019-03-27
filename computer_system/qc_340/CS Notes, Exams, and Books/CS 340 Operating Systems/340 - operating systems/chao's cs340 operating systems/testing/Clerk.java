
/**
 * The Clerk thread in the Dragon and Adventurer game.  While the game is running, the clerk
 * waits (BUSY WAIT loop) for Adventurers to show up.  When Adventurer shows up and takes a number,
 * Clerk will be freed from BUSY WAIT and begin servicing Adventurers FCFS, one at a time, staying
 * in a BUSY WAIT loop while helping the Adventurer.  When clerk is done helping one customer,
 * he will call out the next number and help out remaining customers.
 * When all adventurers leave game, the Clerk will go home.
 *
 * @author Jackson Yeh
 */
public class Clerk extends Thread
{
	private Game game;


   /**
    * Constructor method for Clerk
    *
    * @param g Game that created this object.
    *
    */
	public Clerk(Game g)
	{
		setName("CLERK");
		game = g;
	}


   /**
    * run method for THREAD
    *
    */
	public void run()
	{
		while (!game.isGameOver())
		{
			// CLERK BUSY WAITS for a customer to show up
			// loop breaks if either a customer shows up
			// or game is over.
			while (game.getCustomersWaiting() == 0 && !game.isGameOver())
			{

				try
				{
					int n = (int) game.age() % 9;

					if (n == 0)
					{
						System.out.println("age()=" + game.age() + ", " + getName()  + " goes to the bathroom");
						sleep(1000);
					}
					else if (n == 1)
					{
						System.out.println("age()=" + game.age() + ", " + getName()  + " picks his nose");
						sleep(4000);
					}
					else if (n == 2)
					{
						System.out.println("age()=" + game.age() + ", " + getName()  + " is drinking coffee");
						sleep(1000);
					}
					else if (n == 3)
					{
						System.out.println("age()=" + game.age() + ", " + getName()  + " is on the phone");
						sleep(1000);
					}
					else
					{
						System.out.println("age()=" + game.age() + ", " + getName()  + " waiting for Adventurers");
						sleep(200);
					}
				}
				catch (InterruptedException e) {}
			}

			if (game.isGameOver()) break;			// if game is over, break the loop.

			// Customer shows up!

			System.out.println("age()=" + game.age() + ", " + "CLERK calls out TURN NUMBER: " + (game.getClerkNowServing()+1));

			game.incrClerkNowServing();				// Clerk reads out the next TURN NUMBER to be served:

			// while Adventurer still needs help, keep helping him.
			// keep the CLERK BUSY until ADVENTURER is completely finished
			// He will update need_assistance to 'false', indicating that he is finished,
			// allowing the CLERK to go help the next customer.
			while (game.needAssistance( ( game.getClerkNowServing() % game.getNumAdv() ) ) == true)
			{
				try
				{
							System.out.println("age()=" + game.age() + ", " + "CLERK assisting ADVENTURER");
							sleep(200);			// CLERK assisting ADVENTURER
				}
				catch (InterruptedException e) {}

			}

			// done helping Adventurer
			System.out.println("age()=" + game.age() + ", " + "CLERK finished assisting ADVENTURER"+ game.getClerkNowServingID());

		}

		System.out.println("age()=" + game.age() + ", " + getName()  + " calls it a day and goes home to sleep");

	}
}

