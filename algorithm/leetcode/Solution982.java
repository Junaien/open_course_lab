package bit_manipulation;

import dp.Solution5;

public class Solution982 {

    //dp[i][k] means composed by i numbers, the composition is k
    //以往dp 要求的指数都知道顺序， 如今不同
    //push based algorithm???
    public int countTripletsDP(int[] A) {
        int[] dp  = new int [1 << 16];
        dp[(1 << 16) - 1] = 1;
        for(int i = 0; i < 3; i++){
            int[] temp = new int[1<< 16];
            for(int k = 0; k < (1 << 16); k++){
                for(int j : A){
                    temp[j & k] += dp[k];
                }
            }
            dp = temp;
        }
        return dp[0];
    }

    //compute submask for later use
    public int countTripletsSubmask(int[] A) {
        int size = 16;
        int[] ct = new int [1 << size];
        for(int a: A)ct[a]++;

        for(int i = 1; i <= size; i++){
            int flip = 1 << (i - 1);
            for(int j = 0; j < ct.length; j++){
                if((flip & j) != 0){
                    ct[j] += ct[j ^ flip];
                }
            }
        }

        int res = 0;
        for(int i = 0; i < A.length; i++)
            for(int j = 0; j < A.length; j++){
                int combo  = A[i] & A[j];
                res += ct[((1 << size) - 1) ^ combo];
            }
        return res;
    }


    public static void main(String[] args){
        int[] ct = new int[]{55340,49673,27562,16968,33506,26537,51367,19877,31234,38232,59514,33075,18470,49532,40525,16417,59621,64407,53098,59124,20325,47830,58906,44825,30942,6599,28453,40035,59835,63347,31261,56708,17071,52758,35959,920,47166,26137,54057,43788,74,32347,56859,15984,21312,57047,12521,37192,15637,63408};
        Solution982 s = new Solution982();
        System.out.println(s.countTripletsSubmask(ct));
    }
}
