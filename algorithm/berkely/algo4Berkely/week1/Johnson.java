import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
public class Johnson{
  public Johnson(String path){
    Scanner sc = null;
    try{
      sc = new Scanner ( new File(path));
    }catch(Exception e){
    }
    String[] s = sc.nextLine().split("\\s+");
    int size = Integer.valueOf(s[0]);
    Graph g = new Graph(size + 1);
    while(sc.hasNextLine()){
      s = sc.nextLine().split("\\s+");
      int from = Integer.valueOf(s[0]);
      int to = Integer.valueOf(s[1]);
      int weight = Integer.valueOf(s[2]);
      g.addEdge(from,to,weight);
    }
    for(int i = 1; i <= size; i++){
        g.addEdge(size+1, i, 0);
    }
    Bellmanford b = new Bellmanford(size + 1, g);
    int[] Bresult = b.shortestPath(size + 1);
    if(Bresult[0] == Integer.MAX_VALUE){
      System.out.println("negative cost cycle");
      System.exit(0);
    }

    for(int i = 1; i <= size; i++){
      for(Edge e : g.getAdjEdge(i)){
        int from = e.from();
        int to = e.to();
        e.setWeight(e.getWeight() + Bresult[from] - Bresult[to]);
        if(e.getWeight() < 0 )
        System.out.println(e.getWeight());
      }
    }
    int min = Integer.MAX_VALUE;

    for(int i = 1; i <= size; i++){
      Dijkstra d = new Dijkstra(g,i);
      int [] m = d.shortestPathCost();
      for(int k = 1; k <= size; k++){
        if(k!=i && m[k] != Integer.MAX_VALUE){
          int realCost = m[k]- (Bresult[i] - Bresult[k]);
          min = Math.min(min, realCost);
        }
      }
    }
    System.out.println(min);
  }
  public static void main(String[] args){
    Johnson j= new Johnson("g3.txt");
  }
}
