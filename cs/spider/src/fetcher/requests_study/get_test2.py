import requests
from pprint import pprint

params = {"name": "germey", "age": 22}

r = requests.get("http://httpbin.org/get", params=params)
print(r.text)
pprint(r.json())  # Dict object. Must be json, otherwise, json.decoder.JSONDecodeError

