import java.util.*;

public class Announcer extends Thread
{
    public String name="Announcer";
    private final long startTime=System.currentTimeMillis();

    public String name()
    {
        return name;
    }

    public long age()
    {
        return (System.currentTimeMillis() - startTime);
    }

    public void printAction(String message)
    {
        System.out.println(age() + " " + name() + " " + message);
    }

    public void printSpeech(String message)
    {
        System.out.println(age() + " " + name() + ": " + message);
    }

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
            System.out.println("Problem with Announcer thread!");
            e.printStackTrace();
        }
    }
}
