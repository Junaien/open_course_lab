/**
 * The master thread which creates the students, lab, and Professor for the project.
 * 
 * @author Rossey Charleston
 *
 */
public class Main {

	private static long openingTime = System.currentTimeMillis();

	/**
	 * Current time elapsed to be used by children threads.
	 * 
	 * @return Current time elapsed
	 */
	protected static final long age() {
		return System.currentTimeMillis() - openingTime;
	}

	public static void main(String[] args) {

		Student[] students = new Student[11];
		Professor teacher;
		Lab lab;
		int ch = 65;
		char letter;

		for (int i = 0; i < students.length; i++) {
			letter = (char) (ch + i);
			students[i] = new Student(Character.toString(letter));
		}

		teacher = new Professor("Prof. Fluture");
		lab = new Lab("CS 340 Lab");

		for (int i = 0; i < students.length; i++) {
			students[i].start();
		}

		lab.start();
		teacher.start();

	}

}
