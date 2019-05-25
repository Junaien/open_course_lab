import java.util.concurrent.Semaphore;

public class Visitor extends Thread{
  //static variable
  public static Semaphore mutex   = new Semaphore(1); //use for ticket group
  public static int       visited = 0;
  //instance variables
  private int id;
  private Thread waitFor;
  private int ticketNum = -1;
  //constructor
  public Visitor(int id){
    this.id = id;
    setName("Visitor-" + id);
  }

  public void run(){
	Clock   clock   = Clock.getInstance(null);
    Speaker speaker = Speaker.getInstance(null);
    int session = -1;
    //don't disturb when movie is on
    Main.P(speaker.movieIsOnMutex);
    while(speaker.movieIsOn) {}
    Main.V(speaker.movieIsOnMutex);
	//wait for seats 
	try {
		Main.seatsCountSem.acquire();
		session = clock.whichSession;
	}catch(InterruptedException e) {
		waitForMyDude();
		msg("I did'not get a chance to watch movie, now I am going home");
		return;
	}
	
	//watching .......
	msg("I am very luck to get a seat, now I am watching Session : " + session); 

	//wait for end of the movie, source binary semaphore implementation
	Main.P(speaker.movieEndBinSem);
	msg("I finished the movie, I need to wait for group ticket");
	speaker.movieEndBinSem.release();
	
    
	//wait for group ticket
	Main.P(mutex);
	visited++;
	ticketNum = visited / (Speaker.partyTicket + 1);
	if(visited % Speaker.partyTicket != 0 && visited != Main.theaterCapacity) {
		Main.V(mutex);
		Main.P(speaker.partyGroupBinSem);
	}else {
		visited %= Main.theaterCapacity;
		Main.V(mutex);
		for(int i = 1; i < Speaker.partyTicket; i++) {Main.V(speaker.partyGroupBinSem);}
	}
	
    msg("I am in group-" + ticketNum  + ", I got my ticket");
	
	//leave in order
	msg("Now I am trying to walk aroud the Musuem!"); 
	waitForMyDude();
	msg("<Report>: session-" + session + ", group-" + ticketNum + ", now I am going home"); 
	
  }
 
  /* getters and setters */
  public int getID(){
    return id;
  }
  public void setWaitFor(Thread v){
    waitFor = v;
  }
  public void msg(String m) {
    System.out.println("["+(System.currentTimeMillis() - Clock.time)+"] "+getName()+": "+m);
  }
  
  public void waitForMyDude() {
	//go home
    try{if(waitFor != null && waitFor.isAlive())waitFor.join();}
    catch(InterruptedException e){}
  }
}
