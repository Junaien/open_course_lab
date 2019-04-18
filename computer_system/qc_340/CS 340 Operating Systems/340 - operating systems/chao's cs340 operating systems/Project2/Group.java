import java.util.*;

/**
 * CSCI 340 Project 2
 * Class of 6:30 - 7:45 pm
 * 
 * This is a helper class.  This provides vector for storing visitors and storing groups in a group
 * 
 * Date Finished May 16, 2008
 * @Yachao Liu 
*/

public class Group{
	int groupID;//Group #
	Vector <Visitor> visitors;//storing visitor
		
	/**
	 * Group Constructor
	 * 
	 * @param id int the number of the group
	 */
	public Group(int id){
		visitors = new Vector <Visitor>();
		groupID = id;
	}//constructor
	
	/**
	 * This method adds a visitor to the vector
	 * 
	 * @param v Visitor
	 */
	public void addToGroup(Visitor v){
		visitors.add(v);
	}//addtogroup
	
	/**
	 * This method returns a visitor indicated in the argument
	 * 
	 * @param index int the index of visitor indicated in the vector
	 * @return Visitor
	 */
	public Visitor getVisitor(int index){
		return visitors.get(index);
	}//getVisitor
		
	
	
}//Group

