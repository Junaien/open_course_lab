import java.util.Scanner;
import java.util.PriorityQueue;
import java.io.File;
public class Huffman{
    private Node root;
    private int min = Integer.MAX_VALUE;
    private int max = Integer.MIN_VALUE;

    public Huffman(String path){
      Scanner sc = null;
      try{
        sc = new Scanner(new File(path));
      }catch(Exception e){

      }
      PriorityQueue<Node> p = new PriorityQueue<>();
      String[] s = sc.nextLine().split("\\s+");
      int size = Integer.valueOf(s[0]);
      while(sc.hasNextLine()){
        s = sc.nextLine().split("\\s+");
        int weight = Integer.valueOf(s[0]);
        p.add(new Node(null,null,weight,true));
      }
      constructOptTree(p);
      System.out.println(min);
      System.out.println(max);
    }

    private void constructOptTree(PriorityQueue<Node> q){
        if(q.size() == 2){
            root = new Node(null, null, -1,false);
            Node left = q.poll();
            Node right = q.poll();
            left.depth = 1;
            right.depth = 1;
            root.left = left;
            root.right = right;
            if(left.leave && left.depth < min)min = left.depth;
            if(left.leave && left.depth > max)max = left.depth;
            if(right.leave && right.depth < min)min = right.depth;
            if(right.leave && right.depth < max)max = right.depth;
            return;
        }
        Node left = q.poll();
        Node right = q.poll();
        Node combine = new Node(null,null,(left.weight + right.weight),false);
        q.add(combine);
        constructOptTree(q);
        left.depth = combine.depth + 1;
        right.depth = combine.depth + 1;
        if(left.leave && left.depth < min)min = left.depth;
        if(left.leave && left.depth > max)max = left.depth;
        if(right.leave && right.depth < min)min = right.depth;
        if(right.leave && right.depth < max)max = right.depth;
        combine.left = left;
        combine.right = right;
        return;
    }
    public static  void main(String[] args){
      Huffman hf = new Huffman("huffman.txt");
    }



}
