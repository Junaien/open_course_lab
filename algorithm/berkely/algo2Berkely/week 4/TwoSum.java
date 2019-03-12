import java.util.HashSet;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.io.File;
public class TwoSum{
    private ArrayList<Long> a;
    public TwoSum(String path){
        a = new ArrayList<Long>();
        Scanner sc = null;
        try{
          sc = new Scanner(new File(path));

        }catch(Exception e){

        }
        while(sc.hasNextLine()){
            a.add(Long.valueOf(sc.nextLine()));
        }
        Collections.sort(a);
    }

    public int sumCountWithRange(int l, int r){
      HashSet<Long> b = new HashSet<Long>();
      int c = 0;
      int i = 0, j = a.size()-1;
      while(i != j && a.get(i) != a.get(j)){
        long sum = a.get(i) + a.get(j);
        if(sum < l){
          i++;
        }else if(sum > r){
          j--;
        }else{
          for(int z = j; z > i ; z--){
            long s = a.get(z) + a.get(i);
            if(s < l)break;
            if(!b.contains(s)){
              b.add((Long)s);
              c++;
            }
          }
          i++;
        }
      }
      return c;
    }
    private int findLowBarOfI(int i, int j, int lowBarOfI,  int l){
        if(lowBarOfI == -1){
          lowBarOfI = i;
        }
        int g;
        for(g = lowBarOfI + 1; g <= j; g++){
          if(a.get(i) + a.get(g) < l)continue;
          else break;
        }
        if(g  > j)return -1;
        else return g;

    }
    public static void main(String[] args){
      TwoSum t = new TwoSum("two_sum.txt");
      long h = System.currentTimeMillis();
      System.out.println("Start....");
      System.out.println(t.sumCountWithRange(-10000,10000));
      System.out.println(System.currentTimeMillis() - h);
    }


}
