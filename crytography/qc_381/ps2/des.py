from Crypto.Cipher import DES

key0 = b"some key"
key1 = b"anotherk"

cipher1 = DES.new(key0, DES.MODE_ECB)
cipher2 = DES.new(key1, DES.MODE_ECB)
m1 = "any messagexyzab"

print(cipher1.encrypt(cipher2.encrypt(m1)))
print(cipher2.encrypt(cipher1.encrypt(m1)))
