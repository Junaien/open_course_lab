import java.util.Random;
import java.util.concurrent.Semaphore;


public class dragon extends Thread{
   int num_adv;
   Random r = new Random();
   items advItems [];
   Semaphore d1;
   Semaphore a2;

   static boolean isDragonNeeded = true;

   private static boolean adventurerWon = false;
   private static int dragonDiceScore;
   private static int adventurerDiceScore;

   public void setIsDragonNeeded(boolean f){
      isDragonNeeded = f;
   }

   public dragon(String threadName, int num_adv, items advItems[], Semaphore d1, Semaphore a2){
      super(threadName);
      this.num_adv = num_adv;
      this.advItems = advItems;
      this.d1 = d1;
      this.a2 = a2;
   }

   public static long time = System.currentTimeMillis();

   public void msg(String m) {
      System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
   }

   private void playGame(){
      dragonDiceScore = r.nextInt(6)+ 1; //roll a number between 1 and 6
      adventurerDiceScore = r.nextInt(6) + 1;
      while(dragonDiceScore==adventurerDiceScore){//in case of a tie keep rolling over again
         dragonDiceScore = r.nextInt(6)+ 1;
         adventurerDiceScore = r.nextInt(6) + 1;
      }
      if(dragonDiceScore>adventurerDiceScore){//dragon wins
         adventurerWon = false;
      }
      else{//adventurer wins
         adventurerWon = true;            
      }
   }

   public void run(){
      msg("begins");
      while(isDragonNeeded){
         try {
            a2.acquire();//wait for an adventurer to signal that they want to enter mountain
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
         for(int i = 0; i<num_adv; i++){
            if(advItems[i].wants_fight==true){
               playGame();
               //now adventurer is fighting dragon
               if(adventurerWon == true){//if adventurer wins gets a random item
                  msg("lost against Adventurer " + (i+1) + " (dragonScore " + dragonDiceScore + ", adventurerScore " + adventurerDiceScore+ ")");
                  int j = r.nextInt(3);//generate random number between 0 and 2
                  if(j == 0){
                     advItems[i].precStone++;
                     msg("awarded a precStone to Adventurer " + (i+1));
                  }
                  else if(j==1){
                     advItems[i].ring++;
                     msg("awarded a ring to Adventurer " + (i+1));
                  }
                  else{
                     advItems[i].necklace++;          
                     msg("awarded a necklace to Adventurer " + (i+1));
                  }
                  d1.release(); //signal the dragon is now free
               }
               else{//if adventurer loses
                  msg("beat Adventurer " + (i+1) + " (dragonScore " + dragonDiceScore + ", adventurerScore " + adventurerDiceScore + ")");
                  d1.release(); //signal the dragon is now free
               }
            }
         }
      }
      msg("ends");
   }
}
