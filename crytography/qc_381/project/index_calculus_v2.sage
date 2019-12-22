from sage.misc.prandom import randrange
import time
def gcd(a, b):
    while a % b != 0:
        a, b = b, a % b
    return b

# pollard_rho with computational limits
def pollard_rho(n, limits):
  i = 1
  x = randrange(0, n - 1)
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
    if not miller_rabin_test(randrange(2, n - 2),r, s, n):
      return False
  return True

# precondition: n is smooth
# postcondition: relation set
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

  if is_smooth_t(n, factor_base):
    return [1]

  n = p - n
  relation = [0] * len(factor_base)
  relation[0] = 1
  if is_smooth_t(n, factor_base):
    return [1]

  return []

def is_smooth_t(n, factor_base):

  for i in range(1, len(factor_base)):
    if n == 1: return True

    while n % factor_base[i] == 0:
      n = n // factor_base[i]

  return False
# this function test B_smooth number, adopt early abort
def is_smooth(n, B_mult, factor_base):
  B = factor_base[-1]
  g = gcd(B_mult, n)
  while g != 1 and n > B:
    n = n // g
    g = gcd(B_mult, n)

  if n <= B:
    return True

  limits = int(B ** 0.5) // 50
  f = pollard_rho(n, limits)

  while f != -1 and n > B:
    if f > B and miller_rabin(f,3):
      return False
    n = n // f
    f = pollard_rho(n, limits)

  return n <= B

# solve g^x = h(mod p)
def index_calculus(g, h, p, smoothness, slack):

  start = time.time()
  # step 0: find out all primes that are smaller than
  B_mult = 1
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

  for i in range(1, min(3000, smoothness)):
    B_mult = B_mult * factor_base[i]
  print 'step 0: ', time.time() - start, ' s'

  a = 1.0 / math.log(factor_base[-1], p)
  print "smooth density ", dickman_rho(a)

  start = time.time()
  # step 1: find system relations for g^k
  relations = []
  trials = 0
  # loop until have enough system relations
  while len(relations) < (slack + smoothness):
    k = randrange(1, p)
    trials = trials + 1

    # initialize potential relation
    g_k = power(g, k, p)
    t = time.time()
    relation = factor_base_hit(g_k, factor_base, p, B_mult)
    print "is_smooth: ", time.time() - t

    if relation:
      relation.append(k)
      relations.append(relation)
#       print(len(relations))
#       print(1.0 * len(relations) / trials)
  print "step 1: ", time.time() - start, " s"

def main():
#     index_calculus(2, 3124124, 1323314315162339, 10000, 0) # 50 bits - 16d
#     index_calculus(5, 213213432, 1683035312517090047, 10000, 0) # 60 bits - 20d
#       index_calculus(5, 21321, 1789553520200033022047, 2000, 0) # 70 bits - 22d
#     index_calculus(13, 23123124312,1747657214360998464084959, 2000, 0) # 80 bits
  index_calculus(2, 213, 2023364620433439350571413294927, 10000, 0) # 100 bits
#     index_calculus(2, 241234, 2404937687692766174564178497714944859, 10000, 0) # 120 bits
#     index_calculus(2,23112, 2352135217867182205962592313493773501999, 10000, 0) # 130 bits
#     index_calculus(3, 2123124, 61845915503831114091865164962647232917206327870669899, 100000, 0) # 150 bits

if __name__ == "__main__":
  main()