import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
public class Museum{
  public static int  numVisitors = 23;
  public static final int  theaterCapacity = 5;

  private static final Object seatLock     = new Object();
  
  //shared variable representing current visitors who get the seat
  private static volatile List<Visitor> visitorsOnSeats = new ArrayList<>();

  public static void main(String[] args){
    System.out.println("------------------------------ Welcome to the American museum ------------------------------");
    numVisitors = args.length > 0? Integer.valueOf(args[0]) : numVisitors;
    Clock   clock   = new Clock("Clock");
    Speaker speaker = new Speaker("Speaker");
    for(int i = 0; i < numVisitors; i++){
      (new Visitor(i + 1)).start();
    }
    clock.start();
    speaker.start();
  }
  
  /**
   * [a synchronized call to simulate visitors leaving their seats]
   * @return list of visitors who finished and left
   */
  public static List<Visitor> clearSeats(){
    synchronized(seatLock){
      //we want to sort the thread by id, for later easier to force the order
      Collections.sort(visitorsOnSeats, (Visitor a, Visitor b) -> {return a.getID() - b.getID();});
      for(int i = 0; i < visitorsOnSeats.size(); i++){
        if(i >= 1)visitorsOnSeats.get(i).setWaitFor(visitorsOnSeats.get(i-1));
        visitorsOnSeats.get(i).interrupt();
      }
      List<Visitor> visitorsWhoFinished = visitorsOnSeats;
      visitorsOnSeats = new ArrayList<>();
      return visitorsWhoFinished;
    }
  }

  /**
   * [a synchronized call to simulate visitors finding a seat]
   * @param v [who is finding a seat]
   * @return [if there is seats available, visitor will be registered and method returns true, false otherwise]
   */
  public static boolean findAvailableSeat(Visitor v){
    synchronized(seatLock){
      if(visitorsOnSeats.size() >= theaterCapacity || Clock.isPresentationOver())return false;
      visitorsOnSeats.add(v);
      return true;
    }
  }

}
