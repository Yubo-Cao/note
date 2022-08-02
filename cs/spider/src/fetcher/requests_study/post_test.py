import requests

data = {"name": "yubo", "age": 16}
r = requests.post("https://httpbin.org/post", data=data)
print(r.text)
