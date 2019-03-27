
public class Teacher extends Thread 
{
	//Teacher's Class Schedule Variables
	public static int classlength = 10000;
	static int ClassSched [] = {15000, 30000, 50000, 65000, 80000};
	String OfficeHoursStarted = "No";
	String ClassStarted[] = {"No", "No", "No", "No", "No"};
	
	/**
	 * Gives the Teacher Threads the name Teacher when created and then starts them
	 */
	Teacher()
	{
		setName("Teacher");
		start();
	}

	/**
	 * This is an easier function to be used that just grabs the current time
	 * of the system.
	 * @return
	 */
	public long currentTime()
	{
		return System.currentTimeMillis()-GlobalVariables.time;
	}
	
	/**
	 * All messages that occur throughout the day come from this function.
	 * The time is grabbed from the currentTime function so that teacher
	 * teacher is on the same time schedule as the students.
	 * @param m
	 */
	public void msg(String m) 
	{
		System.out.println("["+(currentTime())+"] "+getName()+":"+m);
	}
	
	/**
	 * Runs the Thread that has been started.	
	 */
	public void run()
	{
		while(GlobalVariables.ClassStatus[Students.Classes.length-1] != "Over")
		{
			for (int i = 0; i < Students.Classes.length; i++)//Runs through each class
			{
				if (currentTime() >= ClassSched[i] && currentTime() <= ClassSched[i] + classlength)
				{
					/**
					 * If the time right now is in-between the time the class is
					 * started and the time the class's length is over then the teacher
					 * sends a signal to the student class that the class has started,
					 * this function is in the teacherArrivesAtClass function.
					 */
					if (ClassStarted[i] == "No")
					{
						msg(" started " + Students.Classes[i] + ".");
					}
					ClassStarted[i] = "Yes";
					GlobalVariables.ClassStatus[i] = "On";
					GlobalVariables.TeacherArrives.teacherArrivesAtClass();
				}
				if (currentTime() > ClassSched[i] + classlength)
				{
					/**
					 * If this class is over the teacher sends another signal back to 
					 * the student this time by using the ClassStatus = "Over". The 
					 * student will exit its while loop. After the signal is sent the 
					 * for loop begins again and the next class beings. 
					 */
					if (ClassStarted[i] == "Yes")
					{
						msg(" ended " + Students.Classes[i]+ ".");
					}
					ClassStarted[i] = "Done";
					GlobalVariables.ClassStatus[i] = "Over";
				}
			}//ends for
			
			
			//OFFICE HOURS
			
			if (currentTime() >= ClassSched[1] + classlength && currentTime() <= ClassSched[2])
			{
				/**
				 * If the time is between the end of the second class and the beginning
				 * of the third class then Office Hours begin for the Teacher.
				 */
				if (OfficeHoursStarted == "No")
				{
					msg(" started Office Hours.");
				}
				OfficeHoursStarted = "Yes";
			}
			if (currentTime() > ClassSched[2] - 1)
			{
				/**
				 * Right before the third class begins office hours end and classes
				 * resume.
				 */
				if (OfficeHoursStarted == "Yes")
				{
					msg(" ended Office Hours.");
				}
				OfficeHoursStarted = "Done";
			}
			//END OF OFFICE HOURS
			
			/**
			 * The join function kills off each thread individually
			 */
			if (GlobalVariables.ClassStatus[Students.Classes.length-1] == "School's Over")
			{
				try 
				{
					currentThread().join();
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}//ends join
		}//ends while
	}//ends run
}//ends Teacher class