public class Edge implements Comparable<Edge>{
    private int from;
    private int to;
    private int weight;
    public Edge(int from, int to, int weight){
      this.from = from;
      this.to = to;
      this.weight = weight;
    }
    public int from(){
      return from;
    }
    public int to(){
      return to;
    }
    public int other(int i){
      return i == from? to:from;
    }
    public int getWeight(){
      return weight;
    }
    public void setWeight(int w){
      this.weight = w;
    }
    public int compareTo(Edge o){
      if(weight < o.getWeight())return -1;
      else if(weight > o.getWeight())return 1;
      else return 0;
    }
}
