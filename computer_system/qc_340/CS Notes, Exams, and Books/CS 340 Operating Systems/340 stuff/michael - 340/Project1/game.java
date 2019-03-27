
public class game {
   public game(){
      
   }
   
   public synchronized void doGame(adventurer a){
      dragon.fightingAdv = a;
      a.i.wants_game = true;
      a.msg( " waits at game.");
       while(a.i.wants_game==true){
         try {         
            this.wait();
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
       }
      a.msg(" is free from game.");
   }
   
   public synchronized void endGame(adventurer a){
      a.i.wants_game= false;
      this.notifyAll();
   }
   
   
   
   
   
   
   
   
   
   
   
}
