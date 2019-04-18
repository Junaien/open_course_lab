import java.util.Random;


public class Student extends Thread {
	
	private static boolean restroomed = false;
	public static String name = "muss";
	boolean gotoclass1 = false;
	boolean gotoclass2 = false;
	boolean gotoclass3 = false;
	boolean gotoclass4 = false;
	boolean gotoclass5 = false;
	
	public Student() {	
		
	}

	//public void setName(String newname){
		//Student.name = newname;
		
	//}
	
	public void run(){
	
	Random randomGenerator = new Random();
    int snooze = randomGenerator.nextInt(3000);
    int gotobathroom = randomGenerator.nextInt(2000);
	int bathroom = 0;
	
	try {
		Student.sleep(snooze);
		System.out.println(Student.name = " has woken up");
		
		
		
		while(Student.restroomed==false){
		while(bathroom == 0){//bathroom is empty
		System.out.println(Student.name + " has gone into the bathroom");
		bathroom =1; //bathroom is now occupied
		Student.sleep(gotobathroom);
		System.out.println(Student.name + " has left the bathroom");
		Student.restroomed=true;
		}
		bathroom=0;
	}
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
	
}
