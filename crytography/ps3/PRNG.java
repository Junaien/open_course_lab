import java.util.Set;
import java.util.HashSet;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
public class PRNG {

  LFSR[] L;
  public PRNG() {
    L = new LFSR[4];
    L[0] = new LFSR(20, 123, 524349);
    L[1] = new LFSR(21, 421, 1048631);
    L[2] = new LFSR(23, 11, 4194320);
    L[3] = new LFSR(16, 73, 32862);
  }

  public int value() {
     return (L[0].fill[0] ^ L[1].fill[0] ^ L[2].fill[0] ^ L[3].fill[0]);
  }

  public void next() {
    int scramble = L[0].ones;
    scramble = scramble * 13 + L[1].ones;
    scramble = scramble * 13 + L[2].ones;
    scramble = scramble * 13 + L[3].ones;

    L[(scramble + 1) % L.length].step();

    if (scramble % 2 == 0) {
      L[(scramble * scramble) % L.length].step();
    }
  }

  // LFSR maintains number of ones in the current fill
  private class LFSR {
    int[] fill;
    List<Integer> tap;
    int ones = 0;
    // d [degree]
    // f [initial fill]
    // t [tap]
    public LFSR(int d, int f, int t) {
    	fill = new int[d];
    	tap = new ArrayList<Integer>();
      for (int i = fill.length - 1; i >= 0; i--) {
      	fill[i] = (f & 1);
        if (fill[i] == 1) { ones++; }
        if ((t & 1) != 0) { tap.add(i); }
      	f = (f >>> 1);
        t = (t >>> 1);
      }
    }

    public void step() {
    	ones -= fill[0];

    	// compute feed
      int sum = 0;
      for (Integer i : tap) {
        sum = (sum + fill[i]) % 2;
      }

      // shift
      for (int i = 1; i < fill.length; i++) {
      	fill[i-1] = fill[i];
      }

      fill[fill.length - 1] = sum;
      ones += sum;
    }
  }

  public static void main(String[] args) {
    try
    {
      PrintWriter out = new PrintWriter("./prng.txt");
      PRNG g = new PRNG();
      for (int i = 0; i < 15000000; i++) {
        out.print(String.valueOf(g.value()));
        g.next();
      }
      out.close();
    } catch (Exception e) {}
  }
}