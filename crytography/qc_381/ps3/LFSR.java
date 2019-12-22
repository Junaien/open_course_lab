import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

// inplemention of LFSR based on question 1
public class LFSR {
  int[] fill;
  List<Integer> tap;
  int shifted = 0;

  public LFSR(int d, int f, int t) {
    fill = new int[d];
    tap = new ArrayList<Integer>();
    for (int i = fill.length - 1; i >= 0; i--) {
      fill[i] = (f & 1);
      if ((t & 1) != 0) { tap.add(i); }
      f = (f >>> 1);
      t = (t >>> 1);
    }
  }

  public void step() {
  	shifted++;

  	// compute feed
    int sum = 0;
    for (Integer i : tap) {
      sum ^= fill[i];
    }

    // shift
    for (int i = 1; i < fill.length; i++) {
    	fill[i-1] = fill[i];
    }

    fill[fill.length - 1] = sum;
  }

  public String shrink() {
  	String rt = "";
    if (fill[0] == 0) {rt = "";}
    else {rt = String.valueOf(fill[1]);}

    step();
    step();
    return rt;
  }

  public int fill() {
  	int sum = 0;
  	for (int i : fill) { sum = sum * 2 + i; }
  	return sum;
  }
  
  // test if the LFSR has started repeating
  public boolean repeated() {
  	return shifted >= (int)Math.pow(2, fill.length) - 2;
  }

  public static void main(String[] args) {
    int size = 12;
    int tapStart = (int)Math.pow(2, size - 1);
    int tapEnd = (int)Math.pow(2, size) - 1;

  	int ones = 0, total = 0; // for computing question 1 a)
  	int largestK = -1;  // for computing question 1 b)

    int count = 0; // count how many primitive poly tap
  	for (int tap = tapStart; tap <= tapEnd; tap++) {
  		Set<Integer> states = new HashSet<>(); // test lfsr cyclying
  		LFSR lfsr = new LFSR(size, 1, tap);

      int fill = lfsr.fill();
      while (!states.contains(fill)) {
      	states.add(fill);
      	lfsr.step();
      	fill = lfsr.fill();
      }

      if (states.size() == tapEnd) {
      	count++;

      	// question a)
      	for (int f = 1; f < (int)Math.pow(2, size); f++) {
      		LFSR temp = new LFSR(size, f, tap);
      		String rt = "";
          while (rt.length() < 12) {
          	rt += temp.shrink();
          }
          if (rt.equals("000000000001") || rt.equals("000000000000")) {
          	total++;
            if (rt.equals("000000000001")) { ones++; }
          }
      	}

        // question b)
      	for (int f = 1; f < (int)Math.pow(2, size); f++) {
      		LFSR temp = new LFSR(size, f, tap);
      		String rt = "";
          while (!temp.repeated()) {
          	String add = temp.shrink();
          	if (add.equals("1")) {
              largestK = Math.max(largestK, rt.length());
              break;
          	} 
          	rt += add;
          }
      	}

      }
  	}
  	System.out.println("Total=" + total + ", Ones=" + ones);
  	System.out.println("largestK=" + largestK);
  	System.out.println("#primitive tap=" + count);

  }
}