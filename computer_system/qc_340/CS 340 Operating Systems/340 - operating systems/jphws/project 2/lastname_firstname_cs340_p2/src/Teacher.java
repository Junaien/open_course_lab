import java.util.concurrent.Semaphore;


public class Teacher extends Thread 
{
	//Teacher's Class Schedule Variables
	static int ClassSched [] = {15000, 30000, 50000, 65000};
	static String ClassStarted[] = {"No", "No", "No", "No"};
	public static int classlength = 10000;
	int ClassTurn = 0;
	
	//Teacher Has To Wait For The Last Student to Go To The Dorms
	public static Semaphore TeacherWaitsForStudent = new Semaphore(0, true);
	
	/**
	 * Constructor for EACH Student
	 */
	Teacher()
	{
		setName("Teacher");
		start();
	}

	/**
	 * A simple function that displays the current time in milli seconds
	 * @return
	 */
	public long currentTime()
	{
		return System.currentTimeMillis()-GlobalVariables.time;
	}
	
	/**
	 * Messages that occur throughout the day come from this function.
	 * @param m
	 */
	public void msg(String m) 
	{
		System.out.println("["+(currentTime())+"] "+getName()+":"+m);
	}
	
	/**
	 * Runs a Thread that has been started
	 */
	public void run()
	{
		while(ClassStarted[Students.Classes.length - 1] != "Over")
		{
			//Office Hours kicks in after the second class
			if(ClassTurn == 2)
			{
				msg(" started Office Hours.");
				try 
				{
					sleep(10000);
				} 
				catch (InterruptedException e) 
				{}
				msg(" ended Office Hours.");
			}
			
			/**
			 * Runs through each class by releasing all the students that
			 * were in the auditorium waiting for the class to start.
			 * Then moves to the next class till classes are all over.
			 */
			if (currentTime() >= ClassSched[ClassTurn])
			{
				ClassStarted[ClassTurn] = "Yes";
				msg(" started " + Students.Classes[ClassTurn] + ".");
				for(int i = 0; i < Students.waitingForClass[ClassTurn]; i++)
				{
					Students.StartClass.release();
				}
				try 
				{
					sleep(classlength);
					ClassStarted[ClassTurn] = "Over";
					msg(" ended " + Students.Classes[ClassTurn]+ ".");
					for(int i = 0; i < Students.waitingForClass[ClassTurn]; i++)
					{
						Students.InClass.release();
					}
				} 
				catch (InterruptedException e) 
				{}
				ClassTurn++;
			}
		}//ends for
		
		/**
		 * The first thing the teacher does is wait for a group of students
		 * to be formed.  Once the group is formed it checks for an available
		 * table.  If no table is available it will wait until a table is free
		 * aka after 1 group finishes eating at 1 table. When there is no one
		 * left waiting to be seated to eat the teacher waits till the last
		 * student finishes eating and leaves the cafeteria. This student
		 * notifies the teacher that alll students are done and the teacher leaves.
		 */
		try 
		{
			Students.GroupFormed.acquire();
			Students.TablesAvailable.acquire();
			while(Students.WaitingToEat.hasQueuedThreads())
			{
				for (int i = 0; i < Students.TableCapacity; i++)
				{
					Students.WaitingToEat.release();
				}
				
				if(Students.WaitingToEat.hasQueuedThreads())
				{
					Students.GroupFormed.acquire();
					Students.TablesAvailable.acquire();
				}
				else
				{
					TeacherWaitsForStudent.acquire();
					msg(" Leaves The Cafeteria");
				}
			}
		} 
		catch (InterruptedException e) 
		{}		
	}
}
