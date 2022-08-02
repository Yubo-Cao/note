from urllib.request import urlopen

response = urlopen("https://www.python.org")
print(response.status)  # 200, OK
print(response.getheaders())  # List of tuple
print(response.getheader("Server"))  # nginx
print(response.getheader("DoesNotExist"))  # None
