package logic;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
class Solution971 {

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    public int position = 0;
    List<Integer> res = new ArrayList<>();
    int i = 0;
    public List<Integer> flipMatchVoyageTrash(TreeNode root, int[] voyage) {
        List<Integer> rt = flipMatchVoyageHelper(root,voyage);
        if(position != voyage.length)return Arrays.asList(-1);
        else return rt;
    }
    public List<Integer> flipMatchVoyageHelper(TreeNode root, int[] voyage) {
        if(root == null)return new ArrayList<>();
        else{
            if(position >= voyage.length || voyage[position++] != root.val)return Arrays.asList(-1);
            else if(root.left == null)return flipMatchVoyageHelper(root.right,voyage);
            else if(root.right == null)return flipMatchVoyageHelper(root.left,voyage);
            else if(position + 1 >= voyage.length)return Arrays.asList(-1);
            List<Integer> rt = new ArrayList<>();
            if(root.left.val != voyage[position]){
                rt.add(root.val);
                TreeNode left = root.left;
                TreeNode right = root.right;
                root.left = right;
                root.right = left;
            }
            List<Integer> tempL = flipMatchVoyageHelper(root.left, voyage);
            List<Integer> tempR = flipMatchVoyageHelper(root.right, voyage);
            if(tempL.size() == 1 && tempL.get(0) == -1 ||
                    tempR.size() == 1 && tempR.get(0) == -1){
                return Arrays.asList(-1);
            }else{
                rt.addAll(tempL);
                rt.addAll(tempR);
                return rt;
            }
        }
    }
    
    public List<Integer> flipMatchVoyage(TreeNode root, int[] v) {
        return verifyMatch(root,v) && i == v.length? res:Arrays.asList(-1);
    }
    private boolean verifyMatch(TreeNode root, int[] v){
        if(root == null)return true;
        else{
            if(i >= v.length || root.val != v[i++])return false;
            if(root.left != null && i >= v.length)return false;
            if(root.left != null && v[i] != root.left.val && root.right == null)return false;
            if(root.left != null && v[i] != root.left.val){
                res.add(root.val);
                return verifyMatch(root.right,v) && verifyMatch(root.left,v);
            }
            return verifyMatch(root.left,v) && verifyMatch(root.right,v);

        }
    }


}