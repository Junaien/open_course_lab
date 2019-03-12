import java.util.PriorityQueue;
public class Prim{
  private int tWeight;
  public Prim(Graph g){
    PriorityQueue<Vertex> q = new PriorityQueue<>();
    Vertex[] v = new Vertex[g.size()+1];
    for(int i = 2 ; i <= g.size(); i++){
        v[i] = new Vertex(i,Integer.MAX_VALUE);
    }

    for(Edge e : g.getAdjEdge(1)){
      v[e.other(1)].setKey(e.getWeight());
    }

    for(int i = 2 ; i <= g.size(); i++){
        q.add(v[i]);
    }
    int w = 0;
    while(!q.isEmpty()){
      Vertex vertex = q.poll();
      v[vertex.getE()] = null;
      w += vertex.getKey();
      for(Edge e : g.getAdjEdge(vertex.getE())){
        int to = e.other(vertex.getE());
        if(v[to] == null)continue;
        if(e.getWeight() < v[to].getKey()){
          q.remove(v[to]);
          v[to].setKey(e.getWeight());
          q.add(v[to]);
        }
      }
    }
    this.tWeight = w;
  }
  public int totalWeight(){
      return tWeight;
  }
  public static void main(String[]args){
    Graph g = new Graph("edges.txt");
    Prim p = new Prim(g);
    System.out.println(p.totalWeight());

  }
}
