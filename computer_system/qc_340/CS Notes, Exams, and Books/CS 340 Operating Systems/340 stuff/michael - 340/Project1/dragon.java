import java.util.Random;


public class dragon extends Thread{
   int num_adv;
   Random r = new Random();
   mountain m;
   priorityMountain p;
   items advItems [];
   game g;

   static adventurer fightingAdv;

   private boolean isDragonNeeded = true;

   static boolean adventurerWon = false;
   static int dragonDiceScore;
   static int adventurerDiceScore;

   public void setIsDragonNeeded(boolean f){
      isDragonNeeded = f;
   }

   public dragon(String threadName, int num_adv, mountain m, items advItems[], game g, priorityMountain p){
      super(threadName);
      this.num_adv = num_adv;
      this.m = m;
      this.advItems = advItems;
      this.g = g;
      this.p = p;
   }

   public static long time = System.currentTimeMillis();

   public void msg(String m) {
      System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
   }

   public void playGame(){
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
         if(p.atpmountain ==true){
            p.stopFighting();
         }
         else{
            m.stopFighting();
         }
         try {sleep(100);} 
         catch (InterruptedException e) {e.printStackTrace();}

         if(fightingAdv!=null && fightingAdv.i.wants_game==true){
            int i = Integer.parseInt(fightingAdv.getName().substring(11)) - 1;
            playGame(); //now adventurer is fighting dragon
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
               g.endGame(fightingAdv);
            }
            else{//if adventurer loses
               msg("beat Adventurer " + (i+1) + " (dragonScore " + dragonDiceScore + ", adventurerScore " + adventurerDiceScore + ")");
               g.endGame(fightingAdv);
               try {sleep(500);} 
               catch (InterruptedException e) {e.printStackTrace();}
            } 
         }

      }
      msg("ends");
   }
}
