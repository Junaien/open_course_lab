/*
 * CS:APP Data Lab
 *
 * <Please put your name and userid here>
 *
 * bits.c - Source file with your solutions to the Lab.
 *          This is the file you will hand in to your instructor.
 *
 * WARNING: Do not include the <stdio.h> header; it confuses the dlc
 * compiler. You can still use printf for debugging without including
 * <stdio.h>, although you might get a compiler warning. In general,
 * it's not good practice to ignore compiler warnings, but in this
 * case it's OK.
 */

#if 0
/*
 * Instructions to Students:
 *
 * STEP 1: Read the following instructions carefully.
 */

You will provide your solution to the Data Lab by
editing the collection of functions in this source file.

INTEGER CODING RULES:

  Replace the "return" statement in each function with one
  or more lines of C code that implements the function. Your code
  must conform to the following style:

  int Funct(arg1, arg2, ...) {
      /* brief description of how your implementation works */
      int var1 = Expr1;
      ...
      int varM = ExprM;

      varJ = ExprJ;
      ...
      varN = ExprN;
      return ExprR;
  }

  Each "Expr" is an expression using ONLY the following:
  1. Integer constants 0 through 255 (0xFF), inclusive. You are
      not allowed to use big constants such as 0xffffffff.
  2. Function arguments and local variables (no global variables).
  3. Unary integer operations ! ~
  4. Binary integer operations & ^ | + << >>

  Some of the problems restrict the set of allowed operators even further.
  Each "Expr" may consist of multiple operators. You are not restricted to
  one operator per line.

  You are expressly forbidden to:
  1. Use any control constructs such as if, do, while, for, switch, etc.
  2. Define or use any macros.
  3. Define any additional functions in this file.
  4. Call any functions.
  5. Use any other operations, such as &&, ||, -, or ?:
  6. Use any form of casting.
  7. Use any data type other than int.  This implies that you
     cannot use arrays, structs, or unions.


  You may assume that your machine:
  1. Uses 2s complement, 32-bit representations of integers.
  2. Performs right shifts arithmetically.
  3. Has unpredictable behavior when shifting if the shift amount
     is less than 0 or greater than 31.


EXAMPLES OF ACCEPTABLE CODING STYLE:
  /*
   * pow2plus1 - returns 2^x + 1, where 0 <= x <= 31
   */
  int pow2plus1(int x) {
     /* exploit ability of shifts to compute powers of 2 */
     return (1 << x) + 1;
  }

  /*
   * pow2plus4 - returns 2^x + 4, where 0 <= x <= 31
   */
  int pow2plus4(int x) {
     /* exploit ability of shifts to compute powers of 2 */
     int result = (1 << x);
     result += 4;
     return result;
  }

FLOATING POINT CODING RULES

For the problems that require you to implement floating-point operations,
the coding rules are less strict.  You are allowed to use looping and
conditional control.  You are allowed to use both ints and unsigneds.
You can use arbitrary integer and unsigned constants. You can use any arithmetic,
logical, or comparison operations on int or unsigned data.

You are expressly forbidden to:
  1. Define or use any macros.
  2. Define any additional functions in this file.
  3. Call any functions.
  4. Use any form of casting.
  5. Use any data type other than int or unsigned.  This means that you
     cannot use arrays, structs, or unions.
  6. Use any floating point data types, operations, or constants.


NOTES:
  1. Use the dlc (data lab checker) compiler (described in the handout) to
     check the legality of your solutions.
  2. Each function has a maximum number of operations (integer, logical,
     or comparison) that you are allowed to use for your implementation
     of the function.  The max operator count is checked by dlc.
     Note that assignment ('=') is not counted; you may use as many of
     these as you want without penalty.
  3. Use the btest test harness to check your functions for correctness.
  4. Use the BDD checker to formally verify your functions
  5. The maximum number of ops for each function is given in the
     header comment for each function. If there are any inconsistencies
     between the maximum ops in the writeup and in this file, consider
     this file the authoritative source.

/*
 * STEP 2: Modify the following functions according the coding rules.
 *
 *   IMPORTANT. TO AVOID GRADING SURPRISES:
 *   1. Use the dlc compiler to check that your solutions conform
 *      to the coding rules.
 *   2. Use the BDD checker to formally verify that your solutions produce
 *      the correct answers.
 */


#endif
//1
/*
 * bitXor - x^y using only ~ and &
 *   Example: bitXor(4, 5) = 1
 *   Legal ops: ~ &
 *   Max ops: 14
 *   Rating: 1
 */
int bitXor(int x, int y) {
  // bit Xor(x,y)         = (~x & y) | (x & ^y)
  //apply demorgan's law  = ~[~(~x & y) & ~(x & ^y)]
  int x0y1 = (~x & y);
  int x1y0 = (x & ~y);

  return ~((~x0y1) & (~x1y0));
}
/*
 * tmin - return minimum two's complement integer
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 4
 *   Rating: 1
 */
int tmin(void) {
  //first shift
  return 1 << 31;

}
//2
/*
 * isTmax - returns 1 if x is the maximum, two's complement number,
 *     and 0 otherwise
 *   Legal ops: ! ~ & ^ | +
 *   Max ops: 10
 *   Rating: 1
 */
int isTmax(int x) {
  //x ^ 01111111 == 00000000 iff x == 01111111,
  //so we can identify if x is Tmax by this fact
  int negativeTmax = x + 2;
  int isNegativeOne = !(~x);
  return !isNegativeOne & !(negativeTmax + x);
}
/*
 * allOddBits - return 1 if all odd-numbered bits in word set to 1
 *   where bits are numbered from 0 (least significant) to 31 (most significant)
 *   Examples allOddBits(0xFFFFFFFD) = 0, allOddBits(0xAAAAAAAA) = 1
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 12
 *   Rating: 2
 */
int allOddBits(int x) {
  //let xc = 01010101
  //(xc | x) == 11111111 iff x has all ones in odd position
  int xc = 5;
  xc = (xc << 4)  | xc;
  xc = (xc << 8)  | xc;
  xc = (xc << 16) | xc;
  return !(~(xc | x));
}
/*
 * negate - return -x
 *   Example: negate(1) = -1.
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 5
 *   Rating: 2
 */
int negate(int x) {
  return ~x + 1;
}
//3
/*
 * isAsciiDigit - return 1 if 0x30 <= x <= 0x39 (ASCII codes for characters '0' to '9')
 *   Example: isAsciiDigit(0x35) = 1.
 *            isAsciiDigit(0x3a) = 0.
 *            isAsciiDigit(0x05) = 0.
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 15
 *   Rating: 3
 */
int isAsciiDigit(int x) {
  //x is in range iff  x  == 00110ppp (call this a match)
                  //or x  == 0011100p (call this b match)

  //we can identify whether x belongs to 00110ppp or 0011100p
  int a_mask = 6;
  int a_match = !((x >> 3) ^ a_mask);
  int b_mask = 28;
  int b_match = !((x >> 1) ^ b_mask);
  return a_match | b_match;
}
/*
 * conditional - same as x ? y : z
 *   Example: conditional(2,4,5) = 4
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 16
 *   Rating: 3
 */
int conditional(int x, int y, int z) {
  //if x is true, mask = ff, else mask = 00000000
  int mask = ((~x + 1) | x)  >> 31;
  return (y & mask) | (z & (~mask));
}
/*
 * isLessOrEqual - if x <= y  then return 1, else return 0
 *   Example: isLessOrEqual(4,5) = 1.
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 24
 *   Rating: 3
 */
int isLessOrEqual(int x, int y) {
  //x <= y iff  (first_bit(x) == 1 && first_bit(y) == 0) ||
  //           !(first_bit(x) == 0 && first_bit(y) == 1) && x - y <= 0)
  int leBySign = (x >> 31) & (~(y >> 31));
  int notGTBySign = (x >> 31) | (~(y >> 31));
  int leNotBySign = !((y + (~x + 1)) >> 31);
  return (1 & leBySign) |( notGTBySign & leNotBySign);
}
//4
/*
 * logicalNeg - implement the ! operator, using all of
 *              the legal operators except !
 *   Examples: logicalNeg(3) = 0, logicalNeg(0) = 1
 *   Legal ops: ~ & ^ | + << >>
 *   Max ops: 12
 *   Rating: 4
 */
int logicalNeg(int x) {
  // ((~x + 1) | x)  >> 31, makes  0 -> 0, non-zero -> fffff...f
  int mask = ((~x + 1) | x)  >> 31;
  return mask + 1;
}
/* howManyBits - return the minimum number of bits required to represent x in
 *             two's complement
 *  Examples: howManyBits(12) = 5
 *            howManyBits(298) = 10
 *            howManyBits(-5) = 4
 *            howManyBits(0)  = 1
 *            howManyBits(-1) = 1
 *            howManyBits(0x80000000) = 32
 *  Legal ops: ! ~ & ^ | + << >>
 *  Max ops: 90
 *  Rating: 4
 */
int howManyBits(int x) {
  // divide and conquer
  // initialization: k = 32
  //                 ret = 0
  // step 1:
  //    only consider [0, k - 1] bits
  //    if first k/2 + 1 bits are equal
  //        no-op
  //    else
  //        ret += k / 2
  //        replace [0,k/2-1] bits with [k/2, k - 1] bits
  //    endif
  //    k /= 2;
  //    if(k > 0)go back to step 1
  int ret = 0;
  int allF = (~1 + 1);
  //shift the bits to the right to check equality of the first k / 2 + 1 bits
  int firstHalf = x >> 15;
  int notEquality  = (!(firstHalf ^ 0) | !(firstHalf ^ allF)) ^ 1;
  int mask = ~notEquality + 1;
  ret += (mask & 16);
  x = x >> (mask & 16);
  //-----
  firstHalf = x >> 7;
  notEquality  = (!(firstHalf ^ 0) | !(firstHalf ^ allF)) ^ 1;
  mask = ~notEquality + 1;
  ret += (mask & 8);
  x = x >> (mask & 8);
  //-----
  firstHalf = x >> 3;
  notEquality  = (!(firstHalf ^ 0) | !(firstHalf ^ allF)) ^ 1;
  mask = ~notEquality + 1;
  ret += (mask & 4);
  x = x >> (mask & 4);
  //-----
  firstHalf = x >> 1;
  notEquality  = (!(firstHalf ^ 0) | !(firstHalf ^ allF)) ^ 1;
  mask = ~notEquality + 1;
  ret += (mask & 2);
  x = x >> (mask & 2);
  //-----
  firstHalf = x;
  notEquality  = (!(firstHalf ^ 0) | !(firstHalf ^ allF)) ^ 1;
  mask = ~notEquality + 1;
  ret += (mask & 1);
  return ret + 1;
}
//float
/*
 * floatScale2 - Return bit-level equivalent of expression 2*f for
 *   floating point argument f.
 *   Both the argument and result are passed as unsigned int's, but
 *   they are to be interpreted as the bit-level representation of
 *   single-precision floating point values.
 *   When argument is NaN, return argument
 *   Legal ops: Any integer/unsigned operations incl. ||, &&. also if, while
 *   Max ops: 30
 *   Rating: 4
 */
unsigned floatScale2(unsigned uf) {
  //if exp == 11111111 return uf
  //if exp == 00000000 && first_bit(mantissa) is 1,shift mantissa to the left by 1, increment esp by 1
  //if exp == 00000000 && first bit of mantissa is not 1, shift mantissa to the left by 1
  //other situation just increment esp by one, no shift

  int exp  = (uf >> 23) & 255;
  int sign = (uf & (1 << 31));
  int mantissa = uf;
  int firstBitOfMantissa = (mantissa >> 22) & 1;

  if(exp == 255)return uf;
  if(exp == 0){mantissa = mantissa << 1;}
  if(firstBitOfMantissa || exp != 0){exp += 1;}
  mantissa = mantissa & (~(~0 << 23));
  return sign | (exp << 23) | mantissa;
}
/*
 * floatFloat2Int - Return bit-level equivalent of expression (int) f
 *   for floating point argument f.
 *   Argument is passed as unsigned int, but
 *   it is to be interpreted as the bit-level representation of a
 *   single-precision floating point value.
 *   Anything out of range (including NaN and infinity) should return
 *   0x80000000u.
 *   Legal ops: Any integer/unsigned operations incl. ||, &&. also if, while
 *   Max ops: 30
 *   Rating: 4
 */
int floatFloat2Int(unsigned uf) {
  //if exp < 2 ** 7 - 1, return 0
  //if exp >= 2 ** 7 - 1, convert to number and check

  int E    = ((uf >> 23) & 255) - 127;
  int mantissa = ~((-1) << 23) & uf;
  int sign = uf >> 31;

  if(E < 0)return 0;
  if((sign == 0 && E > 30) ||
     (sign && E > 31) ||
     (sign && E == 31 && mantissa != 0))return 1 << 31;

  mantissa = (1 << 23) | mantissa;
  if(E <= 23)mantissa = mantissa >> (23 - E);
  else mantissa =  mantissa << (E - 23);

  if(sign)mantissa = ~mantissa + 1;
  return mantissa;
}
/*
 * floatPower2 - Return bit-level equivalent of the expression 2.0^x
 *   (2.0 raised to the power x) for any 32-bit integer x.
 *
 *   The unsigned value that is returned should have the identical bit
 *   representation as the single-precision floating-point number 2.0^x.
 *   If the result is too small to be represented as a denorm, return
 *   0. If too large, return +INF.
 *
 *   Legal ops: Any integer/unsigned operations incl. ||, &&. Also if, while
 *   Max ops: 30
 *   Rating: 4
 */
unsigned floatPower2(int x) {
    //minimum representable float is: 2 ^ -149
    //maximum representable float is: 2 ^ 127
    //denorm range [2 ^ -149, 2 ^ -127]
    //norma  range [2 ^ -126, 2 ^ 127]
    if(x < -149)return 0;
    if(x >  127)return 255 << 23;
    if(x <= -127)return 1 << (x + 149);
    else return (x + 127) << 23;

}
