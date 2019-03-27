
public class Project1 {
	static int class1;
	static int class2;
	static int class3;
	static int class4;
	static int class5;
	static int bathroom;
	public Project1(){
	}
	
	private static final long startTime = System.currentTimeMillis();
	  
	   protected static final long age() {
	      return System.currentTimeMillis() - startTime;
	   }
	
	public static void initThreads()
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
	
	
	public static void main(String[] args)
	{
	
		System.out.println("Let the school day begin!");
		initThreads();
		class1 = class2 = class3 = class4 = class5 =-1;
	
		bathroom = 0;
		}
}
