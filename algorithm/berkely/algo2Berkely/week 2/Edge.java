public class Edge{
    private int from;
    private int to;
    private int weight;
    public Edge(int from, int to, int weight){
      this.from = from;
      this.to = to;
      this.weight = weight;
    }
    public int other(int i){
      return i == from? to:from;
    }
    public int weight(){
      return weight;
    }
}
