from lxml import etree

with open("parser/example.html") as example:
    html = etree.HTML(example.read())
    result = etree.tostring(html)
    print(result.decode("utf-8"))
