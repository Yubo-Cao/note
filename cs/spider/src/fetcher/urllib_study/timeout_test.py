import socket
from urllib import request, error

try:
    response = request.urlopen("http://httpbin.org/get", timeout=0.001)
except error.URLError as e:
    if isinstance(e.reason, socket.timeout):
        print("TIME OUT")
