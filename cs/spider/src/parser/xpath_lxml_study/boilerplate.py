from lxml import etree as __etree

def xpath_finder(xpath:str, print_result:bool = True, file_name: str= 'parser/example.html'):
    try:
        with open(file_name) as f:
            html = __etree.HTML(f.read())
    except IOError as e:
        print(f'{e!r}') 
    result = html.xpath(xpath)
    if print_result:
        print(result)
    return result