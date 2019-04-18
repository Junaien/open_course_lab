import Utilities.*;

class Beeper extends MyObject implements Runnable {

   private int beep = 0;

   public Beeper(String name, int beep) {
      super(name);
      this.beep = beep;
      System.out.println(getName() + " is alive, beep=" + beep);
      Thread t = new Thread(this);
      t.setPriority(t.getPriority()-1); // so main() thread has priority and
      t.start();                        // surely gets CPU later to stop() us
   }

   public void run() {
      long value = 1;
      System.out.println("age()=" + age() + ", " + getName() + " running");
      while (true) {
         if (value++ % beep == 0)
            System.out.println("age()=" + age()
                + ", " + getName() + " beeps, value=" + value);
      }
   }
}

class Beeping extends MyObject {

   public static void main(String[] args) {

      // parse command line arguments, if any, to override defaults
      GetOpt go = new GetOpt(args, "Ut:b:n:R:");
      String usage = "Usage: -t slice -b beep -n numBeepers -R runTime";
      go.optErr = true;
      int ch = -1;
      int numBeepers = 4;
      int beep = 100000;
      int timeSlice = 0;
      int runTime = 60;   // default in seconds
      while ((ch = go.getopt()) != go.optEOF) {
         if      ((char)ch == 'U') {
            System.out.println(usage);  System.exit(0);
         }
         else if ((char)ch == 'n')
            numBeepers = go.processArg(go.optArgGet(), numBeepers);
         else if ((char)ch == 'b')
            beep = go.processArg(go.optArgGet(), beep);
         else if ((char)ch == 't')
            timeSlice = go.processArg(go.optArgGet(), timeSlice);
         else if ((char)ch == 'R')
            runTime = go.processArg(go.optArgGet(), runTime);
         else {
            System.err.println(usage);  System.exit(1);
         }
      }
      System.out.println("Beeping: numBeepers=" + numBeepers
         + ", beep=" + beep + ", timeSlice=" + timeSlice
         + ", runTime=" + runTime);

      // enable time slicing Solaris; noop on Windows 95
      if (timeSlice > 0) ensureTimeSlicing(timeSlice); // so threads share CPU

      // the Beeper threads are started in the constructor
      for (int i = 0; i < numBeepers; i++) new Beeper("Beeper"+i, beep);
      System.out.println("All Beeper threads started");

      // let the Beepers run for a while
      nap(runTime*1000);
      System.out.println("age()=" + age()
         + ", time to stop the threads and exit");
      System.exit(0);
   }
}

/* ............... Example compile and run(s)

D:\>javac beep.java

D:\>java Beeping -R7
Java version=1.1.1, Java vendor=Sun Microsystems Inc.
OS name=Windows 95, OS arch=x86, OS version=4.0
Tue Jun 17 21:10:09 EDT 1997
Beeping: numBeepers=4, beep=100000, timeSlice=0, runTime=7
Beeper0 is alive, beep=100000
Beeper1 is alive, beep=100000
Beeper2 is alive, beep=100000
Beeper3 is alive, beep=100000
All Beeper threads started
age()=60, Beeper0 running
age()=60, Beeper1 running
age()=110, Beeper2 running
age()=110, Beeper3 running
age()=550, Beeper0 beeps, value=100001
age()=610, Beeper1 beeps, value=100001
age()=610, Beeper2 beeps, value=100001
age()=720, Beeper3 beeps, value=100001
age()=1100, Beeper0 beeps, value=200001
age()=1150, Beeper1 beeps, value=200001
age()=1210, Beeper3 beeps, value=200001
age()=1260, Beeper2 beeps, value=200001
age()=1700, Beeper3 beeps, value=300001
age()=1760, Beeper0 beeps, value=300001
age()=1810, Beeper1 beeps, value=300001
age()=1810, Beeper2 beeps, value=300001
age()=2310, Beeper3 beeps, value=400001
age()=2250, Beeper0 beeps, value=400001
age()=2360, Beeper1 beeps, value=400001
age()=2420, Beeper2 beeps, value=400001
age()=2800, Beeper0 beeps, value=500001
age()=2910, Beeper2 beeps, value=500001
age()=2860, Beeper3 beeps, value=500001
age()=2970, Beeper1 beeps, value=500001
age()=3350, Beeper0 beeps, value=600001
age()=3410, Beeper3 beeps, value=600001
age()=3460, Beeper2 beeps, value=600001
age()=3630, Beeper1 beeps, value=600001
age()=3850, Beeper0 beeps, value=700001
age()=4010, Beeper3 beeps, value=700001
age()=4070, Beeper2 beeps, value=700001
age()=4120, Beeper1 beeps, value=700001
age()=4450, Beeper0 beeps, value=800001
age()=4560, Beeper2 beeps, value=800001
age()=4670, Beeper3 beeps, value=800001
age()=4720, Beeper1 beeps, value=800001
age()=5000, Beeper0 beeps, value=900001
age()=5160, Beeper2 beeps, value=900001
age()=5220, Beeper1 beeps, value=900001
age()=5220, Beeper3 beeps, value=900001
age()=5600, Beeper0 beeps, value=1000001
age()=5770, Beeper1 beeps, value=1000001
age()=5770, Beeper2 beeps, value=1000001
age()=5770, Beeper3 beeps, value=1000001
age()=6210, Beeper0 beeps, value=1100001
age()=6260, Beeper3 beeps, value=1100001
age()=6320, Beeper1 beeps, value=1100001
age()=6370, Beeper2 beeps, value=1100001
age()=6700, Beeper0 beeps, value=1200001
age()=6810, Beeper2 beeps, value=1200001
age()=6870, Beeper3 beeps, value=1200001
age()=6920, Beeper1 beeps, value=1200001
age()=7030, time to stop the threads and exit

% javac beep.java

% java Beeping -R7
Java version=1.1_Final, Java vendor=Sun Microsystems Inc.
OS name=Solaris, OS arch=sparc, OS version=2.x
Tue Jun 17 11:37:01 PDT 1997
Beeping: numBeepers=4, beep=100000, timeSlice=0, runTime=7
Beeper0 is alive, beep=100000
Beeper1 is alive, beep=100000
Beeper2 is alive, beep=100000
Beeper3 is alive, beep=100000
All Beeper threads started
age()=55, Beeper0 running
age()=327, Beeper0 beeps, value=100001
age()=679, Beeper0 beeps, value=200001
age()=1030, Beeper0 beeps, value=300001
age()=1381, Beeper0 beeps, value=400001
age()=1732, Beeper0 beeps, value=500001
age()=2083, Beeper0 beeps, value=600001
age()=2434, Beeper0 beeps, value=700001
age()=2785, Beeper0 beeps, value=800001
age()=3135, Beeper0 beeps, value=900001
age()=3486, Beeper0 beeps, value=1000001
age()=3838, Beeper0 beeps, value=1100001
age()=4189, Beeper0 beeps, value=1200001
age()=4539, Beeper0 beeps, value=1300001
age()=4890, Beeper0 beeps, value=1400001
age()=5241, Beeper0 beeps, value=1500001
age()=5592, Beeper0 beeps, value=1600001
age()=5943, Beeper0 beeps, value=1700001
age()=6294, Beeper0 beeps, value=1800001
age()=6645, Beeper0 beeps, value=1900001
age()=6996, Beeper0 beeps, value=2000001
age()=7060, time to stop the threads and exit

% java Beeping -R7 -t1000
Java version=1.1_Final, Java vendor=Sun Microsystems Inc.
OS name=Solaris, OS arch=sparc, OS version=2.x
Tue Jun 17 11:37:10 PDT 1997
Beeping: numBeepers=4, beep=100000, timeSlice=1000, runTime=7
Scheduler: timeSlice=1000 randomSlice=false priority=10
Beeper0 is alive, beep=100000
Beeper1 is alive, beep=100000
Beeper2 is alive, beep=100000
Beeper3 is alive, beep=100000
All Beeper threads started
age()=73, Beeper0 running
age()=344, Beeper0 beeps, value=100001
age()=696, Beeper0 beeps, value=200001
age()=1047, Beeper0 beeps, value=300001
age()=1058, Beeper1 running
age()=1328, Beeper1 beeps, value=100001
age()=1679, Beeper1 beeps, value=200001
age()=2030, Beeper1 beeps, value=300001
age()=2068, Beeper2 running
age()=2339, Beeper2 beeps, value=100001
age()=2689, Beeper2 beeps, value=200001
age()=3040, Beeper2 beeps, value=300001
age()=3078, Beeper3 running
age()=3348, Beeper3 beeps, value=100001
age()=3700, Beeper3 beeps, value=200001
age()=4051, Beeper3 beeps, value=300001
age()=4428, Beeper0 beeps, value=400001
age()=4779, Beeper0 beeps, value=500001
age()=5412, Beeper1 beeps, value=400001
age()=5763, Beeper1 beeps, value=500001
age()=6421, Beeper2 beeps, value=400001
age()=6772, Beeper2 beeps, value=500001
age()=7077, time to stop the threads and exit
                                            ... end of example run(s)  */
