package state_of_art;

public class Solution793 {
    public int preimageSizeFZF(int K) {
        if(K == 0)return 5;
        else return (int)binarySearch(K + 1) - (int)binarySearch(K);
    }

    //search for the first index x such that f(x) > k -1
    private long binarySearch(int K){
        long low = 0, high = 5000000000l;
        while(low <= high){
            long middle = (low + high) >> 1;
            int t = 0;
            for(long k = 5; k <= middle; k *= 5){
                System.out.println(k + ", " + middle);
                t += middle / k;
            }
            if(t < K)low = middle + 1;
            else high = middle - 1;
        }
        return low;
    }
    public static void main(String[] args){
        Solution793 s = new Solution793();
        System.out.println(s.preimageSizeFZF(5));
    }


}
