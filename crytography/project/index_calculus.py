
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


# fast exponentiation
def power(a, x, n):
  ret = 1
  if x == 0:
    return ret % n
  if (x % 2 == 1):
    ret = a
  return (ret * (power(a, x // 2, n)**2)) % n

def index_calculus(g, h, p, smoothness, slack):
  # step 0: find out all primes that are smaller than 
  factor_base = [p-1]
  for i in range(2, p):
    if len(factor_base) >= smoothness: 
      break 

    if miller_rabin(i, 100):
      factor_base.append(i)

  print(f"successfully initialize {smoothness} factor bases")

  # step 1: find system relations for g^k
  relations = []
  relations_rhs = []
  while True:
    k = int(random.randint(1, p))
    # test if we already have enough system relations
    if len(relations) >= (slack + smoothness):
      break

    # initialize potential relation
    g_k = power(g, k, p)
    relation = [0] * len(factor_base)

    # factor g_k to our factor base
    for i in range(0, len(factor_base)):
      if g_k == 1: break

      while g_k % factor_base[i] == 0:
        relation[i] = relation[i] + 1
        g_k = g_k // factor_base[i]

    # if g_k completely factor to our base primes record the relation
    if g_k == 1:
      relations_rhs.append(k)
      relations.append(relation)

  print(f"successfully get {len(relations)} relations")
  
  # step 2 output the equation we find to a file
  
def main():
  # index_calculus(3, 5, 131, 10, 20)
  print(power(5, 251, 503))

if __name__ == "__main__":
  main()
