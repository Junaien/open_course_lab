public class GlobalVariables 
{
	//These Variables Are All Globally Used By Both Threads
	
	//Class Based Variables
	static boolean ClassListOccupied = false;
	static boolean ClassInSession[] = {false, false, false, false, false};
	static String ClassStatus[] ={"Didn't Start","Didn't Start","Didn't Start","Didn't Start","Didn't Start"};

	//Join Based Variables
	static int amountOfPeopleFinished = 0;
	
	//System Variables
	public static long time = System.currentTimeMillis();
	
	//Wait & Notify functions used for when the Teacher Arrives
	public static GlobalVariables TeacherArrives = new GlobalVariables();
	public synchronized void waitForTeacher() 
	{
		try 
		{
			wait();
		} 
		catch (InterruptedException e) 
		{
			
		}
	}
	public synchronized void teacherArrivesAtClass() 
	{
		notifyAll();
	}
	
}
	

