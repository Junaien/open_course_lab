package math_thinking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import java.util.Set;

class Solution952 {
    private static int index = 1;
    private static class UF{
        public int[] nodes;
        public UF(int n){
            nodes = new int[n+1];
            for(int i = 1; i < n+1;i++){
                nodes[i] = i;
            }
        }
        public boolean union(int i, int j){
            int iR = find(i);
            int jR = find(j);
            if(iR == jR)return false;
            else{
                nodes[iR] = jR;
                return true;
            }
        }
        public int find(int i){
            int c = i;
            while(nodes[c] != c){
                c = nodes[c];
            }
            while(nodes[i] != i){
                int temp = i;
                i = nodes[i];
                nodes[temp] = c;
            }
            return c;
        }
    }
    public int largestComponentSize(int[] A) {
        //store all possible prime factor into union find --> hashTable
        //union all the factor that composes a number
        //find out how many number are connected
        
        //edge case 
        if(A == null || A.length == 0)return 0;
        else if(A.length == 1)return 1;
        
        //initialization: 
        //primeNumberTable: factorization of prime for each number
        //uf: union find representation of prime number
        Map<Integer,Integer>primeNumberTable = new HashMap<>();
        List<Set<Integer>> factors = new ArrayList<>();
        for(int i = 0; i < A.length; i++){
            int temp = A[i];
            Set<Integer> s = factorization(temp);
            factors.add(s);
            for(Integer j:s) {
            	if(!primeNumberTable.containsKey(j)) {
            		primeNumberTable.put(j, index++);
            	}
            }
        }
        
        UF uf  = new UF(primeNumberTable.size());
        for(int k = 0; k < A.length;k++) {
        	Set<Integer> s = factors.get(k);
        	int prevPrime = -1;
        	for(Integer i:s){
        		if(prevPrime == -1) {
        			prevPrime = i;
        		}else {
	        		uf.union(primeNumberTable.get(prevPrime), primeNumberTable.get(i));
	        		prevPrime = i;
	        	}
            }
        }
        	
        //count the maximum component on UF
    	int currentMax = Integer.MIN_VALUE;
        Map<Integer,Integer> map = new HashMap<>();
        for(int k = 0; k < A.length; k++){
        	Set<Integer> s = factors.get(k);
            for(Integer i: s) { 
            	int root = uf.find(primeNumberTable.get(i));
                int freq = map.getOrDefault(root,0) + 1;
                if(freq > currentMax)currentMax = freq;
                map.put(root,freq);
                break;
            }
            
            
        }
        index = 1;
        return currentMax;        
    }

    private Set<Integer> factorization(int temp){
    	Set<Integer> rt = new HashSet<>();
  
        for(int i = 2; i*i <= temp ; i++){
            while(temp % i == 0){
                rt.add(i);
                temp /= i;
            }
        }
        if(temp != 1)rt.add(temp);
        
        return rt;   
    }
    public static void main(String []args) {
    	Solution952 s = new Solution952();
    	System.out.println(s.largestComponentSize(new int[] {4,6,3,15}));
    }
}