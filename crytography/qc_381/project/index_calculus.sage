# version 1:
# vanila

from sage.misc.prandom import random.randint

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

def factor_base_hit(n, factor_base):
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

def figure_out_index(relations, factor_base, relation, offset):
  x = -offset
  A = Matrix(relations).echelon_form()
  for i in range(0, len(factor_base)):
    x = x + (A[i][-1] * relation[i])
  return x

# solve g^x = h(mod p)
def index_calculus(g, h, p, smoothness, slack):
  # step 0: find out all primes that are smaller than
  factor_base = [2]
  i = 3
  while True:
    if len(factor_base) >= smoothness:
      break

    if miller_rabin(i, 100):
      factor_base.append(i)
    i = i + 2

  print 'successfully initialize' , smoothness, 'factor bases'

  # step 1: find system relations for g^k
  relations = []
  while True:
    k = random.randint(1, p)
    # test if we already have enough system relations
    if len(relations) >= (slack + smoothness):
      break

    # initialize potential relation
    g_k = power(g, k, p)
    relation = factor_base_hit(g_k, factor_base)

    # if g_k completely factor to our base primes record the relation
    if relation:
      relation.append(k)
      relations.append(relation)

  print 'successfully get',  len(relations), ' relations'

  #step 2: try to factorize h*g^s into our factor base
  while True:
    k = random.randint(1, p)
    g_k_h = (power(g, k ,p) * h) % p
    factor_base_hits = factor_base_hit(g_k_h, factor_base)
    if factor_base_hits:
      x = figure_out_index(relations, factor_base, factor_base_hits, k)
      print 'fuck I solve it -> ', x
      break;

def main():
#   index_calculus(5, 20, 503, 10, 20)
  index_calculus(2, 92327518017225, 247457076132467, 50, 50)
#   index_calculus(2, 31, 83, 10, 15)

if __name__ == "__main__":
  main()