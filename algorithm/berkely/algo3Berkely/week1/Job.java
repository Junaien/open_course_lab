public class Job implements Comparable<Job>{
  private int weight;
  private int length;

  public Job(int w, int l){
    weight = w;
    length = l;
  }
  public int compareTo(Job j){
    int determinantM =  weight - length;
    int determinantO = j.weight - j.length;
    if(determinantM > determinantO)return -1;
    else if(determinantM < determinantO)return 1;
    else{
      if(weight > j.weight)return -1;
      else if(weight < j.weight)return 1;
      else return 0;
    }


  }

  public int getLength(){
    return length;

  }
  public int getWeight(){
    return weight;

  }

}
