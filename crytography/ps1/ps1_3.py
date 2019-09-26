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
def dlp_solver_bf(g, h, m):
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
def dlp_solver(g, h, m):
  n = int(math.sqrt(m) + 1);  
  g_i_n_table = {}
  
  # step1: calculate g ^ n
  g_n = 1
  for i in range(n):
    g_n = (g_n * g) % m

  # step2: for all i in [0, n], store g ^ (i * n)
  accumulateA = 1
  for i in range(1, n + 1):
    accumulateA = (accumulateA * g_n) % m
    g_i_n_table[accumulateA] = i

  # step3: loop through all j in [0, n] to find match in g_i_n_table
  accumulateB = 1
  for j in range(1, n + 1):
    accumulateB = (accumulateB * g) % m
    key = ((accumulateB * h) % m)
    if key in g_i_n_table:
      return (g_i_n_table[key] * n - j + (m - 1)) % (m-1)

  # there is no answer, because if there were, we would have find it
  return -1;  

def main():
  # question 3a 
  print("solving question 3a....")
  print(is_primitive_root())
 
  # question 3b
  print("solving question 3b....")
  print(f"7^x = 2(mod 65537) then x =   {dlp_solver_bf(7, 2, 65537)}\n")
  
  # question 3c
  print("solving question 3c....")
  # print(f"x = {dlp_solver(2, 92327518017225, 247457076132467)}");  
  print(f"x = {dlp_solver(3, 7, 11)}");  


if __name__ == "__main__":
  main()