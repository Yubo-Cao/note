---
date created: 2022-05-23 11:27
date updated: 2022-05-23 11:59
---

# 爬虫基础

Learn some basics about HTTP, HTML, and Cookies, before start our journey.

## HTTP

### URI and URL

- URI is _Uniform Resource Identifier_, 统一资源标志符
  - URL is _Universal Resource Locator_, 统一资源定位符
    - `https://github.com/index.hmtl`
    - 上述 `URL` 包括访问协议，路径，以及域名等等
  - URN is _Universal Resource Name_, 统一资源名称
    - URN 只命名资源但是不指定如何定位资源，例如 `urn: isbn:0000`
    - 目前来说, URN 使用的
  - $URL \cup URN = URI$

## Hypertext

- 我们在浏览器看到的网页都是由超文本，hypertext 解析而得到
  - 网页源代码是一系列 HTML 代码，其中包含一系列标签，例如
    - `img` 显示图片
    - `p` 显示段落
  - 浏览器解析 `xml` 形式的超文本，渲染得到网页。点击检查/或者 F12 打开开发者工具用于查看网页的源代码，也就是超文本

### Http & Https

- URL 开头的部分有 http/https，这被称为是协议类型。除此以外，还有 ftp/sftp/smb 开头的，他们都是协议类型。

#### HTTP

- 超文本传输协议, Hyper Text Transfer Protocol.
- W3C 和 IETF 合作制定了 HTTP 的规范，目前主要使用的为 `http 1.1` 版本。

#### HTTPS

- 安全版的超文本超文本传输协议，Hyper Text Transfer Protocol Over Secure Socket Layer (Transport Layer Security 是 SSL 的替代品)
- 通过 SSL 加密传输
  - 保证信息传输的安全
  - 确认网站的真实性，CA 证书
  - 越来越多的网站使用 HTTPS，但是有些网站虽然使用了 HTTPS 仍然显示不安全，因为有些公司的 CA 证书是自己签发的——因此不受 CA 机构信任。需要设置忽略证书的选项，否则就会 SSL 链接错误。

#### 过程

- 在浏览器中输入一个 URL
- 浏览器向网页所在服务器发送 HTTP 请求
- 服务器处理和解析这个请求，并返回 HTTP, JS, CSS 等内容，然后浏览器再进一步解析呈现内容。

##### 请求

- 请求方法 `Request Method`
  - `GET` 和 `POST`
    - `GET` 请求的参数会直接放入 URL 中，作为 query string 发送。因此，数据可以通过 URL 看见。只能提交 1024 字节的数据。
    - `POST` 请求一般用于表单提交发送。不可在 URL 中看见，数据通过表单形式传输，包含在请求体中。没有大小限制。
      - 使用较为广泛。例如提交密码（避免因为 URL 中存在密码而泄露）和上传文件（文件内容大）
  - 其他方法

| 方法      | 描述                     |
| ------- | ---------------------- |
| GET     | 请求页面内容                 |
| HEAD    | 请求报头                   |
| POST    | 提交表单或者上传文件             |
| PUT     | 客户端向服务器传送数据取代指定文档内容    |
| DELETE  | 从服务器中删除内容              |
| CONNECT | 把服务器当作跳板，让服务器代替客户端访问网页 |
| OPTIONS | 客户端查看服务器性能             |
| TRACE   | 回显服务器收到的请求，用于测试和诊断     |

- 请求网址 `Request URL`
  - 也就是 URL
- 请求头 `Request Headers` **重要内容**
  - 说明服务器要使用的附加信息
  - `Accept`
    - 指明客户端接受什么类型的信息
  - `Accept-Language`
    - 指明客户端接受的语言类型
  - `Accept-Encoding`
    - 指明客户端接受的内容编码
  - `Host`
    - 指定请求资源的主机 IP 和 Port。内容是请求 URL 的原始服务器或者网关的位置。从 HTTP 1.1 开始，必须包含这部分内容。
  - `Cookie` or `Cookies`
    - 网站为了辨别用户进行会话跟踪而存储在用户本地的数据 。 它的主要功能是维持当前访问会话。
      - 我们输入用户名和密码成功登录某个网站后，服务器会用会话保存登录状态信息，后面我们每次刷新或请求该站点的其他页面时，会发现都是登录状态，这就是 Cookies 的功劳 。
      - Cookies 里有信息标识了我们所对应的服务器的会话，每次浏览器在请求该站点的页面时，都会在请求头中加上 Cookies 并将其发送给服务器，服务器通过 Cookies 识别出是我们自己，并且查出当前状态是登录状态，所以返回结果就是登录之后才能看到的网页内容 。
  - `Referer`
    - 内容用来标识这个请求是从哪个页面发过来的，服务器可以拿到这一信息并做相应的处理，如来源统计，防盗链等
  - `User-Agent`
    - 简称 UA ，它是一个特殊的字符串头，可以使服务器识别客户使用的操作系统及版本 、 浏览器及版本等信息 。 在做爬虫时加上此信息，可以伪装为浏览器；如果不加，很可能会被识别州为爬虫。
  - `Content-Type`
    - 也叫互联网媒体类型（ Internet Media Type ）或者 MIME 类型，在 HTTP 协议消息头中，它用来表示具体请求中的媒体类型信息 。 例如， text/html 代表 HTML 格式，image/gif 代表 GIF 图片， application/json 代表 JSON 类型
- 请求体 `Request Body`
  - POST 请求表单中的内容。对于 GET 请求，请求体为空。
  - 表单数据的形式
    - Content-Type
      - `application/x-www-form-urlencoded` 表单数据
      - `multipart/from-data` 上传表单文件
      - `application/json` 序列化 JSON 数据
      - `text/xml` XML 数据
    - 需要使用正确的 `Content-Type`，不然不会正确响应。

#### 响应

- 响应状态码 Response Status Code
  - 表示服务器的响应状态
  - `200` 服务器正常响应
  - `404` 页面未找到
  - `500` 服务器内部错误
  - ![[常见的错误代码以及原因’.png]]
  - ![[Pasted image 20220523115558.png]]
- 响应头 Response Headers
  - 服务器的应答信息
  - `Date` 相应创建时间
  - `Last-Modified` 资源最后修改时间
  - `Content-Encoding` 响应内容编码
  - `Server` 服务器信息，如名称，版本号
  - `Content Type` 同 MIME Type
  - `Set-Cookie` 设置 Cookies. 要求浏览器把内容放到 Cookies 中，下次请求时携带 Cookies
  - `Expires` 指定响应的过期时间，允许浏览器或者代理把家财内容更新到缓存中，减少服务器负载和响应时间
- 响应体 Response Body
  - 响应的正文内容
  - 我们制作爬虫主要关心正文内容：如图片的二进制，HTML 代码，等等。我们大多时候都是堆这部分内容进行解析。

## TCP/UDP/Ports

- Network ports are provided by the **TCP or UDP** protocols at the **Transport layer**.
- Port numbers are used to determine what protocol incoming traffic should be directed to.
- Ports allow a single host with a single IP address to run network services. Each port number identifies a distinct service, and each host can have 65535 ports per IP address.
- Port use is regulated by the **Internet Corporation for Assigning Names and Numbers (ICANN)**. By ICANN there are three categories for ports:

| Port  Number Range | Purpose                                                                                                                                                                                           |
| ------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 0  to 1023         | well  known ports assigned to common protocols and services                                                                                                                                       |
| 1024  to 49151     | registered  ports assigned by ICANN to a specific service                                                                                                                                         |
| 49152  to 65535    | dynamic  (private, high) ports range from 49,152 to 65,535. Can be used by  any service on an ad hoc basis. Ports are assigned when a session  is established and released when the session ends. |

## Well Known Ports

| Port    | Service  Name                                 | Transport  Protocol |
| ------- | --------------------------------------------- | ------------------- |
| 20,21   | File Transfer  Protocol (FTP)                 | TCP                 |
| 22      | Secure Shell (SSH)                            | TCP and UDP         |
| 23      | Telnet                                        | TCP                 |
| 25      | Simple Mail  Transfer Protocol (SMTP)         | TCP                 |
| 50,51   | IPSec                                         | nan                 |
| 53      | Domain Name  Server (DNS)                     | TCP and UDP         |
| 67,68   | Dynamic Host  Configuration Protocol (DHCP)   | UDP                 |
| 80      | HyperText  Transfer Protocol (HTTP)           | TCP                 |
| 110     | Post Office  Protocol (POP3)                  | TCP                 |
| 123     | Network Time  Protocol (NTP)                  | UDP                 |
| 143     | Internet Message  Access Protocol (IMAP4)     | TCP and UDP         |
| 161,162 | Simple Network  Management Protocol (SNMP)    | TCP and UDP         |
| 389     | Lightweight  Directory Access Protocol (LDAP) | TCP and UDP         |
| 443     | HTTP with Secure  Sockets Layer (SSL)         | TCP and UDP         |
