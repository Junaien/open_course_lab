public class Visitor extends Thread{
  public  static final int forever = Integer.MAX_VALUE;
  private boolean amIDone = false;  
  private int id;
  
  /* a flag for visitor to wait for their partyTicket if he finishes the movie */
  private volatile int partyTicket = -1;

  
  /*
   if visitor comes out as T3 T2 T5 T1, 
   speaker will make T5 call T3.join(), T3 call T2.join(), T2 call T1.join()
   [waitFor variable refers to the visitor this threads needs to wait for]
   */
  private Visitor waitFor;
  public Visitor(int id){
    this.id = id;
    setName("Visitor-" + id);
  }

  public void run(){

	//visitor busy wait if he has not watched any presentation and museum is still open
    while(!amIDone && Clock.isMuseumOpen()){
    	
 
        if(!Clock.isSessionOn() && Museum.findAvailableSeat(this)){
          try{
            msg("I <find a seat>");
            Thread.sleep(forever);
          }catch(InterruptedException ex){
            msg("I have <finished> the movie");
            yield();
            yield();
            setPriority(5);

            //the visitor who finishes busy waits for speaker's party tickets
            while(partyTicket == -1){}
            amIDone = true;
            msg("I got <partyTicket> in <group-" + partyTicket + "> from speaker" );
            
            try{
              //exits the theater in order
              if(waitFor != null && waitFor.isAlive())waitFor.join();
            }catch(InterruptedException e){}
          }
        }else{
          setPriority(Math.min(10,getPriority() + 1));
        }
    }

    if(amIDone){
      msg("I <leave> the theater (happy face)");
    }else{
      msg("I <didn't watch> the presentation and have to leave (sad face)");
    }
  }

  public void msg(String m) {
    System.out.println("["+(System.currentTimeMillis() - Clock.time)+"] "+getName()+": "+m);
  }

  /* getters and setters */
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
