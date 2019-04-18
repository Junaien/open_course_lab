import java.util.*;

/**
 * CSCI 340 Project 1
 * Class of 6:30 - 7:45 pm
 * 
 * This is a helper class.  This provides vector for storing visitors and storing groups in a group
 * 
 * Date Finished April 24, 2008
 * @Yachao Liu 
*/

public class Group{
	int groupID;//Group #
	Vector <Visitor> visitors;//storing visitor
	Visitor handiVisitor;//handicapvisitor
	boolean hasHandicap;//if this group has a handicap
		
	/**
	 * Group Constructor
	 * 
	 * @param id int the number of the group
	 */
	public Group(int id){
		visitors = new Vector <Visitor>();
		groupID = id;
		hasHandicap = false;
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
		
	/**
	 * This method sets the incoming visitor to handicap
	 * 
	 * @param v Visitor sets this visitor the handicap visitor
	 */
	public void setHandiVisitor(Visitor v){
		handiVisitor = v;	
		visitors.add(v);
		hasHandicap = true;
	}//setHandiVisitor
	
	/*
	 * This method returns the handicap visitor in this vector
	 * 
	 * @return Visitor the handicap visitor
	 */
	public Visitor getHandiVisitor(){
		return handiVisitor;
	}//getHandiVisitor
	
	
}//Group

