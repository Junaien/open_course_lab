import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
public class Graph{
  private ArrayList<ArrayList <Integer>> adjList;
  private ArrayList<ArrayList<Integer>> adjListR;
  private int size;
  public Graph(int size){
    this.size = size;
    adjList = new ArrayList<ArrayList <Integer>>();
    adjListR = new ArrayList<ArrayList <Integer>>();
    for( int i = 0; i <= size; i++){
      adjList.add(new ArrayList<Integer>());
      adjListR.add(new ArrayList<Integer>());
    }
  }
  public int size(){
    return size;
  }

  public void addEdge(int from, int to){
    if(outOfRange(from) || outOfRange(to)){
      System.out.println(from + "-> " + "error, add edge out of range.....");
      return;
    }
    adjList.get(from).add(to);
    adjListR.get(to).add(from);
  }
  public ArrayList<Integer> adj(int from, boolean reverse){
    if(outOfRange(from)){
      System.out.println(from + "-> " + "error, add edge out of range.....");
      return null;
    }
    if(reverse){
      return adjListR.get(from);
    }else{
      return adjList.get(from);
    }
  }
  public boolean outOfRange(int from){
    if(from < 1 || from > size)return true;
    return false;
  }

}
