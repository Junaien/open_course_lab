import java.util.LinkedList;
import java.util.Queue;


public class Contestant implements Runnable{
	String name;
	Thread CThread;
	int num;
	int rounds=0;
	volatile Dates myDate;
	volatile boolean Busy=true;
	volatile static boolean Decision=false;
	volatile boolean answer; 
	static Queue<String> Girls= new LinkedList<String>();
	static Queue<String> Numbers= new LinkedList<String>();
	static Thread[] Leave= new Thread[Project1.num_contestants+1];
	
	public Contestant(int i) {
		name = "Contestant-" + (i + 1);
		num = i + 1;
		CThread = new Thread(this, name);
		CThread.start();
		Leave[num]=this.CThread;
	}//end constructor

	public  synchronized void run() {
		try {
			Thread.sleep(Project1.rand.nextInt(10000) + 1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		msg("Arrives.");
		Project1.Attendance.add(this);
		while(Busy){Thread.yield();};
		Thread.yield();
		try {
			Thread.sleep(9000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		while(rounds<Project1.num_Rounds){
			if(Project1.Available.isEmpty()){
				msg(" is waiting by the juice Bar.");
				Thread.yield();
				try { Thread.sleep(Project1.rand.nextInt(10000) + 1);}
				catch(InterruptedException e) { e.printStackTrace(); }
			}//end if
			else{
				myDate=Project1.start();
				Decision=false;
				//if(Girls.isEmpty()){ Girls.add(myDate.DThread.getName()); }//if this is my first date 
				//else{
					//while(Girls.contains(myDate.DThread.getName())){ 
						
					//	Project1.Available.add(myDate);
					//	myDate=Project1.start();
						
					//}//end while
				//}//end else
				Girls.add(myDate.DThread.getName());
				myDate.contestant=this;
				myDate.free=false;
				msg(" is waiting for " + myDate.DThread.getName()+"'s decision.");
				while(checkDecision()){  }
				myDate.free=true;
				//System.out.println();
				try {Thread.sleep(Project1.rand.nextInt(1000) + 1);}
				catch (InterruptedException e) { }
				checkanswer();
				
				rounds++;
			}//end else
		}//end while
		while(!Project1.Smartpants.sleeping){}
		Project1.Smartpants.SThread.interrupt();
		Project1.Smartpants.contestant=this;
		Project1.done(num);
		Thread.yield();
		try {Thread.sleep(Project1.rand.nextInt(3000) + 1);}
		catch (InterruptedException e) { }
		while(!Project1.Show_end){}
		Thread.yield();
		try {Thread.sleep(Project1.rand.nextInt(7000) + 1);}
		catch (InterruptedException e) { }
		if(this.num == Project1.num_contestants){
			msg("is leaving");
		}
		else{
			if(Leave[this.num+1].isAlive()){
				try {
					Leave[this.num+1].join();
					msg("is leaving");
				} catch (InterruptedException e) {e.printStackTrace();}
			}
			else{
				msg("is leaving");
				}	
		}//end else
		
		
	}//end run 

	
	
	
	private void checkanswer() {
		if(answer== true){ 
		Numbers.add(myDate.DThread.getName());
		msg("Got" + myDate.DThread.getName() +"'s number :}");}
		else{msg("Didn't get " + myDate.DThread.getName() +"'s number :{");}
	}//end check

	public static synchronized boolean checkDecision(){
		if(!Decision) return true;
		else return false;
		
	}
	
	public static synchronized void Ready(int n){
			Project1.DateArray[n]=true;
	}
	
	private void msg(String s) {
		System.out.println("[" + Project1.age() + "] " + name + " " + s);
	}//end msg

	public String  getName() {return name;}

}//end ckass


