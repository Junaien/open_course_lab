
/**
 * The Dragon thread in the Dragon and Adventurer game.  While the game is running, the dragon
 * will signal to any waiting Adventurers that he is ready for a fight via notify().  All the
 * adventurers will be waiting for the dragon via wait().  the dragon will fight one adventurer
 * at a time.
 * When all adventurers leave game, the Dragon will go back to his cave to sleep.
 *
 * @author Jackson Yeh
 */
 class Dragon extends Thread
{
	Game game;


   /**
    * Constructor method for Dragon
    *
    * @param g Game that created this object.
    *
    */
	public Dragon(Game g)
	{
		setName("DRAGON");
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

			System.out.println("age()=" + game.age() + ", " + getName()  + " roars that he is ready for a fight!");
			roar();	// DRAGON signals that he is ready for fight

			try
			{
				sleep(4000);		// Dragon waiting for response...
			}
			catch (InterruptedException e) {}
		}

		System.out.println("age()=" + game.age() + ", " + getName()  + " goes back into cave to sleep!");


		System.out.println("                                                   ___");
		System.out.println("                                                .~))>>");
		System.out.println("                                               .~)>>");
		System.out.println("                                             .~))))>>>");
		System.out.println("                                           .~))>>             ___");
		System.out.println("                                         .~))>>)))>>      .-~))>>  ");
		System.out.println("                                       .~)))))>>       .-~))>>)>");
		System.out.println("                                     .~)))>>))))>>  .-~)>>)>");
		System.out.println("                 )                 .~))>>))))>>  .-~)))))>>)>");
		System.out.println("              ( )@@*)             //)>))))))  .-~))))>>)>");
		System.out.println("            ).@(@@               //))>>))) .-~))>>)))))>>)>");
		System.out.println("          (( @.@).              //))))) .-~)>>)))))>>)>");
		System.out.println("        ))  )@@*.@@ )          //)>))) //))))))>>))))>>)>");
		System.out.println("     ((  ((@@@.@@             |/))))) //)))))>>)))>>)>");
		System.out.println("    )) @@*. )@@ )   (V (V-n  |))>)) //)))>>)))))))>>)>");
		System.out.println("  (( @@@(.@(@ .    _/`-`  ~|r |>))) //)>>)))))))>>)>");
		System.out.println("   )* @@@ )@*     (@) (@)  /V|))) //))))))>>))))>>");
		System.out.println(" (( @. )@( @ .   _/       /  V)) //))>>)))))>>>_._");
		System.out.println("  )@@ (@@*)@@.  (6,   6) / ^  V)//))))))>>)))>>   ~~-.");
		System.out.println("@jgs@@. @@@.*@_ ~^~^~, /(  ^   V/)>>))))>>      _.     `,");
		System.out.println("((@@ @@@*.(@@ .   |^^^/' (  ^   (r)))>>        .'         `,");
		System.out.println(" ((@@).*@@ )@ )    `-'   ((   ^  ~)_          /             `,");
		System.out.println("   (@@. (@@ ).           (((   ^    `(        |               `.");
		System.out.println("     (*.@*              / ((((        (        (      .         `.");
		System.out.println("                       /   (((((  (    (    _.-~(     Y,         ;");
		System.out.println("                      /   / (((((( (    (.-~   _.`' _.-~`,       ;");
		System.out.println("                     /   /   `(((((()    )    (((((~      `,     ;");
		System.out.println("                   _/  _/      `'''/   /'                  ;     ;");
		System.out.println("               _.-~_.-~           /  /'                _.-~   _.'");
		System.out.println("             ((((~~              / /'              _.-~ __.--~");
		System.out.println("     ___                        ((((          __.-~ _.-~");
		System.out.println("    /**/|                                   .'   .~~");
		System.out.println("   |**|*|                                  :    ,'");
		System.out.println("   |**|/                                  ~~~~~");

	}




   /**
    * synchronized method used by dragon to signal or notify() that he is ready for any waiting Adventurer.
    *
    */
	public synchronized void roar()
	{
		notify();	// notify a waiting Adventurer.
	}



   /**
    * synchronized method used by Adventurers to fight the dragon.  It imcorporates wait() so that only one
    * Adventurer can be notify() at a time to fight the dragon.
    *
    * @return int the result of the fight. 0 if dragon wins, 1 if adventurer wins stone,
    *										2 if adventurer wins ring, 3 if adventurer wins necklace.
    *
    */
	public synchronized int fight()
	{

		try
		{

			wait();		// wait for dragon to be available
		}
		catch (InterruptedException e) {}


		// After dragon has notify(), lets start the fight:

		// Dragon rolls 2 dice:
		int dragonRolls = (((int)(Math.random() * 10) % 6) + 1) + (((int)(Math.random() * 10) % 6) + 1);

		// Adventurer rolls 2 dice:
		int advRolls = (((int)(Math.random() * 10) % 6) + 1) + (((int)(Math.random() * 10) % 6) + 1);

		// if Adventurer wins...
		if ( advRolls > dragonRolls )
		{


			// randomly return 1,2, or 3 to determine prize...
			return ((int)(Math.random() * 10) % 3 + 1);
		}
		// else the Dragon wins
		// (in case of tie, dragon wins because of house advantage.)
		else
		{

			return 0;
		}


	}



}

