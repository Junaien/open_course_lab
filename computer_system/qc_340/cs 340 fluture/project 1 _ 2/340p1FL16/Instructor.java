/*
 Mei Ye (23459517)
 340 project 1
 Instructor class 
 */
import java.util.*;

public class Instructor implements Runnable
{
	private static long time = System.currentTimeMillis();
	public boolean classroomOpen = false;
	Classroom classroom;
	boolean examTime = false;
	boolean examOver = false;
	Student student;
	Random rand = new Random();
	ArrayList<Student> students = new ArrayList<>();
	boolean exam3Begin = false;
	
	Instructor(Classroom cla)
	{
		classroom = cla;
	}
	
	public void run()
	{
		try
		{ 
			  Thread.sleep((long)(Math.random()*5000));   
			  
		}
		catch(InterruptedException e){}	
		
		instructorArrive();
		
		/*------------------------------Exam 1---------------------------------*/
		//wait until for the exam to start,random sleep, need to change.
		instructorSleeping(1000);
		examBegin();//exam 1 begin
		instructorSleeping(4000);//sleep during exam1
		exam1IsOver();//allowStudentToLeave();
		
		//take a brake after exam  1 is over
		try
		{
			msg(" Taking brake.");
			Thread.sleep((long)(Math.random() * 1000));
		}
		catch(InterruptedException e){}
		/*------------------------------Exam 1---------------------------------*/
		
		
		/*------------------------------Exam 2---------------------------------*/
		
		//wait until for the exam 2 start,random sleep, need to change.
		instructorSleeping(10000);
		examBegin();//exam 2 begin
		instructorSleeping(4000);//sleep during exam2
		exam2IsOver();//allowStudentToLeave();
		
		//take a brake after exam  2 is over
		try
		{
			msg(" Taking brake.");
			Thread.sleep((long)(Math.random() * 2000));
		}
		catch(InterruptedException e){}
		/*------------------------------Exam 2---------------------------------*/
		
		/*------------------------------Exam 3---------------------------------*/
		classroomOpen = true;
		instructorSleeping(3000);//for student to rush in the classroom
		exam3Begin();
		instructorSleeping(10000); //sleep during exam3
		exam3IsOver();
		/*------------------------------Exam 3---------------------------------*/
		
		msg("--------------Students Exam Grade-----------------");
		postExamGrade();
		msg("--------------------------------------------------");
			
	}
	
	public void msg(String m)
	{
		System.out.println("["+ (System.currentTimeMillis()-time) + "]" + "Instructor:" + m);
	}
	
	public void instructorArrive()
	{
		classroomOpen = true;
		msg(" Arrive, open the door."); 
	}
	
	public void instructorSleeping(int sleep_time)
	{
		try{ 
			  Thread.sleep(sleep_time);       
		  }
		catch(InterruptedException e){}
		
	}
	
	public void examBegin()
	{
		msg("----------------- Exam Begin-----------------");
		examTime = true;	
	}
	
	public void exam3Begin()
	{
		msg("----------------- Exam Begin-----------------");
		exam3Begin = true;
	}
	
	public void exam1IsOver()
	{
		examOver = true;
		msg("----------------Exam 1 over-----------------");
		msg(" Collecting the exam.");
		//allow student leave classroom
		while(!(classroom.orderOfEnterClassroom.isEmpty()))
		{
			//students leave in the order which they enter.
			msg(" Student " + new Integer(classroom.orderOfEnterClassroom.remove().getName()).toString() + " leaving classroom");	
		}
		examTime = false;
	}
	
	public void exam2IsOver()
	{
		examOver = true;
		msg("----------------Exam 2 over-----------------");
		msg(" Collecting the exam.");
		
		while(!(classroom.orderOfEnterSecondExam.isEmpty()))
		{
			//students leave in the order which they enter.
			msg(" Student " + new Integer(classroom.orderOfEnterSecondExam.remove().getName()).toString() + " leaving classroom");	
		}
		examTime = false;
	}
	
	public void exam3IsOver()
	{
		examOver = true;
		msg("----------------Exam 3 over-----------------");
		msg(" Collecting the exam.");
		while(!(classroom.orderOfEnterThirdExam.isEmpty()))
		{
			//students leave in the order which they enter.
			msg(" Student " + new Integer(classroom.orderOfEnterThirdExam.remove().getName()).toString() + " leaving classroom");	
		}
		examTime = false;
	}

	public void postExamGrade()
	{
		System.out.println("StudentID Exam1 Exam2 exam3" );
		for(Student stud : students)
		{
			System.out.print("Student "+ stud.StudentID +"  ");
			for(int grade : stud.studentGrade)
			{
				System.out.print(grade+ "	");	
			}
			System.out.println(" ");
		}	
	}
	
}
