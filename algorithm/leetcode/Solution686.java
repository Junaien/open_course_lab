package maintain;
import java.math.BigInteger;
class Solution686 {
    public int repeatedStringMatch(String A, String B) {
        /*
         * input:"abcd","cdabcdab", output:3
         */
        //intuition we can using a rolling hash data structure
        RollingHash Enge1 = new RollingHash();
        RollingHash Enge2 = new RollingHash();
        for(int i = 0; i < B.length();i++)Enge1.append(B.charAt(i));
        int count = 1;
        int l = 0;
        int pollIndex = 0;
        while(l <= A.length() + B.length()){
            for(int i = 0; i < A.length(); i++){
                Enge2.append(A.charAt(i));
                l++;
                if( l > B.length()){
                    Enge2.poll(A.charAt(pollIndex));
                    pollIndex = (pollIndex + 1) % A.length();
                }
                if(Enge2.hash() == Enge1.hash())return count;
            }
            count++;
        }
        
        return -1;

    }
    
    private static class RollingHash{
        private long hash;
        private long baseModInverse;
        private long magic;
        public static final long PRIME = 1_000_000_007;
        public static final long BASE = 256;
        
        public RollingHash(){
           
            hash = 0;
            baseModInverse =  BigInteger.valueOf(BASE)
                             .modInverse(BigInteger.valueOf(PRIME))
                             .intValue();
             System.out.println(baseModInverse);
            magic = baseModInverse % PRIME; 
            
        }
        public void append(long n){
            hash = (hash * BASE + n)  % PRIME;
            magic = (magic * BASE) % PRIME;    
        }
        public void poll(long n){
            hash = (hash - n * magic + PRIME * BASE) % PRIME;
            magic = (magic * baseModInverse) % PRIME;
        }
        public long hash(){
            return hash;
        }
        
    }
    public static void main(String[] args) {
		Solution686 s2 = new Solution686();
		System.out.println(s2.repeatedStringMatch("abcd", "cdabcdab"));
	}
}