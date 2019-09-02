package maintain;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;
import java.util.TreeMap;

public class Solution962 {

    /**
     * Intuition: We maintain a set that is possible candidates
     *            we delete a item if there is some element that is smaller
     * 1.1        this set is monotoic decreasing in terms of index order
     *            >> because we would not add an element if there is something that is smaller on the left side
     * 1.2        since it is monotonic decreasing, we use treeMap floor key to find out the best candidate for
     *            A[j]
     *
     * @param A
     * @return
     */
    public int maxWidthRampSort(int[] A) {
        //edge case
        if(A == null || A.length <= 1)return 0;

        //initailzation
        Integer[] indexS = new Integer[A.length];
        for(int i = 0; i < A.length; i++)
            indexS[i] = i;

        //sort the array and put there index there
        Arrays.sort(indexS, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if(A[o1] == A[o2])return 0;
                else if(A[o1] < A[o2])return -1;
                else return 1;
            }
        });
        int rt = 0;
        int curSmallesIndex = indexS[0];
        for(int i = 0; i < indexS.length; i++){
            rt = Math.max(rt,indexS[i] - curSmallesIndex);
            if(indexS[i] < curSmallesIndex)curSmallesIndex = indexS[i];
        }
        return rt;
    }

    /**
     * Intuition: ignore this element if on the left side there is a smaller one
     *            all element that is smaller that the current element would be recorded
     * @param A
     * @return
     */
    public int maxWidthRampTreeMap(int[] A) {
        //edge case
        if(A == null || A.length <= 1)return 0;

        //initailzation
        int rt = 0;
        TreeMap<Integer,Integer> tmap = new TreeMap<>();
        for(int i = 0; i < A.length; i++)
            if(tmap.floorKey(A[i]) == null){
                tmap.put(A[i],i);
            }else{
                rt = Math.max(rt,i - tmap.get(tmap.floorKey(A[i])));
            }
        return rt;
    }

    /**
     * Intuition: using stack, the same approach as TreeMap
     *            but we iterate from back of the array, making this algorithm runs in O(n)
     * @param A
     * @return
     */
    public int maxWidthRampStack(int[] A){
        //edge case
        if(A == null || A.length <= 1 )return 0;
        //initailization
        int rt = 0;
        Stack<Integer> s = new Stack<>();

        //first compute the monotonic decreasing stack, only add an element if it is currently smallest
        s.push(0);
        for(int i = 1; i < A.length; i++){
            if(A[i] < A[s.peek()])s.push(i);
        }

        //iterate from the back of the array A, pop out the elements that is smaller than A[j]

        for(int i = A.length -1; i >= rt; i--){
            int possibleFrom = i;
            while(!s.empty() && A[s.peek()] <= A[i]){
                possibleFrom = s.pop();
            }
            rt = Math.max(rt,i - possibleFrom);
        }

        return rt;
    }
    public static void main(String[] args){
        Solution962 s =  new Solution962();
        System.out.println(s.maxWidthRampStack(new int[]{9,8,1,0,1,9,4,0,4,1}));

    }

}
