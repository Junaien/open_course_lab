def power(a, x, n):
  ret = 1
  if x == 0:
    return ret
  if (x % 2 == 1):
    ret = a
  return (ret * (power(a, x // 2, n)**2)) % n

def euler_criterion(a, p):
  return power(a, (p - 1) / 2, p)

def legendre(a, p):
  return euler_criterion(a, p)
  
def solve_recursive(g, h, p, limit):
  # easy case
  if g == h:
    return 1
  if h == 1:
    return 0
  if limit == 0:
    return -1
  
  # recusive case
  legd = legendre(h, p)
  if legd == 1:
    sqrt = power(h, (p + 1) // 4, p)
    x1 = solve_recursive(g, sqrt, p, limit - 1)
    x2 = solve_recursive(g, p - sqrt, p, limit - 1)
    if x1 != -1:
      return 2 * x1
    elif x2 != -1:
      return 2 * x2
    else: 
      return -1
  elif legd == p-1:
    sqrt = power((h * g) % p, (p + 1) // 4, p)
    x1 = solve_recursive(g, sqrt, p, limit - 1)
    x2 = solve_recursive(g, p - sqrt, p, limit - 1)
    if x1 != -1:
      return 2 * x1 - 1
    elif x2 != -1:
      return 2 * x2 - 1
    else: 
      return -1
  else:
    print("error")
    exit(0)


assert power(2, solve_recursive(2, 48, 107, 7), 107) == 48
