from urllib import request, error

try:
    response = request.urlopen("https://www.cuiqingcai.com/index.htm")
except error.HTTPError as e:
    print(e.reason, e.code, e.headers, seq="\n--------\n")
except error.URLError as e:
    print(e.reason)
else:
    print("Success")
