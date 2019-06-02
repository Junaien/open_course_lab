import java.util.*;
class Solution1073 {

    public int[] addNegabinary(int[]arr1, int[]arr2){
        //init & early return
        if(arr1 == null || arr2 == null)return null;
        if(arr1.length == 0)return arr2;
        if(arr2.length == 0)return arr1;

        //adding arr1 arr2 without worrying overflow
        int[] ret = new int[Math.max(arr1.length,arr2.length) + 2];
        for(int i = 0; i < arr1.length; i++){ret[ret.length - i - 1] += arr1[arr1.length - i - 1];}
        for(int i = 0; i < arr2.length; i++){ret[ret.length - i - 1] += arr2[arr2.length - i - 1];}
        //1.01 - we can propagate overflow bit one by one up to higher position
        //     - case 1: if [i-th] bit >= 2,  then we can fix it by [i-1] -=  (num / 2) and [i] = (num % 2);
        //        - e.g. [1 0 0 2] -> [1 0 -1 0]
        //     - case 2: if [i-th] bit < 0,   then we can fix it by [i] = -1*[i] and [i-1] += (-1*[i]);
        //        - e.g. [1 0 -1 0] -> [1 1 0 0]
        //1.1  - if we do operations like 1.01, we have two properties
        //       - a) the overflow problem will be propagated up, and [finnaly will be eliminated]
        //       - b) any bit position along the propagation, is at most decremented by 1 and at most incremented by 1
        //          - base case is fine, n-th(last) position can only be one of {0,1,2}
        //          - inductive: i-th position has range [0, 2], and might be increment or decremented by 1 => [-1,3]
        //                       case 1: if i-th position becomes -1,     then (i - 1)-th position will increment by 1,
        //                               b) holds for (i-1)th position
        //                       case 2: if i-th position becomes 2 or 3, then (i - 1)-th position will decrement by 1,
        //                               b) still holds for (i-1)-th position
        //1.2  - by 1.1 we know that [? ? ? ?] + [? ?] => [{0 or 1} {-1 or 0 or 1} ? ? ? ?]
        //     - which indicates that we might need [2 extra] position to handle overflow
        for(int i = ret.length - 1; i > 0;  i--){
            if(ret[i] > 1){
                ret[i-1] -= ret[i] / 2;
                ret[i]    = ret[i] % 2;
            }else if(ret[i] < 0){
                ret[i]   *= -1 ;
                ret[i-1] += ret[i];
            }
        }

        //copy back after bit propagation, clear heading zeros
        int z = 0;
        while(z < ret.length && ret[z] == 0)z++;
        if(z == ret.length)return new int[]{0};
        int[] ans = new int[ret.length - z];
        for(int i = 0; i < (ret.length - z); i++){ans[ans.length - i - 1] = ret[ret.length - i - 1];}
        return ans;
    }

    public static void main(String[] args){
      Solution1073 s = new Solution1073();
      assert equal(new int[]{1,0,0,0,0}, s.addNegabinary(new int[]{1,1,1,1,1},new int[]{1,0,1})): "test1 failed";
      assert equal(new int[]{0}, s.addNegabinary(new int[]{1,1},new int[]{1})): "test2 failed";
      assert equal(new int[]{1,1}, s.addNegabinary(new int[]{0},new int[]{1,1})): "test3 failed";
      System.out.println("all tests passed!");
    }

    public static boolean equal(int[] a, int[] b){
      //this is auto generated stub
      if(a == null && b == null)return true;
      if(a == null || b == null)return false;
      if(a.length != b.length)return false;
      for(int i = 0; i < b.length; i++){if(a[i] != b[i])return false;}
      return true;
    }

}
