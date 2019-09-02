package dp;

import java.util.Arrays;

public class Solution980 {
    int m = 0, n = 0;
    int res = 0;
    int[] moveR = new int[]{-1, 1, 0, 0};
    int[] moveC = new int[]{0, 0, -1, 1};

    public int uniquePathsIII(int[][] grid) {
        int blocks = 0;
        int startRow = -1;
        int startCol = -1;
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] != -1) blocks++;
                if (grid[i][j] == 1) {
                    startRow = i;
                    startCol = j;
                }
            }
        backTrack(grid, startRow, startCol, 1, blocks);
        return res;
    }

    private void backTrack(int[][] grid, int r, int c, int l, int targetL) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || grid[r][c] == -1) return;
        if (l == targetL && grid[r][c] == 2) {
            res++;
            return;
        } else {
            if(grid[r][c] == 2)return;
            grid[r][c] = -1;
            for (int i = 0; i < 4; i++) {
                int targetR = r + moveR[i];
                int targetC = c + moveC[i];
                backTrack(grid, targetR, targetC, l + 1, targetL);
            }
            grid[r][c] = 0;
        }
    }

    public int uniquePathsIIIDP(int[][] grid){
        int t = 0 , s = 0;
        m = grid.length;
        n = grid[0].length;
        int[][] dp = new int[m * n][1 << 20];
        for(int i = 0; i < dp.length; i++)
            Arrays.fill(dp[i],-1);
        int mask  = 0;
        for(int i = 0; i < grid.length;i++)
            for(int j = 0; j < grid[0].length; j++){
                int encode = i * n + j;
                if(grid[i][j] == 0) mask |= (1 << encode);
                else if(grid[i][j] == 2) t = encode;
                else if(grid[i][j] == 1) s = encode;
            }

        return memoSearch(dp,mask | 1 << t,s,t);
    }
    private int memoSearch(int[][]dp, int mask, int s, int t){
        if(dp[s][mask] != -1)return dp[s][mask];
        else if(mask == 0){
            return s == t? 1:0;
        }else if(s == t){
            return 0;
        }else{
            int x = s / n, y = s % n;
            int sum = 0;
            if(y > 0 && (mask & (1 << s - 1)) != 0)sum +=  memoSearch(dp,mask ^ (1 << (s - 1)),s - 1, t);
            if(y < n - 1 && (mask & (1 << s + 1)) != 0)sum +=  memoSearch(dp,mask ^ (1 << (s + 1)),s + 1, t);
            if(x > 0 && (mask & (1 << s - n)) != 0)sum +=  memoSearch(dp,mask ^ (1 << (s - n)),s - n, t);
            if(x < m - 1 && (mask & (1 << s + n)) != 0)sum +=  memoSearch(dp,mask ^ (1 << (s + n)),s + n, t);
            dp[s][mask] = sum;
            return sum;
        }
    }

    public static void main(String[] args){
        int[][] input = new int[][]{{1,0},{2,0}};
        Solution980 s = new Solution980();
        System.out.println(s.uniquePathsIIIDP(input));
    }

}