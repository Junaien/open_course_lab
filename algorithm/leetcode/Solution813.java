package dp;
class Solution813 {
    public double largestSumOfAverages(int[] A, int K) {
        double[] prefixSum = new double[A.length];
        double[] dp = new double[A.length];
        double sum = 0;
        for(int i = 0; i < prefixSum.length; i++){
            sum += A[i];
            prefixSum[i] = sum;
            dp[i] = sum / (i+1);
        }

        for(int g = 2; g <= K; g++){
            double[] temp = new double[A.length];
            for(int e = g - 1; e < A.length; e++){
                double max = Double.MIN_VALUE;
                for(int splitIndex = e - 1; splitIndex >= 0 && splitIndex + 1 >= g - 1; splitIndex--){
                    double curAverage = dp[splitIndex] +
                            (prefixSum[e] - prefixSum[splitIndex]) / (e - splitIndex);
                    max = Math.max(max,curAverage);
                }
                temp[e] = max;
            }
            dp = temp;
        }
        return dp[A.length -1];
    }
}
