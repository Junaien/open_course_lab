package logic;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Arrays;
class Solution970 {

    public List<Integer> powerfulIntegersTrash(int x, int y, int bound) {
        Set<Integer> rt = new HashSet<>();
        if(x == 1 && y == 1){
            if(bound < 2)return new ArrayList<>();
            else return Arrays.asList(2);
        }else if(x == 1){
            for(int j = 0; Math.pow(y,j) + 1 <= bound; j++){
                rt.add((int)Math.pow(y,j) + 1);

            }
            return new ArrayList<>(rt);
        }else if(y == 1){
            for(int j = 0; Math.pow(x,j) + 1 <= bound; j++){
                rt.add((int)Math.pow(x,j) + 1);

            }
            return new ArrayList<>(rt);
        }else{
            for(int i = 0; Math.pow(x,i) < bound; i++)
                for(int j = 0; Math.pow(y,j) < bound; j++){
                    int target =(int) Math.pow(x,i) + (int)Math.pow(y,j);
                    if(target <= bound)rt.add(target);
                }

        }
        return new ArrayList<>(rt);
    }

    public List<Integer> powerfulIntegers(int x, int y, int bound) {
        Set<Integer> rt = new HashSet<>();
        for(int i = 1; i < bound; i *= (x > 1? x:bound)) {
            for(int j = 1; i + j <= bound; j *= ((y > 1) ? y : bound)) rt.add(i + j);
        }
        return new ArrayList<>(rt);
    }
}