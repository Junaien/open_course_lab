
public class clerk extends Thread{
   int num_adv;
   shop s;
   items advItems [];
   private boolean isClerkNeeded = true;

   public void setIsClerkNeeded(boolean f){
      isClerkNeeded = f;
   }

   public clerk(String threadName, int num_adv, shop s, items advItems[]){
      super(threadName);
      this.num_adv = num_adv;
      this.s = s;
      this.advItems = advItems;
   }

   public static long time = System.currentTimeMillis();

   public void msg(String m) {
      System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
   }

   public void run(){
      msg("begins");
      while(isClerkNeeded){
         for(int i = 0; i<num_adv; i++){
            if(mainprog.adventurers[i].i.needs_assistance){
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
               s.stopShopping(mainprog.adventurers[i]);
            }
         }        
      }      
      msg("ends");
   }
}