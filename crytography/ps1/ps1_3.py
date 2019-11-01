import math;

# this function check if n is primitive root of m
# def is_primitive_root(n, m):
#   visited, accumulator = set(), 1

#   # stop when any n^i(mod m) starts cycling
#   for i in range(1, m):
#     accumulator = (accumulator * n) % m
#     if accumulator in visited:
#       return False
#     else:
#       visited.add(accumulator)
#   return True

def is_primitive_root(n = 7, m = 65537):
  # calculate 7 ^ 2 (mod 65537)
  # 7 ^ (2 ^ (1...16))
  power = 7 
  for i in range(1, 16):
    power = (power * power) % 65537
    if power == 1:
      print(f"7 is not primitive root of 65537")
      return

  print("7 is a primitive root of 65537")

# this function solves discrete logarithm problem g^x = h(mod m) by trying all x
def bsgs_bf(g, h, m):
  x, accumulator = 0, 1
  for i in range(m):
    if accumulator == h:
      return x
    x += 1
    accumulator = (g * accumulator) % m
  return -1

# - Algorithm to find x, where g^x = h(mod m)
# - let n = sqt(m) + 1
#   - case 1: if there is k in [0, m) so that k solves the equation,
#             it must exist k = i * n - j, where i in [0, n], j in [0, n],
#             g ^ k = g ^ ((i * n) - j) = h(mod m)
#             there must be i, j such that g ^ (i * n) = h * g ^ j (mod m)
#   - case 2: if there is no k that solves the equation, then there is no such (i, j) pair
# def bsgs(g, h, m):
#   n = int(math.sqrt(m) + 1);  
#   g_i_n_table = {}
  
#   # step1: calculate g ^ n
#   g_n = 1
#   for i in range(n):
#     g_n = (g_n * g) % m

#   # step2: for all i in [0, n], store g ^ (i * n)
#   accumulateA = 1
#   for i in range(1, n + 1):
#     accumulateA = (accumulateA * g_n) % m
#     g_i_n_table[accumulateA] = i

#   # step3: loop through all j in [0, n] to find match in g_i_n_table
#   accumulateB = 1
#   for j in range(1, n + 1):
#     accumulateB = (accumulateB * g) % m
#     key = ((accumulateB * h) % m)
#     if key in g_i_n_table:
#       return (g_i_n_table[key] * n - j + (m - 1)) % (m-1)

#   # there is no answer, because if there were, we would have find it
#   return -1;  


def power(a, x, n):
  ret = 1
  if x == 0:
    return ret % n
  if (x % 2 == 1):
    ret = a
  return (ret * (power(a, x // 2, n)**2)) % n
# - Parallel algorithm to find x, where g^x = h(mod m)
# - let n = sqt(m) + 1
#   - case 1: if there is k in [0, m) so that k solves the equation,
#             it must exist k = i * n - j, where i in [0, n], j in [0, n],
#             g ^ k = g ^ ((i * n) - j) = h(mod m)
#             there must be i, j such that g ^ (i * n) = h * g ^ j (mod m)
#   - case 2: if there is no k that solves the equation, then there is no such (i, j) pair
def bsgs(g, h, m, r, offset = 0):
  n = int(math.sqrt(r) + 1);  
  g_i_n_table = {}
  
  # step0: calculate g ^ offset
  g_offset = power(g, offset, m)

  # step1: calculate g ^ n
  g_n = 1
  for i in range(n):
    g_n = (g_n * g) % m

  # step2: for all i in [0, n], store g ^ (i * n) + offset
  accumulateA = 1
  for i in range(1, n + 1):
    accumulateA = (accumulateA * g_n) % m
    g_i_n_table[(accumulateA * g_offset) % m] = i

  # step3: loop through all j in [0, n] to find match in g_i_n_table
  accumulateB = 1
  for j in range(1, n + 1):
    accumulateB = (accumulateB * g) % m
    key = ((accumulateB * h) % m)
    if key in g_i_n_table:
      print(f"j is {j}, i is {g_i_n_table[key]}")
      return (g_i_n_table[key] * n - j + offset)
  # there is no answer, because if there were, we would have find it
  return -1;  
def main():
  # question 3a 
  # print("solving question 3a....")
  # print(is_primitive_root())
 
  # question 3b
  # print("solving question 3b....")
  # print(f"7^x = 2(mod 65537) then x =   {bsgs_bf(7, 2, 65537)}\n")
  
  # question 3c
  # print("solving question 3c....")
  # print(power(2, 208891284998759, 247457076132467))

  print(power(3, 312453215313253213123124314312312412, 61845915503831114091865164962647232917206327870669899))
  print(f"x = {bsgs(3, 39502035270701603303757755602043385545506760485094672, 61845915503831114091865164962647232917206327870669899, 100000, 312453215313253213123124314312310000)}");  
  # print(f"x = {bsgs(3, 7, 11)}");  
  

if __name__ == "__main__":
  main()