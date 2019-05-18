import java.util.List;
public class Speaker extends Thread{
  public static final int  partyTicket     = 3;

  /* the flag signal when it is time to wake the visitors at the end of a session */
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


  /**
   * [speaker calls this method to wake up visitors
   *  and distribute party tickets to them group by group]
   */
  public static void notifyEndOfPresentation(){
    isTimeToWakeVisitors = false;
    List<Visitor> visitorsWhoFinished = Museum.clearSeats();
    for(int i = 0; i < visitorsWhoFinished.size(); i++){
      visitorsWhoFinished.get(i).setPartyTicket((i / partyTicket) + 1);
    }
  }

  public static void setIsTimeToWakeVisitors(boolean value){
	    isTimeToWakeVisitors = value;
  }

  public void msg(String m) {
    System.out.println("["+(System.currentTimeMillis()-Clock.time)+"] "+getName()+": "+m);
  }
}
