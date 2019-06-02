
class Solution {
    //1.01 - A = [x x x x x x x x i(index)]
    //     - we want to find a set [S] such that [ (sum / size) == avg([x x x x .. i]) ];
    //     - it seems like we have [sum] [size] [avg] to be considered by dynamic programming
    //     - if such [S] exists, then element [i] either is or is not in set [S]
    //         - case 1: element i is     in the set [S]
    //             - only consider first i - 1 elements, find set S' s.t. [sum' == (sum - A[i])] [size' == (size - 1)],
    //         - case 2: element i is not in the set [S]
    //             - only consider first i - 1 elements, find set S' s.t. [sum' == sum]          [size' == size]
    //         - the state transition don't care variable [avg]
    //     - if such [S] not exists, case 1 & case 2 will both fail, we return false because there is no other way

    public boolean splitArraySameAverage(int[] A) {
        int l = A.length;
        double avg = 0.0;
        for(int i = 0; i < A.length; i++){
            A[i] += 1;
            avg += (double)A[i];
        }
        avg /= A.length;
        //boolean [sum][n] dp is not as efficient as int[sum] dp
        int[] dp = new int[10001 * l / 2 + 1];
        //base case
        dp[A[0]] |= (1 << 1);
        for(int i = 1; i < A.length; i++){
            dp[0] |= 1;
            for(int n = A.length; n >= 1; n--){
                for(int sum = 1; sum <= 10001 * l /  2; sum++){
                    if(sum - A[i] >= 0){
                        dp[sum] |= (dp[sum - A[i]] & (1 << (n-1))) << 1;
                    }
                }
            }
        }
        for(int n = 1; n < l; n++){
            for(int sum = 1; sum <= 10001 * l /  2; sum++){
                if(((dp[sum] & (1 << n)) != 0) && Math.abs((sum * 1.0) / n - avg) <= 1e-5)return true;
            }
        }
        return false;
    }

}
