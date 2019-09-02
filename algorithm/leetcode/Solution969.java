package logic;
import java.util.List;
import java.util.ArrayList;
class Solution969 {
    private List<Integer> pancakeSortTrash(int[] A, int endPoint){
        if(endPoint == 0)return new ArrayList<>();
        else{
            List<Integer> rt = new ArrayList<>();
            int targetIndex = -1;
            for(int i = 0; i < A.length;i++){
                if(A[i] == endPoint + 1){
                    targetIndex = i;
                    break;
                }
            }
            if(targetIndex == endPoint)return pancakeSortTrash(A, endPoint - 1);
            else if(targetIndex == 0){
                for(int i = 0; i <= endPoint / 2; i++){
                    int t = A[i];
                    A[i] = A[endPoint - i];
                    A[endPoint - i] = t;
                }
                rt.add(endPoint + 1);
                rt.addAll(pancakeSortTrash(A, endPoint - 1));
                return rt;
            }else{
                int[] temp = new int[endPoint + 1];
                System.arraycopy(A, targetIndex + 1, temp,0, endPoint - targetIndex);
                System.arraycopy(A, 0, temp,endPoint - targetIndex, targetIndex + 1);
                for(int i = 0; i <= (endPoint - targetIndex - 1) / 2; i++){
                    int t = temp[i];
                    temp[i] = temp[(endPoint - targetIndex - 1) - i];
                    temp[(endPoint - targetIndex - 1) - i] = t;
                }
                rt.add(targetIndex + 1);
                rt.add(endPoint + 1);
                List<Integer> res = pancakeSortTrash(temp, endPoint - 1);
                rt.addAll(res);
                return rt;
            }


        }
    }

    public List<Integer> pancakeSort(int[] A) {
        List<Integer> rt = new ArrayList<>();
        for(int x = A.length, index; x > 0; x--){
            for(index = 0; A[index] != x; index++);
            rt.add(index + 1);
            reverse(A,index);
            rt.add(x);
            reverse(A,x - 1);
        }
        return rt;
    }

    private void reverse(int[] A, int end){
        if(A == null)throw new IllegalArgumentException("array to be reversed shouldn't be null");
        else if(end < 0 || end >= A.length)throw new IllegalArgumentException("index out of bound");
        else{
            for(int i = 0 , j = end; i < j; i++, j--){
                int temp = A[i];
                A[i] = A[j];
                A[j] = temp;
            }
        }
    }
}