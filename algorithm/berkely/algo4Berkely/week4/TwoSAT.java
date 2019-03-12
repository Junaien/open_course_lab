import java.util.Scanner;
import java.io.File;

public class TwoSAT{
  public TwoSAT(String path){
    Scanner sc = null;
    try{
      sc = new Scanner(new File(path));
    }catch (Exception e){

    }
    int variableSize = Integer.valueOf(sc.nextLine());
    Graph g = new Graph(2*variableSize);
    while (sc.hasNextLine()){
      String[] s = sc.nextLine().split("\\s+");
      int firstV = Integer.valueOf(s[0]);
      int secondV = Integer.valueOf(s[1]);
      if(firstV < 0 && secondV < 0){
        g.addEdge(findPositive(-1*firstV), findNegative(-1*secondV));
        g.addEdge(findPositive(-1*secondV), findNegative(-1*firstV));
      }else if(firstV > 0 && secondV > 0){
        g.addEdge(findNegative(firstV), findPositive(secondV));
        g.addEdge(findNegative(secondV), findPositive(firstV));
      }else if(firstV < 0 && secondV > 0){
        g.addEdge(findPositive(-1*firstV), findPositive(secondV));
        g.addEdge(findNegative(secondV), findNegative(-1*firstV));

      }else{
        g.addEdge(findNegative(firstV), findNegative(-1*secondV));
        g.addEdge(findPositive(-1*secondV), findPositive(firstV));
      }
     }
    Kosaraju k = new Kosaraju(g);
    int[] group = k.group();
    k = null;

    for(int i = 1; i <= variableSize; i++){
      if(group[findPositive(i)] == group[findNegative(i)]){
        System.out.println("not satisfied");
        System.exit(0);
      }
    }
    System.out.println("satisfied");

  }
  private int findNegative(int v){
      return 2*v;
  }
  private int findPositive(int v){
      return 2*v -1;
  }
  public static void main(String[]args){
    TwoSAT s  = new TwoSAT("2sat6.txt");

  }
}
