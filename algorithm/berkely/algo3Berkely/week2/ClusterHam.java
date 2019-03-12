import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.util.Scanner;
import java.util.HashMap;

public class ClusterHam{
    private int bits;
    private int cleaner;
    public ClusterHam(String path){
      HashMap<Integer,Integer> map = new HashMap<>();
      ArrayList<Integer> codes = new ArrayList<>();
      codes.add(-1);
      Scanner sc = null;
      try{
        sc = new Scanner ( new File(path));
      }catch(Exception e){

      }
      String[] s = sc.nextLine().split("\\s+");
      int size = Integer.valueOf(s[0]);
      UF uf = new UF(size);
      this.bits = Integer.valueOf(s[1]);
      this.cleaner = (int)Math.pow(2,bits) -1;

      int i =1;
      while(sc.hasNextLine()){
        s = sc.nextLine().split("\\s+");
        int num  = convertBitToInt(s);
        if(!map.containsKey(num)){
          map.put(num,i++);
          codes.add(num);
        }else{
          i++;
          codes.add(-1);
          size--;
        }
      }
      // System.out.println(codes.size());
      // System.out.println(map.size());

      // System.out.println(map.size());
      for(int j = 1; j < codes.size(); j++){
        if(codes.get(j) == -1){
          continue;
        }else{
          size -= checkAdjacent(codes.get(j),j,map,uf);
        }
      }
      System.out.println(size);
      // String[] ar = {"1","1","1","1","1","1","1","1",
      //                                     "1","1","1","1","1","1","1","1",
      //                                     "1","1","1","1","1","1","1","1"};
      // System.out.println(convertBitToInt(ar));

    }
    private int convertBitToInt(String[] s){
      int num  = 0;
      for(int i = bits-1; i >=0; i--){
        num += (Math.pow(2, bits - i -1) * Integer.valueOf(s[i]));
      }
      return num;
    }
    private int checkAdjacent(int code,int n, HashMap<Integer,Integer> map, UF uf){
      // System.exit(0);

      int s = 0;
      //check in one distance
      for(int i = 0; i <bits; i++){
        int temp = code;
        temp = temp ^ (1 << i);
        // System.out.println(temp);
        Integer position = map.get(temp);
        if((position != null) && (uf.find(position) != uf.find(n))){
          s++;
          uf.union(n,position);
        }
      }

      //check in two distance
      for(int i = 0; i <bits -1; i++){
        int left = 1 << i;
        for(int j = i + 1; j < bits; j++){
          int temp = code;
          temp = temp ^ (left | (1 << j));
          Integer position = map.get(temp);
          if((position != null) && (uf.find(position) != uf.find(n))){
            s++;
            uf.union(n,position);
          }
        }
      }
      // System.out.println(s);
      return s;
    }



    public static void main(String[] args){
      ClusterHam c = new ClusterHam("clustering_big.txt");
    }
}
