package dp;
import java.util.Arrays;

//input1  -> ["alex","loves","leetcode"]
	//output1 -> "alexlovesleetcode" or any permutation
	//input2  -> ["catg","ctaagt","gcta","ttca","atgcatc"]
	//output2 -> "gctaagttcatgcatc"
public class Solution943 {
	//initialization
	int[] path = null;
	int curMin = Integer.MAX_VALUE;
	/*Intuition:
	 * a)using dfs to search all sample space
	 * b)doing pruning along the way of dfs searching
	 */
	public String shortestSuperstringBF(String[] A) {
		//edge case
		if(A == null || A.length == 0)return null;
		if(A.length == 1)return A[0];
		//initialization
		/*costTable contains when A[i] appends A[j], exactly how
		 *many additional characters got added when ignoring the 
		 *prefix of A[j] that overlaps suffix of A[i] 
		 */
		int[][] costTable = computerCostTable(A);
		
		
		//dfs search all possible path and along the way update optimal solution
		dfs(0,0,new int[A.length],A,costTable,0);
        return constructStortestSuperstringByPath(A,path,costTable);
    }
	private int calculateCost(int i, int j, String[]A) {
		String left = A[i];
		String right = A[j];
		for(int k = 0; k < left.length();k++) {
			if(right.startsWith(left.substring(k))) {
				int prefixLength = left.length() - k;
				return right.length() - prefixLength;
			}
		}
		return right.length();
	}
	
	//depth    current depth: 0 is the depth when we select first element
	//curCost: current cost corresponds the path
	//used:    bit mask for current used string
	//curPath: currentPath
	private void dfs(int depth,  int curCost, int[] curPath,
			         String[] A, int[][]costTable, int used) {
		
		//pruning when it is not nessasary to continue this brach
		if(curCost >= curMin)return;
		if(depth == A.length) {
			curMin = curCost;
			path = new int[A.length];
			for(int i =0; i < A.length;i++) {
				path[i] = curPath[i];
			}
			return;
		}
		
		for(int h = 0; h < A.length; h++) {
			curPath[depth] = h;
			if(depth == 0){
				dfs(depth+1,A[h].length(),curPath,
					    A,costTable,used ^ (1 << h));
			}else if((used & (1 << h)) == 0) {
				int lastPosition = curPath[depth-1];
				dfs(depth+1,curCost+costTable[lastPosition][h],curPath,
				    A,costTable,used ^ ( 1 << h));
			}
		}
		return;
		
		
	}
	private int[][] computerCostTable(String[]A){
		int[][] costTable = new int[A.length][A.length];
		for(int i = 0; i < A.length; i++)
			for(int j = 0; j < A.length; j++) {
				costTable[i][j] = calculateCost(i,j,A);
			}
		return costTable;
	}
	private String constructStortestSuperstringByPath(String[] A, int[] path,int[][]costTable) {
		String rt = "";
		rt += A[path[0]];
		for(int i = 1; i < path.length;i++) {
			int indexL = path[i-1];
			int indexR = path[i];
			int cost = costTable[indexL][indexR];
			rt += A[indexR].substring(A[indexR].length() - cost);
		}
		return rt;
	}
	
	//dp
	public String shortestSuperstringDP(String[] A) {
		//edge case
		if(A == null || A.length == 0)return "";
		if(A.length == 1)return A[0];
		
		//initialization
		/*costTable contains when A[i] appends A[j], exactly how
		 *many additional characters got added when ignoring the 
		 *prefix of A[j] that overlaps suffix of A[i] 
		 */
		int[][] costTable = computerCostTable(A);
		//initialization
		int[][]dp = new int[1 << A.length][A.length];
		for(int i = 1; i < (1 << A.length); i++){
			Arrays.fill(dp[i],Integer.MAX_VALUE);
		}
		path = new int[A.length];
		//base case
		//computing the answer when there is only one bits
		for(int i = 0; i < A.length;i++) {
			dp[1<<i][i] = A[i].length();
		}
		for(int i = 1; i < (1 << A.length); i++){
			for(int k = 0; k < A.length;k++){
				if(((1 << k) & i) == 0)continue;
				for(int j = 0; j < A.length;j++){
					if(((1 << j) & i) == 0){
						int curCost = dp[i ^ (1 << j)][j];
						dp[i ^ (1 << j)][j] = Math.min(curCost,dp[i][k] + costTable[k][j]);
					}
				}
			}
		}
		
		//reconstruct path
		int curMinIndex = -1;
		int curMin = Integer.MAX_VALUE;
		for(int i = 0 ; i < A.length;i++) {
			if(dp[(1 << A.length)-1][i] < curMin) {
				curMinIndex = i;
				curMin = dp[(1 << A.length) - 1][i];
			}
		}
		path[A.length-1] = curMinIndex;
		int partialPathBits = (1 << A.length)- 1;
		for(int i = A.length -1; i >= 1; i--){
			int prePathBits = partialPathBits ^ (1 << curMinIndex);
			for(int j = 0; j < A.length; j++){
				if(dp[prePathBits][j] + costTable[j][curMinIndex] == dp[partialPathBits][curMinIndex]) {
					path[i-1] = j;
					partialPathBits = prePathBits;
					curMinIndex = j;
					break;
				}
			}
		}
			
        return constructStortestSuperstringByPath(A, path,costTable);
    }
	public static void main(String[]args){
		String[] input1 = new String[] {"cedefifgstkyxfcuajfa","ooncedefifgstkyxfcua",
                "assqjfwarvjcjedqtoz","fcuajfassqjfwarvjc",
                "fwarvjcjedqtozctcd","zppedxfumcfsngp",
                "kyxfcuajfassqjfwa","fumcfsngphjyfhhwkqa",
                "fassqjfwarvjcjedq","ppedxfumcfsngphjyf",
                "dqtozctcdk"};
		Solution943 s = new Solution943();
		System.out.println(s.shortestSuperstringDP(input1));
		System.out.println(s.shortestSuperstringBF(input1));
	}
}
