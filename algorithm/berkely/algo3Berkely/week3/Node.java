public class Node implements Comparable<Node>{
    public Node left;
    public Node right;
    public int weight;
    public int depth;
    public boolean leave;
    public Node(Node left, Node right, int weight,boolean l){
      this.left = left;
      this.right = right;
      this.weight = weight;
      this.depth = 0;
      this.leave = l;
    }
    public int compareTo(Node o){
      if(this.weight< o.weight)return -1;
      else if(this.weight > o.weight)return 1;
      else return 0;
    }
}
