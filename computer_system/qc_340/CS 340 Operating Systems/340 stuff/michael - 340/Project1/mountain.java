
public class mountain {
   
   public mountain(){
   }
    
   
   public synchronized void getReadyToFight(adventurer a)
   {
      a.msg(" waits at mountain.");
         try {
            this.wait();
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
     a.msg(" is free from mountain.");
   }
   
   public synchronized void stopFighting()
   {
       this.notify();       
   }
}
