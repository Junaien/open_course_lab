public class Contestant extends Thread
{
	public String name="";
	private final long startTime = System.currentTimeMillis();
    public boolean passedExam=false;
    public int inGroup=0;
    public static int examGrade=0;

    /**
     * Contestant Constructor:
     * Sets the name of each Contestant as they are initialized.
     *
     * @param i - the Contestants' number.
     */
	public Contestant(int i)
	{
		name = ("Contestant " + (i+1));
	}

    /**
     * Name Method:
     * Returns the name of the current Contestant.
     *
     * @return name - the name of the Contestant.
     */
	public String name()
	{
		return name;
	}
    public void makeName(String n)
    {
        name = n;
    }

    /**
     * Age Method:
     * Returns the age, in milliseconds, of the current Contestant thread.
     *
     * @return age - the age of the Contestant.
     */
	public final long age()
	{
		return (System.currentTimeMillis() - startTime);
	}

    /**
     * Print Action Method:
     * Prints out an action of the current Contestant.
     *
     * @param message - the message to be acted out.
     */
	public void printAction(String message)
	{
		System.out.println(age() + " " + name() + " " + message);
	}

    /**
     * Print Speech Method:
     * Prints out dialog of the current Contestant.
     *
     * @param message - the message to be spoken.
     */
	public void printSpeech(String message)
	{
		System.out.println(age() + " " + name() + ": " + message);
	}

    /**
     * Born Method:
     * This is where the current Contestant will begin execution.
     */
	public void born() throws InterruptedException
	{
        Project2.mutex.acquire();
		printAction("is born");
	}

    /**
     * Enter Room Method:
     * Contestant waits to form a group of 4,
     * enter the classroom,
     * waits for the exam to start
     */
	public void enterRoom() throws InterruptedException
	{
        Project2.waiting++;
        if((Project2.waiting%Project2.room_capacity)==0)
        {
            for(int i=1; i<Project2.room_capacity; i++)
            {
                Project2.group.release();
            }
            Project2.waiting=0;
            Project2.inRoom += Project2.room_capacity;
            Project2.mutex.release();

        }
        else if ((Project2.waiting%Project2.room_capacity)!=0)
        {
            Project2.mutex.release();
            Project2.group.acquire();
        }
        printAction("has entered the room.");
        printAction("is in group " + inGroup);
        printAction("inRoom: " + Project2.inRoom + " initial: " + Project2.initial_num_contestants);
        if(Project2.inRoom==Project2.initial_num_contestants)
        {
            Project2.roomReady.release();
        }
		
		printAction("end of ENTERROOM()************");
	}

    /**
     * Take Exam Method:
     * Contestants sleep for the pre-determined length of the exam.
     */
	public void takeExam() throws InterruptedException
	{
		printAction("began TAKEEXAM()************");
		//get signal from announcer
        printAction("is in the room, waiting for the exam to begin.");
        Project2.takeExam.acquire();

		//contestants and announcer sleep for exam_time
        sleep(Project2.exam_time);
        printAction("has woken up.");
        Project2.finishedExam.release();

		printAction("end of TAKEEXAM()************");
	}

    /**
     * Wait For Results Method:
     * After the exam, Contestants wait for their results.
     * They either leave or continue waiting for the introductions.
     */
	public void waitForResults() throws InterruptedException
	{
		printAction("began WAITFORRESULTS()************");
		//exam ends, contestants wait for the results
        Project2.examResults.acquire();
		//if winner, wait for introductions to start (signal from Announcer)
        if(passedExam==true)
        {
            Project2.startIntros.acquire();
        }
		printAction("end of WAITFORRESULTS()************");
	}

    /**
     * Introduction Method:
     */
	public void introduction()
	{
		printAction("began INTRODUCTION()************");
		//increase priority
		//say introduction
		//decrease priority
		//wait until time to answer question (signal from host)
		printAction("end of INTRODUCTION()************");
	}
	public void answerQuestion()
	{
		printAction("began ANSWERQUESTION()************");
		//receive signal from host
		//answer the question
		//wait until chosen again, or Final Jeopardy (signal from host)
		printAction("end of ANSWERQUESTION()************");
	}
	public void finalJeopardy()
	{
		printAction("began FINALJEOPARDY()************");
		//signaled by Host in order
		//if negative score, say bye to the host
		//else if positive score, place a bet between 0 and the score
		//signal to Host that answer is given
		//terminate
		leave();
		printAction("end of FINALJEOPARDY()************");
	}
	public void leave()
	{
		printAction("has left the stage.");
	}
	public void run()
	{
		try
		{
			//contestant starts
			born();

			//ENTER ROOM:
			enterRoom();

			//TAKE EXAM:
			takeExam();

			//WAIT FOR RESULTS:
			waitForResults();
            if(passedExam)
            {
                //INTRODUCTION:
                //introduction();

                //ANSWER QUESTION:
                //answerQuestion();

                //FINAL JEOPARDY:
                //finalJeopardy();
            }
            //contestant leaves
			printAction("has terminated");
		}
		catch(Exception e)
		{
			System.out.println("Problem with Contestant thread!");
			e.printStackTrace();
		}
	}
}