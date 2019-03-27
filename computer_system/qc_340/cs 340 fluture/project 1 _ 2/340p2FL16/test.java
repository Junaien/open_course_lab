//wait for student thread end
        for(Thread thr: threads)
        {
            try
            {
                thr.join(); 
            }
            catch(InterruptedException e){}
        }
        //test if the student thread is alive
        for(Thread thr: threads)
        {
            msg("Is the "+ thr.getName()+ " is alive? "+ thr.isAlive() );
        }
        //wait for instructor thread  end
        try
        {
            thread_instr.join();
            msg("Instructor is going home.");
        }
        catch(InterruptedException e){}
     
      msg("-------------thread end----------------"); 





