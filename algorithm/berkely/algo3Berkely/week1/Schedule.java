import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
public class Schedule{
  private long total;
  private ArrayList<Job> jobs;
  public Schedule(String path){
    total = 0;
    jobs = new ArrayList<Job>();
    Scanner sc = null;
    try{
      sc = new Scanner(new File(path));
    }catch(Exception e){

    }
    while(sc.hasNextLine()){
      String s = sc.nextLine();
      String[] params = s.split("\\s+");
      int weight = Integer.valueOf(params[0]);
      int length = Integer.valueOf(params[1]);
      jobs.add(new Job(weight,length));
    }
    Collections.sort(jobs);
    int completeT = 0;
    for(int i = 0; i < jobs.size(); i++){
        Job j = jobs.get(i);
        completeT += j.getLength();
        total += j.getWeight()*completeT;
    }
  }

  public long total(){
    return total;
  }
  public static void main(String[] args){
    Schedule s = new Schedule("jobs.txt");
    System.out.println(s.total());
  }
}
