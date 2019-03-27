/*
 Mei Ye (23459517)
 340 project 1
  student class
 */


import java.util.ArrayList;
import java.util.Random;

class Student implements Runnable
{
	boolean[] tookExam = new boolean[3];//record number of exams which student took
	public int StudentID;// student name
	Instructor instructor;
	public int defaultPriority;
	Classroom classroom;
	public ArrayList<Integer> studentGrade = new ArrayList<Integer>();//store student grade
	public int Grade;
	
	public static long time = System.currentTimeMillis();
	
	Student(int name, Instructor instr, Classroom cla)
	{
		StudentID = name;
		instructor = instr;
		instr.students.add(this);
		classroom = cla;
	}
	
	public void run()
	{
		try
		{
			msg(" Arrives school.");
			Thread.sleep((long)(Math.random() * 3000));
		}
		catch(InterruptedException e){}
		
		wait_For_Instructor();//wait for instructor open the classroom door
		
		enterClassroom();
		
		if(tookExam[0]== true)
		{
			waitForExam();
			takeExam1();
			//sleep long time,for the exam
			studentSleeping(5000);
			
			//take a brake, sleep random time
			takeExamBrake();
			enterClassroomSecondTime();
			waitForExam();//wait for second exam begin
			takeExam2();   //take second Exam
			
			studentSleeping(2000); //sleep long time,for the exam
			//Exam 2 is end, students and instructor are taking brake
			takeBrake2();//exam all done, wait for grade.
			
			if(tookExam[1]== true)//the student who already took 2 exam, they don't need to take third exam, exam3 false
			{
				tookExam[2] = false;	
				studentGrade.add(0);
				studentSleeping(10000);	
			}
			else//tookExam[1] = false
			{
				//need to wait for exam 2 done
				
				waitForExam3();
				
				synchronized(this) 
				{
					enterClassroomThirdTime();
			     }
				Thread.yield();
				studentSleeping(1000);
				// student who take third exam
				takeExam3();
				
			}
			
		}
		else//tookExam[0]== false
		{
			studentGrade.add(0);//assign grade(0) to the student who miss the exam 1
			
			waitForExam();//wait for second exam begin
			takeExam2();
			//tookExam{false,true}
			
			studentSleeping(2000);//sleep during exam2
			takeBrake2();
			
			waitForExam3();
			enterClassroomThirdTime();
			Thread.yield();
			studentSleeping(1000);
			takeExam3();//take 3 exam.	
			
			//leave classroom
		}
		
		studentSleeping(10000);
		
	}
		
	public void enterClassroom()
	{
		try
		{
			if ((classroom.numberOfStudents != classroom.classroomCapacity)&&(instructor. examTime == false))
			{
				synchronized(this) 
				{
					classroom.numberOfStudents++;
					//keep track of the order of student come in the classroom.
					classroom.orderOfEnterClassroom.add(this);
					msg( " Enter the classroom, Classroom have "+ classroom.numberOfStudents + " students.");
			    }
				
				defaultPriority = Thread.currentThread().getPriority(); 
				Thread.currentThread().setPriority(Thread.currentThread().getPriority() + 1);
				studentSleeping(1000);
				Thread.currentThread().setPriority(defaultPriority);	
				
				tookExam[0] = true;//indicate the student already took exam 1					 
			}
			else
			{
				tookExam[0] = false;
				msg(" Classroom is full or Exam begins, need to wait for next exam.");
				missExam1();		
			}
		}
		catch (Exception e)
		{
			msg("[ERR]");
		}
	}
	
	public void missExam1()
	{
		//Thread need to skip following statements, execute 2exam statement.
		//wait for next Exam
		synchronized(this) 
		{
			classroom.numberOfStusSecExam++;
			classroom.orderOfEnterSecondExam.add(this);
			tookExam[1] = true;
	     }
		
		Thread.yield();
		Thread.yield();
	
		while(instructor.examOver == false)
		{
			studentSleeping(1000);
			
		}		
	}
	
	public void takeExam1()
	{
		//assign grade to the student
		if(tookExam[0] == true)
		{
			msg(" Taking Exam 1.");
			studentGrade.add(getGrade());
		}
		else
			studentGrade.add(0);		
	}

	public void takeExam2()
	{
		if(tookExam[1] == true)
		{
			msg(" Taking Exam 2.");
			studentGrade.add(getGrade());
		}
		else
			studentGrade.add(0);	
	}
	
	public void	enterClassroomSecondTime()
	{
		if((classroom.numberOfStusSecExam != classroom.classroomCapacity)&&(instructor. examTime == false))
		{
			synchronized(this) 
			{
				tookExam[1] = true;	
				classroom.numberOfStusSecExam++;
				classroom.orderOfEnterSecondExam.add(this);
				msg( " Enter classroom, Classroom have "+ classroom.numberOfStusSecExam + " students.");
		     }
		}
		else
		{
			msg(" Classroom is full or Exam begins, wait for next exam.");
			tookExam[1] = false;
			missExam2();
		}
		
	}
		
	public void missExam2()
	{
		Thread.yield();
		Thread.yield();
		
		while(instructor.examOver == false)
		{
			studentSleeping(100);
		}
	}

	public void takeExam3()
	{
		if(instructor.exam3Begin == true)
		{
			msg(" Taking Exam 3.");
			studentGrade.add(getGrade());
		}
	}
	
	public void enterClassroomThirdTime()
	{	
		synchronized(this) 
		{
			classroom.orderOfEnterThirdExam.add(this);
			classroom.numberOfStusThiExam++;
			msg( " Enter the classroom, Classroom have "+ classroom.numberOfStusThiExam + " students.");
		}
		tookExam[2] = true;		
		
	}
	public void takeExamBrake()
	{
		if(instructor.examOver == true)
		{
			try
			{
				msg(" Taking brake.");
				Thread.sleep((long)(Math.random() * 5000));
			}
			catch(InterruptedException e){}
		}
	}	
	
	public void takeBrake2()
	{
		if((tookExam[1] == true)&&(instructor.examOver == true))
		{
			try
			{
				msg(" Taking brake.");
				Thread.sleep((long)(Math.random() * 5000));
			}
			catch(InterruptedException e){}
		}
	}
	
	public void waitForExam()
	{
		while(instructor.examTime == false)
		{
			studentSleeping(4000);	
		}
	}
	
	public void waitForExam3()
	{
		while(instructor.exam3Begin == false)
		{
			studentSleeping(4000);
		}
	}
	public void wait_For_Instructor()
	{
		if(instructor.classroomOpen == false)
		{
			msg(" Waiting for the instructor.");
			
		}
		
		while(instructor.classroomOpen == false)
		{
			studentSleeping(1000);
			
			if(instructor.classroomOpen == false)
			{
				msg(" Still waiting for the instructor.");
			}
			else
			{
				break;
			}
		}
	}
	public void msg(String m)
	{
		System.out.println("["+ (System.currentTimeMillis()-time) + "]"+ "Student " + StudentID + " :" + m);
	}
	public void studentSleeping(int sleep_time)
	{
		try
		{ 
			  Thread.sleep(sleep_time);       
		 }
		catch(InterruptedException e){}
		
	}
	public int getGrade()
	{
		Random r = new Random();
		int Low = 10;
		int High = 100;
		int Result = r.nextInt(High-Low) + Low;
		return Result;	
	}
	public int getName() 
	{
		return  StudentID ;
	}

}
