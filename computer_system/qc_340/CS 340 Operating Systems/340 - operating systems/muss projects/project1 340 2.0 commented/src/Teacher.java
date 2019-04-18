
public class Teacher extends Thread {

	public Teacher(){
	}
	//int report = 0;
	public static long time = System.currentTimeMillis();
	public void msg(String m) {
	System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+":"+m);
	}
	
	public void run(){
	while(isAlive()){ // run until end of day to constantly check what time to start and end class	
		try
		{
        //class 1
	    if(Project1.age()>=7000 && Project1.age()<=10000){
	    	Project1.class1=0;
	    }
	    else if(Project1.age()>10001 && Project1.age()<18000){
	    		Project1.class1=1; 
	    	}
	    else if(Project1.age()>18000){
	    	Project1.class1=2;//leave/dismiss class - signal to exit class
	    }
		// enter class 2 - end student busy wait
	    
	    if(Project1.age()>=23000 && Project1.age()<=26000){
	    	Project1.class2=0;
	    }
	    else if(Project1.age()>=26001 && Project1.age()<=34000){
	    		Project1.class2=1;
	    	}
	    else if(Project1.age()>34000){
	    	Project1.class2=2;//leave/dismiss class - signal to exit class
	    }
	    // Notice office hour is length of 10,000 milliseconds
	    //class 3
	    if(Project1.age()>=44000 && Project1.age()<=47000){
	 	    	Project1.class3=0;
	 	    }
	 	    else if(Project1.age()>=47001 && Project1.age()<=55000){
	 	    		Project1.class3=1;
	 	    	}
	 	    else if(Project1.age()>55000){
	 	    	Project1.class3=2;//leave/dismiss class - signal to exit class
	 	    }
	 	//class 4
	 	if(Project1.age()>=60000 && Project1.age()<=63000){
	 		 	 Project1.class4=0;
	 	   }
	 		 	 else if(Project1.age()>=63001 && Project1.age()<=71000){
	 		 	        Project1.class4=1;
	 		 	    }
	 		 	 else if(Project1.age()>71000){
	 		 	    	Project1.class4=2;//leave/dismiss class - signal to exit class
	 		 	 }
	 	
	 	//class 5
	 	if(Project1.age()>=76000 && Project1.age()<=79000){
	 		 	 Project1.class5=0;
	 		}
	 		 	else if(Project1.age()>=79001 && Project1.age()<=87000){
	 		 		   Project1.class5=1;
	 		 		 }
	 		 	else if(Project1.age()>87000){
	 		 		  Project1.class5=2; //leave/dismiss class - signal to exit class 	    	
	 		 	    
	    }
	  
		
		
		//take a break till next class
		
		//wait till 10 o'clock for second class
		
        // enter class - end student BW
		
		//leave/dismiss class - signal to exit class
		
		//take a break till next class
	    	 Teacher.sleep(1);
	    	 
	    		
	 	    }
	    
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
	
}
}
}