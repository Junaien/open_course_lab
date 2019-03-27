
public class shop {
   
   public shop(){
   }
   
   
   public synchronized void getReadyToShop(adventurer a)
   {
       a.i.needs_assistance = true;
       a.msg(" waits at shop.");
       while(a.i.needs_assistance == true){
          try {
            this.wait();
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
       }
      a.msg(" is free from shop.");
   }
   
   public synchronized void stopShopping(adventurer a)
   {
       a.i.needs_assistance = false;
       this.notifyAll();       
   }
   
   
   //For FCFS Busy Waiting Implementation see below two methods instead of above two (All other code is the same)
   
/*   public synchronized void getReadyToShop(adventurer a)
   {
      System.out.println(Thread.currentThread().getName() + " waits at shop.");
      a.i.needs_assistance = true;
      while(a.i.needs_assistance == true){
         //Busy Wait
      }
      System.out.println(Thread.currentThread().getName() + " is free from shop.");
   }

   public  void stopShopping(adventurer a)
   {
      a.i.needs_assistance = false;    
   }*/
   
   
   
   
   
   
}
