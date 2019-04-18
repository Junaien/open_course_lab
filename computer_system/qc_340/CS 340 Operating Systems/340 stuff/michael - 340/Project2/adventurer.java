import java.util.Random;
import java.util.concurrent.Semaphore;


public class adventurer extends Thread{
   Random r = new Random();
   String threadName;
   items i;
   int num_adv;
   Semaphore c1;
   Semaphore a1;
   Semaphore d1;
   Semaphore a2;
   Semaphore mutex;
   Semaphore mutex2;

   adventurer() {

   }

   public adventurer(String threadName, items i, int num_adv, Semaphore c1, Semaphore a1, Semaphore d1, Semaphore a2, Semaphore mutex, Semaphore mutex2){
      super(threadName);
      this.threadName = threadName;
      this.i = i;
      this.num_adv = num_adv;
      this.c1 = c1;
      this.a1 = a1;
      this.d1 = d1;
      this.a2 = a2;
      this.mutex = mutex;
      this.mutex2 = mutex2;
   }

   public static long time = System.currentTimeMillis();

   public void msg(String m) {
      System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
   }

   public void run(){
      msg("begins");
      try {
         sleep(1000);//allow enough time for all the other threads to get started
      } catch (InterruptedException e1) {
         e1.printStackTrace();
      }
      while(i.numTreasures<i.fortuneSize){//leave town once adventurer's treasures has reached fortune_size
         System.out.println();
         msg("precStone " + i.precStone + ", ring " + i.ring + ", necklace " + i.necklace + ", numTreasures " + i.numTreasures);         
         if(i.precStone>=1 && (i.ring>=1 || i.necklace>=1)){//have enough items to make at least one magical ring or one magical necklace
            msg("has enough items to make a treasure and will go to shop");
            try {
               this.sleep(r.nextInt(100));//go to shop
            } 
            catch (InterruptedException e) {
               e.printStackTrace();
            }
            
            msg(" does wait on Semaphore mutex.");
            try { mutex.acquire();} catch (InterruptedException e1) { e1.printStackTrace(); }
            i.needs_assistance = true;
            
            a1.release();//signal an adventurer wants shop
            try {
               msg(" does wait on Semaphore c1.");
               c1.acquire();//wait for the clerk
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
            msg(" is free from Semaphore c1.");
            i.needs_assistance = false;
            
            mutex.release();
            msg(" is free from Semaphore mutex.");
            
         }
         else{//don't have enough items to make anything, go to mountain to battle dragon           
            msg("does not have enough items to make a treasure and will go to fight dragon");
            try {
               this.sleep(r.nextInt(100));//go to mountain
            } 
            catch (InterruptedException e) {
               e.printStackTrace();
            }
            
            msg(" does wait on Semaphore mutex2.");
            try {  mutex2.acquire(); } catch (InterruptedException e1) {  e1.printStackTrace(); }
            
            i.wants_fight = true;
            a2.release(); //adventurer signals wants a fight
            try {
               msg(" does wait on Semaphore d1.");
               d1.acquire();
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
            msg(" is free from Semaphore d1.");
            i.wants_fight = false;
            mutex2.release();
            msg(" is free from Semaphore mutex2.");         

         }         
      }
      
      msg(" is ready to leave and has the following: " + "precStone " + i.precStone + ", ring " + i.ring + ", necklace " + i.necklace + ", numTreasures " + i.numTreasures);

      if(currentThread().getName().equals("Adventurer " + num_adv)){
         mainprog.leave[num_adv].release();
      }
      
      for(int i = 1; i<num_adv; i++){
         if(currentThread().getName().equals("Adventurer " + i)){
            try {
               msg(" does wait on Semaphore leave" + (i+1));
               mainprog.leave[i+1].acquire();
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
            msg(" is free from Semaphore leave" + (i+1));
            mainprog.leave[i].release();
         }         
      }
      if(currentThread().getName().equals("Adventurer 1")){
         dragon.isDragonNeeded = false;
         a2.release();
         clerk.isClerkNeeded = false;
         a1.release();
      }

      msg(" actually leaves");
   }
}
