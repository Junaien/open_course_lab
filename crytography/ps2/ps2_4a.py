import math

def find_factor(n):
  x_i = 2
  y_i = 2
  c = 1
  
  while True:
    x_i = f(x_i, c, n)
    y_i = f(f(y_i, c, n), c, n)
    gcd_check = gcd(abs(x_i - y_i), n)

    if gcd_check == n:
      print("finding factor failed, try different x_i, y_i, c or f(x) function")
    elif gcd_check == 1:
      continue
    else:
      print(f"one factor == : {gcd_check}")
      break

def f(x, c, m):
  return (x ** 2 + c) % m

def gcd(a, b):
  if b == 0:
    return a
  return gcd(b, a % b);

def main():
  find_factor(2 ** 64 + 1)

if __name__ == "__main__":
  main()