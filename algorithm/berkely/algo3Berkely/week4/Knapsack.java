import java.util.Scanner;
import java.util.HashMap;
import java.io.File;
public class Knapsack{
  private long capacity;
  public Knapsack(String path){

      Scanner sc = null;
      try {
        sc = new Scanner(new File(path));
      }catch(Exception e){

      }

      String[]s = sc.nextLine().split("\\s+");
      this.capacity = Long.valueOf(s[0]);
      int item_size = Integer.valueOf(s[1]);
      int[] value = new int[item_size];
      int[] weight = new int[item_size];
      int i = 0;
      while(sc.hasNextLine()){
        s = sc.nextLine().split("\\s+");
        value[i] = Integer.valueOf(s[0]);
        weight[i] = Integer.valueOf(s[1]);
        i++;
      }
      HashMap<Long,Integer> oldTable = new HashMap<>();
      // HashMap<Integer,Integer> newTable = new HashMap<>();
      // for(int ipt = 0; ipt < item_size; ipt++){
      //   newTable = new HashMap<Integer,Integer>();
      //   for(int w = 1; w <= capacity; w++){
      //
      //       Integer exclude = oldTable.get(w);
      //       if(exclude == null)exclude = 0;
      //       if(weight[ipt] > w){
      //         if(exclude != 0)newTable.put(w,exclude);
      //       }else{
      //         Integer include = oldTable.get(w-weight[ipt]);
      //         include = ((include == null)? 0:include) + value[ipt];
      //         Integer max = Math.max(exclude,include);
      //         if(max != 0)newTable.put(w,max);
      //       }
      //   }
      //   oldTable = newTable;
      //  }

      System.out.println(calculate(item_size-1,capacity,oldTable,value,weight));
}
private int calculate(long i, long w, HashMap<Long,Integer> map, int[] value, int[] weight){
  //base case
  if(i < 0)return 0;
  if(w <= 0)return 0;
  Integer result = map.get(convertXYNumber(i,w));
  if(result != null)return result;

  int exclude = calculate(i-1,w,map,value,weight);
  if(weight[(int)i] > w){
      map.put(convertXYNumber(i,w),exclude);
      return exclude;
  }else{

    int include = calculate(i-1,w-weight[(int)i],map,value,weight);
    include = include + value[(int)(i)];
    int max = Math.max(include,exclude);
    map.put(convertXYNumber(i,w),max);
    return max;
  }

}
private long convertXYNumber(long r,long c){
  return r * capacity + c;
}
public static void main(String[] args){
  Knapsack k = new Knapsack("Knapsack.txt");
}
}
