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
  return ((x ** 2 + 1) % k) + 1 

# g^x = h (mod p)
def kangaroo(g, h, p, b, k, n):
  answer = -1
  z = 0

  # step 0: initialize tame kangroo, distance = b
  t = power(g, b, p)
  d_t = b
 
  # step 1: tame kangroo jump n times then set the trap
  for i in range(0, n):
    d_t = (d_t + f(t, k))
    t = (power(g, f(t, k), p) * t) % p
  
  print ("trap set")
  while answer == -1:
    # step 2: initialize wild kangroo
    w = (h * power(g, z, p))
    d_w = z

    # step 3: wild kangroo jump
    while d_w <= d_t:
      if w == t:
        # we trap the wild kangroo
        answer = (d_t  - d_w)
        break
      else:
        d_w = (d_w + f(w, k))
        w = (power(g, f(w, k), p) * w)  % p

    z = z + 1

  return answer

def test():
  # p = 61845915503831114091865164962647232917206327870669899
  # g = 3
  # x = 13215411345132
  # h = power(g, x, p)
  # print(kangaroo(g, h, p, x + 1000000000, 1000, 100000))

  # g = 2
  # h = 208891284998759
  # p = 247457076132467
  # print(kangaroo(g, h, p, p - 1, 100, 10000000))

  # g^x = h (mod p)
  g = 3
  x = 3124532153132532131231243143123124
  p = 61845915503831114091865164962647232917206327870669899
  h = power(g, x, p)

  # set b very close to x
  b = x + 900000000
  
  print(kangaroo(g, h, p, b, 1000, 100000))

def main():
  test()

if __name__ == "__main__":
  main()