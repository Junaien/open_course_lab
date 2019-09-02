package dp;

class Solution799 {
    public double champagneTowerTrash(int poured, int query_row, int query_glass) {
        if(poured == 0)return 0.0;
        //initialization
        double[] champagne = new double[]{poured * 2 + 1};
        for(int level = 0; ; level++){
            //polulate this level
            double[] temp = new double[level + 1];
            for(int i = 0; i < temp.length; i++){
                int leftIndex = i - 1;
                int rightIndex = i;
                if(leftIndex >=0 && leftIndex < champagne.length){
                    double leftOverOnLeft = (champagne[leftIndex] - 1) > 0.0? champagne[leftIndex] - 1: 0;
                    temp[i] += (0.5 * leftOverOnLeft);
                }
                if(rightIndex >=0 && rightIndex < champagne.length){
                    double rightOverOnRight = (champagne[rightIndex] - 1) > 0.0? champagne[rightIndex] - 1: 0;
                    temp[i] += (0.5 * rightOverOnRight);
                }
            }
            //query this level
            if(query_row == level){
                return temp[query_glass] >= 1? 1:temp[query_glass];
            }
            champagne = temp;
        }
    }

    public double champagneTower(int poured, int query_row, int query_glass) {
        if(poured == 0)return 0.0;
        //initialization
        double[] champagne = new double[101];
        champagne[0] = poured;
        for(int level = 0; level < 101; level++){
            //query this level
            if(query_row == level)return Math.min(1.0,champagne[query_glass]);
            //polulate next level
            for(int i = level; i>=0; i--){
                champagne[i+1] += Math.max(0.0,champagne[i] - 1) / 2.0;
                champagne[i]   =  Math.max(0.0,champagne[i] - 1) / 2.0;
            }
        }
        return 0.0;
    }
    public static void main(String[] args){
        Solution799 s = new Solution799();
        System.out.println(s.champagneTower(2,1,1));
    }
}