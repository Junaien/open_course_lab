import java.util.PriorityQueue;
import java.util.ArrayList;

public class Dijkstra{

    private Graph g;
    private int[] shortestPath;
    private int[] cost;
    public Dijkstra(Graph g, int source){
      shortestPath = new int [g.size() + 1];
      cost = new int [g.size() + 1];
      boolean[] visited = new boolean[g.size()+1];
      ArrayList<Vertex> V = new ArrayList<Vertex>();
      for(int i = 0; i <= g.size(); i++){
          V.add(new Vertex(i, Integer.MAX_VALUE));
          cost[i] = Integer.MAX_VALUE;
      }
      PriorityQueue<Vertex> q = new PriorityQueue<>();
      V.get(source).setKey(0);
      q.add(V.get(source));

      while(!q.isEmpty()){
        Vertex candidate = q.poll();
        cost[candidate.getE()] = candidate.getKey();
        visited[candidate.getE()] = true;
        ArrayList<Edge> adjE = g.getAdjEdge(candidate.getE());
        for(Edge e : adjE){
          relax(candidate.getE(),e,V, q,visited);
        }
      }
    }
    public int[] shortestPathCost(){
      return cost;
    }
    private void relax(int from, Edge e, ArrayList<Vertex> V, PriorityQueue<Vertex> q, boolean[] visited){
      int other = e.other(from);
      if(visited[other])return;
      int newPathWeight = V.get(from).getKey() + e.getWeight();
      if(newPathWeight < V.get(other).getKey()){
        shortestPath[other] = from;
        V.get(other).setKey(newPathWeight);
        q.remove(V.get(other));
        q.add(V.get(other));
      }
    }
    public int[] path(){
      return shortestPath;
    }
    public static void main(String[] args){
      Graph g = new Graph();
      g.format_two("DijkstraData.txt");
      Dijkstra d = new Dijkstra(g,1);
    }
}
