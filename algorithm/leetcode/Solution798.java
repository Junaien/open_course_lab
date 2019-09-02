package dp;

class Solution798 {
    public int bestRotation(int[] A) {
        if(A == null || A.length == 0)return 0;
        int res = 0;
        int maxMove = 0;
        int currentScore = 0;
        int[] contributionAfterStep = new int[A.length + 1];
        for(int i = 0; i < A.length; i++){
            if(A[i] <= i)currentScore++;
        }
        //start calculate the two steps that each element contribute to the whole score
        contributionAfterStep[0] = 0;
        for(int i = 0; i < A.length; i++){
            if(A[i] == 0 || A[i] == A.length)continue;
            else if(A[i] <= i){
                contributionAfterStep[i - A[i] + 1] += -1;
                contributionAfterStep[i+1] += 1;
                int a = 1;
            }else{
                contributionAfterStep[i + 1] += 1;
                contributionAfterStep[A.length - (A[i] - i) + 1] += -1;
                int a  = 1;
            }
        }
        for(int i = 0; i < A.length; i++){
            currentScore += contributionAfterStep[i];
            if(currentScore > res){
                maxMove = i;
                res = Math.max(currentScore,res);
            }
        }

        return maxMove;

    }

    //No need to calculate the actual score, just observe the change of the system
    public int bestRotationObserveChange(int[] A){
        int optK = 0;
        int N = A.length;
        int[] change = new int[A.length];
        for(int i = 0; i < change.length; i++)change[(i - A[i] + 1 + N) % N] += -1;
        for(int i = 1; i < change.length; i++){
            change[i] += change[i - 1] + 1;
            optK = change[i] > change[optK]? i:optK;
        }
        return optK;
    }
    public static void main(String[] args){
        Solution798 s = new Solution798();
        s.bestRotation(new int[]{1,3,0,2,4});
    }
}