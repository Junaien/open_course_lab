import math
import random
from timeit import default_timer as timer
# g^x = h (mod m)
# r is range we want to search
# offset is starting point we want to search on
def bsgs(g, h, m, r, offset = 0):
  n = int(math.sqrt(r) + 1);  
  g_i_n_table = {}

  # step0: calculate g ^ offset
  g_offset = power(g, offset, m)

  # step1: calculate g ^ n
  g_n = 1
  for i in range(n):
    g_n = (g_n * g) % m

  # step2: for all i in [1, n], store g ^ (i * n) + offset
  accumulateA = 1
  for i in range(1, n + 1):
    accumulateA = (accumulateA * g_n) % m
    g_i_n_table[(accumulateA * g_offset) % m] = i

  # step3: loop through all j in [1, n] to find match in g_i_n_table
  accumulateB = 1
  for j in range(1, n + 1):
    accumulateB = (accumulateB * g) % m
    key = ((accumulateB * h) % m)
    if key in g_i_n_table:
      return (g_i_n_table[key] * n - j + offset)

  # there is no answer, because if there were, we would have find it
  return -1;  

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

# sudo randrom mapping function
def f(x, k):
  return ((x ** 2 + 1) % k) + 1 

# g^x = h (mod p)
def kangaroo(g, h, p, b, k, n):

  # step 0: initialize tame kangroo, starting at g^b
  t = power(g, b, p)
  d_t = 0

  # step 1: tame kangroo jump n times then set the trap
  for i in range(0, n):
    hop_distance = (2**f(t, k))
    d_t = (d_t + hop_distance)
    t = (power(g, hop_distance, p) * t) % p
  
  # print (f"trap set with right bound {d_t + b}")

  answer = -1
  z = 0
  while answer == -1:
    # step 2: initialize wild kangroo at (x + z)
    w = (h * power(g, z, p))
    d_w = z

    # step 3: wild kangroo jump
    while (d_t  - d_w + b) >= 1:
      if w == t:
        # we trap the wild kangroo
        answer = (d_t  - d_w + b)
        break
      else:
        hop_distance = (2**f(w, k))
        d_w = (d_w + hop_distance)
        w = (power(g, hop_distance, p) * w)  % p

    z = z + 1
    # print(f"trying new z = {z}")

  return answer

def test():
  # g^x = h (mod p)
  t_time = 0
  trials = 1
  for i in range(trials):
    # ---------------- experiment 1, [0.00022s] ---------------------
    # g = 7
    # p = 179
    # x = random.randint(1, p - 1)
    # h = power(g, x, p)

    # start = timer()
    # ans = kangaroo(g, h, p, p - 1, 7, int(math.sqrt(p)))
    # ans = bsgs(g, h, p, p)
    # end = timer()
    # t_time = t_time + end - start

    # ------------------ experiment 2, [0.001s] 2^10 -------------------------
    # g = 2
    # p = 1019
    # x = random.randint(1, p - 1)
    # h = power(g, x, p)

    # start = timer()
    # # ans = kangaroo(g, h, p, p - 1, 9, int(math.sqrt(p)))
    # ans = bsgs(g, h, p, p)
    # end = timer()
    # t_time = t_time + end - start

    # -------------------- experitment 3, [0.0077] 2^15 ---------------------------
    # g = 2
    # p = 32843
    # x = random.randint(1, p - 1)
    # h = power(g, x, p)
    # i = 14

    # # assert power(g, (p - 1)//2, p) == p - 1
    # # assert miller_rabin((p-1)//2, 100)

    # start = timer()
    # # ans = kangaroo(g, h, p, p - 1, i, int(math.sqrt(p)))
    # ans = bsgs(g, h, p, p)
    # end = timer()
    # t_time = t_time + end - start

    # -------------------- experitment 4, [0.15] 2^20 ---------------------------
    # g = 5
    # p = 1048703
    # x = random.randint(1, p - 1)
    # h = power(g, x, p)
    # i = 19

    # # assert power(g, (p - 1)//2, p) == p - 1
    # # assert miller_rabin((p-1)//2, 100)

    # start = timer()
    # # ans = kangaroo(g, h, p, p - 1, i, int(math.sqrt(p)))
    # ans = bsgs(g, h, p, p)
    # end = timer()
    # t_time = t_time + end - start

    # -------------------- experitment 5, [0.15] 2 ^ 25 ---------------------------
    # g = 5
    # p = 35616587
    # x = random.randint(1, p - 1)
    # h = power(g, x, p)
    # i = 24

    # # assert power(g, (p - 1)//2, p) == p - 1
    # # assert miller_rabin((p - 1)//2, 100)

    # start = timer()
    # # ans = kangaroo(g, h, p, p - 1, i, int(math.sqrt(p)))
    # ans = bsgs(g, h, p, p)
    # end = timer()
    # t_time = t_time + end - start

    # -------------------- experitment 6, [2s] 2 ^ 30 ---------------------------
    # g = 7
    # p = 2376034799
    # x = random.randint(1, p - 1)
    # h = power(g, x, p)
    # i = 29

    # # assert power(g, (p - 1)//2, p) == p - 1
    # # assert miller_rabin((p - 1)//2, 100)

    # start = timer()
    # # ans = kangaroo(g, h, p, p - 1, i, int(math.sqrt(p)))
    # ans = bsgs(g, h, p, p)
    # end = timer()
    # t_time = t_time + end - start

    # -------------------- experitment 7, [22s] 2 ^ 35 (on 5 smaple)---------------------------
    # g = 2
    # p = 66622725563
    # x = random.randint(1, p - 1)
    # h = power(g, x, p)
    # i = 34

    # # assert power(g, (p - 1)//2, p) == p - 1
    # # assert miller_rabin((p - 1)//2, 100)

    # start = timer()
    # # ans = kangaroo(g, h, p, p - 1, i, int(math.sqrt(p)))
    # ans = bsgs(g, h, p, p)
    # end = timer()
    # t_time = t_time + end - start

    # -------------------- experitment 8, [190s] 2 ^ 40 (2 sample)---------------------------
    # g = 13
    # p = 1500395717399
    # x = random.randint(1, p - 1)
    # h = power(g, x, p)
    # i = 39

    # # assert power(g, (p - 1)//2, p) == p - 1
    # # assert miller_rabin((p - 1)//2, 100)

    # start = timer()
    # # ans = kangaroo(g, h, p, p - 1, i, int(math.sqrt(p)))
    # ans = bsgs(g, h, p, p)
    # end = timer()
    # t_time = t_time + end - start

    # -------------------- experitment 9, [331s] 2 ^ 45 (3 sample)---------------------------
    # g = 2
    # p = 61129823211299
    # x = random.randint(1, p - 1)
    # h = power(g, x, p)
    # i = 44
    
    # # assert power(g, (p - 1)//2, p) == p - 1
    # # assert miller_rabin((p - 1)//2, 100)

    # start = timer()
    # # ans = kangaroo(g, h, p, p - 1, i, int(math.sqrt(p)))
    # ans = bsgs(g, h, p, p)
    # end = timer()
    # t_time = t_time + end - start

    # -------------------- experitment 10, [] 2 ^ 50 (1 sample)---------------------------
    # g = 2
    # p = 1323314315162339
    # x = random.randint(1, p - 1)
    # h = power(g, x, p)
    # i = 49

    # # assert power(g, (p - 1)//2, p) == p - 1
    # # assert miller_rabin((p - 1)//2, 100)

    # start = timer()
    # # ans = kangaroo(g, h, p, p - 1, i, int(math.sqrt(p)))
    # ans = bsgs(g, h, p, p)
    # end = timer()
    # t_time = t_time + end - start

    # -------------------- experitment 11, [] 2 ^ 55 (1 sample)---------------------------
    g = 5
    p = 1303302925709858303
    x = random.randint(1, p - 1)
    h = power(g, x, p)
    i = 54

    assert power(g, (p - 1)//2, p) == p - 1
    assert miller_rabin((p - 1)//2, 100)

    start = timer()
    # ans = kangaroo(g, h, p, p - 1, i, int(math.sqrt(p)))
    ans = bsgs(g, h, p, p)
    end = timer()
    t_time = t_time + end - start

    # -------------------- experitment 12, [] 2 ^ 130 (1 sample)---------------------------
    # g = 7
    # p = 2352135217867182205962592313493773501999
    # x = random.randint(1, p - 1)
    # h = power(g, x, p)
    # # i = 54

    # assert power(g, (p - 1)//2, p) == p - 1
    # assert miller_rabin((p - 1)//2, 100)

    # start = timer()
    # # ans = kangaroo(g, h, p, p - 1, i, int(math.sqrt(p)))
    # ans = bsgs(g, h, p, p)
    # end = timer()
    # t_time = t_time + end - start

    # -------------------- experitment 13, [] 2 ^ 80 (1 sample)---------------------------
    # g = 13
    # p = 1747657214360998464084959
    # x = random.randint(1, p - 1)
    # h = power(g, x, p)
    # # i = 54

    # assert power(g, (p - 1)//2, p) == p - 1
    # assert miller_rabin((p - 1)//2, 100)

    # start = timer()
    # # ans = kangaroo(g, h, p, p - 1, i, int(math.sqrt(p)))
    # ans = bsgs(g, h, p, p)
    # end = timer()
    # t_time = t_time + end - start

    # -------------------- experitment 14, [] 2 ^ 60 (1 sample)---------------------------
    # g = 5
    # p = 1683035312517090047
    # x = random.randint(1, p - 1)
    # h = power(g, x, p)
    # # # i = 54

    # assert power(g, (p - 1)//2, p) == p - 1
    # assert miller_rabin((p - 1)//2, 100)

    # start = timer()
    # # ans = kangaroo(g, h, p, p - 1, i, int(math.sqrt(p)))
    # ans = bsgs(g, h, p, p)
    # end = timer()
    # t_time = t_time + end - start


    # -------------------- experitment 15, [] 2 ^ 70 (1 sample)---------------------------
    # g = 5
    # p = 1789553520200033022047
    # x = random.randint(1, p - 1)
    # h = power(g, x, p)
    # # # i = 54

    # assert power(g, (p - 1)//2, p) == p - 1
    # assert miller_rabin((p - 1)//2, 100)

    # start = timer()
    # # ans = kangaroo(g, h, p, p - 1, i, int(math.sqrt(p)))
    # ans = bsgs(g, h, p, p)
    # end = timer()
    # t_time = t_time + end - start
    
    # -------------------- experitment 16, [] (1 sample)---------------------------
    # p = 247457076132467
    # g = 2
    # x = random.randint(1, p - 1)
    # h = power(g, x, p)
    # # # i = 54

    # assert power(g, (p - 1)//2, p) == p - 1
    # assert miller_rabin((p - 1)//2, 100)

    # start = timer()
    # # ans = kangaroo(g, h, p, p - 1, i, int(math.sqrt(p)))
    # ans = bsgs(g, h, p, p)
    # end = timer()
    # t_time = t_time + end - start
    

    # -------------------- experitment 16, [] (1 sample)---------------------------
    # p = 61845915503831114091865164962647232917206327870669899
    # g = 2
    # x = random.randint(1, p - 1)
    # h = power(g, x, p)
    # # # i = 54

    # assert power(g, (p - 1)//2, p) == p - 1
    # assert miller_rabin((p - 1)//2, 100)

    # start = timer()
    # # ans = kangaroo(g, h, p, p - 1, i, int(math.sqrt(p)))
    # ans = bsgs(g, h, p, p)
    # end = timer()
    # t_time = t_time + end - start
  # assert ans % (p-1) == x
  print(f"time = {(t_time) / trials}")

def main():
  test()

if __name__ == "__main__":
  main()