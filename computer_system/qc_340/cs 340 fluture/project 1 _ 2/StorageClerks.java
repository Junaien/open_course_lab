
import java.util.*;


public class StorageClerks 
{
    public int StorageClerksCount;
    public int customerCount = 0;
    public static long time = System.currentTimeMillis();
    Queue<Customer> lineQue = new LinkedList<Customer>();
  //  protected volatile int ticket = 0;
    
    public StorageClerks(int count)
    {
       StorageClerksCount = count;
    }
    
    public void run()
    {
       try {
          Thread.sleep((long)(Math.random()*6000));   
       }
       catch(InterruptedException e){}
    //   locStorageR();
    }
    
    
  /*  public void locStorageR()
    {
       synchronized(this) {
          
     //     Thread[] threads = new Thread[StorageClerks.customerCount];
          for(int i = 0; i < BalaMall.customerCount; ++i) {
             ++ticket;
          //msg( " has ticket #: " + ticket);
          
          }     
          msg( " has ticket #: " + ticket);  
       }
          waitingonlazycustomer(5000);
            msg(" picking up item(s) at storage room.");
     
    } */
    
    public void waitingonlazycustomer(long sleep_time)
    {
        try
        { 
              Thread.sleep(sleep_time);       
         }
        catch(InterruptedException e){}
        
    }
    
    public void msg(String m)
    {
        System.out.println("["+ (System.currentTimeMillis()-time)/1000 + "]" + StorageClerksCount + " " + m);
    }
    
    public int getName() 
    {
        return  StorageClerksCount;
    }
}
