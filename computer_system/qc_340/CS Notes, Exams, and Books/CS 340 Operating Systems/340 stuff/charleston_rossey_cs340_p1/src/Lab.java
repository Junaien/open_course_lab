import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Vector;

/**
 * This lab will be the container and the sorter for Students entering the lab.
 * Students will enter the lab until capacity and will be sorted at the end of 
 * session when signal is received from the professor.
 * 
 * @author Rossey Charleston
 *
 */
public class Lab implements Runnable {

	private final static int capacity = 15;
	private static int studentsInLab;
	private static boolean labOpen;
	private Thread t;
	private Random rand;
	private static String labName;
	private static boolean activeSession;
	private static final int CLASS_TIME = 60000;
	//private static Vector<Student> exitQueue;
	private static Map<Student, String> exitSequence;
	private static boolean letStudentsLeave;

	/**
	 * Constructor for the Lab object which will initialize important variables
	 * needed for the runtime of this thread.
	 * 
	 * @param labName Name of the lab
	 */
	public Lab(String labName) {
		this.labName = labName;
		rand = new Random();
		t = new Thread(this, labName);
		activeSession = false;
		letStudentsLeave = false;
	    //exitQueue = new Vector<Student>();
		exitSequence = new HashMap<Student, String>();
		studentsInLab = 0;
	}

	/**
	 * Method used by student threads in which they can only enter the lab if
	 * the lab is open.
	 * 
	 * @return True is lab is open for session, false otherwise
	 */
	public static synchronized boolean labIsOpen() {
		return labOpen;
	}

	/**
	 * Method that will indicate if the lab/online session is active or not.
	 * Purpose was so that students who tried to enter the lab once it was full 
	 * could get out of a starvation problem.
	 * 
	 * @return True is online session is active, false otherwise
	 */
	public synchronized static boolean sessionActive() {
		return activeSession;
	}

	/**
	 * Method that returns the name of the lab object.
	 * 
	 * @return Name of the lab
	 */
	public static String getLabName() {
		return labName;
	}

	/**
	 * Method that will indicate if the lab is full or not.
	 * 
	 * @return True if full, false otherwise
	 */
	public synchronized static boolean labIsFull() {
		return studentsInLab == capacity;
	}

	/**
	 * Method to return how long the online session should be active for.
	 * 
	 * @return Final Long for class_time
	 */
	public static int getClassTime() {
		return CLASS_TIME;
	}

	/**
	 * Method used to sort and release students in descending order from the lab
	 */
	private void sortStudents(){
		ArrayList<Entry<Student, String>> stu_inorder = sortStudentsByName(exitSequence);
		
		for(int i = stu_inorder.size() - 1; i >= 0; i--){
			if (stu_inorder.get(i).getKey().getThread().isAlive()) {
				try {
					stu_inorder.get(i).getKey().okToLeave();
					stu_inorder.get(i).getKey().getThread().interrupt();
					stu_inorder.get(i).getKey().getThread().join();
				} catch (InterruptedException e) {
					System.out.println(e);
				}
			}
		}
		
	}
	
	/**
	 * The heart of the sorting of students.
	 * 
	 * @param exitSequence2 A map of the students
	 * @return returns the sorted students
	 */
	private ArrayList<Entry<Student, String>> sortStudentsByName(Map<Student, String> exitSequence2){
		ArrayList<Map.Entry<Student, String>> l = new ArrayList<Entry<Student, String>>(
				((Map<Student, String>) exitSequence2).entrySet());
		
		Collections.sort(l, new Comparator<Map.Entry<Student, String>>() {
			public int compare(Map.Entry<Student, String> o1,
					Map.Entry<Student, String> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});
		return l;
	}
	
	/**
	 * Method used my Professor thread to signal that students can now leave the lab
	 * since the professor is done speaking with students.
	 */
	public synchronized static void signalToRelease() {
		letStudentsLeave = true;
	}

	/**
	 * Method to printing tracking statements regarding the Lab.
	 * 
	 * @param m String of the current action of the lab.
	 */
	private static void msg(String m) { 
		System.out.println("[" + Main.age()+ "] Lab: " + labName + " " + m);
	}
	
	/**
	 * Method to be used by the Student threads in which a student enters the lab
	 * and gets added into the exitSequence map to be sorted at the end. If the student
	 * was able to actually enter then it is marked that they are in the lab.
	 * 
	 * @param x Student trying to enter the lab
	 */
	public synchronized static void enteredLab(Student x) {

		if (studentsInLab < capacity) {
			
			msg("Student: " + x.getName() + " entered lab "
					+ labName + ".");
			
			//System.out.println("Student: " + x.getName() + " entered lab "
			//		+ labName + ".");
			
			++studentsInLab;
			//exitQueue.add(x);
			exitSequence.put(x, x.getName());
			x.setInLab();

			if (studentsInLab == capacity) {
				
				msg("Lab is full, Student: " + x.getName()
						+ " closing the door...");
				
				//System.out.println("Lab is full, Student: " + x.getName()
				//		+ " closing the door...");
				
				closeDoor();
			}
		}
	}

	/**
	 * If the last student who enter is the capacity of the lab, then they close the
	 * door preventing any more students from entering.
	 */
	public static void closeDoor() {
		labOpen = false;
	}

	/**
	 * Method to start the lab thread.
	 */
	public void start() {
		
		msg(" is about start its session...");
		
		//System.out.println(labName + " is about start its session...");
		
		t.start();
	}

	/**
	 * The heart of the lab thread which handles the purpose of this class.
	 */
	@Override
	public void run() {
		
		msg("Lab is now open for session!");
		
		//System.out.println("Lab opened at time: " + Main.age());
		
		labOpen = true;
		activeSession = true;

		/*
		while (studentsInLab < capacity) {
			try {
				Thread.sleep(rand.nextInt(5000));
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		} // busy wait

		*/
		
		while (!letStudentsLeave) {
			try {
				Thread.sleep(rand.nextInt(5000));
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		} // Waits for signal
		
		//HashMapIterator iterator
		
		sortStudents();
		
		//for (int i = 0; i < exitQueue.size(); i++) {
		//	if (exitQueue.get(i).getThread().isAlive()) {
		//		try {
		//			exitQueue.get(i).okToLeave();
		//			exitQueue.get(i).getThread().join();
		//		} catch (InterruptedException e) {
		//			System.out.println(e);
		//		}
		//	}
		//}

		msg(" has closed for the day!");
		
		//System.out.println(labName + " has closed for the day at time " + Main.age());
		
		labOpen = false;
		System.out.println("Total run time: " + Main.age()/1000 + " secs");
	}
}
