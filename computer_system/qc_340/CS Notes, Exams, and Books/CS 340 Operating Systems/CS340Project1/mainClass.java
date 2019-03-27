/*
 * BARRY S. BALONDO
 * CS340 
 * PROJECT#1
 */
public class mainClass {
	//Threads
	public static studentThread[] student;
	public static teacherThread teacher;
	public static timerThread timer;
	
	//Default Values
	public static int capacity = 10;
	public static int num_Students = 15;
	public static int num_questions_A = 10;
	public static int num_questions_B = 10;
	
	public static void main(String[] args){
		
		//Command line inputs
		if(args.length > 0){
			
			capacity = Integer.parseInt(args[0]);
			num_Students = Integer.parseInt(args[1]);
			num_questions_A = Integer.parseInt(args[2]);
			num_questions_B =  Integer.parseInt(args[3]);
			
		}
		/*
		 * Initialize for timer, teacher and student.
		 */
		timer = new timerThread();
		timer.start();
		
		teacher = new teacherThread(timer);
		teacher.setCapacity(capacity);
		teacher.start();
		
		student = new studentThread[num_Students];
		
		for(int i=0; i<num_Students; i++){
			
			student[i] = new studentThread(i+1, timer, teacher);
			student[i].setQuestionsA(num_questions_A);
			student[i].setQuestionsB(num_questions_B);
			student[i].setWillSleep(i*10);
			student[i].start();
			
		}
		/*
		 * A busy wait that guarantees no other hit for sleeps are
		 * interrupted. Ends at exactly when all questions are 
		 * asked for question B, including its calculated random 
		 * time after which threads sleeping if they are in browsing mode
		 * are interrupted.
		 */
		while(timer.surfEnd()){
			//Busy wait
		}
		/*
		 * Interrupts threads that are sleeping(browsing the net).
		 */
		for(int i=0; i<num_Students; i++){
				if(student[i].getState().toString()=="TIMED_WAITING"){
					student[i].interrupt();
				}
					
			
		}
	
		//Student threads calls join if it is still alive
		for(int i=0; i<num_Students; i++){
			if(student[i].isAlive()){
				
				try {		
					student[i].join();
				} 
				catch (InterruptedException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		System.out.println();
		System.out.println("Simulation Done.");
	
	}
}
