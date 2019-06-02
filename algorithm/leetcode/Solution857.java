import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Solution857{
  //wage paid to i-th worker has lower bound wage[i];
  //workers must be paid fairly according to their quality[i]
  //1.01 - there must be on person i that is paid by wage[i]
  //1.1  - brute force algorithm
  //       -for each person i, we chose this person as the one who is paid by wage[i]
  //            we pick other K - 1 person that satisfies
  //            1: they are paid according to ratio, not minimum wage
  //            2: they are paid as low as possible
  //1.2  - algorithm runs O(n^2) in time
  //2.01 -


  public double mincostToHireWorkers_bs(int[] quality, int[] wage, int K){
    int n = quality.length;
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
    Integer[] workersSortByEQ = new Integer[n];
    for(int i = 0; i < n; i++){workersSortByEQ[i] = i;}
    Arrays.sort(workersSortByEQ, (a,b) -> Double.compare(wage[a] * 1.0 / quality[a], wage[b] * 1.0 / quality[b]));
    double ret = Double.MAX_VALUE;
    int minKQualitySum = 0;
    for(int i = 0; i < K - 1; i++){
        minKQualitySum += quality[workersSortByEQ[i]];
        maxHeap.add(quality[workersSortByEQ[i]]);
    }
    for(int i = K - 1; i < n; i++){
        int qualitySum = minKQualitySum + quality[workersSortByEQ[i]];
        double ratio   = (wage[workersSortByEQ[i]] * 1.0 /  quality[workersSortByEQ[i]]);
        ret = Math.min(ret,qualitySum * ratio);
        if(!maxHeap.isEmpty() && quality[workersSortByEQ[i]] < maxHeap.peek()){
          minKQualitySum = minKQualitySum + quality[workersSortByEQ[i]] - maxHeap.poll();
          maxHeap.add(quality[workersSortByEQ[i]]);
        }
    }
    return ret;
  }

  public double mincostToHireWorkers_bf(int[]quality, int[] wage, int K){
    double ret = Double.MAX_VALUE;
    int n = quality.length;
    for(int i = 0; i < n; i++){
      List<Double> paid = new ArrayList<>();
      for(int j = 0; j < n; j++){
        double ratioCost = (quality[j] * wage[i] * 1.0 / quality[i]);
        if(j != i && wage[j] <= ratioCost){
            paid.add(ratioCost);
        }
      }
      if(paid.size() < K - 1)continue;
      Collections.sort(paid);
      double total_cost = wage[i];
      for(int z = 0; z < K - 1; z++){total_cost += paid.get(z);}
      ret = Math.min(ret,total_cost);
    }
    return ret;
  }

  public static void main(String[] args){
    Solution857 s = new Solution857();
    double test1  = s.mincostToHireWorkers_bs(new int[]{10,20,5}, new int[]{70,50,30}, 2);
    assert Math.abs(test1 - 105.0000) <= 0.001 : "test1 should be equal to 105.0000" + " but " + test1;
    double test2  = s.mincostToHireWorkers_bs(new int[]{3,1,10,10,1}, new int[]{4,8,2,2,7}, 3);
    assert Math.abs(test2 - 30.66667) <= 0.001 : "test2 should be equal to 30.66667" + " but " + test2;
    double test3  = s.mincostToHireWorkers_bs(new int[]{2}, new int[]{14}, 1);
    assert Math.abs(test3 - 14.0) <= 0.001 : "test2 should be equal to 14.0" + " but " + test3;
    double test4  = s.mincostToHireWorkers_bs(new int[]{2,1,5}, new int[]{17,6,4}, 2);
    assert Math.abs(test4 - 25.5) <= 0.001 : "test2 should be equal to 25.5" + " but " + test4;
    System.out.println("all tests passed");
  }
  //Q [2,1,5]
  //W [17,6,4]
  //2
  //sortedWorkersByQ => [1, 0, 2]
  //sortedWorkersByW => [2, 1, 0]
  //
}
