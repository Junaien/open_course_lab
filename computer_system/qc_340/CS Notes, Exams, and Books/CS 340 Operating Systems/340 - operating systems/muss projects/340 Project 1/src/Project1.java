
public class Project1 {
	static int class1;
	static int class2;
	static int class3;
	static int class4;
	static int class5;
	
	public Project1(){
			
		
	}
	
	
	public static void initThreads()
	{
		Teacher teacher = new Teacher();
		teacher.start();
		
		Student[] student = new Student[4];
		for(int i=1; i<=1; i++)
		{
			student[i] = new Student();
			//student[i].setName("Student ");
			System.out.println(student[i].name + "is born");
			student[i].start();
		}//for
	}//initThreads
	
	
	public static void main(String[] args)
	{
	
		System.out.println("Let the school day begin!");
		Project1 project1 = new Project1();
		initThreads();
		int class1 = -1;
		int class2 = -1;
		int class3 = -1;
	    int class4 = -1;
		int class5 = -1;
		}
}
