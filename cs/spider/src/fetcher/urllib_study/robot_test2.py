from socket import timeout
from urllib.error import HTTPError, URLError
from urllib.request import Request, urlopen
from urllib.robotparser import RobotFileParser

r = RobotFileParser()

headers = {
    "User-Agent": "Mozilla/5.0 (X11; Linux x86_64; rv:100.0) Gecko/20100101 Firefox/100.0",
}
req = Request("https://quizlet.com/robots.txt", headers=headers, method="GET")
try:
    content = urlopen(req).read().decode("utf-8")
except HTTPError as e:
    print(e.reason, e.status, e.headers)
except URLError as e:
    if isinstance(e.reason, timeout):
        print("Time out error")
    else:
        print(e.reason)
r.parse(content.split("\n"))
print(r.can_fetch(headers["User-Agent"], "https://quizlet.com/search "))
