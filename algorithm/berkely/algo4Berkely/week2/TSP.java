import java.util.Scanner;
import java.util.HashMap;
import java.io.File;
public class TSP{
  public static int vertSize;
  private Vertex[] verts;
  public TSP(String path){
      Scanner sc = null;
      try{
        sc = new Scanner(new File(path));
      }catch(Exception e){
      }
      int n = Integer.valueOf(sc.nextLine());
      vertSize = n;
      verts = new Vertex[n + 1];
      int c = 1;
      while(sc.hasNextLine()){
        String[] s = sc.nextLine().split("\\s+");
        double X = Double.valueOf(s[0]);
        double Y = Double.valueOf(s[1]);
        verts[c++] = new Vertex(X,Y);
      }
      // System.out.println(distance(1,24));
      double min =  Double.MAX_VALUE;
      int minI = -1;
      HashMap<Integer,Double> map = new HashMap<>();
      for(int j = 2; j <= n; j++){
        double coming = calculateTSP(map,j,(int)Math.pow(2,vertSize)-1) + distance(j,1);
        if(coming < min){
          min = coming;
          minI = j;
        }

        // System.out.println();
      }
      System.out.println("minimum TSP: " + min);
      reconstructPath(map,minI);
  }

  private Double calculateTSP(HashMap<Integer,Double>map, int j, int set){
      //base case
      if(j == 1){
        if(set == 1)return Double.valueOf(0.0);
        else return Double.MAX_VALUE;
      }
      int subp = encodeSub(set,j);
      Double result = map.get(subp);
      if(result != null)return result;
      Double min = Double.MAX_VALUE;
      int enc = set;
      int minIndex = -1;
      for(int i = 1; i <= vertSize; i++){
        int V = enc % 2;
        enc = enc / 2;
        if(V == 1 && i != j){
          // System.out.println(j + " call " + i + " set: " + (set ^ (1 << (i-1))));
          Double answer = calculateTSP(map, i, set ^ (1 << (j-1)));
          // System.out.println(j + " call " + i + " set: " + (set ^ (1 << (i-1))) + " return answer " + answer);
          if(answer == Double.MAX_VALUE)continue;
          else{
             min = Math.min(answer + distance(j,i),min);
             minIndex = i;
           }
        }
      }
      int encode = encodeSub(set,j);
      map.put(encode,min);
      // System.out.println(" <- " + minIndex);
      return min;
  }
  private void reconstructPath(HashMap<Integer,Double> map, int lastIndex){
      int c = vertSize;
      double cost = map.get((int)Math.pow(2,vertSize) - 1 + (lastIndex << vertSize));
      int set = ((int)Math.pow(2,vertSize) -1) ^ (1 << lastIndex -1);
      while( c > 1){
        System.out.print(lastIndex + " <- ");
        int enc = set;
        for(int j = 1; j <= vertSize; j++){
          int V = enc % 2;
          if(V == 1){
            if(map.get(encodeSub(set,j)) != null && Math.abs(map.get(encodeSub(set,j)) - cost + distance(lastIndex,j)) < 0.01){
              lastIndex = j;
              cost = map.get(encodeSub(set,j));
              set = set ^ (1 << j -1);
              break;
            }
          }
          enc /= 2;
        }
        c--;
      }
      System.out.println("1");

  }
  private double distance(int x ,int y){
    return Math.pow(Math.pow(verts[x].X - verts[y].X, 2) + Math.pow(verts[x].Y - verts[y].Y, 2),0.5);
  }
  private int encodeSub(int set, int j){
    return set + (j << vertSize);
  }
  public static void main(String[] args){
      TSP tsp = new TSP("tsp.txt");
  }
}
