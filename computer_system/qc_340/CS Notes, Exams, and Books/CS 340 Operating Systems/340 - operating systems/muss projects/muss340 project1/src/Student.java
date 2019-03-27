import java.util.Random;


public class Student extends Thread {
	
	private static boolean restroomed = false;
	public static String name;
	boolean gotoclass1 = false;
	boolean gotoclass2 = false;
	boolean gotoclass3 = false;
	boolean gotoclass4 = false;
	boolean gotoclass5 = false;
	
	
	public static long time = System.currentTimeMillis();
	public void msg(String m) {
	System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+":"+m);
	}
	
	public Student(int i){	
		setName("Student " + i);
	}

	
	public void run(){
	
	Random randomGenerator = new Random();
    int snooze = randomGenerator.nextInt(10000);
    int gotobathroom = randomGenerator.nextInt(3000);
	
	
	try {
		Student.sleep(snooze);
		System.out.println(Project1.age() + " " + getName() + " has woken up");
		
		while(Project1.bathroom==1){
		//System.out.println(getName() + "is waiting for the bathroom");
		}
		
		while(Project1.bathroom==0){//bathroom is empty
		System.out.println(Project1.age() + " " + getName() + " has gone into the bathroom");
		Project1.bathroom=1; //bathroom is now occupied
		Student.sleep(gotobathroom);
		System.out.println(Project1.age() + " " + getName() + " has left the bathroom");
		Student.restroomed=true;
		
		}
		Project1.bathroom=0;
		
		if(Project1.class1==-1){
			System.out.println(Project1.age() + " " + getName() + "is waiting for class1 to start");
		}
		while(Project1.class1==-1){
			
			//busywait for class
		}
		
		if(Project1.class1==1 || Project1.class1==2 ){
			System.out.println(Project1.age() + " " + getName() + "missed class1 fml");
			//missed class
		}
		
		if(Project1.class1==0){
			System.out.println(Project1.age() + " " + getName() + "is in class1");
			this.gotoclass1=true;
		}
		while(Project1.class1== 0 ||Project1.class1== 1 && this.gotoclass1==true){
		
		}
		
		
		if(this.gotoclass1==true){
			System.out.println(Project1.age() + " " + getName() + " went to class1 bitches!");
		}
		
		if(this.gotoclass1==false){
			System.out.println(Project1.age() + " " + getName() + " is running errands ... fail");
			Student.sleep(snooze);
		}
		//class 2 stuff
		if(Project1.class2==-1){
			System.out.println(Project1.age() + " " + getName() + "is waiting for class2 to start");
		}
		while(Project1.class2==-1){
			
			//busywait for class
		}
		
		if(Project1.class2==1 || Project1.class2==2 ){
			System.out.println(Project1.age() + " " + getName() + "missed class2 fml");
			//missed class
		}
		
		if(Project1.class2==0){
			System.out.println(Project1.age() + " " + getName() + "is in class2");
			this.gotoclass2=true;
		}
		while(Project1.class2== 0 ||Project1.class2== 1 && this.gotoclass2==true){
		
		}
		
		
		if(this.gotoclass2==true){
			System.out.println(Project1.age() + " " + getName() + " went to class2 bitches!");
		}
		
		if(this.gotoclass2==false){
			System.out.println(Project1.age() + " " + getName() + " is running errands ... fail");
			Student.sleep(snooze);
		}
		
	} 
	
	catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
	
}
