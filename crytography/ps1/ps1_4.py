import math;

def withOrder(n, m):
  rt = []
  for i in range(m):
    cur = 1
    for j in range(n - 1):
      cur = (cur * i) % m
      if cur == 1:
        cur = 0
        break

    if (cur * i) % m == 1:
      rt.append(i)
  return rt

def phi(n): 
      
    # Initialize result as n 
    result = n;  
  
    # Consider all prime factors 
    # of n and subtract their 
    # multiples from result 
    p = 2;  
    while(p * p <= n): 
          
        # Check if p is a  
        # prime factor. 
        if (n % p == 0):  
              
            # If yes, then  
            # update n and result 
            while (n % p == 0): 
                n = int(n / p); 
            result -= int(result / p); 
        p += 1; 
  
    # If n has a prime factor 
    # greater than sqrt(n) 
    # (There can be at-most  
    # one such prime factor) 
    if (n > 1): 
        result -= int(result / n); 
    return result; 
def isprime(n):
    '''
    check if integer n is a prime, return True or False
    '''
    # 2 is the only even prime
    if n == 2:
        return True
    # integers less than 2 and even numbers other than 2 are not prime
    elif n < 2 or not n & 1:
        return False
    # loop looks at odd numbers 3, 5, 7, ... to sqrt(n)
    for i in range(3, int(n**0.5)+1, 2):
        if n % i == 0:
            return False
    return True

def main():
  rt = []
  for i in range(1, 40943):
    z = i
    if (25 * z) % phi(z) == 0:
      m = [0,0,0]
      while(z % 2 == 0):
        z /= 2
        m[0] += 1

      while(z % 3 == 0):
        z /= 3
        m[1] += 1
      
      while(z % 11 == 0):
        z /= 11
        m[2] += 1
      rt.append(m)
      if z != 1 or (m[0] == 0 and (m[1] != 0 or m[2] != 0)) or (m[1] != 0 and m[2] != 0): 
       print('%d broken' % i)
      else:
        print(i)
  for r in rt:
    print(r)

if __name__== "__main__":
  main()