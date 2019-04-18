import java.util.*;


public class Student extends Thread {
	
	private boolean restroomed = false;
	public static String name;
	String gotoclass1 = "false"; //variable to keep track of what classes the student attended
	String gotoclass2 = "false"; //variable to keep track of what classes the student attended
	String gotoclass3 = "false"; //variable to keep track of what classes the student attended
	String gotoclass4 = "false"; //variable to keep track of what classes the student attended
	String gotoclass5 = "false"; //variable to keep track of what classes the student attended
	String report[] = {gotoclass1,gotoclass2,gotoclass3,gotoclass4,gotoclass5};
	int classCount =0; // tracks the amount of classes each student attended
	int turn; // turn to goto the bathroom
	//boolean waiting = false;
	//boolean First = true;
	
	Random randomGenerator = new Random();
    int snooze = randomGenerator.nextInt(10000); //random value for sleep
    int fun = randomGenerator.nextInt(35000); //random value for after class
    int gotobathroom = randomGenerator.nextInt(5000); // random value for time inside of the bathroom
	
	public void afterclass(){
		setPriority(10); // takes priority over all other threads and completes method.
		classCount++;
	msg(" is having some fun");
	
	try {
		Student.sleep(fun);
		
		setPriority(5);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	public static long time = System.currentTimeMillis();
	public void msg(String m) {
	System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+":"+m);
	}
	
	public Student(int i){	
		setName("Student " + i);
	}

	public synchronized void getTurn(){
		
		this.turn = Project1.bathroomturn; // sets the Student's turn for the bath room
		Project1.bathroomturn++; //increments turn to be assigned for next student
	}
	
	public void gotoBathroom(){
		while(Project1.bathroom==1){// while occupied
		msg(" is waiting for the bathroom");
		}
		
		while(Project1.bathroom==0){//bathroom is empty
		msg(" has gone into the bathroom");
		Project1.bathroom=1; //bathroom is now occupied
		msg(" has left the bathroom");
		this.restroomed=true;
		}
	}
   //Vector<Student> fcfs = new Vector<Student>();
   
   
    
	public void run(){
		
		
	try {
		Student.sleep(snooze); // student sleeps for random time
		msg(" has woken up");
		
		getTurn(); // assigns each student a priority
		
	//	if(Project1.bathroom==1 || fcfs.size()>0 || First==false){
	//		synchronized (this){
	//		  fcfs.addElement(this);
	//		  msg(" is waiting for the bathroom");
			  
	//		  try {
	//				wait();
	//			} catch (InterruptedException e){
	//			}
				
			  
	//	 }}
	//	else{
	//		msg(" has gone into the bathroom");
	//		First=false;
	//		Project1.bathroom =1;
	//		this.restroomed=true;
	//		Student.sleep(gotobathroom);
		
	//	}
	//		if(this.restroomed==false){
	//		synchronized (this) {
	//			Student temp = fcfs.elementAt(0);
	//			msg("notify");
	//			notify();
	//			//fcfs.removeElementAt(0);
	//			msg("exits the bathroom");
	//		}
	//		
	//	}
	//		else{
	//			Project1.bathroom =1;
	//			msg(" exits the bathroom");
	//		}
		yield();
		
		//}
		Project1.bathroom=0; // sets bathroom to unoccupied
		
		if(Project1.class1==-1){ // if teacher has not yet started class bw
			msg(" is waiting for class1 to start");
		}
		while(Project1.class1==-1){
			
			//busywait for class
		}
		
		if(Project1.class1==1 || Project1.class1==2 ){ 
			msg(" missed class1");
			//missed class
		}
		
		if(Project1.class1==0){ // if teacher has opened class enter class
			msg(" is in class1");
			gotoclass1="true";
		}
		while(Project1.class1== 0 ||Project1.class1== 1 && gotoclass1=="true"){
		}
		
		if(gotoclass1=="true"){ // if student has attended class they can now have fun
			afterclass();
		}
		
		if(gotoclass1=="false"){// if student did not goto class run errands
			msg(" is running errands");
			Student.sleep(snooze);
		}
		//class 2 stuff
		if(Project1.class2==-1){// if teacher has not yet started class bw
			msg(" is waiting for class2 to start");
		}
		while(Project1.class2==-1){// if teacher has not yet started class bw	
			//busywait for class
		}
		
		if(Project1.class2==1 || Project1.class2==2 ){ // if class is already in session or class has ended miss class
			msg(" missed class2");
			//missed class
		}
		
		if(Project1.class2==0){ // if class is open enter
			msg(" is in class2");
			gotoclass2="true";
		}
		// if teacher has opened class enter class
		while(Project1.class2== 0 ||Project1.class2== 1 && gotoclass2=="true"){
		}
		
		if(gotoclass2=="true"){
			afterclass(); //students have some fun
		}
		
		if(gotoclass2=="false"){ // if student did not goto class, 
			msg(" is running errands ... ");
			Student.sleep(snooze);
		}
		//class 3 stuff
		if(Project1.class3==-1){ // if it is going to enter BW it prints
			msg(" is waiting for class3 to start");
		}
		
		while(Project1.class3==-1){// if teacher has not yet started class bw
		}
		
		if(Project1.class3==1 || Project1.class3==2 ){ // if class is in session or has ended run errands
			msg(" missed class3");
			//missed class
		}
		
		if(Project1.class3==0){ //if class is open goto class
			msg(" is in class3");
			gotoclass3="true";
		}
		//while loop to hold students in class while class is in session and reject late students
		while(Project1.class3== 0 ||Project1.class3== 1 && gotoclass3=="true"){
		}
		
		if(gotoclass3=="true"){// if attended have fun
			afterclass();
		}
		
		if(gotoclass3=="false"){ // if missed class run errands - random sleep
			msg(" is running errands");
			Student.sleep(snooze);
		}
		
		//class 4 stuff
		if(Project1.class4==-1){ // Wait for teacher to initiate the start of class
			msg(" is waiting for class4 to start");
		}
		
		while(Project1.class4==-1){// if teacher has not yet started class bw	
			//busywait for class
		}
		
		if(Project1.class4==1 || Project1.class4==2 ){// if class is in session or has ended run errands
			msg(" missed class4");
			//missed class
		}
		
		if(Project1.class4==0){// class is open for students to enter
			msg(" is in class4");
			gotoclass4="true";
		}
		
		//while loop to hold students in class while class is in session and reject late students
		while(Project1.class4== 0 ||Project1.class4== 1 && gotoclass4=="true"){
		}
		
		if(gotoclass4=="true"){ // if attended have fun
			afterclass();
		}
		
		if(gotoclass4=="false"){
			msg(" is running errands");
			Student.sleep(snooze);
		}
		
		//class 5 start
		if(Project1.class5==-1){// if teacher has not yet started class bw
			msg(" is waiting for class5 to start");
		}
		
		while(Project1.class5==-1){// if teacher has not yet started class bw
			//busywait for class
		}
		
		if(Project1.class5==1 || Project1.class5==2 ){// if class is in session or has ended run errands
			msg(" missed class5");
			//missed class
		}
		
		if(Project1.class5==0){ // class is open for students to enter
			msg(" is in class5");
			gotoclass5="true";
		}
		
		//while loop to hold students in class while class is in session and reject late students
		while(Project1.class5== 0 ||Project1.class5== 1 && gotoclass5=="true"){
		}
		
		if(gotoclass5=="true"){// if attended have fun
			afterclass();
		}
		
		if(gotoclass5=="false"){
			msg(" is running errands");
			Student.sleep(snooze);
		}//end class5
		
		yield();
		//print out the status report for each student
		System.out.println(getName() + " Classes Attended:" + " " +classCount +  " class1" + " "  + gotoclass1 + " " + " class2 " + gotoclass2 + " " + "class3 " + gotoclass3 + " " + "class4 " + gotoclass4 + " " + " class5 " +gotoclass5);	    	
	 	   
		this.join();
	}
	
	catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
}
