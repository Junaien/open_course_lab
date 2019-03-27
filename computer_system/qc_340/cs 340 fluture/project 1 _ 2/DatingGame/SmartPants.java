
public class SmartPants implements Runnable{
	public volatile boolean sleeping;
	Thread SThread;
	Contestant contestant;
	
	public SmartPants() {
		SThread = new Thread(this, "SmartPants");
		SThread.start();
	}

	public void msg(String s) {
		System.out.println("[" + Project1.age() + "] SmartPants " + s);
	}
	
	
	public void run() {
		while (true) {
			int i=0;
			while(i<Project1.num_contestants){
				try {Thread.sleep(Project1.rand.nextInt(1000) + 1);}
				catch (InterruptedException e) { }
				contestant=Project1.call();
				if(contestant!=null){
					msg("welcomes " + contestant.getName() + ".");
					contestant.Busy=false;
					i++;
				}//end if 
			}	
			
			Thread.yield();
			while (!SThread.isInterrupted()){
					sleeping=true;
				try {Thread.sleep(Project1.rand.nextInt(900000) + 1);
						System.out.println("SmartPants is Sleeping");
				} 
				catch (InterruptedException e) {
					sleeping=false;
					try {Thread.sleep(2000);}
					catch (InterruptedException e1) { }
					System.out.println();
					msg(" "+contestant.CThread.getName() + " Has finished his # of rounds");
					msg("congradulates" +contestant.CThread.getName());
					System.out.println();
					//Project1.done(contestant.num);
					if(Project1.Show_end){break;}
					//else{continue;}
				} 
			}//end while
			msg(" says the show has ended and leaves");
			System.out.println(Project1.Show_end);
			break;
		}//end while
	}//end run

}//end class

	
	
	


