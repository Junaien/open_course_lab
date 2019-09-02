package math_thinking;

class Solution754 {
	/*
	 * input  -> 3
	 * output -> 2
	 * 1)
	 * let S = 1 + 2 + 3 + .... k be the first sum that is bigger than target
	 * let T = (S - target) / 2
	 * S - 2 * T = target
	 * 
	 * 2)
	 * case 1: if (S - target) is even and T <= k, 
	 * then the answer is k,because we can flip T to be negative
	 * S = 1 + 2 + 3 - T + T+ 1.... k in this case
	 * 
	 * case 2: if (S - target) is even and T > k(S - 2k > target),
	 * we know T < S
	 * we can negate k, now the question becomes from (1....k-1)
	 * flip subset such that sum(subset) = (S - 2k - target)/2 = T - k
	 * if(T - k) > k -1, we do this kind of reduction again
	 * 
	 * -->since every time we make sure left side is always positive
	 * -->since T < S,  T - k - (k -1) - (k - 2) - .... - 2 < 1, the program will always stop subtracting T
	 * we know it is always going to find the correct answer for negating numbers
	 * 
	 * case 3: if (S - target) is odd number, we can't do it in k moves, since flipping
	 * cause S to be decrease by even number
	 * 
	 * we can try k + 1 moves, if S - target + k + 1 is even, we are back to case 1,2, answer is k + 1;
	 * or else the answer is k + 2;
	 */
    public int reachNumber(int target) {
        int sum = 0;
        target = Math.abs(target);
        if(target == 0)return 0;
        int i = 1;
        for(; sum < target;i++){
            sum += i;
        }
        if(target == sum)return i-1;
        else if((sum - target) % 2 == 0)return i-1;
        else if((sum - target + i) % 2 == 0)return i;
        else return i+1;
        
    }
    public static void main(String[] args) {
    	Solution754 s = new Solution754();
    	System.out.println(s.reachNumber(100));
    }
}
