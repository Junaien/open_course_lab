
public class Teacher extends Thread {

	public Teacher(){
	}
	
	public static long time = System.currentTimeMillis();
	public void msg(String m) {
	System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+":"+m);
	}
	
	public void run(){
	while(isAlive()){	
		try
		{

		// wait till 8 o'clock for first class
	    if(Project1.age()>=7000 && Project1.age()<=10000){
	    	Project1.class1=0;
	    }
	    else if(Project1.age()>10001 && Project1.age()<18000){
	    		Project1.class1=1;
	    	}
	    else if(Project1.age()>18000){
	    	Project1.class1=2;
	    }
		// enter class - end student busy wait
	    Teacher.sleep(5000);
	    
	    if(Project1.age()>=17000 && Project1.age()<=20000){
	    	Project1.class2=0;
	    }
	    else if(Project1.age()>=20001 && Project1.age()<=28000){
	    		Project1.class2=1;
	    	}
	    else if(Project1.age()>28000){
	    	Project1.class2=2;
	    }
		//leave/dismiss class - signal to exit class
		
		//take a break till next class
		
		//wait till 10 o'clock for second class
		
        // enter class - end student BW
		
		//leave/dismiss class - signal to exit class
		
		//take a break till next class
		}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
	
}
}
}