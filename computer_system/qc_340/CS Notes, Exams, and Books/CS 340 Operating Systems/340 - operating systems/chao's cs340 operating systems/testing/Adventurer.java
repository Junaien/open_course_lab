
/**
 * The Adventurer thread in the Dragon and Adventurer game.  If the Adventurer's personal
 * fortune is less than the game's goal Fortune Size, he will either go to the clerk to
 * to make some jewelry or keep fighting the dragon for more raw material to make jewelry.
 *
 * Once the Adventurer has enough fortune, he will leave the game, unless the adventurer in
 * front of him is still playing, in which case he wait to join him to leave.
 *
 * @author Jackson Yeh
 */
public class Adventurer extends Thread
{
	private int stones;								// number of stones
	private int rings;								// number of rings
	private int necklaces;							// number of necklaces
	private int personalFortune;					// personal fortune size
	private int id;									// ID of ADVENTURER
	private int myClerkTurn;						// TURN NUMBER for CLERK
	private int losses;								// number of losses to the DRAGON
	private Game game;								// the game that the Adventurer is part of.
	private Dragon dragon;							// the dragon that the Adventurer is fighting.



   /**
    * Constructor method for Adventurer
    *
    * @param g Game that created this object.
    * @param i int the adventurer ID
    * @param d Dragon the dragon thread that the adventurer is fighting
    *
    */
	public Adventurer(int i, Game g, Dragon d)
	{
		id = i;									// initialize ID
		stones = (int)(Math.random() * 10) % 3;		// initialize random number of stones
		rings = (int)(Math.random() * 10) % 3;		// initialize random number of rings
		necklaces = (int)(Math.random() * 10) % 3;	// initialize random number of necklaces
		personalFortune = 0;						// initialize personal fortune to 0
		losses = 0;									// initialize losses to 0
		setName("ADVENTURER"+id);					// set the name of the thread
		game = g;
		dragon = d;
	}


   /**
    * run method for THREAD
    *
    */
	public void run()
	{
		System.out.println("age()=" + game.age() + ", " + getName()
							+ " joins game with: \n ***** stones: " + stones
							+ "\n ***** necklaces: " + necklaces
							+ "\n ***** rings: " + rings
							+ "\n ***** fortune size: " + personalFortune);

		// keep thread running while personal fortune is less than the fortune size goal of game.
		while ( personalFortune < game.getFortuneSize() )

		{
			System.out.println("age()=" + game.age() + ", " + getName()  + " has FORTUNE SIZE: " + personalFortune);
			// if Adventurer has enough items to make a complete magical ring or necklace.
			// visit the Clerk
			if (( stones >= 1 && rings >= 1 ) || (stones >= 1 && necklaces >= 1))
			{

				System.out.println("age()=" + game.age() + ", " + getName() + " going to CLERK");



				// take a Number from the clerk.
				myClerkTurn = game.getClerkNextTurn();


				// tell the Clerk that you need help
				game.setNeedAssistance((myClerkTurn % game.getNumAdv()), true);


				game.incrCustomersWaiting();		// frees the CLERK from BUSY WAIT

				System.out.println("age()=" + game.age() + ", " + getName()  + " got TURN NUMBER: " + myClerkTurn + " waiting for CLERK");

				// BUSY WAIT for the Clerk to call your Number
				while ( game.getClerkNowServing() != myClerkTurn )
				{

					System.out.println("age()=" + game.age() + ", " + getName() + " waiting for CLERK");
					try
					{
						sleep(200);
					}
					catch (InterruptedException e) {}

				}
				game.setClerkNowServingID(id);
				System.out.println("age()=" + game.age() + ", " + getName()  + " getting help from CLERK");


				// trading in stuff...
				while ( stones >= 1 && rings >= 1 )		// getting a magic ring!
				{
					stones--;
					rings--;
					personalFortune++;
					System.out.println("age()=" + game.age() + ", " + getName()  + " got a Magic Ring!  Personal Fortune: " + personalFortune);
				}
				while (stones >= 1 && necklaces >= 1)   // getting a magic necklace!
				{
					stones--;
					necklaces--;
					personalFortune++;
					System.out.println("age()=" + game.age() + ", " + getName()  + " got a Magic Necklace!  Personal Fortune: " + personalFortune);
				}

				System.out.println("age()=" + game.age() + ", " + getName()  + " is done with the CLERK for now");

				// say goodbye to the CLERK.
				game.decrCustomersWaiting();		// reduce number of ppl waiting for clerk.

				// free CLERK from BUSY WAIT loop, so it can help someone else.
				game.setNeedAssistance((myClerkTurn % game.getNumAdv()), false);
			}


			// else visit the Dragon
			else
			{
				// visit the Dragon
				System.out.println("age()=" + game.age() + ", " + getName()  + " waiting to fight DRAGON");

				int fightResult;

				// FIRST FIGHT
				if (losses == 0)
				{

					fightResult = dragon.fight();  // fight() contains a wait() for the dragon.

					if (fightResult == 1)
					{

						System.out.println("age()=" + game.age() + ", " + getName()  + " fights DRAGON and wins a STONE from DRAGON");
						stones++;
					}
					else if (fightResult == 2)
					{
						System.out.println("age()=" + game.age() + ", " + getName()  + " fights DRAGON and wins a RING from DRAGON");
						rings++;
					}
					else if (fightResult == 3)
					{
						System.out.println("age()=" + game.age() + ", " + getName()  + " fights DRAGON and wins a NECKLACE from DRAGON");
						necklaces++;
					}
					else
					{
						System.out.println("age()=" + game.age() + ", " + getName()  + " loses to DRAGON");
						// increase priority
						Thread.currentThread().setPriority(getPriority() + 1);
						losses++;
					}
				}
				// SECOND FIGHT
				else if (losses == 1)
				{
					fightResult = dragon.fight();

					// restore priority back to default.
					Thread.currentThread().setPriority(getPriority() - 1);

					if (fightResult == 1)
					{
						System.out.println("age()=" + game.age() + ", " + getName()  + " fights DRAGON and wins a STONE from DRAGON");
						stones++;
					}
					else if (fightResult == 2)
					{
						System.out.println("age()=" + game.age() + ", " + getName()  + " fights DRAGON and wins a RING from DRAGON");
						rings++;
					}
					else if (fightResult == 3)
					{
						System.out.println("age()=" + game.age() + ", " + getName()  + " fights DRAGON and wins a NECKLACE from DRAGON");
						necklaces++;
					}
					else
					{
						System.out.println("age()=" + game.age() + ", " + getName()  + " loses to DRAGON");
						losses++;
					}
				}
				else
				{
					// have lost more than once.
					// allow any other Adventurers to go first.
					if (game.getRemainingAdv() > 1)
					{
						System.out.println("age()=" + game.age() + ", " + getName()  + " yields to other ADVENTURERS");
						yield();
					}

					fightResult = dragon.fight();


					if (fightResult == 1)
					{
						System.out.println("age()=" + game.age() + ", " + getName()  + " fights DRAGON and wins a STONE from DRAGON");
						stones++;
					}
					else if (fightResult == 2)
					{
						System.out.println("age()=" + game.age() + ", " + getName()  + " fights DRAGON and wins a RING from DRAGON");
						rings++;
					}
					else if (fightResult == 3)
					{
						System.out.println("age()=" + game.age() + ", " + getName()  + " fights DRAGON and wins a NECKLACE from DRAGON");
						necklaces++;
					}
					else
					{
						System.out.println("age()=" + game.age() + ", " + getName()  + " loses to DRAGON");
						losses++;
					}
				}
			}
		}

		// leave the game

		System.out.println("REMAINING ADVENTURERS: " + game.getRemainingAdv());

		if (game.getRemainingAdv() == 1)
		{
			System.out.println("age()=" + game.age() + ", " + getName()  + " sees that no other ADVENTURER is left in game");
			System.out.println("age()=" + game.age() + ", " + getName()  + " is done, yells: yaba daba doo, everyone go home!");
			game.decrRemainingAdv();
			game.setGameOver();
		}
		else
		{
			System.out.println("age()=" + game.age() + ", " + getName()  + " sees that there are " + game.getRemainingAdv() + " ADVENTURERS left in game");
			if (id == (game.getNumAdv() - 1))
			{
				System.out.println("age()=" + game.age() + ", " + getName()  + " is done");
				game.decrRemainingAdv();
			}
			else if (game.adv[id+1].isAlive())
			{
				try
				{
					System.out.println("age()=" + game.age() + ", " + getName()  + " is done, waiting to join ADVENTURER" + (id+1));
					game.decrRemainingAdv();
					game.adv[id+1].join();		// wait for dragon to be available
				}
				catch (InterruptedException e) {}
			}
			else
			{
				System.out.println("age()=" + game.age() + ", " + getName()  + " is done");
				game.decrRemainingAdv();
			}
		}
	}
}






