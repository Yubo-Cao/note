from requests import Request, Session

url = "http://httpbin.org/post"
data = {"name": "yubo", "age": 16}
headers = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.64 Safari/537.36 Edg/101.0.1210.53"
}

with Session() as s:
    req = Request("POST", url, data=data, headers=headers)
    prep = s.prepare_request(req)
    r = s.send(prep)
    print(r.text)
