package maintain;

public class Solution459 {
	
	/*
	 * compute the next[] array for string s;
	 * if next[s.length() -1] != 0 && s.length() % (s.length() - next[s.length() -1] == 0) 
	 * 	return true;
	 * end if
	 */
	public boolean repeatedSubstringPattern(String s) {
		//edge case
		if(s == null || s.length() == 0 || s.length() == 1)return false;
		
		//construct next[]
		//initialization
		int [] next = new int[s.length()];
		next[0] = 0;
		int currentMatch = 0;
		for(int i = 1; i < s.length();i++) {
			/*we maintain each loop
			 currentMatch to be the character that comes before
			 i-th position's (maximum proper suffix-prefix) match
			 */
			while(currentMatch != 0 && s.charAt(i) != s.charAt(currentMatch)) 
				currentMatch = next[currentMatch-1];
			if(s.charAt(currentMatch) == s.charAt(i))currentMatch++;
			next[i] = currentMatch;
		}
		
        return next[s.length() -1] != 0 && 
        	   s.length() % (s.length() - next[s.length() -1]) == 0;
    }
	public static void main(String[] args) {
		Solution459 s1 = new Solution459();
		s1.repeatedSubstringPattern("ababac");
	}
}
