
public class priorityMountain {
   public priorityMountain(){

   }

   static boolean atpmountain = false;

   public synchronized void getReadyToFight(adventurer a)
   {
      atpmountain = true;
      a.msg(" waits at Priority Mountain.");
      try {
         this.wait();
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
      a.msg(" is free from Priority Mountain.");
   }

   public synchronized void stopFighting()
   {
      atpmountain=false;
      this.notify();       
   }
}
