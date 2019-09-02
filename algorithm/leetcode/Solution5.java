package dp;

public class Solution5 {
    public String longestPalindromeBF(String s) {
        int maxL = 0;
        int center = 0;
        for(int i = 0; i < s.length(); i++){
            int even = matchLength(i,i+1,s);
            int odd  = matchLength(i,i,s);
            if(even > odd && even > maxL){
                maxL = even;
                center = i;
            }else if(odd > even && odd > maxL){
                maxL = odd;
                center = i;
            }
        }
        if(maxL == 0)return "";
        else{
            if(maxL % 2 == 0){
                return s.substring(center - maxL/2 + 1,center + maxL/2 + 1);
            }else{
                return s.substring(center - maxL/2, center + maxL/2 + 1);
            }
        }
    }
    private int matchLength(int i, int j, String s){
        if(s == null || i > j || i < 0 || j >= s.length())return 0;
        while(i >= 0 && j < s.length()){
            if(s.charAt(i) != s.charAt(j))return j - i - 1;
            i--; j++;
        }
        return j - i - 1;
    }

    public String longestPalindromeDP(String s){
        if(s == null)return "";
        int l = 0 , r = 0;
        boolean[][] dp = new boolean[s.length()][s.length()];
        for(int d = 1; d <= s.length(); d++)
            for(int start = 0; start + d - 1 < s.length(); start++){
                if(s.charAt(start) != s.charAt(start + d - 1))dp[start][start + d - 1] = false;
                else{
                    dp[start][start + d - 1]  = (d == 1 || d == 2)? true: dp[start + 1][start + d - 2];
                    if(dp[start][start + d - 1] && d > (r - l  + 1)){
                        l = start;
                        r = start + d - 1;
                    }
                }
            }
        return s.substring(l,r+1);
    }

    public String longestPalindromeManacher(String s){
        //todo
        return "";
    }

    public static void main(String[] args){
        Solution5 s = new Solution5();
        System.out.println(s.longestPalindromeBF("abcda"));
        System.out.println(s.longestPalindromeDP("abcda"));
    }
}
