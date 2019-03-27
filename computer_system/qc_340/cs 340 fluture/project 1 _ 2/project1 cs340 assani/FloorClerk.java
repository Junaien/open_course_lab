
import java.util.*;

//using implements as I just want the benefits of Threads
//and not to be limited to just Thread class
public class FloorClerk implements Runnable
{
    public int FloorClerkCount;
    private static long time = System.currentTimeMillis();
    public boolean waiting = false;
    boolean assistCustomer = false;
    //timetoclose not used
  //  boolean timeToClose = false;
    volatile boolean busywait=true;
    
   public FloorClerk(int count)
   {
      FloorClerkCount = count;
   }
    public void run()
    {
        try
        { 
              Thread.sleep((long)(Math.random()*5000));   
              
        }
        catch(InterruptedException e){} 
        
        //assist civilians (customers)
        shiftStarts();        
        //this job stinks
        playingPhoneGames(10000);
        //assisted all civilians, now time to get that magical hat
        closingTime();
        
    }
    
    public void shiftStarts()
    {
        try {
         Thread.sleep((long)(Math.random() * 12000));
         waiting = true;
         msg("*playing game on phone*");
         Thread.sleep((long)(Math.random() * 6000));
        
         msg("Time to get my daily 'CastleMania' points");
         Thread.sleep((long)(Math.random() * 6000));
         msg("*a customer approachs*");
         Thread.sleep((long)(Math.random() * 4000));
         msg("what do you want?");
         Thread.sleep((long)(Math.random() * 4000));
         msg("Giving slips to customer(s) in line...");
      } catch (InterruptedException e) {}
    }
    
    public void playingPhoneGames(int sleep_time)
    {
        try{ 
              Thread.sleep(sleep_time);       
          }
        catch(InterruptedException e){}
        
    }
   
    
    public void closingTime()
    {
        /* while(busywait){Thread.yield();};
        Thread.yield();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e1) {}
                Thread.yield();
       */
        //allow customer to pay for item and take it home
         try
         {
            msg("Back to playing phone games... when do I get my check?");
             Thread.sleep((long)(Math.random() * 1000));
         }
         catch(InterruptedException e){}   
       
         assistCustomer = true;
       //still playing phone games until time to close store
        try
        {
            Thread.sleep((long)(Math.random() * 7000));
        }
        catch(InterruptedException e){}
      
     //  timeToClose = true;
    
    }
    
    public static void itsAllOver()
    {
       msg("Closing the store, is TacoBell still open?");
    }
    
    public static void msg(String m)
    {
        System.out.println("["+ (System.currentTimeMillis()-time)/1000 + "]" + "FloorClerkJerk : " + m);
    }
    
}
