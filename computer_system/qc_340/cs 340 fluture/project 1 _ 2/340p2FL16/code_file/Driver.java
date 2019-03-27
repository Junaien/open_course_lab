/*
 Mei Ye (23459517)
 340 project 2
 main thread
 */

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Driver 
{
	private static long time = System.currentTimeMillis();
	public static int numberOfStudent = 14;
	public static int classroomCapacity = 8;
	public static int groupSize = 3;
	public static int studentGroup = 0;
	public static Semaphore group = new Semaphore(0); // counting semaphore for group student 
	static Queue<Student> groupList = new LinkedList<Student>();//record student who is in the same group
	
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
	    
	    //wait for student thread end
	    for(Thread thr: threads)
		{
			try
			{
				thr.join();	
			}
			catch(InterruptedException e){}
		}
	    //test if the student thread is alive
	    for(Thread thr: threads)
		{
			msg("Is the "+ thr.getName()+ " is alive? "+ thr.isAlive() );
		}
		//wait for instructor thread  end
	    try
		{
			thread_instr.join();
			msg("Instructor is going home.");
		}
		catch(InterruptedException e){}
	 
	  msg("-------------thread end----------------"); 
		
	}
		
}
