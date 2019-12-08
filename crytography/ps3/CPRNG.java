import java.util.Set;
import java.util.HashSet;
import java.io.PrintWriter;

public class CPRNG {
  int[] fill;
  int[] tap;
  int shift = 0;
  public CPRNG(int d, int n, int t) {
  	fill = new int[d];
  	tap = new int[d];
    for (int i = fill.length - 1; i >= 0; i--) {
    	fill[i] = (n & 1);
    	tap[i] = (t & 1);
    	n = (n >>> 1);
      t = (t >>> 1);
    }
  }

  public int size() {return fill.length;}

  public int next() {
  	shift++;
  	int rt = fill[0];
  	int sum = 0;

  	// compute feed
    for (int i = 0; i < tap.length; i++) {
      sum = (sum + tap[i] * fill[i]) % 2;
    }

    // shift
    for (int i = 1; i < fill.length; i++) {
    	fill[i-1] = fill[i];
    }

    fill[fill.length - 1] = sum;
    return rt;
  }

  public String shrink_next() {
  	String rt = "";
    if (fill[0] == 0) {rt = "";}
    else {rt = String.valueOf(fill[1]);}

    next();
    next();
    return rt;
  }

  public int fill() {
  	int sum = 0;
  	for (int i = 0; i < fill.length; i++) {
  		sum = sum * 2 + fill[i];
  	}
  	return sum;
  }

  public boolean repeated() {
  	int degree = fill.length;
  	return shift >= (int)Math.pow(2, degree) - 2;
  }

  public static void main(String[] args) {
    try
    {
      PrintWriter out = new PrintWriter("./cprng.txt");
      CPRNG g = new CPRNG(30, 1, 1312);
      for (int i = 0; i < 1000000; i++) {
        if ()
        out.print(String.valueOf(g.next()));
      }
      out.close();
    } catch (Exception e) {}
  }
}