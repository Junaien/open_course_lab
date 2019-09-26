import numpy as np
import math
import matplotlib.pyplot as plt
from numpy.linalg import norm

def vigenere(alphabet_c_2_i, alphabet, alphabet_frequency, guess_key_length, cypher_path, save_path):
  cypher = ""
  # read text, replace empty and newline character
  with open(cypher_path, 'r') as file:
    cypher = file.read().replace('\n', '')
    cypher = cypher.replace(' ', '')
  # step 1: plot character histogram based on each potential key length, select most likly key lenth
  for i in range(3, 13):
    calculate_histo_and_plot(alphabet_c_2_i, cypher, i, save_path);

  # step 2: for each index from 0...l-1, try to guess possible 'e'(or, in question 6, most frequent) character
  selected_histo = calculate_histo_and_plot(alphabet_c_2_i, cypher, guess_key_length, save_path);
  key = []
  for i in range(guess_key_length):

    # for each character <ch> in alphabet, try to match each character to be 'e'(or, in question 6, most frequent) character
    # shift our character histo array based on <ch>, then compute the angle between two frequency vector (alphabet_frequency, shifted_histo)
    angle_list = {}
    for shift in range(len(alphabet)):
      F = percentage(selected_histo[i], len(cypher) / guess_key_length)
      F_shift = np.roll(F, -shift)
      angle_list[shift] = angle(alphabet_frequency, F_shift)

    angle_list = sorted(angle_list.items(), key=lambda x : x[1])
    print(f'current index mod lenth [{i}], angle list(shift_amout, angle in radius): -----------------------------------')
    print(angle_list)
    print()

    # step 3: select most likely shift amount for each [0...l-1] index, key is equivalent to that shift
    #         e.g. if best match is shift -3 unit, then key = 3 = 'd'
    key.append(alphabet[angle_list[0][0]])

  key = ''.join(key)
  print(f"using key: {key}")
  print("possible original text:")
  print(decrypt(cypher, key, alphabet, alphabet_c_2_i))

def calculate_histo_and_plot(alphabet_c_2_i, cypher, l, save_path):
  histo = np.zeros((l, len(alphabet_c_2_i)), 'int32')
  
  # calculate character occurrence frequency for length index [0...l-1]
  for i in range(len(cypher)):
    histo[i % l][alphabet_c_2_i[cypher[i]]] += 1;
  
  # plot character occurrence frequency at each lenth index
  for i in range(l):
    plt.close("all")
    plot(sorted(histo[i],reverse = True), i, color='r')
    save_plot(i, f'{l}L{i}.png', f'{save_path}/{l}L{i}.png')
  return histo

def decrypt(cypher, key, alphabet, alphabet_c_2_i):
  text = []
  l = len(key)
  for i in range(len(cypher)):
    key_shift = alphabet_c_2_i[key[i % l]]
    text.append(alphabet[(alphabet_c_2_i[cypher[i]] - key_shift + len(alphabet)) % len(alphabet)])
  return ''.join(text)

def encrypt(text, key, alphabet, alphabet_c_2_i):
  cypher = []
  l = len(key)
  for i in range(len(text)):
    key_shift = alphabet_c_2_i[key[i % l]]
    cypher.append(alphabet[(key_shift + alphabet_c_2_i[text[i]]) % len(alphabet)])
  return ''.join(cypher)

def plot(hist, fig, color):
  plt.figure(fig)
  plt.bar(range(len(hist)), hist, color = color)

def save_plot(fig, title, location):
  plt.figure(fig)
  plt.title(title)
  plt.savefig(location)

# make histogram display percentage 
def percentage(a, denominator):
  rt = []
  for i in a:
    rt.append(i / denominator)
  return rt

def angle(a, b):
  return round(np.arccos((a @ b) / (norm(a) * norm(b))), 2)

def test():
  # test encryption & decryption are inverse function
  alphabet = "abcdefghijklmnopqrstuvwxyz"
  alphabet_c_2_i = {x : ord(x) - ord('a') for x in alphabet}

  text = "thequickbrown"
  inverted_text = decrypt(encrypt(text, "fox", alphabet, alphabet_c_2_i), "fox", alphabet, alphabet_c_2_i)

  assert inverted_text == text
  assert encrypt("txhpoi", "bytxcq", alphabet, alphabet_c_2_i) == "uvamqy"

  print("functions tests successfully passed!")

def main():
  test()
  # solve question 5 
  print("<<<")
  print("solving question 5......")
  q5_alphabet = "abcdefghijklmnopqrstuvwxyz"
  q5_alphabet_c_2_i = {q5_alphabet[x] : x for x in range(len(q5_alphabet))}
  q5_alphabet_frequency = [8.2, 1.5, 2.8, 4.3, 12.7, 2.3, 2.0, 6.1, 7.0, 0.2, 0.8, 4.0, 2.4, 6.7, 7.5, 1.9, 0.1, 6.0, 6.3, 9.1, 2.8, 1.0, 2.4, 0.2, 2.0, 0.1]
  vigenere(q5_alphabet_c_2_i, q5_alphabet, q5_alphabet_frequency, 5, 'question5.txt', './q5')

  # solve question 6
  print("solving question 6......")
  q6_alphabet = "aábdðeéfghiíjklmnoóprstuúvxyýþæö"
  q6_alphabet_c_2_i = {q6_alphabet[x] : x for x in range(len(q6_alphabet))}
  q6_alphabet_frequency = [9.14, 2.15, 0.96, 1.48, 3.13, 6.16, 0.53, 2.88, 3.08, 2.47, 8.00, 1.26, 0.96, 4.35, 4.75, 3.40, 8.26, 2.63, 1.43, 0.62, 9.81, 4.76, 5.90, 3.66, 0.48, 2.46, 0.04, 0.76, 0.10, 2.91, 0.59, 0.89]
  vigenere(q6_alphabet_c_2_i, q6_alphabet, q6_alphabet_frequency, 8, 'question6.txt', './q6')

if __name__== "__main__":
  main()