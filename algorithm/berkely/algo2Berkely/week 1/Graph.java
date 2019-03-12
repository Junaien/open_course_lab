import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
public class Graph{
  public ArrayList<ArrayList <Integer>> adjList;
  public ArrayList<ArrayList<Integer>> adjListR;
  public static int s = 875715;
  public Graph(){
    adjList = new ArrayList<ArrayList <Integer>>();
    adjListR = new ArrayList<ArrayList <Integer>>();
    for( int i = 0; i < s; i++){
      adjList.add(new ArrayList<Integer>());
      adjListR.add(new ArrayList<Integer>());
    }
  }
  public int size(){
    return adjList.size();
  }
  public void addEdge(int from, int to){

    adjList.get(from).add(to);
    adjListR.get(to).add(from);
  }
  public void format_one(String path){
    Scanner sc = null;
    try{
      sc = new Scanner ( new File(path));
    }catch(Exception e){

    }

    while(sc.hasNextLine()){
      String[] s = sc.nextLine().split("\\s+");
      addEdge(Integer.valueOf(s[0]),Integer.valueOf(s[1]));
    }

  }
}
