import requests

base = "https://httpbin.org/"
r = requests.post(base + "post")
r = requests.put(base + "put")
r = requests.delete(base + "delete")
r = requests.options(base + "get")
r = requests.head(base + "get")
