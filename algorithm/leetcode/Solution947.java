package dp;

import java.util.ArrayList;
import java.util.List;


public class Solution947 {
	 public int removeStones(int[][] stones) {
	        List<int[]>dp = new ArrayList<>();
	        for(int i = 0; i < stones.length;i++){
	        	List<int[]>newDP = new ArrayList<>();
	            for(int k = 0; k < dp.size();k++){
	                int[] point = dp.get(k);
	                if(point[0] == stones[i][0] || point[1] == stones[i][1]){
	                    
	                }else {
	                	newDP.add(point);
	                }
	            }
	            dp = newDP;
	            dp.add(stones[i]);
	            
	        }
	        return stones.length - dp.size();
	            
	    }
    public static void main(String[] args) {
    	Solution947 s = new Solution947();
    	s.removeStones(new int[][]{{0,0},{0,1},{1,0},{1,2},{2,1},{2,2}});
    }
	
}
