
public class Teacher extends Thread {

	public Teacher(){
	}
	
	
	public void run(){
		
		try
		{
		
	    System.out.println("Teacher waits till 8 O'clock class");
		// wait till 8 o'clock for first class
	    Teacher.sleep(800);
		// enter class - end student busy wait
		
		//leave/dismiss class - signal to exit class
		
		//take a break till next class
		
		//wait till 10 o'clock for second class
		
        // enter class - end student BW
		
		//leave/dismiss class - signal to exit class
		
		//take a break till next class
		}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
	
}
}
