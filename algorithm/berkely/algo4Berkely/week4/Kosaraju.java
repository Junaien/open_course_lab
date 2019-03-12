import java.util.ArrayList;
import java.util.Stack;
//assume G reverse has same size of G
public class Kosaraju{
  private static int[] temp;
  private static int[] group;
  private static int time;
  private static boolean[]marked;
  public Kosaraju(Graph g){
    temp = new int[g.size()+1];
    group = new int[g.size()+1];
    time = 1;
    firstDFS(g);
    secondDFS(g);
  }
  private void firstDFS(Graph g){
    marked = new boolean[g.size()+1];
      for(int i = 1; i <= g.size(); i++){
        if(!marked[i])firstdfs(i,g);
      }
  }
  public int[] group(){
    return group;
  }
  private void firstdfs(int i, Graph g){
    Stack<Integer> s = new Stack<>();
    s.push(i);
    while(!s.isEmpty()){
      int tail = s.peek();
      if(tail == -1){
         s.pop();
         temp[time++] = s.pop();
         continue;
      }else if(marked[tail] == true){
        s.pop();
        continue;
      }else{
        marked[tail] = true;
      }
      s.push(-1);
      for(Integer j:g.adj(tail,true)){
        if(!marked[j]){
          s.push(j);
        }
      }
    }
  }

  private void secondDFS(Graph g){
    marked = new boolean[g.size()+1];
    time = 0;
    for(int i = temp.length-1; i >= 1; i--){
      if(!marked[temp[i]]){
        seconddfs(temp[i],g);
        time++;
      };
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
        group[tail]  = time;
      }
      s.push(-1);
      for(Integer j:g.adj(tail,false)){
        if(!marked[j]){
          s.push(j);
        }
      }
    }
  }


}
