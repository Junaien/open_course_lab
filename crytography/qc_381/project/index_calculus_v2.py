import random
import time

def gcd(a, b):
    while a % b != 0:
        a, b = b, a % b
    return b

# pollard_rho with computational limits
def pollard_rho(n, limits, B):
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

    if d != 1 and d != n and d <= B:
      return d

    if i == k:
      y = x
      k = 2*k

# this function test B_smooth number
def is_smooth_gcd(n, B, B_mult):
  g = gcd(B_mult, n)
  while g != 1:
    n = n // g
    g = gcd(B_mult, n)

  return n == 1

# this function test B_smooth number
def is_smooth_rho(n, B):
  limits = int(B ** 0.5)

  f = pollard_rho(n, limits, B)
  while f != -1 and n > B:
    n = n // f
    f = pollard_rho(n, limits, B)
  
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

def factor_base_hit_v1(n, factor_base):
  relation = [0] * len(factor_base)

  # factor n to our factor base
  for i in range(0, len(factor_base)):
    if n == 1: break

    while n % factor_base[i] == 0:
      relation[i] = relation[i] + 1
      n = n // factor_base[i]

  if n == 1:
    return relation
  else:
    return []

# take -1 into consideration
def factor_base_hit(n, factor_base, relation):
  # factor n to our factor base
  for i in range(1, len(factor_base)):
    if n == 1: return relation
    while n % factor_base[i] == 0:
      relation[i] = relation[i] + 1
      n = n // factor_base[i]

# with -1
def factor_base_hit_v2(n, factor_base, p):
  relation1 = [0] * len(factor_base)
  relation2 = [0] * len(factor_base)
  relation2[0] = 1
  n1 = n
  n2 = (n - p) * (-1)

  # factor n to our factor base
  for i in range(1, len(factor_base)):
    if n1 == 1: return relation1
    if n2 == 1: return relation2

    while n1 % factor_base[i] == 0:
      relation1[i] = relation1[i] + 1
      n1 = n1 // factor_base[i]

    while n2 % factor_base[i] == 0:
      relation2[i] = relation2[i] + 1
      n2 = n2 // factor_base[i]

  return []

# with -1 and pollard rho on time limits
def factor_base_hit_v3(n, factor_base, p):
  relation1 = [0] * len(factor_base)
  relation2 = [0] * len(factor_base)
  relation2[0] = 1
  n1 = n
  n2 = (n - p) * (-1)
  limits = int(sqrt(factor_base[-1]))

  # factor n1 to our factor base
  while True:
    if n1 == 1: return relation1
    if n1 <= factor_base[-1]:
      factor_base_hit(n1, factor_base, relation1)
      return relation1

    sf = pollard_rho(n1, limits)
    # time up
    if sf == -1:
        break

    success = factor_base_hit(sf, factor_base, relation1)
    if not success: break
    else: n1 = n1 // sf

  # factor n2 to our factor base
  while True:
    if n2 == 1: return relation2
    if n2 <= factor_base[-1]:
      factor_base_hit(n2, factor_base, relation2)
      return relation2

    sf = pollard_rho(n2, limits)
    # time up
    if sf == -1:
        break

    success = factor_base_hit(sf, factor_base, relation2)
    if not success: break
    else: n2 = n2 // sf


  return []

# with -1 and gcd
def factor_base_hit_v4(n, factor_base, p, B_mult):
  relation1 = [0] * len(factor_base)
  relation2 = [0] * len(factor_base)
  relation2[0] = 1
  n1 = n
  n2 = (n - p) * (-1)
  
  if is_smooth_rho(n1, factor_base[-1]):
    return factor_base_hit(n1, factor_base, relation1)
  
  if is_smooth_rho(n2, factor_base[-1]):
    return factor_base_hit(n2, factor_base, relation2)

  return []

def factor_base_hit_v5(n, factor_base, p):
  relation1 = [0] * len(factor_base)
  if factor_abort(n, factor_base, relation1):
    return relation1

  relation2 = [0] * len(factor_base)
  relation2[0] = 1
  if factor_abort(p - n, factor_base, relation2):
    return relation2
  return []

def factor_abort(n, factor_base, relation):
  original = n
  k1 = 20
  for i in range(1, k1):
    while n % factor_base[i] == 0:
      relation[i] = relation[i] + 1
      n = n // factor_base[i]

  if n > int((original ** 0.5) / 500): return False

  k2 = 100
  for i in range(k1, k2):
    while n % factor_base[i] == 0:
      relation[i] = relation[i] + 1
      n = n // factor_base[i]

  if n > int((original ** 0.5) / 100000): return False

  for i in range(k2, len(factor_base)):
    while n % factor_base[i] == 0:
      relation[i] = relation[i] + 1
      n = n // factor_base[i]

  if n == 1: return True
  else: return False

# solve g^x = h(mod p)
def index_calculus(g, h, p, smoothness, slack):
  # step 0: find out all primes that are smaller than
  B_mult = 2
  factor_base = [-1, 2]
  i = 3
  while True:
    if len(factor_base) >= smoothness:
      break

    if miller_rabin(i, 10):
      factor_base.append(i)
      B_mult = B_mult * i
    i = i + 2
  print(f'successfully initialize {smoothness} factor bases')
  print(B_mult)
  start = time.time()
  # step 1: find system relations for g^k
  relations = []
  while True:
    k = random.randint(1, p)
    # test if we already have enough system relations
    if len(relations) >= (slack + smoothness):
      break

    # initialize potential relation
    g_k = power(g, k, p)
    relation = factor_base_hit_v4(g_k, factor_base, p, B_mult)

    # if g_k completely factor to our base primes record the relation
    if relation:
      relation.append(k)
      relations.append(relation)
      print(len(relations))
  end = time.time()
  print(f'successfully get {len(relations)} relations in {end - start} seconds!')

def main():
#   index_calculus(5, 20, 503, 10, 30)
#   index_calculus(2, 92327518017225, 247457076132467, 100, 100)
#   index_calculus(2, 31, 83, 10, 15)
    # index_calculus(2, 3124124, 1323314315162339, 10000, 0) # 50 bits
    index_calculus(5, 213213432, 1303302925709858303, 100000, 0) # 55 bits

if __name__ == "__main__":
  main()