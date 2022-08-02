from http.cookiejar import LWPCookieJar
from urllib.error import URLError
from urllib.request import HTTPCookieProcessor, build_opener

cookie = LWPCookieJar()
cookie.load("cookies.txt", ignore_discard=True, ignore_expires=True)
handler = HTTPCookieProcessor(cookie)
opener = build_opener(handler)
try:
    response = opener.open("https://www.google.com")
    print(response.read().decode("utf-8"))
except URLError as e:
    print(e.reason)
