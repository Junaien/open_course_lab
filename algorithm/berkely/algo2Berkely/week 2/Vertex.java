public class Vertex implements Comparable<Vertex>{
    private int e;
    private int key;
    public Vertex(int e, int key){
      this.e = e;
      this.key = key;
    }
    public void setKey(int key){
      this.key = key;
    }
    public int  getKey(){
      return key;
    }
    public int getE(){
      return e;
    }

    public int compareTo(Vertex v){
      if(this.getKey() > v.getKey())return 1;
      else if (this.getKey() == v.getKey())return 0;
      else return -1;
    }
}
