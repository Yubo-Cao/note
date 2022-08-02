from urllib import parse, request

data = bytes(parse.urlencode({"word": "hello-world"}), encoding="utf-8")
response = request.urlopen("http://httpbin.org/post", data=data)
print(response.read().decode("utf-8"))
