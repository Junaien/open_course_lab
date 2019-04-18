import java.util.concurrent.Semaphore;


public class mainprog {
   static adventurer adventurers[];
   static  Semaphore [] leave;
   public static void main(String [] args){
      int num_adv = 0;
      int fortune_size = 0;
      if(args.length>0){
         num_adv = Integer.parseInt(args[0]);
         fortune_size = Integer.parseInt(args[1]);
      }
      else throw new IllegalArgumentException ("Must enter values for number of adventurers and fortune_size");
      
      items advItems[] = new items[num_adv];
      for(int i = 0; i<num_adv; i++){
         advItems[i] = new items(fortune_size);
      }     
         
      Semaphore c1 = new Semaphore(0); //clerk semaphore
      
      Semaphore a1 = new Semaphore(0); //adventurer semaphore for signaling needs assistance at shop
      
      Semaphore d1 = new Semaphore(0); //dragon semaphore
      
      Semaphore a2 = new Semaphore(0); //adventurer semaphore for signaling wants a fight with dragon
          
      Semaphore mutex = new Semaphore(1); //mutex semaphore to prevent multiple adventurers from trying to interact with clerk at same time
      Semaphore mutex2 = new Semaphore(1); //mutex2 semaphore to prevent multiple adventurers from trying to fight dragon at same time
           
      leave = new Semaphore [num_adv+1];
      for(int i = 1; i<=num_adv; i++){
         leave[i] = new Semaphore(0);
      }
      
      adventurers = new adventurer[num_adv];
      for(int i = 0; i<num_adv; i++){
         adventurers[i] = new adventurer("Adventurer " + (i+1), advItems[i], num_adv, c1, a1, d1, a2, mutex, mutex2); //pass each adventurer their items and the semaphores
         adventurers[i].start();
      }
      
     clerk c = new clerk("Clerk", num_adv, advItems, c1, a1);
     c.start();
   
     dragon d = new dragon("Dragon", num_adv, advItems, d1, a2);
     d.start();
      
      System.out.println("Main program ends");
   }
}
