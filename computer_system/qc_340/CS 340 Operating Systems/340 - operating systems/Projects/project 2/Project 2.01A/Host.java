import java.util.*;

public class Host extends Thread
{
    String name = "Trebek";
    private final long startTime = System.currentTimeMillis();

    public long age()
    {
        return (System.currentTimeMillis() - startTime);
    }//age

    public void printAction(String message)
    {
        System.out.println(age() + " " + name + " " + message);
    }//printAction

    public void printSpeech(String message)
    {
        System.out.println(age() + " " + name + ": " + message);
    }//printSpeech

    public void born()
    {
        printAction("is born");
    }

    public void leave()
    {
        printAction("has left the stage.");
    }

    public void run()
    {
        try
        {
            born();

            leave();
        }
        catch(Exception e)
        {
            System.out.println("Problem with Host thread!");
            e.printStackTrace();
        }
    }
}