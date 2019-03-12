import java.math.BigInteger;
public class KarasubaM{
    public static BigInteger multiply(BigInteger x, BigInteger y) {
      String xs = x.toString();
      String ys = y.toString();
      int n = xs.length() < ys.length()? xs.length():ys.length();
      if(n <= 1) return (x.multiply(y));

      BigInteger a =  new BigInteger(xs.substring(0,xs.length()- (n/2)));
      BigInteger c = new BigInteger(ys.substring(0, ys.length()- (n/2)));

      BigInteger b = new BigInteger(xs.substring(xs.length()- (n/2)));
      BigInteger d = new BigInteger(ys.substring(ys.length()- (n/2)));
      System.out.println(a + "-" + b);
      System.out.println(c + "-" + d);


      BigInteger ac = multiply(a, c);
      BigInteger bd = multiply(b, d);

      BigInteger firstPart = (new BigInteger("10")).pow((n/2)*2);
      firstPart = firstPart.multiply(ac);
      BigInteger abxcd = multiply(a.add(b),c.add(d));
      BigInteger secondPart = (new BigInteger("10")).pow(n/2);
      secondPart = secondPart.multiply((abxcd.subtract(ac)).subtract(bd));
      return (firstPart.add(secondPart)).add(bd);

    }

    public static void main( String[] args){

      BigInteger x = new BigInteger("314159265358979323846264338327950288419716939937510582097494459265464565465465465465465464564565464564564565464564564564564564564565464564");

      BigInteger y = new BigInteger("271828182845904523536028747135266249775724709369995957496696762765465464564565465464565466554654654645654645645645654645654645654645645645");
      // System.out.println("3923298721".substring(0, 5));
      // System.out.println(new BigInteger("000052"));
      long s = System.currentTimeMillis();
      System.out.println(multiply(x, y));
      s = System.currentTimeMillis() - s;
      System.out.println("I use " + s + " mili-seconds");
      s = System.currentTimeMillis();
      System.out.println(x.multiply(y));
      s = System.currentTimeMillis() - s;
      System.out.println("system uses " + s + " mili-seconds");


      // System.out.println((new BigInteger("10")).pow(5);
    }
}
