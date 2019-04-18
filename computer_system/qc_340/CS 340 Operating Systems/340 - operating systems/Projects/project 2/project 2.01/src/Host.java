public class Host extends Thread
{
    //Member variables:
	public String name="Host";
	private final long startTime = System.currentTimeMillis();

    /**
     * Name Method:
     * This method returns the name of the Host thread.
     *
     * @return name - the name of the host.
     */
	public String name()
	{
		return name;
	}

    /**
     * Age Method:
     * This method prints out the age, in milliseconds, of the Host thread.
     *
     * @return age - the age of the host.
     */
	public final long age()
	{
		return (System.currentTimeMillis() - startTime);
	}

    /**
     * Print Action Method:
     * This is where the Host's actions are printed to the console.
     *
     * @param message - the message to be acted out.
     */
	public void printAction(String message)
	{
		System.out.println(age() + " " + name() + " " + message);
	}

    /**
     * Print Speech Method:
     * This is where the Host's dialog is printed to the console.
     *
     * @param message - the message to be spoken.
     */
	public void printSpeech(String message)
	{
		System.out.println(age() + " " + name() + ": " + message);
	}

    /**
     * Born Method:
     * The Host gets created by the Announcer,
     * says hello,
     * then waits for the Announcer to start the game.
     */
	public void born()
	{
        //created by announcer
		printAction("is born");
        //say hi
        printSpeech("Good evening, and welcome, all, to tonight's game of Jeopardy!");
		//signal announcer
		//wait for startgame from announcer
	}

    /**
     * Ask Question Method:
     * Pick a random contestant,
     * Wait for their answer,
     * Grade their answer,
     * Update their score accordingly,
     * and loop until the last question of the last round has finished.
     */
    public void askQuestion()
    {
        //ask a question
        //pick a random contestant
        //signal the picked contestant
        //wait for an anaswer
        //grade the answer
        //update the score
        //LOOP
    }

    /**
     * Final Jeopardy Method:
     * The host will signal the Contestants in order,
     * wait until the Final Jeopardy Question is answered,
     * grade the contestants' answers,
     * update their scores accordingly,
     * print out the scores,
     * and declare a winner.
     */
    public void finalJeopardy()
    {
        //signal c's in order
        //wait until FJQ is answered
        //grade FJQ answers
        //update the scores
        //print the score and winner
    }

    /**
     * Leave Method:
     * Here, the Host says good bye and exits the stage.
     */
	public void leave()
	{      
		//say good bye
        printSpeech("I hope you all enjoyed tonight's game, good night!");
        //exit
		printAction("has left the stage.");
	}

    /**
     * Run Method:
     * Goes through the Host's sequence.
     */
	public void run()
	{
		try
		{
			//host started
			born();

			//ASK QUESTION:

			//FINAL JEOPARDY:

			//host leaves
			leave();
		}
		catch(Exception e)
		{
			System.out.println("Problem with Host thread!");
			e.printStackTrace();
		}
	}
}