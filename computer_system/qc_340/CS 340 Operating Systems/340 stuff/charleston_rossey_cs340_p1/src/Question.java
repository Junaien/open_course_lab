/**
 * Class that maintains the status of a question that will be
 * utilized by the student class.
 * 
 * @author Rossey Charleston
 *
 */
public class Question {
	private char type; //Question Type
	private boolean questionAnswered; //Was question answered?
	private long timeStamp;//Time creation of question
	private Student owner;//The student who asked question
	private int questionID;//Unique identifier for question
	
	/**
	 * The constructor for this class which will set the type
	 * of question as well as the time for its creation and will 
	 * also set questionAnswered to false.
	 * @param t
	 */
	public Question(char t, int ID, Student student){
		type = t; //Either A or B
		owner = student;
		questionID = ID;
		timeStamp = Main.age();
		questionAnswered = false;
	}
	
	/**
	 * This method indicates the type of the current question the
	 * student has and returns it
	 * @return
	 */
	public char getType(){
		return type;
	}
	
	/**
	 * This Method will be set to true once the student's
	 * question was answered by professor
	 * 
	 * @param answer either true or false for the question
	 */
	public void setQAnswered(boolean answer){
		questionAnswered = answer;
	}
	
	/**
	 * return if the current question object was answered by professor
	 * @return either true if answered or false
	 */
	public boolean getQAnswered(){
		return questionAnswered;
	}
		
	/**
	 * Method that returns the time that this question was created.
	 * @return timeStamp variable
	 */
	public long getTimeStamp(){
		return timeStamp;
	}
	
	/**
	 * Method that returns the student object
	 * @return student object
	 */
	public Student getStudent(){
		return owner;
	}
	
	/**
	 * Method that returns the name of the student who created the question
	 * object.
	 * @return student's name
	 */
	public String getStudentName(){
		return owner.getName();
	}
	
	/**
	 * return the question ID of the current object
	 * @return questionID of the object
	 */
	public int getQID(){
		return questionID;
	}
	
	/**
	 * Overriding the toString method to allow for easy printing.
	 */
	public String toString(){
		String out = "Student: " + owner.getName() + " Question# " + questionID; 
		return out;
	}
	
}
