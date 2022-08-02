from lxml import etree

html = etree.parse('parser/example.html', etree.HTMLParser())
result = html.xpath('//ul//a')
print(result)