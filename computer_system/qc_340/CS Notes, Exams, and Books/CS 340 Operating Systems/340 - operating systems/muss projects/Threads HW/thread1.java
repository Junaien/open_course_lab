import java.util.Random;


public class thread1 implements Runnable{
	 public void run() {
			int i =0;
	    	while(true){
	        
	    		Random randomGenerator = new Random();
		        int snooze = randomGenerator.nextInt(3000);
	    		
	    	System.out.println("Thread1 is here " + main.age());
	        i++;
	        
	        try {
				Thread.sleep(snooze);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			}	
	    }

}
