from urllib.request import urlopen

response = urlopen("https://www.python.org")
print(response.read().decode("utf-8"))
