
/**
 * The master thread which creates the students, lab, and Professor for the
 * project.
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

		Student[] students;
		Professor teacher;
		Lab lab;
		int ch = 65;
		char letter;
		int value[] = new int[4];

		if (args.length < 4) {
			value[0] = 15;
			value[1] = 11;
			value[2] = 4;
			value[3] = 3;
		} else {
			for (int i = 0; i < 4; i++) {
				if (i == 0) {
					try {
						value[i] = Integer.parseInt(args[i]);
					} catch (NumberFormatException e) {
						System.out
								.println("First arg is not a number getting default 15 for capacity");
						value[i] = 15;
					}
				} else if (i == 1) {
					try {
						value[i] = Integer.parseInt(args[i]);
					} catch (NumberFormatException e) {
						System.out
								.println("Second arg is not a number getting default 11 for Students");
						value[i] = 11;
					}
				} else if (i == 2) {
					try {
						value[i] = Integer.parseInt(args[i]);
					} catch (NumberFormatException e) {
						System.out
								.println("Third arg is not a number getting default 4 for question_A");
						value[i] = 4;
					}
				} else if (i == 3) {
					try {
						value[i] = Integer.parseInt(args[i]);
					} catch (NumberFormatException e) {
						System.out
								.println("Last arg is not a number getting default 3 for question_B");
						value[i] = 3;
					}
				}
			}
		}

		students = new Student[value[1]];

		for (int i = 0; i < students.length; i++) {
			letter = (char) (ch + i);
			students[i] = new Student(Character.toString(letter), value[2],
					value[3]);
		}

		teacher = new Professor("Prof. Fluture");
		lab = new Lab("CS 340 Lab", value[0]);

		for (int i = 0; i < students.length; i++) {
			students[i].start();
		}

		lab.start();
		teacher.start();

	}

}
