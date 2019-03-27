/*
 Mei Ye (23459517)
 340 project 2
 Instructor class 
 */
import java.util.*;
import java.util.concurrent.Semaphore;

public class Instructor implements Runnable
{
	private static long time = System.currentTimeMillis();
	public boolean classroomOpen = false;
	Classroom classroom;
	Student student;
	Random rand = new Random();
	ArrayList<Student> students = new ArrayList<>();
	public Semaphore openclassroom = new Semaphore(0);//use for signal student classroom is open, student can get in.
	
	public Semaphore examOver = new Semaphore(0); //use for signal student exam is end.
	public Semaphore examOver2 = new Semaphore(0);
	public Semaphore exam3Over = new Semaphore(0);
	
	public Semaphore exam2Begin = new Semaphore(0);//signal student can get in classroom for second exam
	public Semaphore exam3Begin = new Semaphore(0);
	public Semaphore leave = new Semaphore(0);//after the grade is post,signal student group and leave
	
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
		
		assignExam();
		instructorSleeping(3000);//sleep through out then exam
		examIsEnd();
		/*---------------second Exam----------------------*/
		exam2begin();
		instructorSleeping(3000);
		assignExam2();
		instructorSleeping(3000);//sleep through out then exam
		exam2IsEnd();
		instructorSleeping(3000);//taking break
		/*---------------Third Exam----------------------*/
		exam3Isbegin();
		instructorSleeping(3000);
		assignExam3();
		instructorSleeping(3000); //sleep through out then exam
		exam3IsEnd();
		
		msg("--------------Students Exam Grade-----------------");
		postExamGrade();
		instructorSleeping(3000);
		//msg("--------------------------------------------------");	
	}
	
	public void msg(String m)
	{
		System.out.println("["+ (System.currentTimeMillis()-time) + "]" + "Instructor:" + m);
	}
	public void instructorArrive()
	{
		msg(" Arrive, open the door."); 
		classroomOpen = true;
		
		for(int i=0; i< Driver.numberOfStudent; i++)
		{          
			openclassroom.release();                        
	    }      		
	}
	public void instructorSleeping(int sleep_time)
	{
		try{ 
			  Thread.sleep(sleep_time);       
		  }
		catch(InterruptedException e){}
		
	}
	
	public void assignExam()
	{
		try {
			Student.Mutex2.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			msg("----------------Exam 1 is begin-----------------");
			msg(" Assigning exam");
		
			while(!(classroom.orderOfEnterClassroom.isEmpty()))
			{
			
			msg(" Student " + new Integer(classroom.orderOfEnterClassroom.remove().getName()).toString() + " got the exam.");	
			}
	}
	public void assignExam2()
	{
		try {
			Student.Mutex2.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			msg("----------------Exam 2 is begin-----------------");
			msg(" Assigning exam");
		
			//msg(classroom.orderOfEnterSecondExam.toString());
			
			while(!(classroom.orderOfEnterSecondExam.isEmpty()))
			{
			
				msg(" Student " + new Integer(classroom.orderOfEnterSecondExam.remove().getName()).toString() + " got the exam.");	
			}
	}
	public void assignExam3()
	{
		try {
			Student.Mutex2.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		msg("----------------Exam 3 is begin-----------------");
		msg(" Assigning exam");
		
		while(!(classroom.orderOfEnterThirdExam.isEmpty()))
		{
			msg(" Student " + new Integer(classroom.orderOfEnterThirdExam.remove().getName()).toString() + " got the exam.");	
		}	
	}
	
	public void examIsEnd()
	{
		msg(" Exam is end, please hand in exam.");
		for(int i=0; i< Driver.numberOfStudent; i++)
		{          
			examOver.release();                        
	    }   
		msg(" Taking break.");	
		instructorSleeping(3000);	
	}
	public void exam2IsEnd()
	{
		msg(" Exam 2 is end, please hand in exam.");
		for(int i = 0; i< Driver.numberOfStudent; i++)
		{          
			examOver2.release();                        
	    }   
		msg(" Taking break.");	
		instructorSleeping(3000);
	}
	public void exam3IsEnd()
	{
		msg(" Exam 3 is end, please hand in exam.");
		for(int i = 0; i< Driver.numberOfStudent; i++)
		{          
			exam3Over.release();                        
	    }   
		msg(" Calculating the grade.");		
		instructorSleeping(3000);	
	}

	public void exam2begin()
	{
		for(int i = 0; i< Driver.numberOfStudent; i++)
		{          
			exam2Begin.release();                        
	    }
	}
	public void exam3Isbegin()
	{
		for(int i = 0; i< Driver.numberOfStudent; i++)
		{          
			exam3Begin.release();                        
	    }
	}
	
	public void postExamGrade()
	{
		System.out.println("StudentID Exam1 Exam2 Exam3 " );
		for(Student stud : students)
		{
			System.out.print("Student "+ stud.StudentID +"  ");
			for(int grade : stud.studentGrade)
			{
				System.out.print(grade+ "	");	
			}
			System.out.println(" ");
		}	
		//signal student to group and leave
		for(int i = 0; i< Driver.numberOfStudent; i++)
		{          
			leave.release();                        
	    } 	
	}
		
}
