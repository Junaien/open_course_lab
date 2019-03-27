import java.util.*;

public class Project1 {
	
	static int num_contestants;
	static int num_Dates;
	static int num_Rounds;
	static long startTime;
	static SmartPants Smartpants;
	static Random rand = new Random();
	static Queue<Contestant> Attendance = new LinkedList<Contestant>();
	static volatile Queue<Dates> Available= new LinkedList<Dates>();//
	static Queue<Dates> Taken= new LinkedList<Dates>();
	static volatile boolean[] DateArray=new boolean[9];
	volatile static boolean Show_end=false;
	static boolean[] Contestant_Done;
	static int count=0;
	public static void main(String[] args) {
		num_contestants=Integer.parseInt(args[0]);
		num_Dates = Integer.parseInt(args[1]);
		num_Rounds=Integer.parseInt(args[2]);
		Contestant_Done =new boolean[num_contestants+1];
		
		for (int i = 0; i < num_contestants; i++) {
			new Contestant(i);
		}//end for 
		
		for (int i = 0; i < num_Dates; i++) {
			new Dates(i);
		}//end for 
		
		Smartpants = new SmartPants();
		
		for (int i = 0; i < num_contestants+1; i++) {
			Contestant_Done[i]=false;
		}//end for 
	}//end main 
	
	protected final static long age() {
		return System.currentTimeMillis() - startTime;
	}
	
	public static synchronized Contestant call() {
		return Attendance.poll();
	}
	
	//public static synchronized void Ready(int num){
	//	DateArray[num]=true;
	//}
	
	public static synchronized Dates start() {
		Dates myDate = Available.poll();
		//Taken.add(myDate);
		return myDate;
	}
	
	
	public static synchronized void done(int n){
		Contestant_Done[n]=true;
		count++;	
		if(count == num_contestants){Show_end=true;}
		
	}//end done
	
}

