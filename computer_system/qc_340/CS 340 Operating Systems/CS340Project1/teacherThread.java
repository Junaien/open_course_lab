/*
 * Teacher thread, simulates the behavior of the teacher.
 */

import java.util.LinkedList;
import java.util.Queue;

public class teacherThread extends Thread{
	
	private timerThread timer;
	private static long time = System.currentTimeMillis();
	private int random;
	
	private int capacity = 10;
	private int studentCount = 0;
	//Queues for the question stored here
	private static Queue<String> queueA;
	private static Queue<Thread> queueB;

	private static int answeredA;
	private static int answeredB;
	
	
	teacherThread(timerThread timer){
		
		setName("Teacher");
		this.timer = timer;
		queueA = new LinkedList<String>();
		queueB = new LinkedList<Thread>();
		
	}//constructor
	
	public void setCapacity(int capacity){ this.capacity = capacity; }
	

	public  void msg(String m){
		
		System.out.println(getName()+" [" + (System.currentTimeMillis()-time)+"] "+": ");
		System.out.println(m);
		
	}//msg
	
	//Methods to add questions to the queue.
	public synchronized void addQueueA(String question){ queueA.add(question); }
	
	public synchronized void addQueueB(Thread student){ queueB.add(student); }	
	/*
	 * Counter for students when to be always
	 * less than or equal capacity or else
	 * they are kicked out.
	 */
	public synchronized boolean isSeatsAvailable(){
		
		studentCount++;
		if(studentCount<=capacity){
			return true;
		}
		return false;

	}	
	/*
	 * Simulates teacher typing E-mail
	 * to reply back to students.
	 */
	private void serve() {
		
		random = (int) ((Math.random()*100)+50);
		try {
			sleep(random);
		} 
		catch (InterruptedException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/*
	 * Flow chart of timer thread
	 */
	public void run(){
		
		goWork();
		answerQuestionA();
		answerQuestionB();
	}
	
	
	private void goWork() {
		
		try {
			sleep(850);
		} 
		catch (InterruptedException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println();
		msg("has arrived.");
			
	}	
	/*
	 * This simulates teacher giving time
	 * for answering questions A. FCFS 
	 * while the queue is not empty.
	 */
	private void answerQuestionA() {
		while(timer.emailSession()){	
				if(!queueA.isEmpty()){	
					serve();
					msg("Replied to " + queueA.remove());
					answeredA++;
				}
		}
		System.out.println();
		msg("answered " + answeredA + " questionsA.");
		
	}
	/*
	 * This simulates teacher giving time 
	 * for answering questions B. FCFS
	 * while queue is not empty.
	 */
	private void answerQuestionB() {
		
		while(timer.chatSession()){
				if(!queueB.isEmpty()){
					if(queueB.isEmpty()){
						throw new IllegalArgumentException("must not me empty to proceed!"); // trying to catch error in timing if it happens.
					}
					serve();
					studentThread currentStudent = (studentThread) queueB.remove();		
					msg("Replied to "+ currentStudent.getName() + ".");
					currentStudent.setIsAnswered(true);
					answeredB++;
					
				}
		}
		System.out.println();
		msg("answered " + answeredB + " questionsB.");
		msg("is now leaving.");
			
	}

}
