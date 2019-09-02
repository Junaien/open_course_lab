package state_of_art;

import java.util.Arrays;

public class Solution28 {
    public int strStr(String haystack, String needle) {
        return 0;
    }

    public int indexOfKMP(String s, String pattern){
        if(s == null)throw new IllegalArgumentException("string can't be null");
        if(s == "")return -1;
        int[] next = next(pattern);
        System.out.println(Arrays.toString(next));
        int j = 0, i = 0;
        for(; i < s.length() && j < pattern.length();){
            while(pattern.charAt(j) != s.charAt(i) && j != 0)j = next[j - 1];
            if(pattern.charAt(j) == s.charAt(i))j++;
            i++;
        }
        return j == pattern.length()? i - pattern.length(): -1;
    }

    private static int[] next(String pattern){
        if(pattern == null || pattern.length() == 0)throw new IllegalArgumentException("pattern can't be null or empty");
        int[] longestCommonPreSuffix = new int[pattern.length()];
        longestCommonPreSuffix[0] = 0;
        for(int i = 1; i < longestCommonPreSuffix.length; i++){
            int prev = longestCommonPreSuffix[i - 1];
            while(true){
                if(prev < 0)break;
                else if(prev == 0){
                    longestCommonPreSuffix[i] = (pattern.charAt(prev) == pattern.charAt(i))? 1:0;
                    break;
                }else{
                    if(pattern.charAt(prev) == pattern.charAt(i)){
                        longestCommonPreSuffix[i] = prev + 1;
                        break;
                    }
                    prev = longestCommonPreSuffix[prev - 1];
                }
            }
        }
        return longestCommonPreSuffix;
    }

    public int indexOfDFA(String s){
        return 0;
    }

    //Implement strStr().
    //Return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.
    //{haystack:"hello", needle:"ll"}  ----------> [2]
    //{haystack:"aaaaa", needle:"bba"} ----------> [-1]
    public static void main(String[] args){
        Solution28 s = new Solution28();
        assert s.indexOfKMP("mississippi","issip") == 4;

    }
}
