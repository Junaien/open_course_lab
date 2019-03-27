import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * This class is the main base for the students in the simulation. Each student
 * will perform the same task basically which is try to get into the lab, think
 * and send their questions if they have any, and if they wish to speak with the
 * professor they will wait until they receive the signal that the professor is
 * ready to chat. Afterwards, students will wait until Professor signals the lab
 * to allow students to leave.
 * 
 * @author Rossey Charleston
 * 
 */
public class Student implements Runnable {
	private String name;
	private Random rand;
	private final int questions_A;
	private final int questions_B;
	private Thread t;
	private Question[] questionsA;
	private Question[] questionsB;
	private boolean insideLab;
	//private boolean okToLeave;
	private boolean wantsChatSession;
	private boolean turnToChat;
	private boolean readyForChat;
	private Semaphore canLeave;
	private Semaphore canChat;
	private static Semaphore waitToBeServed;

	/**
	 * The constructor for the student object which will take in a single
	 * parameter ie name of the student and initialize important variables for
	 * the runtime of the thread.
	 * 
	 * @param n
	 *            Pass in the name of the student.
	 */
	public Student(String n, int question_A, int question_B) {
		name = n;
		questions_A = question_A;
		questions_B = question_B;
		rand = new Random();
		insideLab = false;
		//okToLeave = false;
		readyForChat = false;
		turnToChat = false;
		canLeave = new Semaphore(0, false);
		canChat = new Semaphore(0, false);
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
	 * Method to be used to change/set the name of the student if needed.
	 * 
	 * @param n
	 *            Name passed in to change/update name of the Student
	 */
	public void setName(String n) {
		name = n;
	}

	/**
	 * This method will be utilized by the Lab thread which will mark if the
	 * Student thread was able to enter the lab before it reached the capacity.
	 * If the student entered the lab the variable insideLab will be
	 * automatically set to true.
	 */
	public void setInLab() {
		insideLab = true;
	}

	/**
	 * This method will be used by the Professor thread in which the Professor
	 * can signal the current Student thread that they may chat with the
	 * Professor for their question B.
	 * 
	 * @param x
	 *            Pass in TRUE if the student can now talk to the Professor.
	 */
	public void setTurnToChat(boolean x) {
		turnToChat = x;
		canChat.release();
	}

	/**
	 * Method used by the Lab thread which will signal the student if they are
	 * now allowed to leave the lab.
	 */
	/*
	public void okToLeave() {
		okToLeave = true;
	}
	*/

	/**
	 * This method is used by the Student Thread to inform the Professor Thread
	 * that they are now free to talk to the Professor. The student could have
	 * been busy sending or thinking about their question when it reaches their
	 * turn to speak with the Professor.
	 * 
	 * @return True if Student is free, False otherwise
	 */
	public boolean readyToChat() {
		return readyForChat;
	}

	/**
	 * Method in which will used if the student thread desires to speak with the
	 * professor for their question B.
	 * 
	 * @return True if student wants to speak to professor, false otherwise.
	 */
	public boolean wantsToChat() {
		return wantsChatSession;
	}

	/**
	 * The current student thread waits until Professor thread is answering
	 * their current question. Once the professor answers the question then the
	 * student thread is allowed to move on.
	 */
	public void isServed() {
		waitToBeServed.release();
	}

	/**
	 * This method will simulate the student speaking with the professor during
	 * the chat session. Student will state how many question they have which
	 * can be at most 3(default) or value of questions_B but at least one since
	 * wanting to chat means they have at least one question to ask. The student
	 * will also inform the professor of their last question when reached. While
	 * the student is being answered they will sleep until their question was
	 * answered.
	 */
	private void chatWithProf() {

		Professor.releaseProf();

		msg(" has " + questionsB.length
				+ " type 'B' questions to ask Professor...");

		for (int i = 0; i < questionsB.length; i++) {

			waitToBeServed = new Semaphore(0, false);

			questionsB[i] = new Question('B', i + 1, this);

			if (i != questionsB.length - 1) {

				msg(" asking a new question");

				Professor.askedQuestion(questionsB[i], false);
			} else {
				msg(" informed Professor of last question...");
				Professor.askedQuestion(questionsB[i], true);
			}

			if (!questionsB[i].getQAnswered()) {
				try {
					waitToBeServed.acquire();
				} catch (InterruptedException e) {
					System.out.println(e);
				}
			}
			Professor.answeredQuestionConfirmed();
		}
	}

	/**
	 * This method is used to email the professor their type A question. The
	 * priority of the thread will increase so that the student can send their
	 * question before others and will call the Professor object method
	 * receieveQestion which will queue their question in the prof's email.
	 * 
	 * @param question
	 *            The current question they are e-mailing the professor.
	 */
	public void sendQuestion(Question question) {

		msg(" is sending a new question to the Professor...");

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
	 * 
	 * @return
	 */
	public Thread getThread() {
		return t;
	}

	/**
	 * Method with the intention to be used to sort students by name.
	 * 
	 * @param x
	 *            Student object passed in
	 * @return int value from the comparison of the Student's names.
	 */
	public int compareTo(Student x) {
		if (name.compareTo(x.getName()) < 0) {
			return -1;
		}

		else if (name.compareTo(x.getName()) == 0) {
			return 0;
		}

		else
			return 1;
	}

	/**
	 * Method that will simulate the student thinking about their question A's.
	 * The student thread will yield at this point.
	 */
	public void idle() {
		msg(" is thinking about their question(s)...");
		Thread.yield();
	}

	/**
	 * Method to block student threads from exiting the lab until it is their
	 * turn to exit the lab.
	 */
	private void waitToLeave() {
		try {
			canLeave.acquire();
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}

	/**
	 * Method that will used by the lab thread to release students one by one
	 * from the lab when the professor signals students can leave.
	 */
	public void releaseStudent() {
		canLeave.release();
	}

	/**
	 * Method to print out tracking statements for Student.
	 * 
	 * @param m
	 *            String that contains the most current tracking info regarding
	 *            current student.
	 */
	private void msg(String m) {
		System.out.println("[" + Main.age() + "] Student: " + name + " " + m);
	}

	/**
	 * Method the will be used to start the Student thread. Also at this point
	 * beforehand the student will think about if they want to speak with the
	 * professor before heading to the lab.
	 */
	public void start() {
		wantsChatSession = rand.nextBoolean();
		msg(" arrived in front of Lab door");
		t.start();
	}

	/**
	 * This is the heart of the Student thread which handles the purpose of this
	 * Student class.
	 */
	@Override
	public void run() {

		if (!Lab.labIsOpen() && !Lab.sessionActive()) {
			Lab.waitQueue();
		}

		msg(" is trying to get into lab...");

		Lab.enteredLab(this);

		if (this.insideLab) {
			idle();
			int questionID = 1;
			questionsA = new Question[rand.nextInt(questions_A + 1)];
			
			if (wantsChatSession) {
				questionsB = new Question[rand.nextInt(questions_B) + 1];
				Professor.waitingForSession(this);
			}

			msg(" has " + questionsA.length + " type 'A' question(s) to ask.");

			for (int i = 0; i < questionsA.length; i++) {
				questionsA[i] = new Question('A', questionID++, this);
				sendQuestion(questionsA[i]);
			}

			if (wantsChatSession) {

				msg(" wants to chat with Professor...");

				if (!turnToChat) {
					try {
						canChat.acquire();
					} catch (InterruptedException e) {
						System.out.println(e);
					}
				}

				readyForChat = true;

				chatWithProf();

				msg(" finished with Prof...");

			} else
				msg(" did not want to speak with the Professor");

			msg(" is surfing internet until time to leave....");

			waitToLeave();

		} else
			msg(" could not get into lab, Student going home.");

		msg(" went home...");
	}
}
