/*
 Mei Ye (23459517)
 340 project 2
 classroom class
 */
import java.util.LinkedList;
import java.util.Queue;

public class Classroom 
{
	public int classroomCapacity;
	public int numberOfStudents = 0;
	public int numberOfStusSecExam = 0;
	public int numberOfStusThiExam = 0;
	public static long time = System.currentTimeMillis();
	Queue<Student> orderOfEnterClassroom = new LinkedList<Student>();
	Queue<Student> orderOfEnterSecondExam = new LinkedList<Student>();
	Queue<Student> orderOfEnterThirdExam = new LinkedList<Student>();
	
	Classroom(int clasc)
	{
		classroomCapacity = clasc;
	}
	public void msg(String m)
	{
		System.out.println("["+ (System.currentTimeMillis()-time) + "]" + m);
	}	
}
