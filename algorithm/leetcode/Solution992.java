package two_pointer;

import java.util.HashMap;
import java.util.Map;
import java.awt.Desktop;
import java.net.URI;

class Solution992 {

    //this might take O(N^2)
    public int subarraysWithKDistinctTrash(int[] A, int K) {
        //i points to the start of subarray
        //j points to the first element such that subarray[i,j] has  K element
        //z points to the first element such that subarray[i,z] has  K + 1 element
        Map<Integer,Integer> count = new HashMap<>();
        int ret = 0;
        int j = -1;
        for(int i = 0; i < A.length; i++){
            //set j to be index such that [i,j] is the first subarray to have K element
            while(count.size() < K && j < A.length - 1){
                int e = A[++j];
                count.put(e,count.getOrDefault(e,0)+ 1);
            }

            if(count.size() == K)ret++;
            else break;

            //extends subarray
            int z = j;
            while(z < A.length - 1 && count.containsKey(A[++z]))ret++;

            //update the state of map of the subarray
            int freq = count.get(A[i]);
            if(freq > 1)count.put(A[i],freq -1);
            else count.remove(A[i]);
        }
        return ret;
    }

    //it is a headache to maintain three pointer
    //think about an easier question
    //we can use two pointer to calculate largest [i, ..] such that it has K distinct element
    //no need to backup a third pointer
    //largest(i, ...) with K distinct element also implys the largest subarray that starts with i
    //and has at most K distinct element
    public int subarraysWithKDistinct(int[] A, int K) {
        return atMost(A,K) - atMost(A,K-1);
    }
    private  int atMost(int[] A, int K){
        int ret = 0, j = -1;
        Map<Integer,Integer> count = new HashMap<>();
        for(int i = 0; i < A.length; i++){
            //expand subarray[i, ...], such that [i,j) is maximum subarray that has K distinct element
            while(K >= 0 && j < A.length - 1){
                int freq = count.getOrDefault(A[++j],0);
                if(freq == 0){K--;}
                count.put(A[j],freq + 1);
            }
            ret += (j - i + 1);
            //update the table
            int freq = count.get(A[i]);
            if(freq == 0)K++;
            count.put(A[i],freq - 1);
        }
        return ret;
    }



    public static void main(String[] args){
//        Desktop d= Desktop.getDesktop();
//        URI uri = null;
//        try{
//            uri = new URI("https://leetcode.com/problems/subarrays-with-k-different-integers/");
//            d.browse(uri);
//        }catch(Exception e){}
    }
}