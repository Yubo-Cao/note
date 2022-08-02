from http.cookiejar import MozillaCookieJar, LWPCookieJar
from urllib.error import URLError
from urllib.request import HTTPCookieProcessor, build_opener

filename = "cookies.txt"
cookie = LWPCookieJar(filename)
opener = build_opener(HTTPCookieProcessor(cookie))
try:
    response = opener.open("https://www.google.com")
    cookie.save(ignore_discard=True, ignore_expires=True)
except URLError as e:
    print(e.reason)
