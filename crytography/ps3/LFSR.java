import java.util.Set;
import java.util.HashSet;

public class LFSR {
  int[] fill;
  int[] tap;
  int shift = 0;
  public LFSR(int d, int n, int t) {
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
  	int ones = 0;
  	int total = 0;
  	int largestK = -1;
    int count = 0;
  	for (int tap = 0; tap < (int)Math.pow(2, 12); tap++) {
  		Set<Integer> states = new HashSet<>();
  		LFSR lfsr = new LFSR(12, 1, tap);
      int fill = lfsr.fill();
      while (!states.contains(fill)) {
      	states.add(fill);
      	lfsr.next();
      	fill = lfsr.fill();
      }
      if (states.size() == ((int)Math.pow(2, 12) - 1)) {
      	count++;

      	// for question a)
      	for (int f = 1; f < (int)Math.pow(2, 12); f++) {
      		LFSR temp = new LFSR(12, f, tap);
      		String rt = "";
          while (rt.length() < 12) {
          	rt += temp.shrink_next();
          }
          if (rt.equals("000000000001") || rt.equals("000000000000")) {
          	total++;
          }
          if (rt.equals("000000000001")) {
            ones++;
          }
      	}
         
        // for question b)
      	for (int f = 1; f < (int)Math.pow(2, 12); f++) {
      		LFSR temp = new LFSR(12, f, tap);
      		String rt = "";
          while (!temp.repeated()) {
          	String add = temp.shrink_next();
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
  	System.out.println("primitive tap=" + count);

  }
}