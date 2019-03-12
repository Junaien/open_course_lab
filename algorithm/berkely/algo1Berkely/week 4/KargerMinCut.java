import java.util.concurrent.ThreadLocalRandom;
import java.util.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;


//assume the graph is connected
public class KargerMinCut{

  private class Vert{
    public int v;
    public Vert(){
      // System.out.println("new vert created");

    }
    public Vert(int v){
      this.v = v;
      // System.out.println("new vert created");
    }
  }
  private ArrayList<ArrayList<Vert>> adjList;
  private int V;
  private int E;

  public KargerMinCut(String path){
    adjList = new ArrayList<ArrayList<Vert>>();
    V = 0;
    Scanner sc = null;
    try{
      sc = new Scanner (new File(path));
    }catch (Exception e){

    }
    adjList.add(new ArrayList<Vert>());
    while(sc.hasNextLine()){

      String s = sc.nextLine();
      s = s.trim();
      ArrayList<Vert> l = new ArrayList<Vert>();
      String[] adj = s.split("\\s+");
      for(int i = 1; i < adj.length; i++){
        if(!adj[i].equals(adj[0])){
          // if(adj[0].equals("144")||adj[0].equals("132")){
          //   System.out.print(adj[i] + ",");
          // }
          l.add(new Vert(Integer.parseInt(adj[i])));
          E++;
        }
      }
      adjList.add(l);
      // System.out.println();
      // System.out.println();
    }
    V = adjList.size()-1;
    E = E/2;

    // for(int i = 1; i < adjList.size(); i++){
    //     for(Vert k: adjList.get(i)){
    //       if(k.v == i){
    //         System.out.println("sabi");
    //       }
    //     }
    // }
  }

  private void checkGraph(int[][] adjMatrix){
    for(int i = 1; i < adjMatrix.length; i++){
      for(int j = 1; j < adjMatrix[0].length; j++){
        if(adjMatrix[i][j] != adjMatrix[j][i]){
          System.out.println("broken graph at node " + i + " to " + j);
          System.out.println(adjMatrix[i][j] + " != " + adjMatrix[j][i] );

        }
        if(adjMatrix[i][i] == 1)
          System.out.println("self loop  at node " + i);
      }
    }
    System.out.println("your graph is clean");

  }



  private Vert[] randomE(ArrayList<ArrayList<Vert>> temp, int edgeSize, Vert[] v){
      int total = 0;
      // for(int i = 1; i < temp.size(); i++){
      //     for(Vert k: temp.get(i)){
      //       if(k.v == i){
      //         System.out.println("sabi");
      //       }
      //     }
      //     total += temp.get(i).size();
      // }
      // if(total != edgeSize){
      //   System.out.println("false");
      //   System.out.println("edgeSize = " + edgeSize);
      //   System.out.println("edgeSizeReal = " + total);
      //
      //   System.exit(0);
      // }

      Random rnd = ThreadLocalRandom.current();
      int remainder = rnd.nextInt(edgeSize) +1;
      // System.out.println("remainder: " + remainder + ", total: " + total);
      // if(remainder < 0 || remainder > edgeSize){
      //   System.out.println("wrong remainder: " + remainder);
      //
      // }
      Vert [] fromTo = new Vert[2];
      for(int i = 1; i < temp.size(); i++){
          if(temp.get(i).size() == 0)continue;
          if (remainder <= temp.get(i).size()){
            fromTo[0] = v[i];
            fromTo[1] = temp.get(i).get(remainder-1);
            return fromTo;
          }else{
            remainder -= temp.get(i).size();
          }
      }
      return fromTo;
  }

  public int minCut(){
    if (V <= 1)return -1;
    //mapping contracted Vertex
    Vert [] realVertex = new Vert[V+1];
    for(int i = 0; i < realVertex.length; i++){
      realVertex[i] = new Vert(i);
    }

    //copy graph over
    ArrayList<ArrayList<Vert>> temp = new ArrayList<>();
    for(ArrayList<Vert> l: adjList){
      ArrayList<Vert> t = new ArrayList<Vert>();
      for(Vert v: l){
        t.add(realVertex[v.v]);
      }
      temp.add(t);
    }

    int edgeSize = E;
    int Vsize = V;
    //start contraction
    while(Vsize != 2){
      Vert[] fromTo = randomE(temp,2 * edgeSize, realVertex);
      // if(fromTo[0].v == fromTo[1].v){
      //   System.out.println("from: " + fromTo[0].v + "to: " + fromTo[1].v);
      //   return -2;
      // }

      // System.out.println();
      ArrayList<Vert> from = temp.get(fromTo[0].v);
      ArrayList<Vert> to = temp.get(fromTo[1].v);
      // if(from.size() == 0 || to.size() == 0){
      //   System.out.print("wrong");
      // }
      // checkLoop(from,fromTo[0].v);
      // checkLoop(to,fromTo[1].v);
      // int original = from.size() + to.size();
      // int k = 0;
      ArrayList<Vert> newE = new ArrayList<>();
      for(int i = 0; i < from.size(); i++){
        if(from.get(i).v == fromTo[1].v){
        }else{
          newE.add(from.get(i));
        }
        // System.out.print(from.get(i).v + ", ");
      }
      // System.out.println("---------------------------");

      for(int i = 0; i < to.size(); i++){
        if(to.get(i).v == fromTo[0].v ){
          edgeSize--;
          // k++;
        }else{
          newE.add(to.get(i));
        }
        // System.out.print(to.get(i).v + ", ");

      }
      // System.out.println("---------------------------");

      // System.out.println("last edge decrease is " + k);
      // System.out.println("last internal edge decrease is " + (original - newE.size()));
      // System.out.println("from :" + fromTo[0].v);
      // System.out.println("to :" + fromTo[1].v);
      // System.out.println();

      temp.set(fromTo[1].v, new ArrayList<Vert>());
      temp.set(fromTo[0].v, newE);
      int t = fromTo[1].v;
      for(int i = 1 ; i < realVertex.length; i++){
        if(realVertex[i].v == t)realVertex[i].v = fromTo[0].v;
      }
      Vsize--;
    }

    return edgeSize;

  }
  public static void main(String []args){

      KargerMinCut c = new KargerMinCut("karger.txt");
      int min = Integer.MAX_VALUE;

      for(int i = 0; i < 40000; i ++){
        int m = c.minCut();
        if (m < 0){
          System.out.println("wrong");
          break;
        };
        if(m < min) min = m;
      }
      System.out.println("the KargerMinCut = " + min);
      // System.out.println(c.V);

  }

}
