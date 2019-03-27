public class Dates implements Runnable {
	int num;
	String name;
	Thread DThread; 
	Contestant contestant; // 
	volatile boolean free=true;
	volatile boolean decision; 
	
	public Dates(int i) {
		name = "Date-" + (i+ 1);
		num = i + 1;
		DThread = new Thread(this, name);
		DThread.start();
	}//end constructor

	public void msg(String s) {
		System.out.println("[" + Project1.age() + "] " + name + " " + s);
	}//end msg

	public boolean Rush() {
		int priority = DThread.getPriority();
		int priority2=priority + Project1.rand.nextInt(5) + 1 ;
		DThread.setPriority(priority2);
		msg(" is rushing to make a decision");
		try {Thread.sleep(1000);} 
		catch (InterruptedException e) { }
		DThread.setPriority(priority);
		return MakeDecision(priority2);
	}//end rush 
	
	
	public static boolean MakeDecision(int rand) {
		if (rand % 2 == 0){return false;}
		return true;
	}
	
	public void run() {
		Project1.Available.add(this);
		msg("Arrived and is waiting for a Contestant");
		Thread.yield();
		while(!Project1.Show_end){
			while(free){
				Thread.yield();
				if(Project1.Show_end){break;}
			}//while  date has not met contestant busy wait 
			while(!Project1.Show_end){
				msg(" Meets " + contestant.CThread.getName());
				contestant.answer=Rush();
				contestant.Decision=true;
				Thread.yield();
				Project1.Available.add(this);
			}
		}//end while 
		Thread.yield();
		try {Thread.sleep(3000);} 
	    catch (InterruptedException e) { }
		
		msg(" Is leaving now.");
	}//end run 
	
	
}//end Dates




