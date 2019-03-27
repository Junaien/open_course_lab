import java.util.*;

public class Assignment5 implements Runnable{

	static void printMessage(String message){
		String threadName = Thread.currentThread().getName();	
		System.out.println(threadName + ":" + message);
	}//printMessage

	
	static final void startTime(){
		int hour = 0, minute = 0, second = 0;
		Calendar current = Calendar.getInstance();
		current.setTimeInMillis(System.currentTimeMillis());
		hour = current.get(Calendar.HOUR);
		minute = current.get(Calendar.MINUTE);
		second = current.get(Calendar.SECOND);
		System.out.print(hour + ":" + minute + ":" + second + "--");
	}//age
	

	public void run(){
		Random generator = new Random();
		//sleep randomly 3-5 seconds
		int sleepTime = generator.nextInt(5000) + 3000;
		int i = 1;
			
		try{
			while(true){
				startTime();
				printMessage("I am talking");
				Thread.sleep(sleepTime);
			}//for
		}//try
		
		catch(Exception e){
			System.out.println("error has occured");
			System.exit(1);
		};
			
		}//run


	public static void main(String args[]){
		Thread one = new Thread(new Assignment5());
		Thread two = new Thread(new Assignment5());
		Thread three = new Thread(new Assignment5());
		
		one.start();
		two.start();
		three.start();

	}//main
}//Assignment5