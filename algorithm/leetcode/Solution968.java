package greedy;

import jdk.nashorn.api.tree.Tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Solution968{

    private static class TreeNode
    {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    private static TreeNode createTree(Integer [] input)
    {
        if(input == null || input.length == 0)return null;
        if(input[0] == null)return null;
        TreeNode[] tr = new TreeNode[input.length];
        tr[0] = new TreeNode(input[0]);
        for(int i = 0; i < input.length; i++){
            if(tr[i] == null)continue;
            else{
                if(2 * i + 1 < input.length && input[2 * i + 1] != null){
                    TreeNode n = new TreeNode(input[2 * i + 1]);
                    tr[i].left = n;
                    tr[2 * i + 1] = n;
                }
                if(2 * i + 2 < input.length && input[2 * i + 2] != null){
                    TreeNode n = new TreeNode(input[2 * i + 2]);
                    tr[i].right = n;
                    tr[2 * i + 2] = n;
                }
            }
        }
        return tr[0];
    }
    int rt = 0;
    public int minCameraCover(TreeNode root) {
        if(root == null)return 0;
        statusOfNode(root,root);
        return Math.max(1,rt);
    }

    /**
     * intuition: if we put a camera in the leaf, we can always move the camara to its parent, and along
     *            the way move all cameras one position up, we will produce not worse solution
     * algorithm:
     *            consider the tree sizes is larger than 1
     *            we will put camera on every leaves parent
     *            delete all the nodes that is covered in this process
     *            repeat thoes three steps until every nodes are covered
     *
     *            consider the tree size that is one we return 1
     *            consider the tree size that is none we return 0
     *
     * proof
     * 1.1 algorithm produce a optimal solution
     *            we know there must exist a equally optimal solution on the solution set that put camera on leave's parent
     *            let nodes that cover after put those camera be T, original set be S,
     *            now, the optimal solution must put camera in set (S - T) so that it is optimal for (S-T)
     *            otherwise we can use a better solution for (S - T) to combine with T to get a better solution
     *            we reach a contradiction
     *
     * analysis(assume the tree has more than one nodes):
     * 1.2 implementation
     *            using dfs, to determine if a node has a camera or not
     *            statusOfNode determines the status of a Node
     *            0: this node is not covered
     *            1: this node is cover by neighbor
     *            2: this node has camera on
     *
     *            for any leave we return 0
     *            for any node other than leave
     *            if left child or right child is 0, we must put camara here return 2, means camera node
     *            if left child or right child is 2, we return 1, mean incident parent
     *            if left child or right chile is 1, we return 0, means leaf
     * 1.3 this implementation simulate the pealing process
     *            assume dfs return correct status for node has height n
     *            base case: for height = 0, it is leaf, so it is correct to return 0
     *            so in the pealing process, if any of child is leaf we put camera on it
     *            if any of child is camera node, we know it self is incident parent
     *            else (***warninig***) root can't be leaves, except root this node is leaf
     *
     *
     * @param root
     * @param top
     * @return
     */
    private int statusOfNode(TreeNode root,TreeNode top){
        if(root == null)return 1;
        else{
            int statusL = statusOfNode(root.left,top);
            int statusR = statusOfNode(root.right,top);
            if(statusL == 0 || statusR == 0){
                rt++;
                return 2;
            }else if(statusL == 2 || statusR == 2){
                return 1;
            }else if(top == root){
                rt ++;
                return 2;
            }else{
                return 0;
            }
        }
    }
    public static void main(String[] args){
        Solution968 s = new Solution968();
        TreeNode n = createTree(new Integer[]{0,1,2,null,null,null,3});
        System.out.println(s.minCameraCover(n));
    }

}
