import java.util.ArrayList;
import java.util.Stack;
//assume G reverse has same size of G
public class Kosaraju{
  private static int[] temp;
  private static int time;
  private static boolean[]marked;
  public Kosaraju(Graph g){
    temp = new int[g.size()];
    time = 1;
    firstDFS(g);
    secondDFS(g);
  }
  private void firstDFS(Graph g){
    marked = new boolean[g.size()];
      for(int i = 1; i < g.size(); i++){
        if(!marked[i])firstdfs(i,g);
      }
  }
  private void firstdfs(int i, Graph g){
    Stack<Integer> s = new Stack<>();
    s.push(i);
    while(!s.isEmpty()){
      int tail = s.peek();
      if(tail == -1){
         s.pop();
         temp[time++] = s.pop();
         // System.out.println("order:" + temp[time -1]);
         continue;
      }else if(marked[tail] == true){
        s.pop();
        continue;
      }else{
        marked[tail] = true;
      }
      s.push(-1);
      for(Integer j:g.adjListR.get(tail)){
        if(!marked[j]){
          s.push(j);
        }
      }
    }
  }

  private void secondDFS(Graph g){
    // System.out.println("order:" + temp[time -1]);

    marked = new boolean[g.size()];
    for(int i = temp.length-1; i >= 1; i--){
      time = 0;
      if(!marked[temp[i]]){
        seconddfs(temp[i],g);
      };
      if(time > 200){
      System.out.println(time);
      }
    }
  }
  private void seconddfs(int i, Graph g){
    Stack<Integer> s = new Stack<>();
    s.push(i);
    while(!s.isEmpty()){
      int tail = s.peek();
      if(tail == -1){
        s.pop();
        s.pop();
         continue;
      }else if(marked[tail] == true){
        s.pop();
        continue;
      }else{
        marked[tail] = true;
        time++;
      }
      s.push(-1);
      for(Integer j:g.adjList.get(tail)){
        if(!marked[j]){
          s.push(j);
        }
      }
    }
  }
  public static void main(String[]args){
    Graph g = new Graph();
    g.format_one("SCC.txt");
    Kosaraju k = new Kosaraju(g);

  }
}
