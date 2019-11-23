import math
import random

# assume n is prime
# since b ^ (n - 1) = 1(mod n)
# a): b ^ s = 1(mod n)
# b): b ^ s*(2^ri) = -1(mod n) for some ri in [0, r)
# if b) is not true, then b ^ s*(2^ri) = - 1(mod n) 
# we can keep take square root of b ^ s*(2^ri) until ri = 0,
# at that time we have b ^ s = 1(mod n)

def miller_rabin_test(b, r, s, n):
  # 1: test if b ^ s = 1(mod n) or -1(mod n)
  b_s = power(b, s, n)
  if b_s == 1 or b_s == n-1:
    return True

  # 2: test if b ^ s*(2^ri) = -1(mod n) for some ri in [0, r)
  for i in range(r - 1):
    b_s = (b_s * b_s) % n
    if b_s == (n - 1):
      return True

  return False

def miller_rabin(n, k):
  # step 1: find r,s where s * 2^r = n - 1
  r = 0; 
  s = n - 1;
  while s > 0 and (s % 2 == 0):
    r = r + 1
    s = s // 2

  # step 2: randomly chose base b, do k times miller_rabin primality test
  for i in range(k):
    if not miller_rabin_test(random.randint(2, n - 2),r, s, n):
      return False
  return True

def power(a, x, n):
  ret = 1
  if x == 0:
    return ret
  if (x % 2 == 1):
    ret = a
  return (ret * (power(a, x // 2, n)**2)) % n

def test():
  assert power(11,13,19) == 11, power(11,13,19)
  assert power(11321, 134321, 19112) == 13449, power(11,13,19)
  assert power(4324444233123, 134321321, 31219112) == 11676731, power(4324444233123, 134321321, 31219112)
  assert miller_rabin(9018083461, 4) == True
  print("all test successfully passed")

def check_small_factor(bound, n):
  factor_accumulator = 1
  for i in range(2, bound):
    while n % i == 0:
      n = n // i
      factor_accumulator = factor_accumulator * i

    if factor_accumulator > bound:
      return False

    if miller_rabin(n, 4):
      print(f"small factor for p + 1 is: {factor_accumulator}")
      return True

  return False
if __name__ == "__main__":
  test()
  bits = 20
  trials = 10000
  for i in range(trials):
    q = 4
    while not miller_rabin(q, 100):
      q = random.randint(2**(bits - 1), 2 ** bits)
    if miller_rabin(2 * q + 1, 100):
      print(f"q = {q}")
      exit(0)
        