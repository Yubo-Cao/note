from lxml import etree

html = etree.parse("parser/example.html", etree.HTMLParser())
result = html.xpath('//li[@class="item-0"]')
print(result)
