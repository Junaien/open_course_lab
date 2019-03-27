import java.util.Random;

/**
 * This class is the main base for the students in the simulation.
 * Each student will perform the same task basically which is
 * try to get into the lab, think and send their questions if they have 
 * any, and if they wish to speak with the professor they will wait
 * until they receive the signal that the professor is ready to chat.
 * Afterwards, students will wait until Professor signals the lab to allow
 * students to leave.
 *  
 * @author Rossey Charleston
 *
 */
public class Student implements Runnable {
	private String name;
	private Random rand;
	private final int questions_A = 4;
	private final int questions_B = 3;
	private Thread t;
	private Question[] questionsA;
	private Question[] questionsB;
	private boolean insideLab;
	private boolean okToLeave;
	private boolean wantsChatSession;
	private boolean turnToChat;
	private boolean readyForChat;

	/**
	 * The constructor for the student object which will
	 * take in a single parameter ie name of the student and
	 * initialize important variables for the runtime of the
	 * thread.
	 * 
	 * @param n Pass in the name of the student.
	 */
	public Student(String n) {
		name = n;
		rand = new Random();
		insideLab = false;
		okToLeave = false;
		readyForChat = false;
		turnToChat = false;
		t = new Thread(this, n);
	}

	/**
	 * Method that will return the name of the Student object
	 * 
	 * @return Name of the Student/Thread 
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method to be used to change/set the name of the student
	 * if needed.
	 * 
	 * @param n Name passed in to change/update name of the Student
	 */
	public void setName(String n) {
		name = n;
	}

	/**
	 * This method will be utilized by the Lab thread which will mark
	 * if the Student thread was able to enter the lab before it reached
	 * the capacity. If the student entered the lab the variable insideLab
	 * will be automatically set to true. 
	 */
	public void setInLab() {
		insideLab = true;
	}

	/**
	 * This method will be used by the Professor thread in which the Professor
	 * can signal the current Student thread that they may chat with the Professor
	 * for their question B.
	 * 
	 * @param x Pass in TRUE if the student can now talk to the Professor.
	 */
	public void setTurnToChat(boolean x) {
		turnToChat = x;
	}

	/**
	 * Method used by the Lab thread which will signal the student if they are
	 * now allowed to leave the lab.
	 */
	public void okToLeave() {
		okToLeave = true;
	}

	/**
	 * This method is used by the Student Thread to inform the Professor Thread that
	 * they are now free to talk to the Professor. The student could have been busy sending
	 * or thinking about their question when it reaches their turn to speak with the
	 * Professor.
	 * 
	 * @return True if Student is free, False otherwise
	 */
	public boolean readyToChat() {
		return readyForChat;
	}

	/**
	 * Method in which will used if the student thread desires to speak with the professor
	 * for their question B.
	 * 
	 * @return True if student wants to speak to professor, false otherwise.
	 */
	public boolean wantsToChat() {
		return wantsChatSession;
	}

	/**
	 * This method will simulate the student speaking with the professor during the
	 * chat session. Student will state how many question they have which can be at most
	 * 3 but at least one since wanting to chat means they have at least one question to ask.
	 * The student will also inform the professor of their last question when reached. While
	 * the student is being answered they will sleep until thier question was answered.
	 */
	private void chatWithProf() {

		msg(" has " + questionsB.length
				+ " type 'B' questions to ask Professor...");
		
		//System.out.println("Student " + name + " has " + questionsB.length
		//		+ " type 'B' questions to ask Professor...");

		for (int i = 0; i < questionsB.length; i++) {

			questionsB[i] = new Question('B', i + 1, this);

			if (i != questionsB.length - 1) {
				
				msg(" asking a new question");
				
				//System.out
				//.println("Student " + name + " asking a new question");
				
				Professor.askedQuestion(questionsB[i], false);
			} else {
				
				msg(" informed Professor of last question...");
				
				//System.out.println("Student " + name
				//		+ " informed Professor of last question...");
				
				Professor.askedQuestion(questionsB[i], true);
			}

			while (!questionsB[i].getQAnswered()) {
				try {
					Thread.sleep(rand.nextInt(2000));
				} catch (InterruptedException e) {
					System.out.println(e);
				}
			}
		}
	}

	/**
	 * This method is used to email the professor their type A question. The priority
	 * of the thread will increase so that the student can send their question before others
	 * and will call the Professor object method receieveQestion which will queue their
	 * question in the prof's email.
	 * 
	 * @param question The current question they are e-mailing the professor.
	 */
	public void sendQuestion(Question question) {

		msg(" is sending a new question to the Professor...");
		
		//System.out.println("Student: " + name
		//		+ " is sending a new question to the Professor...");

		t.setPriority(Thread.MAX_PRIORITY);

		try {
			Thread.sleep(rand.nextInt(5000));
		} catch (InterruptedException e) {
			System.out.println(e);
		}

		Professor.receiveQuestionA(question);

		if (t.getPriority() == Thread.MAX_PRIORITY) {
			t.setPriority(Thread.NORM_PRIORITY);
		}

	}

	/**
	 * Method that will return the current Student thread.
	 * @return
	 */
	public Thread getThread() {
		return t;
	}

	/**
	 * Method with the intention to be used to sort students by name.
	 * 
	 * @param x Student object passed in
	 * @return int value from the comparison of the Student's names.
	 */
	public int compareTo(Student x){
		if(name.compareTo(x.getName()) < 0){
			return -1;
		}
		
		else if(name.compareTo(x.getName()) == 0){
			return 0;
		}
		
		else return 1;
	}
	
	/**
	 * Method that will simulate the student thinking about their question A's.
	 * The student thread will yield at this point.
	 */
	public void idle() {
		
		msg(" is thinking about their question(s)...");
		
		//System.out.println("Student: " + name
		//		+ " is thinking about their question(s)...");
		
		Thread.yield();
	}

	/**
	 * Method to print out tracking statements for Student.
	 * 
	 * @param m String that contains the most current tracking info regarding 
	 * current student.
	 */
	private void msg(String m) { 
		System.out.println("[" + Main.age()+ "] Student: " + name + " " + m); 
	}
	
	/**
	 * Method the will be used to start the Student thread. Also at this point beforehand
	 * the student will think about if they want to speak with the professor before heading
	 * to the lab.
	 */
	public void start() {

		wantsChatSession = rand.nextBoolean();

		msg(" arrived in front of Lab door");
		
		//System.out
		//		.println("Student: " + name + " arrived in front of Lab door");
		t.start();
	}

	/**
	 * This is the heart of the Student thread which handles the purpose of this
	 * Student class.
	 */
	@Override
	public void run() {

		while (!Lab.labIsOpen() && !Lab.sessionActive()) {
			try {
				Thread.sleep(rand.nextInt(5000));
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}// Busy Wait

		msg(" is trying to get into lab...");
		
		//System.out
		//		.println("Student: " + name + " is trying to get into lab...");

		Lab.enteredLab(this);

		if (this.insideLab) {
			idle();
			int questionID = 1;
			questionsA = new Question[rand.nextInt(questions_A + 1)];

			if (wantsChatSession) {
				questionsB = new Question[rand.nextInt(questions_B) + 1];
				Professor.waitingForSession(this);
			}

			msg(" has " + questionsA.length
					+ " type 'A' question(s) to ask.");
			
			//System.out.println("Student: " + name + " has " + questionsA.length
			//		+ " type 'A' question(s) to ask.");

			for (int i = 0; i < questionsA.length; i++) {
				questionsA[i] = new Question('A', questionID++, this);
				sendQuestion(questionsA[i]);
			}

			if (wantsChatSession) {

				msg(" wants to chat with Professor...");
				
				//System.out.println("Student " + name
				//		+ " wants to chat with Professor...");

				while (!Professor.chatSessionActive()) {
					try {
						Thread.sleep(rand.nextInt(1000));
					} catch (InterruptedException e) {
						System.out.println(e);
					}
				}// Busy Wait

				while (!turnToChat) {
					try {
						Thread.sleep(rand.nextInt(5000));
					} catch (InterruptedException e) {
						System.out.println(e);
					}
				}// Busy Wait

				readyForChat = true;

				chatWithProf();

				msg(" finished with Prof...");
				
				//System.out
				//		.println("Student " + name + " finished with Prof...");

			} else
				msg(" did not want to speak with the Professor");
				//System.out.println("Student " + name
				//		+ " did not want to speak with the Professor");

			msg(" is surfing internet until time to leave....");
			
			//System.out.println("Student " + name
			//		+ " is surfing internet until time to leave....");

			while (!okToLeave) {
				try {
					Thread.sleep(rand.nextInt(5000));
				} catch (InterruptedException e) {
					//System.out.println(e);
				}
			}// Busy Wait

		} else
			msg(" could not get into lab, Student going home.");
			
		//System.out.println("Student: " + name
		//			+ " could not get into lab, Student going home.");
		
		msg(" went home...");

		//System.out.println("Student: " + name + " went home...");
	}
}
