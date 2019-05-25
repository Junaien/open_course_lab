import java.util.Random;
/**
 * [Singleton class]
 * @author enlin
 *
 */
public class Clock extends Thread{
  //final variable
  private static final Random randomGenerator     = new Random(System.currentTimeMillis());
  public  static final long   time                = System.currentTimeMillis();
  public static final int numOfMovies         = 3;

  //static variables
  private static Clock instance;
  
  //instance variables
  public int        whichSession        = 1;
  /**
   * [Singleton class factory method]
   * @param name
   * @return
   */
  public static Clock getInstance(String name) {
	  if(instance == null) {instance = new Clock(name);}
	  if(name    !=  null) {instance.setName(name);}
	  return instance;
  }
  protected Clock(String name){
    setName(name);
  }
  
  public void run(){
	openMuseum();
    try{
      Thread.sleep(randomGenerator.nextInt(6000) + 1000);
      for(int i = 1; i <= numOfMovies; i++){
    	  whichSession = i;
          startOfMovie(i);
          Thread.sleep(randomGenerator.nextInt(6000) + 1000); //simulate movie time
          endOfMovie(i);
      }
      Thread.sleep(randomGenerator.nextInt(6000) + 1000);
      closeMuseum();
    }catch(InterruptedException e){
      System.out.println("clock is interrupted");
    }
  }
  
  private void closeMuseum(){
      System.out.println("\n");
      msg("<<<<<<<<<<<<<<<<<<<<<<<<< Musuem is closed! <<<<<<<<<<<<<<<<<<<<<<<<<" );     
      Main.done(); 
  }
  private void openMuseum() {
      msg("<<<<<<<<<<<<<<<<<<<<<<<<< Welcome To American Museum! <<<<<<<<<<<<<<<<<<<<<<<<<" );     
  }
  
  private void startOfMovie(int i){
      Speaker speaker = Speaker.getInstance(null);
	  speaker.signalMovieOn(i);
  }
  private void endOfMovie(int i){
	  Speaker speaker = Speaker.getInstance(null);
	  speaker.signalMovieEnd(i);	  
  }

 
  public void msg(String m) {
    System.out.println("["+(System.currentTimeMillis()- Clock.time)+"] "+getName()+": "+m);
  }
}