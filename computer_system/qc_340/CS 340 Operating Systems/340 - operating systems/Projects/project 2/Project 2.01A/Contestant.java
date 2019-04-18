import java.util.*;

public class Contestant extends Thread
{
    String name="";
    private long startTime = System.currentTimeMillis();

    public Contestant(int i)
    {
        name = ("Contestant " + (i+1));
    }//contestant constructor

    /**
     * Age Method:
     * This method calculates how old (in milliseconds) the specified thread is.
     *
     * @return age - the age of the thread.
     */
    public long age()
    {
        return System.currentTimeMillis()-startTime;
    }//age

    /**
     * Print Action Method:
     * This method will print out an action of the specified thread.
     *
     * @param message - the message to be printed out.s
     */
    public void printAction(String message)
    {
        System.out.println(age() + " " + name + " " + message);
    }//printAction

    /**
     * Print Speech Method:
     * This method will print out the dialog of a the specified thread.
     *
     * @param message - the message to be printed
     */
    public void printSpeech(String message)
    {
        System.out.println(age() + " " + name + ": " + message);
    }//printMessage

    /**
     * Born Method:
     * This is where the specified thread will begin it's execution.
     * It will print out an action confirming that the thread has started.
     */
    public void born()
    {
        printAction("is born");
    }//born

    /**
     * Leave Method:
     * This is where the specified thread will end it's execution.
     * It will print out an action confirming that the thread has terminated.
     */
    public void leave()
    {
        printAction("has left the stage.");
    }

    /**
     * Run Method:
     * This is where the specified thread will carry out it's execution.
     * This method will simply "go through the motions" of the game.
     */
    public void run()
    {
        try
        {
            born();

            leave();
        }//try
        catch(Exception e)
        {
            System.out.println("Problem with Contestant thread!");
            e.printStackTrace();
        }//catch
    }//run
}//Contestant class