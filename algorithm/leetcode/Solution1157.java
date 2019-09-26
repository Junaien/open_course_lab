import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class Solution1157 {
public SegmentTree tree;

  public static class SegmentTree {
    private Map<Integer, List<Integer>> numOccurrences;
    private static class Node {
      public int majority;
      public int lower;
      public int upper;
      public Node left;
      public Node right;
      public Node(int lower, int upper) {
        this.lower = lower;
        this.upper = upper;
      }
    }
    private Node root;
    public SegmentTree(int[] arr){
      numOccurrences = new HashMap<>();
      for(int i = 0; i < arr.length; i++) {
        List<Integer> occurrences = numOccurrences.getOrDefault(arr[i], new ArrayList<>());
        occurrences.add(i);
        numOccurrences.put(arr[i], occurrences);
      }
      root = buildSegmentTree(root, arr, 0, arr.length - 1);
      // print(root);

    }

    private Node buildSegmentTree(Node root, int[] arr, int lower, int upper) {
      if(root == null) {
        root = new Node(lower, upper);
      }

      if(lower == upper) {
        root.majority = arr[lower];
        return root;
      }

      int middle = (lower + upper) / 2;
      root.left = buildSegmentTree(root.left, arr, lower, middle);
      root.right = buildSegmentTree(root.right, arr, middle + 1, upper);
      int leftMajority = findFrequency(lower, upper, root.left.majority);
      int rightMajority = findFrequency(lower, upper, root.right.majority);
      if(leftMajority > ((upper - lower + 1) / 2)) {
        root.majority = root.left.majority;
      }else if(rightMajority > ((upper - lower + 1) / 2)) {
        root.majority = root.right.majority;
      }else {
        root.majority = -1;
      }
      return root;
    }

    private int findMajority(Node n, int left, int right) {
      if(n == null) return -1;
      else if(right < n.lower || left > n.upper) return -1;
      else if(n.lower >= left && n.upper <= right) {return n.majority;}
      int half = (right - left) / 2;
      int leftM = findMajority(n.left, left, right);
      int rightM = findMajority(n.right, left, right);
      int leftMFreq = findFrequency(left, right, leftM);
      int rightMFreq = findFrequency(left, right, rightM);
      if(leftMFreq > half) return leftM;
      else if(rightMFreq > half) return rightM;
      else return -1;
    }

    public int findMajority(int left, int right, int threshold) {
      int rt = findMajority(root, left, right);
      return findFrequency(left, right, rt) >= threshold? rt : -1;
    }

    private int findFrequency(int lower, int upper, int num) {
      if(numOccurrences.get(num) == null) {
        return -1;
      }
      int leftIndex = binarySearch(numOccurrences.get(num), lower);
      int rightIndex = binarySearch(numOccurrences.get(num), upper + 1);
      return rightIndex - leftIndex;
    }

    private static int binarySearch(List<Integer> arr, int key) {
      int lower = 0, upper = arr.size() - 1;
      int middle = -1;
      // invariant: (upper  ...) >= key
      //            (...   lower) <  key
      while(lower <= upper) {
        middle = (lower + upper) / 2;
        int midVal = arr.get(middle);
        if(midVal >= key)upper = middle - 1;
        else if(midVal < key)lower = middle + 1;
      }
      return lower;
    }

    public void print() {
      print(root);
    }

    private void print(Node root) {
      if(root == null) return;
      System.out.println("[ " + root.lower + ", " + root.upper + " ]: " + root.majority);
      print(root.left);
      print(root.right);
    }
  }

  public Solution1157(int[] arr) {
    tree = new SegmentTree(arr);
  }

  public int query(int left, int right, int threshold) {
    return tree.findMajority(left, right, threshold);
  }
}


