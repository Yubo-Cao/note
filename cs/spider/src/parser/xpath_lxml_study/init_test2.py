from lxml import etree

html = etree.parse("parser/example.html", etree.HTMLParser(encoding="utf-8"))
result = etree.tostring(html)
print(result.decode("utf-8"))
