/*
 Mei Ye (23459517)
 340 project 2
  student class
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

class Student implements Runnable
{
	boolean[] tookExam = new boolean[3];//record number of exams which student took
	public int StudentID;// student name
	Instructor instructor;
	Classroom classroom;
	public static long time = System.currentTimeMillis();
	public ArrayList<Integer> studentGrade = new ArrayList<Integer>();//store student grade
	public int Grade;
	static Semaphore Mutex = new Semaphore(1);//for protest classroom.numberOfStudents++
	static Semaphore Mutex2 = new Semaphore(0);// for signal the exam is start
	static Semaphore Mutex3 = new Semaphore(1);//for protest classroom.numberOfStusSecExam++
	static Semaphore Mutex4 = new Semaphore(1);//for protest classroom.numberOfStusThiExam++
	static Semaphore Mutex5 = new Semaphore(1);//for protest Driver.studentGroup++;
	
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
		studentSleeping(1000);//wait for instructor assign exam
		ExamStart();
		studentSleeping(1000);//taking exam
		takeBreak();
		
		/*---------------second Exam----------------------*/
		enterClassroomSEC();
		ExamStart2();
		studentSleeping(5000);//taking exam
		takeBreak2();
		
		/*---------------third Exam----------------------*/
		enterClassroomThir();
		studentSleeping(3000);//taking exam
		waitExamGrade();
		
		/*---------------leaving----------------------*/
		studentSleeping(3000);
		groupTogether();
			
	}
	public void wait_For_Instructor()
	{
		if(instructor.classroomOpen == false)
		{
			msg(" Waiting for the instructor.");	
		}
		try {
			instructor.openclassroom.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	}

	public void enterClassroom()
	{
		try {
			Mutex.acquire();
		} catch (InterruptedException e1) {	
			e1.printStackTrace();
		}
		if(classroom.numberOfStudents != classroom.classroomCapacity)
		{
			classroom.numberOfStudents++;
			classroom.orderOfEnterClassroom.add(this);
			msg( " Enter the classroom, Classroom have "+ classroom.numberOfStudents + " students.");
			tookExam[0] = true;
			studentGrade.add(getGrade());
			Mutex.release();
		}
		else
		{
			msg(" Classroom is full or Exam begins, need to wait for next exam.");
			tookExam[0] = false;
			studentGrade.add(0);
			Mutex.release();
				
			try {
				Mutex3.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//student who miss the first exam, will wait in the queue, at enter classroom first for the next exam	
			classroom.numberOfStusSecExam++;
			classroom.orderOfEnterSecondExam.add(this);		
			tookExam[1] = true;
			studentGrade.add(getGrade());
			Mutex3.release();
							
		}		
	} 		
	public void enterClassroomSEC()
	{
		try {
			instructor.exam2Begin.acquire();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		try {
			Mutex3.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if((classroom.numberOfStusSecExam != classroom.classroomCapacity)&&(tookExam[1] != true))
		{		
			classroom.numberOfStusSecExam++;
			classroom.orderOfEnterSecondExam.add(this);	
			msg( " Enter the classroom, Classroom have "+ classroom.numberOfStusSecExam + " students.");
			tookExam[1] = true;
			studentGrade.add(getGrade());
			Mutex3.release();	
		}
		else
		{
			if(tookExam[1] != true)
			{
				tookExam[1] = false;
				studentGrade.add(0);
				msg(" Classroom is full or Exam begins, need to wait for next exam.");
			}
				Mutex3.release();
		}	
	}
	public void enterClassroomThir()
	{
		try {
			instructor.exam3Begin.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if((tookExam[0] == true)&&(tookExam[1] == true))
		{
			tookExam[0] = false;
			studentGrade.add(0);
			studentSleeping(3000);
		}
		else 
		{
			msg( " Enter the classroom");
			classroom.orderOfEnterThirdExam.add(this);
			studentGrade.add(getGrade());
			tookExam[2] = true;
		}	
	}
	
	public void ExamStart()
	{
		if(classroom.numberOfStudents == classroom.classroomCapacity)
		{
			Mutex2.release();	
		}	
	}
	public void ExamStart2()
	{
		if(classroom.numberOfStusSecExam == classroom.classroomCapacity)
		{
			Mutex2.release();	
		}	
	}
	
	public void takeBreak()
	{
		if(tookExam[0] == true)
		{
			try {
				instructor.examOver.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			msg(" leaving classroom.");
			msg(" Taking break.");
		}
		else 
		{
			studentSleeping(1000);	
		}
	}
	public void takeBreak2()
	{
		try {
			instructor.examOver2.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			
		if(tookExam[1] == false)
		{
			studentSleeping(5000);
		}	
		else
		{
			msg(" leaving classroom.");
			msg(" Taking break.");
		}
	}
	public void waitExamGrade()
	{
		try {
			instructor.exam3Over.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		msg(" Waiting instructor post grade.");
	}
	
	public void groupTogether()
	{
		try {
			instructor.leave.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			Mutex5.acquire();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		Driver.studentGroup++;
		if((Driver.studentGroup % Driver.groupSize == 0)||(Driver.studentGroup == Driver.numberOfStudent))
		{
			Driver.groupList.add(this);
			for(int i = 0; i < Driver.groupSize - 1; i++)
			{
				Driver.group.release();	
			}
			//display the name of students grouping together
			msg(" Group List: ");
			for(Student s : Driver.groupList)
			{
				msg(" Student " + s.getName());
			}
			//after group, student will leave
			while(!(Driver.groupList.isEmpty()))
			{
				msg(" Student " + new Integer(Driver.groupList.remove().getName()).toString() + " is leaving.");	
			}
			Mutex5.release();
		}
		else
		{
			Driver.groupList.add(this);
			Mutex5.release();
			try {
				Driver.group.acquire();	
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}
		
	public int getName() 
	{
		return  StudentID ;
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

}
