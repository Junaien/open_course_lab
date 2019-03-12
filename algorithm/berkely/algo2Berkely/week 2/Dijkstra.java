import java.util.PriorityQueue;
import java.util.ArrayList;

public class Dijkstra{

    private Graph g;
    private int[] shortesPath;
    public Dijkstra(Graph g, int source){
      shortesPath = new int [g.size()];
      boolean[] visited = new boolean[g.size()];
      ArrayList<Vertex> V = new ArrayList<Vertex>();
      for(int i = 0; i < g.size(); i++){
          V.add(new Vertex(i, Integer.MAX_VALUE));
      }
      PriorityQueue<Vertex> q = new PriorityQueue<>();
      V.get(source).setKey(0);
      q.add(V.get(source));

      while(!q.isEmpty()){
        Vertex candidate = q.poll();
        visited[candidate.getE()] = true;
        ArrayList<Edge> adjE = g.getAdjEdge(candidate.getE());
        for(Edge e : adjE){
          relax(candidate.getE(),e,V, q,visited);
        }
      }
      for(int i = 0; i < shortesPath.length; i++){
        if(i == 7 || i == 37 || i == 59 || i == 82 || i == 99 || i == 115 || i == 133 || i == 165 || i == 188 || i == 197)
         System.out.println(V.get(i).getKey());
      }
    }
    private void relax(int from, Edge e, ArrayList<Vertex> V, PriorityQueue<Vertex> q, boolean[] visited){
      int other = e.other(from);
      if(visited[other])return;
      int newPathWeight = V.get(from).getKey() + e.weight();
      if(newPathWeight < V.get(other).getKey()){
        V.get(other).setKey(newPathWeight);
        q.remove(V.get(other));
        q.add(V.get(other));
      }
    }
    public static void main(String[] args){
      Graph g = new Graph();
      g.format_two("DijkstraData.txt");
      Dijkstra d = new Dijkstra(g,1);
    }
}
