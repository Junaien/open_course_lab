package greedy;

import java.util.Arrays;

class Solution948 {
	/*
	 * Intuition: 
	 * we must buy  our points with less power as possible,
	 * we must sell our points for  more power as possible
	 */
	
	/*
	 * Algorithm:
	 * greedily buy point as long as it can afford to do so,
	 */
	
	/*
	 * Claim: 
	 * 1.1 Algorithm will have the maximum point possible during this process
	 * 
	 * 1.2 Algorithm will stay more points than any Sequence of moves
	 * Proof:
	 * a) Let any sequence of moves be S, let sequence of moves of our algorithm be A
	 * b) Assume for move 1...k(k >= 1), the Points that obtain by doing move A_1...A_k larger than S_1...S_k
	 * c) for move k + 1, the only case that will breaks P(A_1...A_k+1) >= P(S1...S_k+1) is
	 * 	  P(A_1...A_k) = P(S1....S_k) &&
	 *    A_k+1 = face_down			  &&
	 *    S_k+1 = face_up			  &&
	 *    
	 * d) after move k, since A only picks small tokens as possible for face_up, large tokens as possible for face_down
	 * 	  power_left(A) = starting power - sum(face_up token's power in A1..A_k) + sum(face_down token's power in A1.A_K) 
	 * 			>=
	 * 	  power_left(B) = starting power - sum(face_up token's power in S1..S_k) + sum(face_down token's power in S1.S_K)
	 * e) so if S_k+1 can do move face_up, A_k+1 can also be move face_up, we reach a contradictory
	 * 
	 */
    public int bagOfTokensScore(int[] tokens, int P) {
        if(tokens == null || tokens.length == 0)return 0;
        Arrays.sort(tokens);
        //Initialization
        //the index before nextBuy are all been played face up
        //the index after nextSell are all been played face down
        int nextBuy = 0, nextSell = tokens.length-1;
        int point = 0;
        int maxP = 0;
        while(nextBuy <= nextSell){
            //buy the point using cheap token as long as possible
            while(nextBuy <= nextSell && P >= tokens[nextBuy]){
                P -= tokens[nextBuy];
                nextBuy++;
                point++;
                maxP = Math.max(point,maxP);
            }
            
            //sell the point for the expensive token
            if(nextSell >= nextBuy && point > 0){
                P += tokens[nextSell];
                nextSell--;
                point--;
            }else{
                break;
            }
        }
        return maxP;
    }
}