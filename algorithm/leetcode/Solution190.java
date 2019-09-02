package bit_manipulation;

public class Solution190 {
	// you need treat n as an unsigned value
	public int reverseBits(int n) {
        long rt = 0;
        long nt =  Integer.toUnsignedLong(n);
        for(int i = 0; i < 32; i ++){
            rt = rt * 2 + nt % 2;
            nt /= 2;
        }
        return (int)rt;
    }
	    
    public static void main(String[] args) {
    	int input =   -2147483648;
    	Solution190 s = new Solution190();
    	System.out.println(s.reverseBits(input));
    	
    }
	    
	   
}
