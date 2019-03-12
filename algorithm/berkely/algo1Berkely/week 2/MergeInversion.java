import  java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
public class MergeInversion{
  public static long countInversion(int[] array){

      int [] auxilary = new int [array.length];
      return countInversionAndSort(array, 0, array.length -1, auxilary);

  }

  public static long countInversionAndSort(int[] array, int l, int r, int[] aux){

      if(l == r){
        aux[l] = array[l];
        return 0;
      }
      int middle =  l+ (r - l)/2;
      long leftSplit = countInversionAndSort(array, l, middle, aux);
      long rightSplit = countInversionAndSort(array, middle +1, r, aux);
      for(int i = l; i <= r; i++){
        aux[i] = array[i];
      }
      long crossSplit = merge(aux, array, l, middle,r);
      return leftSplit + rightSplit + crossSplit;
  }

  public static long merge(int[] aux, int[] target, int l, int m, int r){
    int size = r - l + 1;
    int k = l, j = m+1;
    long crossSplit = 0;
    for(int i = 0; i < size; i++){
      if(k > m)target[l+i] = aux[j++];
      else if(j > r)target[l+i] = aux[k++];
      else if(aux[k] < aux[j])target[l+i] = aux[k++];
      else{
        crossSplit += (m - k + 1);
        target[l+i] = aux[j++];
      }
    }

    return crossSplit;
  }
  public static void main(String[] args){
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

    System.out.println(countInversion(a));

  }

}
