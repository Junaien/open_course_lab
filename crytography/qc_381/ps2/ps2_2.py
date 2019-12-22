import math
import random

N_V_memo = {1:1}
N_C_memo = {1:1}

def pr_N_V(L):
  if L in N_V_memo:
    return N_V_memo[L]
  
  N_V_memo[L] = (7 / 12) * pr_N_C(L - 1) + (5 / 12) * pr_N_V(L - 1)
  return N_V_memo[L]

def pr_N_C(L):
  if L in N_C_memo:
    return N_C_memo[L]

  N_C_memo[L] = (5 / 12) * pr_N_V(L - 1)
  return N_C_memo[L]


def pr_N(L):
  return (7 / 12) * pr_N_C(L) + (5 / 12) * pr_N_V(L)

def test():
  assert abs(pr_N(1) - 1) < 0.00001
  assert abs(pr_N(2) - 0.6597) < 0.001
  assert abs(pr_N(3) - 0.5179) < 0.001

def main():
  test()
  for i in range(2, 200): 
    print(f"{i} : {pr_N(i)}")

if __name__ == "__main__":
  main()