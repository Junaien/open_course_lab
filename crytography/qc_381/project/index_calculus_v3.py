import random
import time
import math
from decimal import *
def gcd(a, b):
    while a % b != 0:
        a, b = b, a % b
    return b

# pollard_rho with computational limits
def pollard_rho(n, limits):
  i = 1
  x = random.randint(0, n - 1)
  y = x
  k = 2

  while True:
    i = i + 1
    x = (x * x - 1) % n
    d = gcd(y - x, n)

    # limiting computational steps
    if limits < 0:
        return -1
    else:
        limits = limits - 1

    if d != 1 and d != n:
      return d

    if i == k:
      y = x
      k = 2*k

# this function test B_smooth number, adopt early abort
def is_smooth(n, B_mult, factor_base):
  B = factor_base[-1]
  limits = int(factor_base[-1] ** 0.5)
  count = 0
  g = gcd(B_mult, n)
  while g != 1 and count < 10 and n > B:
    n = n // g
    g = gcd(B_mult, n)
    count = count + 1

  if n <= B: return True
  if miller_rabin(n, 4): return False

  f = pollard_rho(n, limits)
  while count < 15 and f != -1 and n > B:
    n = n // f
    f = pollard_rho(n, limits)
    count = count + 1

  return n <= B
  
# fast exponentiation
def power(a, x, n):
  ret = 1
  if x == 0:
    return ret % n
  if (x % 2 == 1):
    ret = a
  return (ret * (power(a, x // 2, n)**2)) % n

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
  # easy case
  if n == 2 or n == 3 or n == 5:
    return True
  if n == 4:
    return False

  # step 1: find r,s where s * 2^r = n - 1
  r = 0
  s = n - 1;
  while s > 0 and (s % 2 == 0):
    r = r + 1
    s = s // 2

  # step 2: randomly chose base b, do k times miller_rabin primality test
  for i in range(k):
    if not miller_rabin_test(random.randint(2, n - 2),r, s, n):
      return False
  return True

def base_hit(n, factor_base, relation):
  relation = [0] * len(factor_base)

  # factor n to our factor base
  for i in range(1, len(factor_base)):
    if n == 1: return relation

    while n % factor_base[i] == 0:
      relation[i] = relation[i] + 1
      n = n // factor_base[i]

  return []

# with -1 base, and smooth test
# return: [] if n is probably not B-smooth,
#         relation if n is B-smooth
def factor_base_hit(n, factor_base, p, B_mult):
  relation = [0] * len(factor_base)

  if is_smooth(n, B_mult, factor_base):
    return [1]

  n = p - n
  relation = [0] * len(factor_base)
  relation[0] = 1
  if is_smooth(n, B_mult, factor_base):
    return [1]

  return []

# solve g^x = h(mod p)
def index_calculus(g, h, p, smoothness, slack):
  # step 0: find out all primes that are smaller than
  B_mult = 2
  factor_base = [-1, 2, 3]
  i = 5
  while True:
    if len(factor_base) >= smoothness:
      break
    if miller_rabin(i, 10):
      factor_base.append(i)
    if miller_rabin(i + 2, 10):
      factor_base.append(i + 2)
    i = i + 6
  # print(f'successfully initialize {smoothness} factor bases')
  print(factor_base)
  start = time.time()
  # step 1: find system relations for g^k
  relations = []
  trials = 0
  while True:
    k = random.randint(1, p)
    trials = trials + 1
    # test if we already have enough system relations
    if len(relations) >= (slack + smoothness):
      break

    # initialize potential relation
    g_k = power(g, k, p)
    relation = factor_base_hit(g_k, factor_base, p, B_mult)

    # if g_k completely factor to our base primes record the relation
    if relation:
      relation.append(k)
      relations.append(relation)
      print(len(relations) / trials)
  end = time.time()
  print(f'successfully get {len(relations)} relations in {end - start} seconds!')
def main():
    # g = 2
    # p = 61845915503831114091865164962647232917206327870669899

    # pair1 = Decimal(p).log10() / Decimal(g).log10()

    # for k in range(10000, 10001):
    #   pair2 = Decimal(k).log10() / Decimal(g).log10()
    #   print(f"{pair1 + pair2} {k}")
    # print(power(g, 13124213412, p))
    # print(p)

    # index_calculus(2, 3124124, 1323314315162339, 1500, 0) # 50 bits
    # index_calculus(5, 213213432, 1303302925709858303, 5000, 0) # 55 bits
    # index_calculus(2, 23123124312,1747657214360998464084959, 500000, 0) # 80 bits
    # index_calculus(2, 213, 2023364620433439350571413294927, 500000, 0) # 100 bits
    # index_calculus(2, 241234, 2404937687692766174564178497714944859, 500000, 0) # 120 bits
    # index_calculus(7,23112, 2352135217867182205962592313493773501999, 500000, 0) # 130 bits
    # index_calculus(3, 2123124, 61845915503831114091865164962647232917206327870669899, 500000, 0) # 150 bits
    generate_factor_base(100000)

def generate_factor_base(B):
  factor_base = [2, 3]
  i = 5
  while True:
    if len(factor_base) >= B:
      break
    if miller_rabin(i, 20):
      factor_base.append(i)
    if miller_rabin(i + 2, 20):
      factor_base.append(i + 2)
    i = i + 6
  
  file = open("primes.txt","w") 
  for i in range(0, len(factor_base)):
    file.write(str(factor_base[i]))
    file.write(' ')

if __name__ == "__main__":
  main()