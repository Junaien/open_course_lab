import  java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
public class QuickSort{


  public static int quickSort(int [] array, int l, int r){
      if(l >= r)return 0;
      int comparisons = 0;
      int p = choosePivot(array,l,r);
      swap(array, l, p);
      int pivot = array[l];
      int i = l +1;
      for(int j = l +1; j <= r; j++){
        if(array[j] < pivot)swap(array,i++,j);
      }
      swap(array, l, i-1);
      comparisons += (r - l);
      comparisons += quickSort(array, l, i -2);
      comparisons += quickSort(array, i,r);
      return comparisons;

  }
  private static int choosePivot(int[]array,int l, int r){
    int middle = l + (r - l)/2;
    int x = array[l] - array[middle];
    int y = array[middle] - array[r];
    int z = array[r] -  array[middle];
    int k = array[l] - array[r];
    if(x*y > 0) return middle;
    if(k*z > 0) return r;
    return l;
  }

  private static void swap(int[]array, int l, int r){
      int temp = array[l];
      array[l] = array[r];
      array[r] = temp;
  }

  public static void main(String[] args){

    // int [] a = {2,5,6,34,6,7,4,2,4,6,7,2,4};
    // quickSort(a, 0 ,a.length-1);
    // for(int i = 0; i< a.length; i++){
    //   System.out.print(a[i] + ", ");
    // }
    Scanner sc = null;

    try{
      sc = new Scanner (new File("input.txt"));
    }catch (Exception e){

    }

    ArrayList<Integer> array = new ArrayList<>();
    while(sc.hasNextLine()){
      String s = sc.nextLine();
      int i = Integer.parseInt(s);
      array.add(i);
    }
    int [] a = new int[array.size()];
    for(int i = 0; i < array.size(); i++){
      a[i] = array.get(i);
    }

    System.out.println(quickSort(a, 0 , a.length-1));

  }

}
