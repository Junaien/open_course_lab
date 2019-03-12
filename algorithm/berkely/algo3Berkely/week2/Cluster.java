import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.util.Scanner;

public class Cluster{
    private int size;
    private ArrayList<Edge>edges;
    public Cluster(String path){
      edges = new ArrayList<Edge>();
      Scanner sc = null;
      try{
        sc = new Scanner ( new File(path));
      }catch(Exception e){

      }
      String[] s = sc.nextLine().split("\\s+");
      this.size = Integer.valueOf(s[0]);

      while(sc.hasNextLine()){
        s = sc.nextLine().split("\\s+");
        int from = Integer.valueOf(s[0]);
        int to = Integer.valueOf(s[1]);
        int weight = Integer.valueOf(s[2]);
        Edge e = new Edge(from, to, weight);
        edges.add(e);
      }
      Collections.sort(edges);
    }

    public int score(int k_cluster){
      UF uf = null;
      uf = new UF(this.size);
      if(k_cluster > size)return -1;
      int c = size;
      int i = 0;
      while(c != k_cluster){
          Edge e = edges.get(i);
          int from = e.from();
          int to = e.to();
          if(!(uf.find(from) == uf.find(to))){
            uf.union(from,to);
            c--;
          }
          i++;
      }

      Edge e = edges.get(i);
      int from = e.from();
      int to = e.to();
      while((uf.find(from) == uf.find(to))){
        i++;
        e = edges.get(i);
        from = e.from();
        to = e.to();
      }

      System.out.println(uf);
      return edges.get(i).getWeight();
    }

    public static void main(String[] args){
      Cluster c = new Cluster("clustering.txt");
      System.out.println(c.score(4));
    }
}
