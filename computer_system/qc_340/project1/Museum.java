import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
public class Museum{
  public static final int  numVisitors     = 500;
  public static final int  theaterCapacity = 9;

  private static final Object seatLock     = new Object();
  private static volatile List<Visitor> visitorsOnSeats = new ArrayList<>();

  public static void main(String[] args){
    System.out.println("------------------------------ Welcome to the museum ------------------------------");
    Clock   clock   = new Clock("Clock");
    Speaker speaker = new Speaker("Speaker");
    for(int i = 0; i < numVisitors; i++){
      (new Visitor(i + 1)).start();
    }
    clock.start();
    speaker.start();
  }

  public static List<Visitor> clearSeats(){
    synchronized(seatLock){
      Collections.sort(visitorsOnSeats, (Visitor a, Visitor b) -> {return a.getID() - b.getID();});
      for(int i = 0; i < visitorsOnSeats.size(); i++){
        visitorsOnSeats.get(i).interrupt();
        if(i >= 1)visitorsOnSeats.get(i).setWaitFor(visitorsOnSeats.get(i-1));
      }
      List<Visitor> visitorsWhoFinished = visitorsOnSeats;
      visitorsOnSeats = new ArrayList<>();
      return visitorsWhoFinished;
    }
  }


  public static boolean findAvailableSeat(Visitor v){
    synchronized(seatLock){
      if(visitorsOnSeats.size() >= theaterCapacity || Clock.isPresentationOver())return false;
      visitorsOnSeats.add(v);
      return true;
    }
  }

}
