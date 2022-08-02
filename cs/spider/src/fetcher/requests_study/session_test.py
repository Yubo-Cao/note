import requests

with requests.Session() as s:
    s.get("https://httpbin.org/cookies/set/number/123456789")
    r = s.get("https://httpbin.org/cookies")
    print(r.text)
