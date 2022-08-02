from urllib.parse import quote, unquote, urlparse

kw = 'Linux 系统'
url = f'https://www.google.com/s?wd={quote(kw)}'
print(url) # https://www.google.com/s?wd=Linux%20%E7%B3%BB%E7%BB%9F

quoted = urlparse(url).query
print(unquote(quoted)) # wd=Linux 系统