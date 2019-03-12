import java.util.ArrayList;
public class Bellmanford{
    private int V;
    private Graph g;
    public Bellmanford(int V,Graph g){
      this.g = g;
      this.V = V;
    }
    public int[] shortestPath(int source){
        int[] shortestPathResultO = new int [V+1];
        int[] shortestPathResultN = new int [V+1];
        int[] shortestPathResultT = null;
        //base case
        for(int i = 0; i < shortestPathResultO.length;i++){
          shortestPathResultO[i] = Integer.MAX_VALUE;
          shortestPathResultN[i] = Integer.MAX_VALUE;
        }
        shortestPathResultO[source] = 0;
        shortestPathResultN[source] = 0;

        for(int i = 1; i <= V; i++){
            // for(int z = 0; z < shortestPathResultN.length; z++){
            //   System.out.print(shortestPathResultN[z] + ", ");
            // }
            // System.out.println();
            shortestPathResultT = new int[V+1];
            for(int j = 1; j <= V; j++){
              for(Edge e:g.getAdjEdge(j)){
                int from = j;
                int to = e.other(from);
                if(shortestPathResultO[from] == Integer.MAX_VALUE){
                  continue;
                }else{
                  int newCost = shortestPathResultO[from] + e.getWeight();
                  if(newCost < shortestPathResultN[to]){
                    shortestPathResultN[to] = newCost;
                  }
                }
              }
            }
            for(int j = 1; j <= V; j++){
              shortestPathResultT[j] = shortestPathResultN[j];
            }
            if(i == V)break;
            shortestPathResultO = shortestPathResultN;
            shortestPathResultN = shortestPathResultT;
        }
        for(int i = 1; i <= V;i++){
          if(shortestPathResultN[i]!=shortestPathResultO[i]){
            shortestPathResultT[0] = Integer.MAX_VALUE;
          }
        }
        return shortestPathResultT;
    }
}
