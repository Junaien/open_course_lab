import java.util.HashMap;
import java.util.Map;
class Solution741 {
    //x x x x x x
    //x x j x x x
    //x i x x x x
    //x x x x x x
    //x x x x x x
    //x x x x x x
    //dp[i][j] = the maximum gain when first path starts at i, second starts at j
    //(d, d), (d,r), (r,d) (r,r)
    //dp[i][j] = currentIJgain + max(dp[+N,+N], dp[N,+1], dp[+1,+N], dp[+1,+1])
    //base case
    //dp[N-1][N-1] = grid[N-1][N-1];
    public int cherryPickup(int[][] grid) {
        int N = grid.length;
        int ret = cherryPickup(grid, new HashMap<String,Integer>(), 0,0,0,0);
        return ret == Integer.MIN_VALUE? 0:ret;
    }

    private int cherryPickup(int[][] grid, Map<String,Integer> memo, int i, int j, int k, int z){
        String key = i + ","+ j + "," + k + "," + z;
        int N = grid.length;
        if(i >= N || j >= N || k >= N || z >= N)return Integer.MIN_VALUE;
        if(memo.containsKey(key))return memo.get(key);
        if(i == N - 1 && j == N - 1 && k == N - 1 && z == N - 1){return grid[N-1][N-1];}
        if(grid[i][j] == -1 || grid[k][z] == -1)return Integer.MIN_VALUE;

        int ret = Integer.MIN_VALUE;
        if(i < N - 1 && k < N - 1){ret = Math.max(ret, cherryPickup(grid,memo,i + 1, j,     k + 1, z));}
        if(i < N - 1 && z < N - 1){ret = Math.max(ret, cherryPickup(grid,memo,i + 1, j,     k ,    z + 1));}
        if(j < N - 1 && k < N - 1){ret = Math.max(ret, cherryPickup(grid,memo,i,     j + 1, k + 1 ,z));}
        if(j < N - 1 && z < N - 1){ret = Math.max(ret, cherryPickup(grid,memo,i,     j + 1, k ,    z + 1));}
        if(ret != Integer.MIN_VALUE){
          ret += (i == k && j == z)? grid[i][j]: grid[i][j] + grid[k][z];
        }
        memo.put(key,ret);
        return ret;

    }

    public static void main(String[] args){
      Solution741 s = new Solution741();
      int[][] grid1 = new int[][] {{1,1,-1,1,1,1,0,1,1,-1,-1,1,1,-1,1,1,1,1,0,1},
                               {1,1,1,0,1,1,0,1,0,1,-1,1,1,1,1,1,1,0,1,1},
                               {1,1,1,1,1,1,1,0,1,1,-1,-1,-1,1,1,1,-1,1,-1,1},
                               {1,1,1,-1,0,1,1,1,1,1,1,1,1,0,1,-1,-1,1,1,1},
                               {1,-1,-1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                               {1,1,1,0,1,1,1,1,1,-1,1,1,0,1,1,1,1,1,1,1},
                               {0,1,1,-1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1},
                               {1,1,-1,1,1,1,-1,1,0,1,1,1,1,1,1,-1,1,1,1,1},
                               {0,-1,1,1,1,-1,1,1,1,-1,1,1,1,1,1,1,-1,1,1,1},
                               {1,1,-1,1,1,1,0,1,1,1,1,0,1,1,-1,0,1,0,-1,1},
                               {0,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,0,1,-1,0},
                               {1,1,1,1,1,1,-1,1,0,1,1,1,1,-1,1,1,1,0,1,1},
                               {1,1,1,1,-1,-1,1,-1,1,1,1,1,1,1,1,1,1,-1,1,1},
                               {-1,1,1,1,1,-1,1,1,1,1,1,1,-1,1,0,0,1,0,1,1},
                               {0,1,-1,1,1,-1,1,1,1,-1,1,1,1,1,1,1,0,-1,1,1},
                               {1,1,1,-1,1,1,1,-1,1,-1,1,1,1,1,1,1,1,1,1,-1},
                               {1,-1,1,1,1,1,1,1,1,1,0,-1,1,1,1,1,1,1,1,-1},
                               {1,1,1,-1,0,1,0,1,1,0,1,1,1,1,1,1,-1,0,1,1},
                               {1,1,-1,0,-1,1,1,-1,-1,1,1,-1,1,1,1,1,-1,-1,0,1},
                               {-1,0,0,1,1,1,1,1,-1,-1,1,1,1,0,1,0,0,1,-1,1}};
      System.out.println(s.cherryPickup(grid1));

    }
}
