import java.util.Date;


	public class main {
	    
		private static final long startTime = System.currentTimeMillis();
		  
		   protected static final long age() {
		      return System.currentTimeMillis() - startTime;
		   }
		
	    public static void main(String args[]) {
	    	new Thread(new thread1()).start();
	    	new Thread(new thread2()).start();
	    	new Thread(new thread3()).start();
	        
	    }

	}

	
