import java.util.Scanner;
import java.util.HashSet;
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
      boolean[] visited = new boolean[n+1];
      int c = 1;
      while(sc.hasNextLine()){
        String[] s = sc.nextLine().split("\\s+");
        double X = Double.valueOf(s[1]);
        double Y = Double.valueOf(s[2]);
        verts[c++] = new Vertex(X,Y);
      }
      double cost = 0;
      int lastIndex = 1;
      double min =  Double.MAX_VALUE;
      int minI = -1;
      int i = n;
      visited[1] = true;
      while(i > 1){
        min  = Long.MAX_VALUE;
        minI = -1;
        for(int j = 2;j < visited.length; j++){
          if(visited[j] == true)continue;
          double d = distanceSquare(lastIndex,j);
          if(d < min){
            minI = j;
            min = d;
          }
        }
        visited[minI] = true;
        cost += Math.pow((double)min,0.5);
        lastIndex = minI;
        i--;

      }
      cost += distance(lastIndex,1);
      System.out.println("minimum TSP: " + cost);
  }



  private double distance(int x ,int y){
    return Math.pow(Math.pow(verts[x].X - verts[y].X, 2) + Math.pow(verts[x].Y - verts[y].Y, 2),0.5);
  }
  private double distanceSquare(int x ,int y){
    return Math.pow(verts[x].X - verts[y].X, 2) + Math.pow(verts[x].Y - verts[y].Y, 2);
  }
  private int encodeSub(int set, int j){
    return set + (j << vertSize);
  }
  public static void main(String[] args){
      TSP tsp = new TSP("tsp.txt");
  }
}
