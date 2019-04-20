import java.util.List;
public class Speaker extends Thread{
  public static final int  partyTicket     = 12;

  private static volatile boolean isTimeToWakeVisitors = false;
  public Speaker(String name){
    setName(name);
  }
  public void run(){
    while(Clock.isMuseumOpen()){
      if(isTimeToWakeVisitors){
        msg("It's time to wake up those visitors");
        notifyEndOfPresentation();
      }
    }
    notifyEndOfPresentation();
    msg("My job is done! I will go home to sleep");
  }
  public static void setIsTimeToWakeVisitors(boolean value){
    isTimeToWakeVisitors = value;
  }
  public static void notifyEndOfPresentation(){
    isTimeToWakeVisitors = false;
    List<Visitor> visitorsWhoFinished = Museum.clearSeats();
    for(int i = 0; i < visitorsWhoFinished.size(); i++){
      visitorsWhoFinished.get(i).setPartyTicket((i / partyTicket) + 1);
    }
  }
  public void msg(String m) {
    System.out.println("["+(System.currentTimeMillis()-Clock.time)+"] "+getName()+": "+m);
  }
}
