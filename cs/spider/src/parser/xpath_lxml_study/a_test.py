from base64 import encode
from html.parser import HTMLParser
from lxml import etree

html = etree.parse("parser/example.html", etree.HTMLParser(encoding="utf-8"))
result = html.xpath("//li/a")
print(result)
