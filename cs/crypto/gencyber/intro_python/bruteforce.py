from getpass import getpass
import time
from string import printable

userpwd = getpass("Enter the password:")
start = time.time()
allchars = printable
password = ""

for idx in range(0, len(userpwd)):
    curchr = userpwd[idx]
    for chr in allchars:
        if chr == curchr:
            password += curchr
            break
        print(password)
    else:
        print("Uncrackable. Won't ever be able to crack this password.")

print(f"Cracked in {time.time() - start:.2f} second.")
