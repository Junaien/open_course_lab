//https://leetcode.com/contest/weekly-contest-150/problems/last-substring-in-lexicographical-order/
//https://leetcode.com/problems/last-substring-in-lexicographical-order/discuss/363662/Short-python-code-O(n)-time-and-O(1)-space-with-proof-and-visualization
public class Solution1163 {
  
  // 0 - what to do when we see two characters are different in two substring
  // 1 - Loop Invariant: for any p < j && p != i, s[p:] is not the solution
  //   1.1 - initially: i = 0, j = 1, the loop invariant stands
  //   1.2 - transition:
  //       - case 1: if s[j+span] == s[i+span], one round in the loop does span++. invariant 1 is not violated
  //       - case 2: if s[j+span] <  s[i+span], e.g.     i x x x x z          or  x_i x x x x (z)
  //         -                                                 j x x x x b                     x_j x x x x (b) 
  //         - we know that s[p:] where j <= p <= index(b) are not candidates
  //         - we can update j -> index(b) + 1, then variant 1 holds
  //       - case 3: if s[j+span] >  s[i+span], e.g.     i x x x x b          or  x_j x x x x (b) 
  //         -                                                 j x x x x z                     x_j x x x x (z)
  //         - we know that s[p:] where i <= p <= index(b) are not candidates
  //         - we can update i -> max(index(b) + 1, j), then update j -> i + 1, so that invariant will hold still
  //   1.3 - termination:
  //       - on exit, j + span == n, that means for any p < n && p != i s[p:] is not solution, we know s[i:] is the solution 
  public String lastSubstring(String s) {
    int n = s.length();
    int i = 0, j = 1, span = 0;
    while(j + span < n){
      char left = s.charAt(i + span);
      char right = s.charAt(j + span);
      if(left == right)span++;
      else{
        if(left < right){
          i = Math.max(i + span + 1, j);
          j = i + 1;
        }else j += span + 1;
        span = 0;
      }
    }
    return s.substring(i);
  }
}
