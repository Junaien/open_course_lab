public class UF{
  private int[] parentPointer;
  private int[] rank;
  private int count;
  //size is node number,  start from 1
  public UF(int size){
      parentPointer = new int[size+1];
      rank = new int[size+1];
      count = size;
      for(int i = 0 ; i < parentPointer.length; i++){
        parentPointer[i] = i;
        rank[i] = 0;
      }
  }
  public int count(){
    return count;
  }
  public int find(int n){
      if(n<=0 || n >= rank.length){
        System.out.println("Find operation out of bound");
      }
      if(parentPointer[n] == n){
        return n;
      }
      int root = find(parentPointer[n]);
      parentPointer[n] = root;
      return root;
  }
  public int union(int l, int r){
    l = find(l);
    r = find(r);
    if(l == r)return 0;
    if(rank[l] > rank[r]){
      parentPointer[r] = l;
    }else if(rank[r] > rank[l]){
      parentPointer[l] = r;
    }else{
      rank[l]++;
      parentPointer[r] = l;
    }
    count--;
    return 1;
  }
  public String toString(){
    String s = "";
    for(int i = 1; i < parentPointer.length; i++){
      s +=  (find(i) + ", ");
    }
    return s;
  }

}
