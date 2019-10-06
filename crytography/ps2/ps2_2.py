import math
import random

given_vowel_memo = {}
given_consonant_memo = {}

def pr_given_vowel(L):
  if L == 1:
    return 1

  if L in given_vowel_memo:
    return given_vowel_memo[L]
  
  given_vowel_memo[L] = (7 / 12) * pr_given_consonant(L - 1) + (5 / 12) * pr_given_vowel(L - 1)
  return given_vowel_memo[L]

def pr_given_consonant(L):
  if L == 1:
    return 1

  if L in given_consonant_memo:
    return given_consonant_memo[L]

  given_consonant_memo[L] = (5 / 12) * pr_given_vowel(L - 1)
  return given_consonant_memo[L]


def pr_no_consecutive_consonant(L):
  return (7 / 12) * pr_given_consonant(L) + (5 / 12) * pr_given_vowel(L)

def test():
  assert abs(pr_no_consecutive_consonant(1) - 1) < 0.00001
  assert abs(pr_no_consecutive_consonant(2) - 0.6597) < 0.001
  assert abs(pr_no_consecutive_consonant(3) - 0.5179) < 0.001

def main():
  test()
  for i in range(2, 200): 
    print(f"{i} : {pr_no_consecutive_consonant(i)}")

if __name__ == "__main__":
  main()