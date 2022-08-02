from urllib import request, parse

url = "http://httpbin.org/post"
headers = {
    "User-Agent": "Mozilla/5.0 (X11; Linux x86_64; rv:100.0) Gecko/20100101 Firefox/100.0",
    "Accept-Language": "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2",
    "Host": "httpbin.org",
}

data = {"name": "Germany"}
data = bytes(parse.urlencode(data), "utf-8")

req = request.Request(url, data, headers, method="POST")
response = request.urlopen(req)
print(response.read().decode("utf-8"))
