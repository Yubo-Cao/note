import requests

r = requests.get("https://www.google.com")
print(type(r), r.cookies)

[print(f"{k:<10}={v}") for k, v in r.cookies.items()]