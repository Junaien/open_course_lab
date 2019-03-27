import java.util.Random;
import java.util.Vector;
import java.util.concurrent.Semaphore;

/**
 * This class will be used to simulate a Professor interacting with his/her
 * students. Professor class will be used to answer any question received by
 * Student threads and will also chat with students if they desire so.
 * 
 * @author Rossey Charleston
 * 
 */
public class Professor implements Runnable {

	private final long arrivalTime = System.currentTimeMillis();
	private static String profName;
	private Thread t;
	private Random rand;
	private static Vector<Question> studentQuestions;
	private static Vector<Student> studentQueue;
	private static boolean startingOnlineSession;
	private static boolean studentLastQuestion;
	private static Question currentQuestion;
	private static Semaphore emailedQuestion;
	private static Semaphore chatMutex;
	private static Semaphore waitForNextStudent;
	private static Semaphore answerNextQuestion;
	private static Semaphore askedQuestion;

	/**
	 * Constructor for the Professor object which will initialize important
	 * variables needed for the Professor thread.
	 * 
	 * @param n
	 *            Name of the Professor is passed in
	 */
	public Professor(String n) {
		profName = n;
		studentQuestions = new Vector<Question>();
		studentQueue = new Vector<Student>();
		startingOnlineSession = false;
		rand = new Random();
		emailedQuestion = new Semaphore(1, true);
		chatMutex = new Semaphore(1, true);
		waitForNextStudent = new Semaphore(0, false);
		answerNextQuestion = new Semaphore(0, false);
		askedQuestion = new Semaphore(0, false);
		t = new Thread(this, n);
	}

	/**
	 * Method that returns the queue of students who want to speak with
	 * Professor.
	 * 
	 * @return A vector of students
	 */
	public Vector<Student> getStudentQueue() {
		return studentQueue;
	}

	/**
	 * Method that returns the queue of student type A questions.
	 * 
	 * @return a vector of student questions
	 */
	public static Vector<Question> getStudentQuestions() {
		return studentQuestions;
	}

	/**
	 * Method that returns the name of the Professor.
	 * 
	 * @return name of the Professor object
	 */
	public String getProfName() {
		return profName;
	}

	/**
	 * Method that can be used to return the time Professor object was created.
	 * 
	 * @return Time the object was created.
	 */
	public long getProfArrivalTime() {
		return arrivalTime;
	}

	/**
	 * Method that is used to signal students the the professor is currently
	 * ready to chat with students.
	 * 
	 * @return True is professor ready to talk, false otherwise
	 */
	public static boolean chatSessionActive() {
		return startingOnlineSession;
	}

	/**
	 * Method to be used by the student threads in which the next student
	 * allowed to talk will free the prof from its internal waiting queue.
	 */
	public static void releaseProf() {
		waitForNextStudent.release();
	}

	/**
	 * Method that a student currently talking to professor will use to simulate
	 * a chat. Student will use this method to state if they reached their last
	 * question. WAS SYNCHRONIZED IN PREVIOUS ITERATION
	 * 
	 * @param q
	 *            Current question from student
	 * @param lastQuestion
	 *            Indicates if current question is the last one.
	 */
	public static void askedQuestion(Question q, boolean lastQuestion) {

		studentLastQuestion = lastQuestion;
		currentQuestion = q;
		askedQuestion.release();
	}

	/**
	 * Method used by the current student thread interacting with the professor
	 * which will confirm to the professor thread that their question was
	 * answered thus allowing the professor thread to move on.
	 */
	public static void answeredQuestionConfirmed() {
		answerNextQuestion.release();
	}

	/**
	 * Method that prints out tracking info regarding Professor thread.
	 * 
	 * @param m
	 *            String of what the professor is doing is passed in
	 */
	private static void msg(String m) {
		System.out.println("[" + Main.age() + "] Professor: " + profName + " "
				+ m);
	}

	/**
	 * Method that will simulate the Professor thread speaking with the Student
	 * threads. The professor thread will notify students that it is ready for a
	 * chat session and will iterate through the studentQueue and wait until the
	 * student is ready to chat with the professor. Once professor is speaking
	 * with the student it will answer every question the student has before
	 * moving onto the next student.
	 */
	private void chatWithStudents() {
		startingOnlineSession = true;
		boolean studentFinished;

		msg(" is now ready for chat session.");

		for (int i = 0; i < studentQueue.size(); i++) {

			msg(" waiting for next student");

			waitForNextStudent = new Semaphore(0, false);

			studentFinished = false;

			studentQueue.get(i).setTurnToChat(true);

			try {
				waitForNextStudent.acquire();
			} catch (InterruptedException e) {
				System.out.println(e);
			}

			msg(" now chatting with Student " + studentQueue.get(i).getName());

			while (!studentFinished) {

				try {
					// msg(" WAITING TO BE RELEASED.........");
					askedQuestion.acquire();
				} catch (InterruptedException e1) {
					System.out.println(e1);
				}

				if (!currentQuestion.getQAnswered()) {
					msg(" talked about and answered Student "
							+ currentQuestion.getStudentName()
							+ " for question ID " + currentQuestion.getQID());

					answerNextQuestion = new Semaphore(0, false);
					askedQuestion = new Semaphore(0, false);

					if (studentLastQuestion) {
						studentFinished = true;
					}

					currentQuestion.setQAnswered(true);
					studentQueue.get(i).isServed();
				}

				try {
					answerNextQuestion.acquire();
				} catch (InterruptedException e) {
					System.out.println(e);
				}
			}
		}
	}

	/**
	 * Method that will make the Professor thread sleep for a moment to give
	 * student threads more time to be able to send in their question type A.
	 */
	public void relaxABit() {
		msg(" settling down for a bit...");

		try {
			Thread.sleep(1000); // CHANGED from 5 secs to 1
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}

	/**
	 * Method that will force the professor thread to sleep for some time to
	 * allow more questions from the student to be sent in.
	 */
	public void waitForQuestions() {
		try {
			Thread.sleep(rand.nextInt(9000) + 1000);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}

	/**
	 * Method that would return the time a Professor answered a question. (not
	 * used in program)
	 * 
	 * @return current elapsed time
	 */
	public int answerQuestionTime() {
		return (int) (arrivalTime - System.currentTimeMillis());
	}

	/**
	 * Method that Student threads use to email their questions to the professor
	 * which is then queued.
	 * 
	 * @param question
	 *            Current question of the Student
	 */
	public static void receiveQuestionA(Question question) {

		try {
			emailedQuestion.acquire();
		} catch (InterruptedException e) {
			System.out.println(e);
			e.printStackTrace();
		}

		msg(" email received from student " + question.getStudentName()
				+ " with Question ID: " + question.getQID());

		studentQuestions.add(question);
		emailedQuestion.release();
	}

	/**
	 * This method will hold the order in which students asked to have a chat
	 * with the Professor.
	 * 
	 * @param x
	 *            Student to be queued
	 */
	public static void waitingForSession(Student x) {
		try {
			chatMutex.acquire();
		} catch (InterruptedException e) {
			System.out.println(e);
			e.printStackTrace();
		}

		studentQueue.add(x);
		chatMutex.release();
	}

	/**
	 * Method that will start the Professor thread.
	 */
	public void start() {
		msg(" going to office...");
		t.start();
	}

	/**
	 * The heart of the Professor thread which handles the purpose of this
	 * class.
	 */
	@Override
	public void run() {

		msg(" arrived in office.");

		relaxABit();

		msg(" is about to answer some email questions...");

		waitForQuestions();

		int currentIndex = 0;

		while (currentIndex < studentQuestions.size()) {
			if (!studentQuestions.get(currentIndex).getQAnswered()) {

				msg(" sent a response email to Student "
						+ studentQuestions.get(currentIndex).getStudentName()
						+ " about QID: "
						+ studentQuestions.get(currentIndex).getQID());

				studentQuestions.get(currentIndex).setQAnswered(true);
			}

			++currentIndex;
		}

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			System.out.println(e);
		}

		chatWithStudents();

		msg(" is done with session, students may leave...");

		Lab.signalToRelease();

		while (Main.age() < Lab.getClassTime()
				&& currentIndex != studentQuestions.size()) {
			if (currentIndex < studentQuestions.size()) {
				if (!studentQuestions.get(currentIndex).getQAnswered()) {

					msg(" sent a response email to Student "
							+ studentQuestions.get(currentIndex)
									.getStudentName() + " about QID: "
							+ studentQuestions.get(currentIndex).getQID());

					studentQuestions.get(currentIndex).setQAnswered(true);
				}

				++currentIndex;
			}
		}

		if (currentIndex == studentQuestions.size()) {

			msg(" was able to finish answering every type 'A' questions recieved before leaving.");

		} else
			msg(" was not able to finish answering every type 'A' questions before lab closed.");

	}
}
