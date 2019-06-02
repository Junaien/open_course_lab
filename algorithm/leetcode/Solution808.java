import java.util.HashMap;
import java.util.Map;
class Solution808 {
    public double soupServings(int N) {
        Map<String, Double> memo = new HashMap<>();
        return dfs(memo, N, N);
    }
    private double dfs(Map<String,Double> memo, int A, int B){
        if(A <= 0 && B <= 0){
           return 0.5;
        }else if(A <= 0){
           return 1.0;
        }else if(B <= 0)return 0.0;
        else if(memo.containsKey(A + "|" + B)){
          return memo.get(A + "|" + B);
        }else{
          double ret = 0.0;
          ret += 0.25 * dfs(memo, A - 100, B);
          ret += 0.25 * dfs(memo, A - 75, B - 25);
          ret += 0.25 * dfs(memo, A - 50, B - 50);
          ret += 0.25 * dfs(memo, A - 25, B - 75);
          memo.put(A + "|" + B, ret);
          return ret;
        }
    }

    public static void main(String[] args){
      Solution808 s = new Solution808();
      int N = Integer.valueOf(args[0]);
      double test1 = s.soupServings(N);
      System.out.println(N + ": " + test1);
      // assert Math.abs(test1 - 0.625) <= 1e-6: "supposed to be: " + 0.625 + ", got: " + test1;
    }

}
