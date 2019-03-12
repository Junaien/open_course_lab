import java.util.PriorityQueue;
import java.util.Scanner;
import java.io.File;
public class Median{

  private PriorityQueue<Integer> lowMaxHeap;
  private PriorityQueue<Integer> highMinHeap;
  public Median(){
    lowMaxHeap = new PriorityQueue<Integer>();
    highMinHeap = new PriorityQueue<Integer>();

  }

  public void insert(int i){
    if (size() == 0){
      lowMaxHeap.add(-1*i);
      return;
    }
    if (size() >= 1){
        //lMax must not be null
        Integer lMax = positive(lowMaxHeap.peek());
        Integer rMin = highMinHeap.peek();

        if(i >= lMax){
          if(highMinHeap.size() < lowMaxHeap.size()){
            highMinHeap.add(i);
          }else{
            Integer a = highMinHeap.poll();
            Integer goLeft = (i < a) ? i:a;
            Integer goRight = (i < a) ? a:i;
            highMinHeap.add(goRight);
            lowMaxHeap.add(-1*goLeft);
          }
        }else if(rMin == null || i <= rMin) {
          if(highMinHeap.size() == lowMaxHeap.size()){
             lowMaxHeap.add(-1*i);
          }else{
            Integer a = positive(lowMaxHeap.poll());
            Integer goLeft = (i < a) ? i:a;
            Integer goRight = (i < a) ? a:i;
            highMinHeap.add(goRight);
            lowMaxHeap.add(-1*goLeft);
          }
        }
    }
  }
  private int positive(int i){
    return -1 * i;
  }

  public int size(){
    return lowMaxHeap.size() + highMinHeap.size();
  }
  public int median(){
    if(size() == 0)return -1;
    return -1*lowMaxHeap.peek();
  }

  public static void main(String[] args){
    Median m = new Median();
    Scanner sc = null;
    try {
      sc = new Scanner(new File("Median.txt"));
    }catch (Exception e){

    }
    int sum = 0;
    while(sc.hasNextLine()){
        int i = Integer.valueOf(sc.nextLine());
        m.insert(i);
        sum += m.median();
    }
    System.out.println(sum % 10000);
  }

}
