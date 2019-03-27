import java.util.concurrent.Semaphore;


public class clerk extends Thread{
   int num_adv;
   items advItems [];
   static boolean isClerkNeeded = true;
   Semaphore c1;
   Semaphore a1;

   public void setIsClerkNeeded(boolean f){
      isClerkNeeded = f;
   }

   public clerk(String threadName, int num_adv, items advItems[], Semaphore c1, Semaphore a1){
      super(threadName);
      this.num_adv = num_adv;
      this.advItems = advItems;
      this.c1 = c1;
      this.a1 = a1;
   }

   public static long time = System.currentTimeMillis();

   public void msg(String m) {
      System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
   }

   public void run(){
      msg("begins");
      while(isClerkNeeded){
         try {
            a1.acquire();//wait for an adventurer to signal that they want to enter shop
         } catch (InterruptedException e) {
            e.printStackTrace();
         }        
         for(int i = 0; i<num_adv; i++){
            if(advItems[i].needs_assistance){
               while(advItems[i].precStone>=1){
                  if(advItems[i].ring>=1){
                     advItems[i].precStone--;
                     advItems[i].ring--;
                     advItems[i].numTreasures++;//create a magical ring
                     msg("Made a magical ring for Adventurer " + (i+1));
                  }
                  else if(advItems[i].necklace>=1){
                     advItems[i].precStone--;
                     advItems[i].necklace--;
                     advItems[i].numTreasures++;//create a magical necklace
                     msg("Made a magical necklace for Adventurer " + (i+1));
                  }
                  else{
                     break;
                  }                  
               }
            }
         }
         c1.release(); //signal that clerk is now free
      }      
      msg("ends");
   }


}
