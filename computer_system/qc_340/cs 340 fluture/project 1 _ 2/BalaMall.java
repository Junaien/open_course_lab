
/*
 * For the purposes of my short "funny" story,
 * FloorClerk number is kept at one.
 * Other threads can have different values.
 */

public class BalaMall 
{
    
    private static long time = System.currentTimeMillis();
    public static int customerCount = 7;// = 5;
    public static int StorageClerksCount = 6;// = 10;
    public static int FloorClerk = 1;
    

    public static void main(String[] args) 
    {
         customerCount = Integer.parseInt(args[0]); 
         StorageClerksCount=Integer.parseInt(args[1]);
      
         /*If we enable multiple FloorClerks,
          * then just make believe they are all twins... (^-^)
          */
        // FloorClerk=Integer.parseInt(args[2]);
        StorageClerks StorageClerks = new StorageClerks(StorageClerksCount);
        FloorClerk floorClerkJerk = new FloorClerk(FloorClerk);
        Thread threadFCJ = new Thread(floorClerkJerk);
        threadFCJ.start();
        Thread[] threads = new Thread[customerCount];
        System.out.println("***Welcome Shoppers to BALA, at BALA mi casa su casa!***");
        for(int i = 0; i< customerCount; i++)
        {
            threads[i] = new Thread(new Customer(i+1, floorClerkJerk, StorageClerks));
            
            threads[i].start();
        }
            
       
        //customers leave 1 by 1, but customer 1 has to leave last and
        //signal to floorclerkjerk/storageclerks to close the store.
        for(int i= 0; i < threads.length; ++i)
        {
            try
            {  //tried figuring out making first thread leave last
              //  if(threads[i] == threads[0])
           //        threads[i].yield();
                if(i > 0) {
                threads[i].join();
                //use .split or will receive "thread-# left the store
                msg("Customer " + threads[i].getName().split("-")[1] + " left the store."); 
                }     
             }
            catch(InterruptedException e){}
        }
        
        for(int j= 0; j < threads.length; ++j) {
           if(j == 0) {
              try {
               threads[j].join();
               //use .split or will receive "thread-# left the store
               msg("Customer " + threads[j].getName().split("-")[1] + " left the store.");
               msgy("I'm the last customer, goodnight!");
               Thread.sleep(1000);
               threadFCJ.join();
            //   Instructor.itsAllOver();
                msgz("Thank you, have a nice day!");
                msgi("Go home already, is TacoBell still open?");
              }
              catch (InterruptedException e) {}
           }
              }
        }
       
     /*   for(int i = threads.length; i > threads.length-1; --i)
        {   
   //       if(threads[i] == threads[0])
          //   msg("I'm the last (first) customer, goodnight!");
        //   if(thr++ == threads.length - 1)
        //  }
        //Floor-clerk and storage clerk will close store and leave
        try
        {
           msgy("I'm the last (first) customer, goodnight!");
           Thread.sleep(1000);
           threadFCJ.join();
        //   Instructor.itsAllOver();
            msgz("Thank you, have a nice day!");
            msgi("Go home already, is TacoBell still open?");
            
        }
        catch(InterruptedException e){}
        }
     }      */
    
    public static void msg(String m)
    {
        System.out.println("["+ (System.currentTimeMillis()-time)/1000 + "]"+ "PSA : " + m);
    }
    public static void msgi(String m)
    {
        System.out.println("["+ (System.currentTimeMillis()-time)/1000 + "]"+ "FloorClerkJerk : " + m);
    }
    public static void msgy(String m)
    {
        System.out.println("["+ (System.currentTimeMillis()-time)/1000 + "]"+ "Customer 1 : " + m);
    }
    public static void msgz(String m)
    {
        System.out.println("["+ (System.currentTimeMillis()-time)/1000 + "]"+ "StorageClerkGuy : " + m);
    }
}
