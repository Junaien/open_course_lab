import java.util.Random;
public class Clock extends Thread{

  public static  final long time            = System.currentTimeMillis();
  private static final int numOfPresentations = 4;
  private static final int breakTimeBetweenSessions = 4000;
  private static final Random randomGenerator = new Random(System.currentTimeMillis());
  private static final Object sessionLock  = new Object();
  
  private static volatile boolean isMuseumOpen = true;
  private static volatile boolean isSessionOn = false;
  private static volatile boolean isPresentationOver = false;

  public Clock(String name){
    setName(name);
  }

  public void run(){
    try{
      for(int i = 1; i <= numOfPresentations; i++){
          Thread.sleep(breakTimeBetweenSessions);
          msg(">>>>>>>>>>>>>>>>>>>>>>>>> Session " + i + " starts!  >>>>>>>>>>>>>>>>>>>>>>>>>" );
          signalStartOfPresentation(i);
          Thread.sleep(randomGenerator.nextInt(2000) + 1000);
          msg("<<<<<<<<<<<<<<<<<<<<<<<<< Session " + i + " is over! <<<<<<<<<<<<<<<<<<<<<<<<<" );
          signalEndOfPresentation(i);
      }
      Thread.sleep(randomGenerator.nextInt(2000) + 1000);
      msg("Museum is closed!");
      signalEndOfMuseum();
    }catch(InterruptedException e){
      System.out.println("clock is interrupted");
    }
  }

  private static void signalEndOfMuseum(){
    isMuseumOpen = false;
  }
  private static void signalEndOfPresentation(int i){
    synchronized(sessionLock){
      isSessionOn = false;
      if(i == numOfPresentations)isPresentationOver = true;
      Speaker.setIsTimeToWakeVisitors(true);
    }
  }
  
  private static void signalStartOfPresentation(int i){
    synchronized(sessionLock){
      isSessionOn = true;
      Speaker.setIsTimeToWakeVisitors(false);
    }
  }

  
  /* getters and setters */
  public static boolean isMuseumOpen(){return isMuseumOpen;}
  public static boolean isSessionOn(){return isSessionOn;}
  public static boolean isPresentationOver(){return isPresentationOver;};

  public void msg(String m) {
    System.out.println("["+(System.currentTimeMillis()- Clock.time)+"] "+getName()+": "+m);
  }
}
