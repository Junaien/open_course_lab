import math

# fast exponentiation
def power(a, x, n):
  ret = 1
  if x == 0:
    return ret % n
  if (x % 2 == 1):
    ret = a
  return (ret * (power(a, x // 2, n)**2)) % n

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

# g^x = h (mod m)
def main():
  
  # x = 312453215313253213123124314312312412
  p = 61845915503831114091865164962647232917206327870669899
  # h = power(3, x, p)

  # offset = x - 9000000000000
  # # set offset very close to x
  # assert bsgs(3, h, p, 10000000000000, offset) == x

  sqrt = power(3, (p + 1) / 4, p)
  # print(power(-1*sqrt + p, (p - 1) // 2, p))
  # print(power(p-1,  (p - 1) // 2, p))
  # print(power(2, (p-1) // 2, p))
  print(power(12,  (p - 1) // 2, p))
  
if __name__ == "__main__":
  main()