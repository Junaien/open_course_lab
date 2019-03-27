
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

// if(FloorClerk.timeToClose == true)  
// NOT USED USED ABOVE VARIABLE!!

class Customer implements Runnable
{
   //tried experimenting with atomic...
  // private final AtomicLong ticketAL = new AtomicLong();
    Random roll = new Random(); 
    protected int number;
    //needed for ordering issue
    boolean atStorageRoom = false;
    //At BALA, your not just our customers, your our guests... (^_^)
    public int guestNum;
    FloorClerk FloorClerk;
    public int defaultPriority;
    StorageClerks StorageClerks;
    protected static volatile int ticket = 0;
    protected int itemWeight = 10;
    //this is not used, tried experimenting...
   // volatile boolean busywait=true;
    
    public static long time = System.currentTimeMillis();

    
    Customer(int name, FloorClerk krej, StorageClerks sc)
    {
        guestNum = name;
        FloorClerk = krej; //jerk = krej
        StorageClerks = sc;
    }
    
    public void run()
    {
       
        try
        {   
            
            Thread.sleep((long)(Math.random() * 6000));
            msg("Arrived at the BALA showroom.");
            
        }
        catch(InterruptedException e){}
        
        whatToBuy();//wait for FloorClerk to open the StorageClerks door
        shoppingIsTiring(12000);
        payingForItems();
            
        shoppingIsTiring(10000);
           // waitingForJerk();

            preStorageHung();
           shoppingIsTiring(5000);
           
      //     iT.start();
           
            locStorageR();
           //for ordering issue
            shoppingIsTiring(5000);

          //for ordering issue
        shoppingIsTiring(3000);
        
    }
    

    public void whatToBuy()
    {
       shoppingIsTiring(4000);
        if(FloorClerk.waiting == false)
        {
            msg("Carousing the store.");
            
        }
        
        while(FloorClerk.waiting == false)
        {
            shoppingIsTiring(3000);
            
            if(FloorClerk.waiting == false)
            {
                msg("Haven't found quite what (s)he was looking for.");
                shoppingIsTiring(2000);
            }
            else
            {
                break;
            }
        }
    }
   /*
    * if item is heavy its roll must be 7,8,9,10, then 2 clerks needed
    * afterwards, we simulate by changing get/set priorities
    */
    public void payingForItems()
    {   
        try
        {   
            if ((StorageClerks.customerCount != StorageClerks.StorageClerksCount)
              &&(FloorClerk.assistCustomer == false))
            {   number = 1+roll.nextInt(10);
              if(number > 6) {
                 synchronized(this) 
                 {
                     //increase cust count and then add them to queue
                     ++StorageClerks.customerCount;
                     ++StorageClerks.customerCount;
                     StorageClerks.lineQue.add(this);
                     msg( "Rushing to cashier to pay for said HEAVY item.");
                 }
              }
              else
                synchronized(this) 
                {
                    //increase cust count and then add them to queue
                    ++StorageClerks.customerCount;
                    StorageClerks.lineQue.add(this);
                    msg( "Rushing to cashier to pay for said LIGHT item.");
                }
                
                defaultPriority = Thread.currentThread().getPriority(); 
                Thread.currentThread().setPriority(Thread.currentThread().getPriority() + 5);
                shoppingIsTiring((long)(Math.random() * 1000));
                Thread.currentThread().setPriority(defaultPriority);    
                
               
                //customer is now at storage room with storage clerks
                atStorageRoom = true;             
            }
        }
        catch (Exception e){}
    }
 /*   
    public void waitingForJerk()
    {
        while(FloorClerk.assistCustomer == false)
        {
            shoppingIsTiring(4000);  
        }
    } */
    
    //pre-storage room break
    public void preStorageHung()
    {
       synchronized(this) {
       if(atStorageRoom == true)
        {
          try {
             msg(" hungry... need food now.");
             
                 Thread.yield();
                 Thread.yield();
                
                 Thread.sleep((long)(Math.random()*8000));   
               } catch (InterruptedException e) {}
        }
       }
    }   
    
    
    
  /*  Thread iT = new Thread() {
       public void run()
       {
          int y = ticket;
       
       while(BalaMall.customerCount!=0) {
          ++ticket;
          try {
             Thread.sleep(800);
          } catch (InterruptedException e) {}
        
       }
    };
    */
    
    //At storage room, customer tickets must be synchronized
    public void locStorageR()
    {

       if(BalaMall.customerCount > 10)
          shoppingIsTiring(5000);
       synchronized(this) {
          
          // Thread[] threads = new Thread[StorageClerks.customerCount];
          for(int i = 0; i < BalaMall.customerCount; ++i) {
           ++ticket;
                 
             //ticket.incrementAndGet();
          
          }     
          msg( " has ticket #: " + ticket);
          
          try {
             Thread.sleep((long)(Math.random()*10));
             msg(" picking up item(s) at storage room.");
          } catch (InterruptedException e) {}   
       }
    
   }

    
    public void shoppingIsTiring(long sleep_time)
    {
        try
        { 
              Thread.sleep(sleep_time);       
         }
        catch(InterruptedException e){}
        
    }
    
    
    public int getName() 
    {
        return  guestNum ;
    }
    public void msg(String m)
    {
        System.out.println("["+ (System.currentTimeMillis()-time)/1000+ "]"+ "Customer " + guestNum + " : " + m);
    }

}
