import java.util.concurrent.Semaphore;

/**
 * [Singleton class]
 * @author enlin
 * 
 */
public class Speaker extends Thread{
  //final variables
  public  static final  int       partyTicket     = 3;
  
  //static variables
  private static Speaker instance   = null; 
  
  //instance variables
  private Thread waitFor            = null;
  public  Semaphore partyGroupBinSem  = new Semaphore(1);//use for party grouping
  public  Semaphore movieEndBinSem  = new Semaphore(1);  //binary semaphore for end of movie signal
  public  Semaphore movieIsOnMutex  = new Semaphore(1);  //mutual exclusion for changing movieIsOn value;
  public  boolean   movieIsOn       = false;

  /**
   * [Singleton factory method]
   * @param name
   * @return
   */
  public static Speaker getInstance(String name) {
	  if(instance == null) {instance = new Speaker(name);}
	  if(name    !=  null) {instance.setName(name);}
	  return instance;
  }
  protected Speaker(String name){
	  movieEndBinSem.drainPermits();
	  partyGroupBinSem.drainPermits();
      setName(name);
  }

  public void run(){
    if(waitFor != null && waitFor.isAlive()) {
    	try{waitFor.join();}
    	catch(Exception e) {System.out.println(e);}
    }
    msg("My job is done! I will go home to sleep");
  }

 
  public void signalMovieEnd(int i) {
      msg(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Session " + i + " ends!  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n" );
	  msg("It's time to wake up visitors in the theater");
	  movieEndBinSem.release(); //signal old visitors to leave
      try {
		Thread.sleep(4000);
	  } catch (InterruptedException e) {
			e.printStackTrace();
	  } 
      Main.P(movieIsOnMutex);
	  movieIsOn = false;
	  Main.V(movieIsOnMutex);
	  
	  //signal new visitors to come
	  if(i != Clock.numOfMovies) {
		  for(int j = 0; j < Main.theaterCapacity; j++) {
			  Main.seatsCountSem.release();
		  }
	  }
  }
  public void signalMovieOn(int i) {
	  Main.P(movieIsOnMutex);
	  movieIsOn = true;
	  Main.V(movieIsOnMutex);	  
      msg(">>>>>>>>>>>>>>>>>>>>>>>>> Session " + i + " starts.  No one shall disturb! >>>>>>>>>>>>>>>>>>>>>>>>>" );	  
  }
  
  
  //getters and setters   
  public void setWaitFor(Thread t) {
	  waitFor = t;
  }
  
  public void msg(String m) {
    System.out.println("["+(System.currentTimeMillis()-Clock.time)+"] "+getName()+": "+m);
  }
}
