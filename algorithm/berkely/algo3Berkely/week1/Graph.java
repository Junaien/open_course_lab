import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
public class Graph{
  private ArrayList<ArrayList<Edge>> adjList;
  public Graph(String path){
    adjList = new ArrayList<ArrayList<Edge>>();
    Scanner sc = null;
    try{
      sc = new Scanner ( new File(path));
    }catch(Exception e){

    }
    String[] s = sc.nextLine().split("\\s+");
    int size = Integer.valueOf(s[0]);
    for(int i = 0; i <= size; i++){
      adjList.add(new ArrayList<Edge>());
    }
    while(sc.hasNextLine()){
      s = sc.nextLine().split("\\s+");
      int from = Integer.valueOf(s[0]);
      int to = Integer.valueOf(s[1]);
      int weight = Integer.valueOf(s[2]);
      Edge e = new Edge(from, to, weight);
      adjList.get(from).add(e);
      adjList.get(to).add(e);
    }
  }
  public int size(){
    return adjList.size() -1;
  }

  public ArrayList<Edge> getAdjEdge(int i){
    if(i > adjList.size() - 1)return new ArrayList<Edge>();
    return adjList.get(i);
  }
  //1  4 20   (each line represent adjacent vertex and edge weight to that vertex)
  // first line is    vertex number  [space] num of edges
  //

}
