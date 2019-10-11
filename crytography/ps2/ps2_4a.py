import math
import random

def gcd(a, b):
  if b == 0:
    return a
  return gcd(b, a % b);

def pollard_rho(n):
  x_i = 2
  y_i = 2
  c = random.randint(2, n)

  while True:
    x_i = f(x_i, c, n)
    y_i = f(f(y_i, c, n), c, n)
    gcd_check = gcd(abs(x_i - y_i), n)

    if gcd_check == n:
      print("finding factor failed, try different x_i, y_i, c or f(x) function")
    elif gcd_check == 1:
      continue
    else:
      print(f"one factor is: {gcd_check}")
      break

def f(x, c, m):
  return (x ** 2 + c) % m

def main():
  pollard_rho(2**128 + 1) 

if __name__ == "__main__":
  main()