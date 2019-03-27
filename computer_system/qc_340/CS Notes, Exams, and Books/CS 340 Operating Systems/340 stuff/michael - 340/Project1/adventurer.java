import java.util.Random;


public class adventurer extends Thread{
   Random r = new Random();
   items i;
   shop s;
   mountain m;
   priorityMountain p;
   int num_adv;
   game g;
   adventurer() {

   }

   public adventurer(String threadName, items i, shop s, mountain m, int num_adv, game g, priorityMountain p){
      super(threadName);
      this.i = i;
      this.s = s;
      this.m = m;
      this.num_adv = num_adv;
      this.g = g;
      this.p = p;
   }

   public static long time = System.currentTimeMillis();

   public void msg(String m) {
      System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
   }

   public void run(){
      msg("begins");
      try {
         sleep(1000);//allow enough time for all the other threads to get started including the dragon and clerk
      } catch (InterruptedException e1) {
         e1.printStackTrace();
      }
      while(i.numTreasures<i.fortuneSize){//leave town once adventurer reached fortune_size
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
            this.s.getReadyToShop(this);//wait
         }
         else{//don't have enough items to make anything, go to mountain to battle dragon           
            msg("does not have enough items to make a treasure and will go to fight dragon");
            try {
               this.sleep(r.nextInt(100));//go to mountain
            } 
            catch (InterruptedException e) {
               e.printStackTrace();
            }
            this.m.getReadyToFight(this);//wait

            this.g.doGame(this);

            //if dragon won
            if(dragon.adventurerWon==false){
               msg("immediately goes to fight dragon again");
               currentThread().setPriority(currentThread().getPriority()+1);//increase priority by 1 from the thread's current priority
               msg(" Updated Priority is " + currentThread().getPriority());
               this.p.getReadyToFight(this);
               this.g.doGame(this);
               currentThread().setPriority(5);//immediately set priority back to default
               msg(" Resets priority back to " + currentThread().getPriority() + " and yields");
               adventurer.yield(); 
            }
         }         
      }
      msg(" is ready to leave and has the following: " + "precStone " + i.precStone + ", ring " + i.ring + ", necklace " + i.necklace + ", numTreasures " + i.numTreasures);

      for(int i = 1; i<num_adv; i++){
         if(currentThread().getName().equals("Adventurer " + i)){
            for(int j = i; j<num_adv; j++){
               if(mainprog.adventurers[j].isAlive()){
                  try {
                     mainprog.adventurers[j].join();
                  } catch (InterruptedException e) {
                     e.printStackTrace();
                  }
               }
            }
         }
      }

      if(currentThread().getName().equals("Adventurer 1")){
         mainprog.d.setIsDragonNeeded(false);
         mainprog.c.setIsClerkNeeded(false);
      }

      msg(" actually leaves");
   }
}
