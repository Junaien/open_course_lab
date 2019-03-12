import java.util.Scanner;
import java.io.File;
public class Mwis{

    public Mwis(String path){
      Scanner sc = null;
      try{
        sc = new Scanner(new File(path));
      }catch(Exception e){

      }
      int []sum;
      int []weight;
      String[] s = sc.nextLine().split("\\s+");
      int size = Integer.valueOf(s[0]);
      sum = new int[size+1];
      weight = new int[size+1];
      int i = 1;
      while(sc.hasNextLine()){
        s = sc.nextLine().split("\\s+");
        int w = Integer.valueOf(s[0]);
        weight[i++] = w;
      }

      sum[1] = weight[1];
      sum[0] = 0;
      boolean [] set = new boolean[sum.length];
      for(int z = 2; z < sum.length; z++){
        int include = sum[z-2] + weight[z];
        int exclude = sum[z-1];
        sum[z] = Math.max(include,exclude);
      }
      int z = sum.length -1;
      while(z >= 1){
        if(sum[z] == sum[z-1]){
          z--;
        }else{
          set[z] = true;
          z -= 2;
        }
      }
      for(int k = 0; k < set.length; k++){
      if(k == 1 || k==2 ||k==3 ||k==4 ||k==17 ||k==117 ||k==517 ||k==997){
        System.out.println(k + ": " + set[k]);
      }
    }
      // if(set[2]==true)set[1] = false;
      // else set[1] = true;
      // System.out.println(set[1]);
        // System.out.println(sum[sum.length-1]);



  }
    public static void main(String[] args){
      Mwis m = new Mwis("mwis.txt");
    }
}
