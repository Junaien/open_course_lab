import java.util.Random;


public class items {
   Random r = new Random();
   
   int precStone = 0;
   int ring = 0;
   int necklace= 0;
   int fortuneSize;
   int numTreasures = 0;
   boolean needs_assistance = false;
   boolean wants_fight = false;


   public items(int fortune_size){
      this.fortuneSize = fortune_size;
      this.precStone = r.nextInt(3);//generate random number between 0 and 2
      this.ring = r.nextInt(3);//generate random number between 0 and 2
      this.necklace = r.nextInt(3);//generate random number between 0 and 2
   }
   
}
