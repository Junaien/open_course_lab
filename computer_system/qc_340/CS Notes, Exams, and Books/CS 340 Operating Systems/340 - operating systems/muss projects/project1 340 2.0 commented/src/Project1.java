
public class Project1 {
	static int class1; // classes hold values of -1,0,1,2 representing "hasn't started yet", "class is open to enter", " in session" and "class has ended"
	static int class2; // classes hold values of -1,0,1,2 representing "hasn't started yet", "class is open to enter", " in session" and "class has ended"
	static int class3; // classes hold values of -1,0,1,2 representing "hasn't started yet", "class is open to enter", " in session" and "class has ended"
	static int class4; // classes hold values of -1,0,1,2 representing "hasn't started yet", "class is open to enter", " in session" and "class has ended"
	static int class5; // classes hold values of -1,0,1,2 representing "hasn't started yet", "class is open to enter", " in session" and "class has ended"
	static int bathroom; 
	static int bathroomturn =0;
	public Project1(){
	}
	
	private static final long startTime = System.currentTimeMillis();
	  
	   protected static final long age() {
	      return System.currentTimeMillis() - startTime;
	   }
	
	public static void initThreads() // starts teacher and Student threads
	{
		Teacher teacher = new Teacher();
		teacher.start();
		
		Student[] student = new Student[14];
		for(int i=1; i<14; i++)
		{
			student[i] = new Student(i);
			student[i].start();
		}//for
	}//initThreads
	
	//String[] Printer = new String[14];
	
	
	public static void main(String[] args)
	{
	
		System.out.println("Let the school day begin!");
		initThreads(); // starts threads
		class1 = class2 = class3 = class4 = class5 =-1; // initialize all classes to -1, meaning that have not started yet
		bathroom = 0; // bathroom initialized to unoccupied
		}
}
