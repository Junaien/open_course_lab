package math_thinking;

import java.util.HashMap;
import java.util.Map;

public class Solution964 {

    /**
     * Intuition:    for the number of x/x, we use either target mod x or (x - target mod x),
     *               since the only way to express target mod x is to use x/x, then we can recurse on smaller
     *               number set
     * Algorithm:    TODO-LIST
     * Poof:         TODO-LIST
     * Runing Time:  TODO-LIST
     * Poof:         TODO-LIST
     * @param x
     * @param target
     * @return
     */
    public int leastOpsExpressTargetDP(int x, int target) {
        //using dynamic programing
        //edge case
        if(x < 1)return -1;
        Map<String, Integer> dp = new HashMap<>();
        return leastOpsExpressTargetDP(x,dp,target,0)-1;
    }
    public int leastOpsExpressTargetDP(int base, Map<String,Integer> dp,int target,int digit){
        String str = String.valueOf(digit) + "*" + String.valueOf(target);
        Integer value = dp.get(str);
        //base case
        if(value != null)return value;
        if(target == 0)return 0;
        if(target == 1)return digit == 0? 2:digit;

        int rt = Integer.MAX_VALUE;
        int pos = 0, neg = 0;
        int remainder = target % base;
        if(remainder == 0)rt = leastOpsExpressTargetDP(base, dp,target / base,    digit + 1);
        else {
            pos = (digit == 0 ? 2 : digit) * remainder;
            neg = (digit == 0 ? 2 : digit) * (base - remainder);
            rt = Math.min(leastOpsExpressTargetDP(base, dp, target / base, digit + 1) + pos,
                    leastOpsExpressTargetDP(base, dp, target / base + 1, digit + 1) + neg);
            dp.put(str, rt);
        }
        return rt;

    }
    public static void main(String[] args){
        Solution964 s = new Solution964();
        System.out.println(s.leastOpsExpressTargetDP(2,929));
    }

}
