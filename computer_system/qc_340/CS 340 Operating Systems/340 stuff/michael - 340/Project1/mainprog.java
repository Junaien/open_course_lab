
public class mainprog {
   static adventurer adventurers[];
   static clerk c;
   static dragon d;

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

      adventurers = new adventurer[num_adv];

      shop s = new shop();
      mountain m = new mountain();
      priorityMountain p = new priorityMountain();
      game g = new game();

      for(int i = 0; i<num_adv; i++){
         adventurers[i] = new adventurer("Adventurer " + (i+1), advItems[i], s, m, num_adv, g, p); //pass each adventurer their own items and the same shop object and same mountain object
         adventurers[i].start();
      }

      c = new clerk("Clerk", num_adv, s, advItems);
      c.start();

      d = new dragon("Dragon", num_adv, m, advItems, g, p);
      d.start();

      System.out.println("Main program ends");
   }
}

