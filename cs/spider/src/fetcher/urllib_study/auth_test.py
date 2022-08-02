from urllib.request import (
    HTTPPasswordMgrWithDefaultRealm,
    HTTPBasicAuthHandler,
    build_opener,
)
from urllib.error import URLError

user = "username"
passwd = "password"
url = "http://localhost:5000"

p = HTTPPasswordMgrWithDefaultRealm()
p.add_password(None, url, user, passwd)
auth = HTTPBasicAuthHandler(p)
opener = build_opener(auth)

try:
    result = opener.open(url)
    html = result.read().decode("utf-8")
    print(html)
except URLError as e:
    print(f"Failed, because {e.reason!s}")
