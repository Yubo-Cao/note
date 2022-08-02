from getpass import getpass
from itertools import product
import time
from string import ascii_lowercase, ascii_uppercase, digits, punctuation
import tqdm

trgt = getpass("Enter the password: ")
start = time.time()

# determine character set
charsets = []
if any(c in ascii_lowercase for c in trgt):
    charsets.append(ascii_lowercase)
if any(c in ascii_uppercase for c in trgt):
    charsets.append(ascii_uppercase)
if any(c in digits for c in trgt):
    charsets.append(digits)
if any(c in punctuation for c in trgt):
    charsets.append(punctuation)

allchars = "".join(charsets)

print(f"Detected Charsets: {allchars}")

# brute force
for pwd in tqdm.tqdm(
    product(allchars, repeat=len(trgt)), total=len(allchars) ** len(trgt)
):
    if "".join(pwd) == trgt:
        print("".join(pwd))
        break

print(f"Cracked in {time.time() - start:.2f} second.")
