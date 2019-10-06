import math
import random

  
# Utility function to do 
# modular exponentiation. 
# It returns (x^y) % p 
def power(x, y, p): 
      
    # Initialize result 
    res = 1;  
      
    # Update x if it is more than or 
    # equal to p 
    x = x % p;  
    while (y > 0): 
          
        # If y is odd, multiply 
        # x with result 
        if (y & 1): 
            res = (res * x) % p; 
  
        # y must be even now 
        y = y>>1; # y = y/2 
        x = (x * x) % p; 
      
    return res; 
  
# This function is called 
# for all k trials. It returns 
# false if n is composite and  
# returns false if n is 
# probably prime. d is an odd  
# number such that d*2<sup>r</sup> = n-1 
# for some r >= 1 
def miillerTest(d, n): 
      
    # Pick a random number in [2..n-2] 
    # Corner cases make sure that n > 4 
    a = 2 + random.randint(1, n - 4); 
  
    # Compute a^d % n 
    x = power(a, d, n); 
  
    if (x == 1 or x == n - 1): 
        return True; 
  
    # Keep squaring x while one  
    # of the following doesn't  
    # happen 
    # (i) d does not reach n-1 
    # (ii) (x^2) % n is not 1 
    # (iii) (x^2) % n is not n-1 
    while (d != n - 1): 
        x = (x * x) % n; 
        d *= 2; 
  
        if (x == 1): 
            return False; 
        if (x == n - 1): 
            return True; 
  
    # Return composite 
    return False; 
  
# 7381201715189845971443097643817907669946316416088031816317787886620659733216061202372597242341171415964945886666140008607916774848396568533790632829831767
# It returns false if n is  
# composite and returns true if n 
# is probably prime. k is an  
# input parameter that determines 
# accuracy level. Higher value of  
# k indicates more accuracy. 
def isPrime( n, k): 
      
    # Corner cases 
    if (n <= 1 or n == 4): 
        return False; 
    if (n <= 3): 
        return True; 
  
    # Find r such that n =  
    # 2^d * r + 1 for some r >= 1 
    d = n - 1; 
    while (d % 2 == 0): 
        d //= 2; 
  
    # Iterate given nber of 'k' times 
    for i in range(k): 
        if (miillerTest(d, n) == False): 
            return False; 
  
    return True; 
    
# This code is contributed by mits 
def miller_rabin(n):
  # step 1: find r,s where s * 2^r = n - 1
  r = 0; 
  s = n - 1;
  while s > 0 and (s % 2 == 0):
    r = r + 1
    s = s // 2

  # step 2: randomly chose base b, do i times primality test
  for i in range(20):
    if not miller_rabin_test(random.randint(2, n - 2),r, s, n):
      return False
  return True

def exp_mod(a, x, n):
  ret = 1
  if x == 0:
    return ret
  if (x % 2 == 1):
    ret = a
  return (ret * (exp_mod(a, x // 2, n)**2)) % n

# since b ^ (n - 1) = 1(mod n)
# 1: b ^ s = 1(mod n)
# 2: b ^ s*(2^ri) = -1(mod n) for some ri in [0, r)
# [ 1 or 2 must be true ]
def miller_rabin_test(b, r, s, n):
  # 1: test if b ^ s = 1(mod n) or -1(mod n)
  b_s = exp_mod(b, s, n)
  if b_s == 1 or b_s == n-1:
    return True

  # 2: test if b ^ s*(2^ri) = -1(mod n) for some ri in [0, r)
  for i in range(r - 1):
    b_s = (b_s * b_s) % n
    if b_s == (n - 1):
      return True

  return False

def test():
  assert exp_mod(11,13,19) == 11, exp_mod(11,13,19)
  assert exp_mod(11321, 134321, 19112) == 13449, exp_mod(11,13,19)
  assert exp_mod(4324444233123, 134321321, 31219112) == 11676731, exp_mod(4324444233123, 134321321, 31219112)
  assert miller_rabin(9018083461) == True
  print("all test successfully passed")

def ps2_3b():
  for i in range(45300, 55300):
    for j in range(45300, 55300):
      if i % 2 == 0 or j % 2 == 0:
        continue
      if isPrime(i, 4) and isPrime(j, 4) and (i * j - 1) % ((j - 1) * (i - 1)) == 0:
        print(f"{i} x {j}")

if __name__ == "__main__":
  test()
  trials = 1000
  for i in range(trials):
    q = 4
    while not isPrime(q,4):
      q = random.randint(round(2**511 / 134217728),round(2**512 / 2))

    for a in range(round(2**511 / q),round(2**512 / q)):
      if isPrime(a * q + 1, 4):
        print(f"q is {q}, a is {a}")
        
   
