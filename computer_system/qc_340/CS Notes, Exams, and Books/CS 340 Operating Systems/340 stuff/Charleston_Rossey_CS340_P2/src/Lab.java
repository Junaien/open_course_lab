import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Semaphore;

/**
 * This lab will be the container and the sorter for Students entering the lab.
 * Students will enter the lab until capacity and will be sorted at the end of
 * session when signal is received from the professor.
 * 
 * @author Rossey Charleston
 * 
 */
public class Lab implements Runnable {

	private static int capacity;
	private static int studentsInLab;
	private static boolean labOpen;
	private Thread t;
	//private Random rand;
	private static String labName;
	private static boolean activeSession;
	private static final int CLASS_TIME = 45000;
	// private static Vector<Student> exitQueue;
	private static Map<Student, String> exitSequence;
	//private static boolean letStudentsLeave;
	private static Semaphore canEnterMutex;
	private static Semaphore tryToEnterLab;
	private static Semaphore releaseStudents;

	/**
	 * Constructor for the Lab object which will initialize important variables
	 * needed for the runtime of this thread.
	 * 
	 * @param labName
	 *            Name of the lab
	 */
	public Lab(String lName, int cap) {
		labName = lName;
		capacity = cap;
		//rand = new Random();
		t = new Thread(this, labName);
		activeSession = false;
		//letStudentsLeave = false;
		// exitQueue = new Vector<Student>();
		exitSequence = new HashMap<Student, String>();
		studentsInLab = 0;
		canEnterMutex = new Semaphore(1, true);
		tryToEnterLab = new Semaphore(0, false);
		releaseStudents = new Semaphore(0, true);
	}

	/**
	 * Method used by student threads in which they can only enter the lab if
	 * the lab is open.
	 * 
	 * @return True is lab is open for session, false otherwise
	 */
	public static boolean labIsOpen() {
		return labOpen;
	}

	/**
	 * Method that will indicate if the lab/online session is active or not.
	 * Purpose was so that students who tried to enter the lab once it was full
	 * could get out of a starvation problem.
	 * 
	 * @return True is online session is active, false otherwise
	 */
	public static boolean sessionActive() {
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
	public static boolean labIsFull() {
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
	private void sortStudents() {
		ArrayList<Entry<Student, String>> stu_inorder = sortStudentsByName(exitSequence);

		for (int i = stu_inorder.size() - 1; i >= 0; i--) {
			if (stu_inorder.get(i).getKey().getThread().isAlive()) {
				try {
					stu_inorder.get(i).getKey().releaseStudent();
					//stu_inorder.get(i).getKey().getThread().interrupt();
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
	 * @param exitSequence2
	 *            A map of the students
	 * @return returns the sorted students
	 */
	private ArrayList<Entry<Student, String>> sortStudentsByName(
			Map<Student, String> exitSequence2) {
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
	 * Method to prevent students from being able to leave until the lab
	 * thread signals each student thread to leave.
	 */
	private void lockLabThread(){
		try {
			releaseStudents.acquire();
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}

	/**
	 * Method used my Professor thread to signal that students can now leave the
	 * lab since the professor is done speaking with students.
	 */
	public static void signalToRelease() {
		releaseStudents.release();
	}

	/**
	 * Method to printing tracking statements regarding the Lab.
	 * 
	 * @param m
	 *            String of the current action of the lab.
	 */
	private static void msg(String m) {
		System.out.println("[" + Main.age() + "] Lab: " + labName + " " + m);
	}

	/**
	 * This method will utilize the semaphore queue which student threads will 
	 * wait until the lab opens to be able to enter inside. One the lab opens
	 * it will release all the locks so that students can then open the lab.
	 */
	public static void waitQueue(){
		try {
			tryToEnterLab.acquire();
		} catch (InterruptedException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to be used by the Student threads in which a student enters the
	 * lab and gets added into the exitSequence map to be sorted at the end. If
	 * the student was able to actually enter then it is marked that they are in
	 * the lab.
	 * 
	 * @param x
	 *            Student trying to enter the lab
	 */
	public static void enteredLab(Student x) {

		try {
			canEnterMutex.acquire();
		} catch (InterruptedException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
		if (studentsInLab < capacity) {

			msg("Student: " + x.getName() + " entered lab " + labName + ".");

			++studentsInLab;
			// exitQueue.add(x);
			exitSequence.put(x, x.getName());
			x.setInLab();

			if (studentsInLab == capacity) {
				msg("Lab is full, Student: " + x.getName()
						+ " closing the door...");
				closeDoor();
			}
		}
		
		canEnterMutex.release();
	}

	/**
	 * If the last student who enter is the capacity of the lab, then they close
	 * the door preventing any more students from entering.
	 */
	public static void closeDoor() {
		labOpen = false;
	}

	/**
	 * Method to start the lab thread.
	 */
	public void start() {
		msg(" is about start its session...");
		t.start();
	}

	/**
	 * The heart of the lab thread which handles the purpose of this class.
	 */
	@Override
	public void run() {

		msg("Lab is now open for session!");

		labOpen = true;
		activeSession = true;
		
		tryToEnterLab.release(tryToEnterLab.getQueueLength());
		
		lockLabThread();
		
		sortStudents();
		
		msg(" has closed for the day!");

		labOpen = false;
		System.out.println("Total run time: " + Main.age() / 1000 + " secs");
	}
}
