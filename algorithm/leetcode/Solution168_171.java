
public class Solution168_171 {

	
	
	public int titleToNumber(String s) {
        int rt = 0;
        for(int i = 0; i < s.length(); i++){
            rt = rt * 26 + s.charAt(i) - 'A' + 1;
        }
        return rt;
    }
	
    /* input -> output
	    1 -> A
	    2 -> B
	    3 -> C
	    ...
	    26 -> Z
	    27 -> AA
	    28 -> AB 
	    ...
     */
	public String convertToTitle(int n) {
        StringBuilder rt = new StringBuilder("");
        while(n -1 >= 0){ 
            rt.append((char)('A' + (n  -1)% 26 ));
            n = (n -1) / 26;
        }
        return rt.reverse().toString();
	}
}
