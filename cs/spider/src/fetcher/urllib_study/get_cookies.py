from http import cookiejar
from urllib import request
from urllib.error import URLError

cookies = cookiejar.CookieJar()
handler = request.HTTPCookieProcessor(cookies)
opener = request.build_opener(handler)
try:
    response = opener.open("https://www.google.com")
    for item in cookies:
        print(f"{item.name:<10} = {item.value}")
except URLError as e:
    print(e.reason)
