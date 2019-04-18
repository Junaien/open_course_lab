import java.util.*;

public class Announcer extends Thread
{
    public String name = "Announcer";
    private final long startTime = System.currentTimeMillis();
    Random rand = new Random();
    public int randomNumber = 0;
    public int highest = 0;
    public String name()
    {
        return name;
    }
    public final long age()
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
    public int nextRand(int limit)
    {
        return (randomNumber = rand.nextInt(limit));
    }
    public void born() throws InterruptedException
    {
        printAction("is born");
    }
    public void startExam() throws InterruptedException
    {
        printAction("began STARTEXAM()************");
        //wait for contestants to fill up the room
        Project2.roomReady.acquire();
        printAction("has the Room Ready signal.");
        printSpeech("Contestants, it's time to start the exam.");

        for(int i=0; i<Project2.initial_num_contestants; i++)
        {
            Project2.takeExam.release();
        }
        
        //then start the exam
        sleep(Project2.exam_time);
        Project2.finishedExam.acquire(Project2.initial_num_contestants);
        printSpeech("Pencils down! It's now time to grade your exams.");
        printAction("end of STARTEXAM()************");
    }
    public void gradeExam() throws InterruptedException
    {
        //generate a random grade per contestant
        for (int i = 0; i < Project2.initial_num_contestants; i++)
        {
            Project2.contestant[i].examGrade = nextRand(400);
            Project2.player[i].examGrade = 0;
            printAction("Contestant " + (i+1) + " has scored: " + Project2.contestant[i].examGrade);
        }//for

        for(int pp=0; pp<Project2.initial_num_contestants; pp++)
        {
            for(int c=0; c<Project2.initial_num_contestants; c++)
            {
                System.out.println("Player: " + pp + " exam grade: " + Project2.player[pp].examGrade + " Contestant: " + c + " exam grade: " + Project2.contestant[c].examGrade );
                if(Project2.player[pp].examGrade < Project2.contestant[c].examGrade)
                {
                    Project2.player[pp] = Project2.contestant[c];
                    highest = Project2.contestant[c].examGrade;
                    Project2.player[pp].makeName(Project2.contestant[c].name);
                    System.out.println("PP: " + pp + " c: " + c);
                }//if
            }//1st inner for
            for(int i=0; i<Project2.initial_num_contestants; i++)
            {
                if(Project2.contestant[i].examGrade == highest)
                {
                    Project2.contestant[i].examGrade = 0;
                }//if
            }//2nd inner for
        }//outer for
       
        //allow the 3 highest to stay
        for (int i = 0; i < Project2.num_of_players; i++)
        {
            printSpeech("Congratulations, " + Project2.player[i].name + ", you passed the exam!");
            Project2.player[i].passedExam = true;
        }//for

        //terminate the rest
        for (int i = Project2.num_of_players; i < Project2.initial_num_contestants; i++)
        {
            printSpeech("I'm sorry, " + Project2.player[i].name + ", but it's time for you to go home.");
            Project2.player[i].passedExam = false;
        }//for

        printAction("end of GRADEEXAM()************");
    }
    public void startIntros() throws InterruptedException
    {
        printAction("began STARTINTROS()************");
        //sleep(random);
        sleep(randomNumber = rand.nextInt(400));

        //signal c's to present themselves
        Project2.startIntros.release();

        //wait until c's are done
        Project2.finishIntros.acquire();

        printAction("end of STARTINTROS()************");
    }
    public void startHost() throws InterruptedException
    {
        printAction("began STARTHOST()************");
        //start the host
        Host host = new Host();
        host.start();

        //wait until host is done
        Project2.hostIntro.acquire();

        printAction("end of STARTHOST()************");
    }
    public void startGame() throws InterruptedException
    {
        printAction("began STARTGAME()************");
        //get signal from host
        //say start game
        //signal the host
        //terminate
        printAction("end of STARTGAME()************");
    }
    public void leave() throws InterruptedException
    {
        printAction("has left the stage.");
    }
    public void run()
    {
        try
        {
            //announcer started
            born();

            //START EXAM:
            startExam();

            //GRADE EXAM:
            gradeExam();

            //START INTRODUCTIONS:
            //startIntros();

            //START HOST:
            //startHost();

            //START GAME:
            //startGame();

            //announcer left
            leave();

            printAction("has terminated");
        } 
        catch (Exception e)
        {
            System.out.println("Problem with Announcer thread!");
            e.printStackTrace();
        }
    }
}