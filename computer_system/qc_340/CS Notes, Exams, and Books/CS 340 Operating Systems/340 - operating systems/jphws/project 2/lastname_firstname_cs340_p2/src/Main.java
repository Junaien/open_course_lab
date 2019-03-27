import javax.swing.JOptionPane;

public class Main 
{	
	public static void main(String[] args)
	{
		/**
		 * The number of students is initialized at 20, if you want to have a different
		 * number of students then in the command line argument you must type "-s"
		 * followed by the number of students desired.  The minimum is 1 student.
		 * If anything besides this command line argument is entered after 2 failed 
		 * attempts (the second being a JOptionPane) the 20 default will kick in.
		 */
		int numberOfStudents = 20;
		if (args.length > 0 && args[0].equals("-s"))
		{
			try
			{
				numberOfStudents = Integer.parseInt(args[1]);
				while (numberOfStudents<=0 && numberOfStudents > 50)
				{
					numberOfStudents = Integer.parseInt(JOptionPane.showInputDialog(null, "There must be at least 1 Student and no more than 50."));
				}
			}
			catch(NumberFormatException e)
			{
				numberOfStudents = 20;
			}
		}
		
		/**
		 * The Teacher thread is created and so are the student threads based
		 * on the number entered in the command line argument. The threads are started
		 * automatically in the constructor of each class.
		 */
		new Teacher();
		Students[] TotalStudents  = new Students[numberOfStudents];
		for(int i = 0; i < numberOfStudents; i++)
		{
			TotalStudents[i] = new Students();
		}
	}
}
	
	

