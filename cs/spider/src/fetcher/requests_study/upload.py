import requests

files = {"file": open("github.ico", "rb")}
r = requests.post("https://httpbin.org/post", files=files)
print(r.text)
