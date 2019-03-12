import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
public class Graph{
  private ArrayList<ArrayList <Edge>> adjList;
  public Graph(){
    adjList = new ArrayList<ArrayList <Edge>>();
    adjList.add(new ArrayList<Edge>());
  }
  public int size(){
    return adjList.size();
  }

  public ArrayList<Edge> getAdjEdge(int i){
    return adjList.get(i);
  }
  //1  4,10  5,20    (each line represent adjacent vertex and edge weight to that vertex)
  public void format_two(String path){
    Scanner sc = null;
    try{
      sc = new Scanner ( new File(path));
    }catch(Exception e){

    }
    while(sc.hasNextLine()){
      String[] s = sc.nextLine().split("\\s+");
      int k = Integer.valueOf(s[0]);
      ArrayList<Edge> temp = new ArrayList<>();
      for(int i = 1; i < s.length; i++){
          String[] tokens = s[i].split(",");
          Edge e = new Edge(k, Integer.valueOf(tokens[0]), Integer.valueOf(tokens[1]));
          temp.add(e);
      }
      adjList.add(temp);
    }

  }
}
