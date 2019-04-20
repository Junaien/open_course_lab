public class Visitor extends Thread{
  public  static final int forever = Integer.MAX_VALUE;
  private boolean amIDone = false;
  private int partyTicket = -1;
  private int id;
  private Visitor waitFor;
  public Visitor(int id){
    this.id = id;
    setName("Visitor-" + id);
  }

  public void run(){
    while(!amIDone && Clock.isMuseumOpen()){
        if(!Clock.isSessionOn() && Museum.findAvailableSeat(this)){
          try{
            msg("I find a seat");
            Thread.sleep(forever);
          }catch(InterruptedException ex){
            msg("I have finished the movie");
            yield();
            yield();
            setPriority(5);

            //the visitor who finishes busy waits for speaker's party tickets
            while(partyTicket == -1){}
            amIDone = true;
            msg("I got partyTicket in <group-" + partyTicket + ">");
            try{
              if(waitFor != null && waitFor.isAlive())waitFor.join();
            }catch(InterruptedException e){}
          }
        }else{
          setPriority(Math.min(10,getPriority() + 1));
        }
    }

    if(amIDone){
      msg("I leave the theater (happy face)");
    }else{
      msg("I didn't watch the presentation and have to leave (sad face)");
    }
  }

  public void msg(String m) {
    System.out.println("["+(System.currentTimeMillis() - Clock.time)+"] "+getName()+": "+m);
  }

  /* section3: getters and setters */
  public int getID(){
    return id;
  }
  public void setWaitFor(Visitor v){
    waitFor = v;
  }
  public void setPartyTicket(int t){
    this.partyTicket = t;
  }

}
