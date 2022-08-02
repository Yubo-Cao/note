from urllib.parse import parse_qs, parse_qsl, urlencode, urljoin

params = {"name": "germey", "age": 22}
base_url = "https://www.google.com"
url = urljoin(base_url, "?" + urlencode(params))
print(url)

query = urlencode(params)
print(parse_qs(query)) # {'name': ['germey'], 'age': ['22']}
print(parse_qsl(query)) # {'name': ['germey'], 'age': ['22']}
