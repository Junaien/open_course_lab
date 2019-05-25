import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Main{
  //final variables
  public  static final int    theaterCapacity = 5;
  
  //static
  public  static int          numVisitors     = 23;
  private static List<Thread> threads         = new ArrayList<>();
  public  static Semaphore seatsCountSem      = new Semaphore(theaterCapacity); //counting semaphore for seats

  public static void main(String[] args){
	init(args);
    Clock   clock       = Clock.getInstance("Clock");
    clock.start();

    Speaker speaker     = Speaker.getInstance("Speaker");
    Visitor lastVisitor = null; 
    
    for(int i = 0; i < numVisitors; i++){
      Visitor v = new Visitor(i + 1);
      threads.add(v);
      v.setWaitFor(lastVisitor);   //enforce visitors' leaving order
      lastVisitor = v;
      v.start();
    }
    speaker.setWaitFor(lastVisitor); //enforce speaker's leaving order
    speaker.start();

  }
  
  public static void init(String[] args) {
    numVisitors = args.length > 0? Integer.valueOf(args[0]) : numVisitors;
    //command-line numVisitor, otherwise set to default
  }
  
  //finish-off cleaning 
  public static void done() {
	for(Thread t: threads) {
		if(t.isAlive()) {t.interrupt();}
	}
  }
  
  public static void P(Semaphore s) {
    try {
		s.acquire();
    } catch (InterruptedException e) {
		e.printStackTrace();
    }
  }
  public static void V(Semaphore s) {
	  s.release();
  }
  
}
