

# 基本库 -- 向服务器发出请求

- 最初的爬虫操作就是模拟浏览器向服务器发出请求
  - 构造请求
  - 数据结构的实现
  - HTTP/TCP/IP 层的网络实现
- Python 提供了 `urllib`, `httplib2`, `requests` 和 `treq` 用于发送请求，并且掩盖了上述种种的实现细节。

## `urllib`

### 历史

- Python 2 中，有 urllib 和 urllib2 两个库提供请求的发送
- Python 3 中，已经不存在 urllib2 这个库，并且统一为 urllib
- 文档在 <https://docs.python.org/3/library/urllib.html>

### 模块

- `request` 基本的 `HTTP` 请求模块，用于模拟发送请求
- `error` 异常处理
- `parse` 解析 `url` 并合并，拆分，或进行操作
- `robotparser` 判断可以爬取的网站。使用很少（都上爬虫了还关心这个？🙂）

### `request`

- 定义了适用于在各种复杂情况下打开 URL（主要为 HTTP）的函数和类 -- 例如基本认证、摘要认证、重定向、cookies 等

#### `urlopen`

- `urllib.request.urlopen(url, data=None, [timeout, ]*, cafile=None, capath=None, cadefault=False, context=None)`

- 爬取 `Python` 官网
  - ```python
    from urllib.request import urlopen
    
    response = urlopen("https://www.python.org")
    print(response.read().decode("utf-8"))
    ```

- 这个方法返回一个 `http.client.HTTPResponse` 类型的对象。

  - `read()`,  `readinto()` 得到返回的网页内容

  - `getheader(name)`, `getheaders()`

  - `fileno()` 等方法

  - `msg`, `version`, `status`（返回结果的状态码）, `reason`, `debuglevel`, 和 `closed` 等属性

  - 尝试解析 `HTTPResponse` 对象中的有用信息

    - ```python
      from urllib.request import urlopen
      
      response = urlopen("https://www.python.org")
      print(response.status)  # 200, OK
      print(response.getheaders())  # List of tuple
      print(response.getheader("Server"))  # nginx
      print(response.getheader("DoesNotExist"))  # None
      ```

- 更多参数的利用

  - `data`

    - 默认的请求方式是 `GET`。如果想要传递数据，就需要使用 `POST` 方式。

    - 把数据编码成字节流类型。转换之前，使用 `urlencode` 把参数字典转化为字符串。

    - 使用 `httpbin.org` 来进行 `HTTP` 请求测试，可以测试 `POST` 请求并输出请求的相关信息。

    - ```python
      from urllib import parse, request
      
      data = bytes(parse.urlencode({"word": "hello-world"}), encoding="utf-8")
      response = request.urlopen("http://httpbin.org/post", data=data)
      print(response.read().decode("utf-8"))
      ```

    - ```json
      {
        "args": {}, 
        "data": "", 
        "files": {}, 
        "form": {
          "word": "hello-world"
        }, 
        "headers": {
          "Accept-Encoding": "identity", 
          "Content-Length": "16", 
          "Content-Type": "application/x-www-form-urlencoded", 
          "Host": "httpbin.org", 
          "User-Agent": "Python-urllib/3.10", 
          "X-Amzn-Trace-Id": "Root=1-628d7e85-19608fec285061207e6171c8"
        }, 
        "json": null, 
        "origin": "24.131.62.201", 
        "url": "http://httpbin.org/post"
      }
      ```

  - `timeout`

    - 设置超时时间，单位为秒。如果请求超出了设置的时间，并且没有得到响应，就会抛出异常。否则，就会使用全局默认时间。

    - ```python
      import socket
      from urllib import request, error
      
      try:
          response = request.urlopen("http://httpbin.org/get", timeout=0.001)
      except error.URLError as e:
          if isinstance(e.reason, socket.timeout):
              print("TIME OUT")
      ```

  - 其他参数

    - `context`，必须是 `ssl.SSLContext` 类型，用于指定 `SSL` 设置
    - `cafile` 和 `capath` 指定 CA 证书，对于 HTTPS 链接用处很大

#### `Request`

- 为了完善请求，例如加入 `Headers` 等的信息，应当使用 `Request` 类来实现。

- `Request(url, data=None, headers={}, origin_req_host=None, unverifiable=False, method=None)`
  - `url` 就是 URL，是必须传入的
  - `data` 只接受字节流类型。可以使用 `urllib.parse.urlencode()` 进行编码。
  - `headers` 可以在构造方法中指定，或者 `add_header()` 的实例方法的指定。常用的是使用 `user-Agent` 来伪装，默认为 `Python-urllib/version`
  - `origin_req_host` 请求方的 `IP` 地址
  - `unverifiable` 表示请求无法验证。这样的请求，表示用户可能没有足够的权限来接受请求的结果。
  - `method` 接受字符串，如 `GET`, `POST`, `PUT`, `DELETE` 等。见 [[Internet Basic, HTTP(s), TCP, and UDP]]

- 例子

  - ```python
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
    ```

  - 这样，我们就可以更加方便的构造请求，并实现请求的发送了。

#### Handler

- `BaseHandler` 是 `urllib.request` 模块中其他所有 `Handler` 的父类。提供了基本的方法，例如 `default_open`, `protocol_request` 等所有子类都需要实现的基本方法。
  - `HTTPDefaultErrorHandler` 处理 HTTP 响应错误
  - `HTTPRedirectHandler` 处理重定向
  - `HTTPCookieProcesor` 处理 Cookies
  - `ProxyHandler` 管理代理
  - `HTTPPasswordMgr` 管理密码，维护用户名和密码的表格
  - `HTTPBasicAuthHandler` 解决认证的问题。

#### Opener

- 通过使用 `Handler` ，我们可以构建一个`Opener`

##### 处理验证

```python
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
```

- 实例化 `HTTPBasicAuthHandler`。该对象接受一个 `HTTPPasswordMgrWithDefaultRealm` 对象，该队向存储用户名和密码。最终，得到一个处理验证的 `Handler`
- 这个 `Handler` 用于构建 `Opener` 以打开链接

##### 代理

```python
from urllib.error import URLError
from urllib.request import ProxyHandler, build_opener

proxy = ProxyHandler(
    {"http": "http://127.0.0.1:9743", "https": "https://127.0.0.1:9743"}
)
opener = build_opener(proxy)
try:
    response = opener.open("https://www.google.com")
    print(response.read().decode("utf-8"))
except URLError as e:
    print(e.reason)
```

- 上述的代码中，我们在本地搭建一个代理，使其运行在 9743 端口上。
- `ProxyHandler` 接受一个字典作文参数，键为协议类型，值为代理链接，可以添加都各代理。
- 然后，构造 `Opener` 并使用即可。

##### Cookies

###### 获得

```python
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
```

- 声明 `CookieJar` 对象，然后利用 `HTTPCookieProcessor` 来构建一个 `Handler`。 最后，使用 `build_opener` 构建 `opener` 即可。

###### 保存

- MozillaCookieJar

  - ```python
    from distutils.command.build import build
    from http.cookiejar import MozillaCookieJar
    from urllib.error import URLError
    from urllib.request import HTTPCookieProcessor, build_opener
    
    filename = "cookies.txt"
    cookie = MozillaCookieJar(filename)
    opener = build_opener(HTTPCookieProcessor(cookie))
    try:
        response = opener.open("https://www.google.com")
        cookie.save(ignore_discard=True, ignore_expires=True)
    except URLError as e:
        print(e.reason)
    ```

  - 保存出来的 `MozillaCookieJar` 大致为：

    - ```txt
      # Netscape HTTP Cookie File
      # http://curl.haxx.se/rfc/cookie_spec.html
      # This is a generated file!  Do not edit.
      
      .google.com	TRUE	/	TRUE	1656070883	1P_JAR	2022-05-25-11
      .google.com	TRUE	/	TRUE	1669030883	AEC	AakniGOEcEuk63Wk_JB2I0O6eXGL98xufeV1LQ2dL3fgRyYVFYmsK70izfw
      .google.com	TRUE	/	FALSE	1669290083	NID	511=fpqe6Qab1XUxrD4zF0QmEi_qXorwhGszsHZSF-JXm1naTm2cHl5rALvs5rhdiKF3gWR36MUmuDKxUkF1_M9pxOBkSfMSnsp9LdUGeChiYlawt5lGnrtSAn_VYCmCiKTDAnQdWrCg5XB0xzu3KAeBVcBz6yuzAO-ga_SNvKxQkoQ
      ```

- LWPCookieJar

  - 只需要把 `MozzilaCookieJar` 替换为 `LWPCOokieJar` 即可。生成的内容如下：

  - ```txt
    #LWP-Cookies-2.0
    Set-Cookie3: 1P_JAR="2022-05-25-11"; path="/"; domain=".google.com"; path_spec; domain_dot; secure; expires="2022-06-24 11:43:57Z"; version=0
    Set-Cookie3: AEC="AakniGMANGYLqj-B0q9LedkbSVLS3m88a9SjVja-fRHdIdD3eAqlxLmN6A"; path="/"; domain=".google.com"; path_spec; domain_dot; secure; expires="2022-11-21 11:43:57Z"; HttpOnly=None; SameSite=lax; version=0
    Set-Cookie3: NID="511=pV2VlkNkERQ0_5bJ6VBOZYkuLixPIVGyNb6GxQqsqlbj1RIICICgjtcmH6AST_R40AY-SFWTr27G3J5vcPsxS0zXouS1y0zwVnybuB6wlAkbbOo6tweXV-raWPyC7w0PSgF2wPlZCkuZerRL2fGmIBNbdWynhZetAFntVZvhb3c"; path="/"; domain=".google.com"; path_spec; domain_dot; expires="2022-11-24 11:43:57Z"; HttpOnly=None; version=0
    ```

###### 加载

```python
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
```

### `error`

- `error` 模块定义了这个模块可能产生的异常。如果出现问题，`request` 模块就会抛出 `error` 中的异常。

#### `URLError`

- `URLError` 是异常模块的基类，继承与 `OSError`

  - 有一个属性 `reason`，可以返回异常错误的原因。

  - 例如，打开一个不存在的页面，通过捕获 `URLError` 可以进行异常处理。

  - ```python
    from urllib.error import URLError
    from urllib.request import urlopen
    
    try:
        response = urlopen("https://www.doesnotexistsreallyitshouldnot.com")
    except URLError as e:
        print(e.reason)
    ```

#### `HTTPError`

- `HTTPError` 继承与 `URLError`，专门处理 `HTTP` 请求的异常。

  - `code` 返回 `HTTP` 状态码

  - `reason` 同父类

  - `headers` 返回请求头

  - ```python
    from urllib import request, error
    
    try:
        response = request.urlopen("https://www.cuiqingcai.com/index.htm")
    except error.HTTPError as e:
        print(e.reason, e.code, e.headers, seq="\n--------\n")
    ```

- 此处捕获的异常，输出原因、状态码、以及请求头。

- 更好的方式可以先捕获子类的错误，然后再处理父类的错误。

  - ```python
    from urllib import request, error
    
    try:
        response = request.urlopen("https://www.cuiqingcai.com/index.htm")
    except error.HTTPError as e:
        print(e.reason, e.code, e.headers, seq="\n--------\n")
    except error.URLError as e:
        print(e.reason)
    else:
        print("Success")
    ```

  - 有时候，`reason` 返回的可能是一个对象。例如，`socket.timeout` 对象等。此时，可以使用 `isinstance` 的方式来判断其类型，做出更详细的异常判断。

### `parse`

- 提供了处理 `URL` 的标准接口，例如实现对 `URL` 各部分的抽取、合并、转换。支持常见协议，如 `file`, `imap`, `http(s)`, `rsync` 等等。

#### `urlparse`

- `urlparse(urlstring, scheme='', allow_fragements=True)`
  - `urlstring` 是位置参数，必须填写。
  - `scheme` 指明协议。默认为 `http` 或者 `https` 如果链接没有携带 `scheme` 信息。这个选项不能覆盖传入　`url` 中指明的协议。
  - `allow_fragments` 是否忽略 `fragment` 并将其解析为 `path/parameters/query` 的一部分。
  - 对 `url` 的识别和分段。返回一个 `urllib.parse.ParseResult` 对象。

- ```python
  from urllib.parse import urlparse
  
  res = urlparse("http://www.quizlet.com/index.html;user?id=15#comment")
  print(type(res), res)
  ```

  - 这个命名元组的构造：`ParseResult(scheme='http', netloc='www.quizlet.com', path='/index.html', params='user', query='id=15', fragment='comment')`
    - 可以使用索引顺序，点属性名的方式来获取。
    - `://` 之前的部分是协议
    - `/` 之前的部分是 `netloc`
    - 访问路径为 `path`
    - 分号前面为 `params`
    - `?` 后面是 `query string` 一般只在 GET 请求使用
    - `#` 是锚点，直接定位页面内部的位置
  - https://skorks.com/2010/05/what-every-developer-should-know-about-urls/

- 一个标准的 `url` 的格式如下：

  - `<scheme>://<username>:<password>@<host>:<port>/<path>;<parameters>?<query>#<fragment>`
  - 上述的格式中，并不是所有部分都是必需的。可以省略。

#### `urlunparse`

- 接受一个长度为 6 的可迭代对象，然后将其转会为一个 `url` 字符串。长度必须是 6。

- 这个可迭代的对象，按照 `scheme`, `netloc`, `path`, `params`, `query` 和 `fragment` 的方式提供字符串。

  - ```python
    from urllib.parse import urlunparse
    
    data = ["http", "www.quizlet.com", "index.html", "user", "a=6&b=10", "comment"]
    print(urlunparse(data))
    ```

#### `urlsplit`/`urlunsplit`

- `urlunsplit` 接受长度为 `5` 的可迭代对象。但是要求把 `params` 合并到 `path` 中。
- `urlsplit` 分割一个 `url`，返回命名元组, `SplitResult` 作为结果

```python
from urllib.parse import urlsplit, urlunsplit

url = "https://user:passwd@www.quizlet.com:443/inner/index.html;param?id=10#spanish"
result = urlsplit(url)
print(
    result,  # SplitResult(scheme='https', netloc='user:passwd@www.quizlet.com:443', path='/inner/index.html;param', query='id=10', fragment='spanish')
    type(result),  # <class 'urllib.parse.SplitResult'>
    result.scheme,  # https
    result[0],  # https
    urlunsplit(result),  # Same as `url`
    sep="\n" + "-" * 8 + "\n",
)
```

#### `urljoin`

- 接受一个基础链接作为第一个参数，然后新的链接作为第二个参数。该方法解析第二个链接的内容（如协议，路径，端口等），并对第一个链接的内容进行替换/补充。
- 返回字符串作为拼接的结果。

```python
from urllib.parse import urljoin

print(
    urljoin(
        "http://www.google.com:80", "index.html"
    ),  # http://www.google.com:80/index.html
    urljoin(
        "http://www.google.com:80", "http://www.baidu.com/index.html"
    ),  # http://www.baidu.com/index.html
    urljoin(
        "http://www.google.com/about.html", "http://www.baidu.com/index.html"
    ),  # http://www.baidu.com/index.html
    urljoin(
        "http://www.google.com/about.html",
        "http://www.baidu.com/FAQ.html;user=10?question=2",
    ),  # http://www.baidu.com/FAQ.html;user=10?question=2
    urljoin(
        "http://www.google.com/about.html;user=10?question=10",
        "www.baidu.com/index.php",
    ),  # http://www.google.com/www.baidu.com/index.php
    urljoin(
        "http://www.google.com/about.html;user=10?question=10", "www.baidu.com"
    ),  # http://www.google.com/www.baidu.com
    urljoin(
        "www.google.com", "?category=2#comment"
    ),  # www.google.com?category=2#comment
    sep="\n",
)
```

- `base_url` 中的 `scheme, netloc, path` 会被放在一起。但是 `params, query, fragment` 直接被忽略不计。
- 如果两个 `url` 提供重复信息，第二个 `url` 的信息会覆盖第一个 `url`。如果第二个  `url` 不能被解析，不会报错，直接返回第二个作为结果。

#### `dict` 和 `query/params` 的转化

- 把字典转化为字符串（参数表示），然后再转化为 `bytes` 对象就可以用来向服务器发送不同的数据了。

```python
from urllib.parse import parse_qs, parse_qsl, urlencode, urljoin

params = {"name": "germey", "age": 22}
base_url = "https://www.google.com"
url = urljoin(base_url, "?" + urlencode(params))
print(url)
```

- 反序列化通过 `parse_qs` 的方式转化为字典，或者 `parse_qsl` 的方式转化为元组列表。

```python
query = urlencode(params)
print(parse_qs(query)) # {'name': ['germey'], 'age': ['22']}
print(parse_qsl(query)) # {'name': ['germey'], 'age': ['22']}
```

#### `quote`/`unquote`

- `quote` 将字符串转化为 `url` 转义字符的形式，`unquote` 将转义字符字符串转化为 `unicode` 编码形式。

```python
from urllib.parse import quote, unquote, urlparse

kw = 'Linux 系统'
url = f'https://www.google.com/s?wd={quote(kw)}'
print(url) # https://www.google.com/s?wd=Linux%20%E7%B3%BB%E7%BB%9F

quoted = urlparse(url).query
print(unquote(quoted)) # wd=Linux 系统
```

### `robotparser`

#### `Robot` 协议

- Robots 协议也成为爬虫协议、机器人协议，它的全名是网络爬虫排除标准 (Robots Exclusion Protocol)
- 这个协议告诉爬虫和搜索引擎可以抓取的部分，以及不可以抓取的部分。这作为 `robots.txt` 的文件放置在网站的根目录下。
- 爬虫访问站点时，首先检查根目录下是否存在此文件。如果存在，爬虫根据其定义的爬取乏味爬取。反之，访问所有可以直接访问的页面。

```txt
# 所有爬虫受到这个限制
User-agent: *
Allow: /search
Disallow: /ajax
Disallow: /*/autosave$
# User-agent 为 WebCrawler 的爬虫不可以爬取任何内容
User-agent: WebCrawler
Disallow: /
```

-  `User-agent` 描述的爬虫的名称。
  - `*` ：对于所有的爬虫有效
  - 设置为 `Baiduspider`，那么这个协议就仅对百度的爬虫有效
  - 指明多个 `User-agent` 就会有多个爬虫受到限制
- `Disallow` 指定不允许抓取的目录。
  - /` 表示禁止所有页面被抓取。并且，支持使用 `*` 作为通配符。
  - 留空表示允许任何目录
- `Allow` 可以覆盖 `Disallow` 的限制，用来排除某些部分。例如 `/search` 表示可以抓取 search 目录
- 上述这个文件可以留空，表示允许一切。

#### 常见爬虫 UA 和网站

| 爬虫 UA     | 名称           |
| ----------- | -------------- |
| BaiduSpider | 百度           |
| Googlebot   | 谷歌           |
| 360Spider   | 360 搜索       |
| YodaoBot    | 有道词典       |
| ia_archiver | Alexa          |
| Scooter     | altavista/雅虎 |

#### 解析 `robots.txt`

- 创建一个 `RobotFileParser` 对象

  - `RobotFileParser(url='')` 传入需要解析的网页 URL 即可。如果现在不传入，也可以使用 `set_url` 设置，默认为空。

- 调用方法

  - `set_url()` 设置要解析的 URL
  - `read()` 读取并分析。不会自动调用，必须执行才会读取 `robots.txt` 文件
  - `parse(lines: Iterable[str])` 接受一个可迭代的字符串，作为 `robots.txt` 的一部分行，并将其进行解析。
  - `can_fetch(User-agent, url)` 判断是否可以抓取这个 `URL`。返回 `True` 或者 `False`
  - `mtime()` 返回上传抓取的分析的时间。如果爬虫运行时间长，这可能是有必要的。
  - `modified()` 把当前时间设置为上次抓取的分析的时间。

- ```python
  from urllib.robotparser import RobotFileParser
  
  r = RobotFileParser('https://www.google.com/robots.txt')
  r.read()
  print(r.can_fetch('*', 'https://www.google.com/m/finance'))
  print(r.can_fetch('*', 'https://www.google.com/search/about'))
  ```

- ```python
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
  ```

  - `quizlet` 比较神奇，如果 `User-Agent` 不是正常的浏览器的 `UA`，甚至无法访问 `robots.txt`。为此，所有的调用都会返回 `false`，和没有调用 `read()` 的结果一样。

## `requests`

- `requests` 提供了更好的网络 `IO` 解决方案
  - 对于 `HTTP` 验证和 `Cookies`，`request` 不需要我们写 `Opener` 和 `Handler` 来处理。

### `get`

- 基本上和 `urllib.request.urlopen(url, method='GET')` 差不多。

```python
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
```

- 通过伪装 `UA` 来爬取知乎的网页。如果不加入这个，往往会出现 403 forbidden 等，因为网站检测到爬虫正在访问这个网页。

```python
import requests
import regex as re

headers = {
    "User-Agent": "Mozilla/5.0 (X11; Linux x86_64; rv:100.0) Gecko/20100101 Firefox/100.0"
}

r = requests.get("https://www.zhihu.com/explore", headers=headers)

# An failed attempt to use regex to parse something out of it.
'''
(?(DEFINE)
(?'selfcloseelement'(area|base|br|col|command|embed|hr|img|input|keygen|link|meta|param|source|track|wbr))
(?'selfclose'<(?P>selfcloseelement)[^<>]+>|<[^<>]+\/>)
(?'middle'[^<>]+|(?P>selfclose)))
<div\s+class="ExploreHomePage-square">
    (?P<pair>
    <(?<element>(?!(?P>selfcloseelement))\w+)[^<>]*>
        (?:(?P>middle)*|(?P>pair)*)
    <\/\g{element}>)*
<\/div>
'''
```

- 抓取，下载二进制数据

```python
import requests

r = requests.get("https://www.github.com/favicon.ico")
# print(r.text)
# print(r.content)
with open("github.ico", "w+b") as f:
    f.write(r.content)
```

- 显而易见的，`text` 给出了一堆乱码。而 `content` 给出了 bytes 类型的二进制数据。
- 通过写入 `wb` 就可以将其保存到文件中。

### `post`

- 直接将字典类型的 `data` 转入到 `post` 方法中即可。

```python
import requests

data = {"name": "yubo", "age": 16}
r = requests.post("https://httpbin.org/post", data=data)
print(r.text)
```

### `response` 对象的属性

- `status_code` 状态码，`int`
- `headers` 响应头，`requests.structures.CaseInsensitiveDict`
- `cookies` Cookies，`requests.cookies.RequestCookieJar`
- `url` url，`str`
- `history` 请求历史，`List`

```python
import requests

r = requests.get("https://www.jianshu.com")
attrs = ["status_code", "headers", "cookies", "url", "history"]
for t, attr in zip(
    map(lambda attr: repr(type(getattr(r, attr))), attrs),
    map(lambda attr: getattr(r, attr), attrs),
):
    print(t.ljust(40), attr)
```

```txt
<class 'int'>                            403
<class 'requests.structures.CaseInsensitiveDict'> {'Server': 'Tengine', 'Content-Type': 'text/html', 'Transfer-Encoding': 'chunked', 'Connection': 'keep-alive', 'Date': 'Sat, 28 May 2022 21:06:00 GMT', 'Vary': 'Accept-Encoding', 'Strict-Transport-Security': 'max-age=31536000; includeSubDomains; preload', 'Content-Encoding': 'gzip', 'x-alicdn-da-ups-status': 'endOs,0,403', 'Via': 'cache27.l2cm12-6[7,0], cache6.us16[200,0]', 'Timing-Allow-Origin': '*', 'EagleId': '0819529a16537719608501554e'}
<class 'requests.cookies.RequestsCookieJar'> <RequestsCookieJar[]>
<class 'str'>                            https://www.jianshu.com/
<class 'list'>                           []
```

#### 状态码

- `requests.codes` 是一个枚举类，可以用来对状态码进行判断。

- ```python
  import requests
  
  r = requests.get("https://www.google.com")
  if r.status_code == requests.codes.ok:
      print("Ok")
  else:
      print("Failed")
  ```

- 更多可见 [[Internet Basic, HTTP(s), TCP, and UDP]]。常见的状态码在这里都已字典形式提供了。

### 高级用法

#### 上传文件

```python
import requests

files = {"file": open("github.ico", "rb")}
r = requests.post("https://httpbin.org/post", files=files)
print(r.text)
```

- 网站返回的时候包含一个 `files` 字段。该字段中记录了上传的文件。
- 需要注意的是，`form` 字段是空的。

#### Cookies

##### 获取

```python
import requests

r = requests.get("https://www.google.com")
print(type(r), r.cookies)

[print(f"{k:<10}={v}") for k, v in r.cookies.items()]
```

- 需要注意的是，`RequestCookieJar` 没有保存的选项。不过，随便使用 `json` 保存一下也足够了。

##### 传入

- 以学校的网页为例，首先登录并且获得 `Cookie` 的内容（打开网页，然后复制 `Cookies: ` 之后的请求头的内容）

- 通过直接将 `cookies` 作为 `header` 的一部分，可以传入 `cookies`

```python
from email import header
import requests

cookie = "ai_user=XlC7FG14rMQkeifu8Z7zGy|2022-05-28T11:36:00.441Z; PVUE=08; JSESSIONID=0000sIfLWpzKUVmyyqDpjQ5-TRy:1cgcq4nr3; trdipcktrffcext=1"

headers = {
    "Cookie": cookie,
    "Host": "apps.gwinnett.k12.ga.us",
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.64 Safari/537.36 Edg/101.0.1210.53",
}

r = requests.get(
    "https://apps.gwinnett.k12.ga.us/dca/student/dashboard", headers=headers
)
print(r.text)
```

- 也可以通过构建 `RequestCookieJar` 或者直接传入字典的方式来传入 `cookie`。代码中展示的方式，任意一个都可以用来实现对 `cookie` 的管理。

```python
import requests
from requests import cookies

cookies_str = "ai_user=XlC7FG14rMQkeifu8Z7zGy|2022-05-28T11:36:00.441Z; PD-H-SESSION-ID=1_OzaWfIiFIEqhAXIX2zYDL4ftOat8Z10EGLtJN9aaMsBJVEGHhOM=_AAAAAAA=_anGTbqr8zIwAhkILKFPTiB5wqEE=; PD_STATEFUL_1f058092-232b-11ec-bfa4-001a4a16017e=%2Fspvue; ASP.NET_SessionId=axgiishraj2xnvbbqqe5slev; PVUE=08; JSESSIONID=0000sIfLWpzKUVmyyqDpjQ5-TRy:1cgcq4nr3; trdipcktrffcext=1"

# Pass dict directly is accepted
jar = {k: v for k, v in (cookie.split("=", 1) for cookie in cookies_str.split(";"))}

# RequestCookieJar.set
jar = cookies.RequestsCookieJar()
for cookie in cookies_str.split(";"):
    k, v = cookie.split("=", 1)
    jar.set(k, v)

# Wrap in cookie jar
jar = cookies.cookiejar_from_dict(jar)

headers = {
    "Host": "apps.gwinnett.k12.ga.us",
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.64 Safari/537.36 Edg/101.0.1210.53",
}

r = requests.get(
    "https://apps.gwinnett.k12.ga.us/dca/student/dashboard",
    cookies=jar,
    headers=headers,
)

print(r.text)
```

#### 会话

- 我们使用 `get` 和 `post` 方法，实际上是使用多个浏览器打开不同的页面。这要求我们手动的在多次调用之间同步 cookies，比较繁琐。
- 为了减轻这么做的负担，我们可以维持一个会话，为此，不用担心 `cookies` 的问题。

```python
import requests

with requests.Session() as s:
    s.get("https://httpbin.org/cookies/set/number/123456789")
    r = s.get("https://httpbin.org/cookies")
    print(r.text)
```

- 我们发现，`cookies` 在第一次请求服务器时得到的，保留了下来。

#### SSL

- 如果一个网站的 `SSL` 证书有问题，或者是自签发的，在使用 `get` 方法时就会出现 `SSLError`
- 为了避免这个问题，在 `get` 方法传入 `verify=False` 即可。这会造成警告
  - 通过 `urllib3.disable_warnings()` 的方式来关闭警告
  - `logging.captureWarning(True)` 捕获警告到日志来忽略
  - 或者指定本地证书作为客户端证书。可以是单个文件，包含密钥和证书；也可以是一个（证书路径，密钥路径）的元组。这要求获得 `crt` 和 `key` 文件，并且他们必须是解密的

```python
import requests
import urllib3
import logging

logging.captureWarnings(True)
urllib3.disable_warnings()

response = requests.get('https://www.12306.cn', verify=False) # cert=('path/server.crt', 'path/key')
print(response.status_code)
```

#### 代理

- 可以直接向 `requests.get` 方法传入代理

```python
import requests

proxies = {
    "https":"http://10.10.1.10:1080",
    "http": "http://10.10.1.10:3128"
}
requests.get("https://www.taobao.com", proxies=proxies)
```

- 通过安装 `requests[socks]`，还可以支持使用 `socks` 协议代理。

#### 验证

- 可以直接传入 `auth=(user, passwd)` 的方式来进行验证
  - 也可以传入一个 `HTTPBasicAuth` 类对象来进行验证
- 可以通过 `https://user:passwd@example.com` 的方式进行验证

```python
import requests
from requests.auth import HTTPBasicAuth

r = requests.get('https://www.google.com', auth=HTTPBasicAuth('user', 'passwd'))
# Or, requests.get('https://www.google.com', auth=('user', 'passwd'))
print(r.status_code)
```

- 甚至可以使用 `OAuth` 。通过安装 `requests_oauthlib` 进行。见 https://github.com/requests/requests-oauthlib

#### 超时

- 设置 `timeout` 参数来进行超时设置
  - 可以是一个浮点数，也就是连接服务器和读取内容时间的总和不超过这个时间
  - 可以是一个元组，（连接时间，读取时间）
  - 如果希望永久等待，可以不设置或者设置为 `None`。这样，永远不会发生超时错误。

#### Prepared Request

```python
from requests import Request, Session

url = "http://httpbin.org/post"
data = {"name": "yubo", "age": 16}
headers = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.64 Safari/537.36 Edg/101.0.1210.53"
}

with Session() as s:
    req = Request("POST", url, data=data, headers=headers)
    prep = s.prepare_request(req)
    r = s.send(prep)
    print(r.text)
```
