package graph;

import java.util.*;

class Solution928 {
    //核心 暴力
    public int minMalwareSpreadNaive(int[][] graph, int[] initial) {
        int ret = Integer.MAX_VALUE;
        int result = Integer.MAX_VALUE;
        for(int i = 0; i < initial.length; i++){
            int ans = ignore(graph,initial,initial[i]);
            if(ans == result){
                ret = Math.min(initial[i],ret);
            }else if(ans < result){
                result = ans;
                ret = initial[i];
            }
        }
        return ret;
    }

    public int ignore(int[][]graph, int[] initial, int n){
        UF uf = new UF(graph.length);
        for(int i = 0; i < graph.length; i++)
            for(int j = i + 1; j < graph[i].length; j++){
                if(i != n && j != n && graph[i][j] == 1)uf.union(i,j);
            }
        Set<Integer> set = new HashSet<>();
        for(int i:initial){if(i!= n)set.add(uf.find(i));}
        int ret = 0;
        for(int i = 0; i < graph.length; i++){
            if(set.contains(uf.find(i)))ret++;
        }
        return ret;
    }

    private static class UF{
        public int[] data;
        public int[] size;
        public UF(int n){
            data = new int[n];
            size = new int[n];
            for(int i = 0; i < data.length; i++){
                data[i] = i;
                size[i] = 1;
            }
        }
        private int find(int l){
            if(l == data[l])return l;
            int root = find(data[l]);
            data[l]  = root;
            return root;
        }
        public boolean union(int i, int j){
            int left = find(i);
            int right = find(j);
            if(left == right)return false;
            data[left] = right;
            size[right] += size[left];
            return true;
        }
    }
    //核心思想 分开考虑 有毒 没有毒的
    public int minMalwareSpread(int[][] graph, int[] initial) {
        int ret = Integer.MAX_VALUE;
        int maxUnaffect = Integer.MIN_VALUE;
        UF uf = new UF(graph.length);
        Set<Integer> init = new HashSet<>();
        for(int i:initial)init.add(i);
        for(int i = 0; i < graph.length - 1; i++)
            for(int j = i+ 1; j < graph[i].length; j++){
                if(graph[i][j] == 1 && !init.contains(i) && !init.contains(j))uf.union(i,j);
            }

        Map<Integer, Integer> taken = new HashMap<>();
        int[] score = new int[initial.length];
        for(int i = 0; i < initial.length; i++){
            for(int j = 0; j < graph[initial[i]].length; j++){
                if(graph[initial[i]][j] == 0 || init.contains(j))continue;
                int root = uf.find(j);
                Integer takenBy = taken.get(root);
                if(takenBy == null){
                    taken.put(root,i);
                    score[i] += uf.size[root];
                }else if(takenBy == -1){}
                else{
                    score[takenBy] -= uf.size[root];
                    taken.put(root,-1);
                }
            }
        }

        for(int i = 0; i < score.length; i++){
            if(score[i] == maxUnaffect && initial[i] < ret)ret = initial[i];
            else if(score[i] > maxUnaffect){
                maxUnaffect = score[i];
                ret = initial[i];
            }
        }
        return ret;

    }

    public static void main(String[]args){
        Solution928 s = new Solution928();
        int [][] input1 = new int[][]{{1,1,0},{1,1,0},{0,0,1}};
        int [][] input2 = new int[][]{{1,1,0},{1,1,1},{0,1,1}};
        int [][] input3 = new int[][]{{1,1,0,0},{1,1,1,0},{0,1,1,1},{0,0,1,1}};
        int [][] input4 = new int[][]{{1,1,0,0,0,0,0,0,0,0},{1,1,0,0,0,0,0,0,0,0},{0,0,1,0,0,0,0,0,0,0},{0,0,0,1,0,0,1,0,0,1},
                {0,0,0,0,1,0,0,0,0,0},{0,0,0,0,0,1,0,0,0,0},{0,0,0,1,0,0,1,0,0,0},{0,0,0,0,0,0,0,1,0,0},{0,0,0,0,0,0,0,0,1,0},
                {0,0,0,1,0,0,0,0,0,1}};
        assert s.minMalwareSpread(input1,new int[]{0,1}) == 0;
        assert s.minMalwareSpread(input2,new int[]{0,1}) == 1;
        assert s.minMalwareSpread(input3,new int[]{0,1}) == 1;
        assert s.minMalwareSpread(input4,new int[]{2,1,9}) == 9;
    }

}