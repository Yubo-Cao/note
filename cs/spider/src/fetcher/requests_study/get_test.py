import requests

r = requests.get("https://www.baidu.com")
print(
    type(r),  # <class 'requests.models.Response'>
    r.status_code,  # 200
    type(r.text),  # <class 'str'>
    r.text,  # html src
    type(r.cookies),  # <class 'requests.cookies.RequestsCookieJar'>
    r.cookies,  # <RequestsCookieJar[<Cookie BDORZ=27315 for .baidu.com/>]>
    type(r.content),  # bytes
    r.content,  # html src in bytes
    sep="\n",
)
