import math

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
  return ((x ** 2 + 3) % k) + 1 

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
  
  print (f"trap set with right bound {d_t + b}")

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
    print(f"trying new z = {z}")

  return answer

def test():
  # g^x = h (mod p)
  g = 3
  p = 61845915503831114091865164962647232917206327870669899
  h = 5815015754374921280955691220093049847105334794690583

  print(kangaroo(g, h, p, p - 1, 120, 600000))

def main():
  test()

if __name__ == "__main__":
  main()