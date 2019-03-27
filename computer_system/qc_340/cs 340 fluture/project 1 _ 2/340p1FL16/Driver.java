/*
 Mei Ye (23459517)
 340 project 1
 main thread
 */

public class Driver 
{
	private static long time = System.currentTimeMillis();
	public static int numberOfStudent = 14;
	public static int classroomCapacity = 10;
	
	public static void msg(String m)
	{
		System.out.println("["+ (System.currentTimeMillis()-time) + "]"+ "MainThread: " + m);
	}

	public static void main(String[] args) 
	{
		msg("-------------Exam Day----------------");
		
		Classroom classroom = new Classroom(classroomCapacity);
		Instructor instructor = new Instructor(classroom);
		Thread thread_instr = new Thread( instructor );
		thread_instr.start();
	    Thread[] threads = new Thread[numberOfStudent];
		
	    for(int i = 0; i< numberOfStudent; i++)
		{
			threads[i] = new Thread(new Student(i+1, instructor, classroom));
			threads[i].start();
	    }
			
		//students will leave by joining each other
		for(Thread thr: threads)
		{
			try
			{
				thr.join();
				msg("Student " + thr.getName().split("-")[1] + " is going home.");
			}
			catch(InterruptedException e){}
		}
		
		// test if the thread is alive
		for(Thread thr: threads)
		{
			msg("Is the "+ thr.getName()+ " is alive? "+ thr.isAlive() );
		}
		
		//The instructor will terminate after all students leave
		try
		{
			thread_instr.join();
			msg("Instructor is going home.");
		}
		catch(InterruptedException e){}
	 }		
}
