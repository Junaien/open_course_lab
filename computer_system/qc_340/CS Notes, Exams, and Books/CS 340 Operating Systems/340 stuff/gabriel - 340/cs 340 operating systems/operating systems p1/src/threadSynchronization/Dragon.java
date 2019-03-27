package threadSynchronization;
import java.util.Date;
public class Dragon implements Runnable{
	String name;
	TheAdventure advant;
	
	//Dragon interface with two threads
	public Dragon(String name, TheAdventure advant){
		this.name= name;
		this.advant = advant;
	}
	
	//@Override
	public void run() {
		System.out.println("Dragon is waiting for his victom");
		while(true){
			//busy waiting for adventure 
			while(!advant.getAvailable()){;}
			
			advant.setIsInBattle(true);
			
			synchronized (advant){
				System.out.println("The Dragon is notifying to come battle " + new Date());
				startBattle();
			}
			
			advant.setAvailable(false);
			advant.setIsInBattle(false);
		}
	}
	
	public synchronized void startBattle(){
		advant.notify();
	}
}
