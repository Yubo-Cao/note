# 基本库 - 解析

- 使用正则表达式提取信息是可以的，但是这样的操作繁琐，并且极大的依赖于 HTML 文档结构，产生的爬虫不稳定，不健壮，并且难以维护。
- 为此，使用 XPath, CSS Selector 就是有必要的。

## `XPath`

- 全称为 XML Path Language, 也就是 XML 路径语言。是一门在 XML 文档中查找信息的语言。最初用来搜寻 XML 文档，但是同样适用于 HTML 文档的搜索。

- 可以使用 XPath 进行 HTML 文档的信息抽取。任何想要定位的节点，都可以使用 XPath 来选择。
- 1999 年，成为 W3C 标准，用于给 XSLT, XPointer 等解析软件使用。
- https://www.w3.org/TR/2017/REC-xpath-31-20170321/

### 常用规则

| 表达式     | 描述                     |
| ---------- | ------------------------ |
| `nodename` | 选择这个节点的所有子节点 |
| `/`        | 选择当前节点的直接子节点 |
| `//`       | 选择当前节点的子孙节点   |
| `.`        | 选择当前节点             |
| `..`       | 选择当前节点的父节点     |
| `@`        | 选择属性                 |

### `lxml`

- 使用的样例 `HTML` 见下

```html
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
</head>

<body>
    <div>
        <ul>
            <li class="item-0"><a href="link1.html">first item</a></li>
            <li class="item-1"><a href="link2.html">second item</a></li>
            <li class="item-intactive"><a href="link3.html">third item</a></li>
            <li class="item-1"><a href="link4.html">fourth item</a></li>
            <li class="item-0"><a href="link5.html">fifth item</a>
        </ul>
    </div>
</body>

</html>
```

- 导入模块，生成 `XPath` 解析对象。`lxml` 解析器可以在一定程度上修正 HTML 文本。例如，上面没有闭合的 `</li>` 标签并不会造成解析出现问题。
  - 调用 `toString` 生成修正后的 `HTML` 代码。
  - 返回的是 `bytes` 类型。为此，需要使用 `decode` 方法转化为字符串。
  - 如果没有包含 `html`, `body` 等节点，也会被自动添加。下面的案例还可以更简单一点，直接传入 `HTML` 文档的文件名即可。

```python
from lxml import etree

with open("parser/example.html") as example:
    html = etree.HTML(example.read())
    result = etree.tostring(html)
    print(result.decode("utf-8"))
```

```python
from lxml import etree

html = etree.parse("parser/example.html", etree.HTMLParser(encoding="utf-8"))
result = etree.tostring(html)
print(result.decode("utf-8"))
```

### 使用

#### 所有节点

```python
html = etree.parse("parser/example.html", etree.HTMLParser(encoding="utf-8"))
result = html.xpath("//*")
print(result)
```

- `*` 匹配所有节点，`//` 匹配所有子孙节点。
- 返回一个列表，每个列表都是 `Element` 类型，后面跟随节点的名称。

#### 节点名称

```python
html = etree.parse("parser/example.html", etree.HTMLParser(encoding="utf-8"))
result = html.xpath('//li')
print(result)
print(result[0])
```

- 选取所有的 `li` 节点，同样，返回一个列表。通过索引取出元素

#### 子节点

- 通过 `/` 来查找元素的子节点。通过 `//` 来查找元素的子孙节点。
- 选择所有 `li` 节点的子节点 `a`。

```python
from html.parser import HTMLParser
from lxml import etree

html = etree.parse("parser/example.html", etree.HTMLParser(encoding="utf-8"))
result = html.xpath("//li/a") # 使用 ul/a 不会产生结果。没有直接 a 节点
print(result)
```

- 直接获取所有 `ul` 节点的子孙节点中的 `a`
- 不过，使用 `//ul/a` 什么都不会发生，因为其直接子节点没有 `a`

```python
result = html.xpath('//ul//a')
```

#### 父节点

- 通过 `..` 来获取父节点
- 首先选中`href` 属性为 `link4.html` 的 `a` 节点，然后获取其父节点的 `class` 属性的选择器如下

```python
result = html.xpath('//a[@href="link4.html"]/../@class')
print(result)
```

- 也可以使用 `parent` 轴来在 XML 文档树中导航。

```python
xpath('//a[@href="link4.html"]/parent::*/@class')
```

#### 节点轴

| Axis Name          | Result                                                       |
| :----------------- | :----------------------------------------------------------- |
| ancestor           | Selects all ancestors (parent, grandparent, etc.) of the current node |
| ancestor-or-self   | Selects all ancestors (parent, grandparent, etc.) of the current node and the current node itself |
| attribute          | Selects all attributes of the current node                   |
| child              | Selects all children of the current node                     |
| descendant         | Selects all descendants (children, grandchildren, etc.) of the current node |
| descendant-or-self | Selects all descendants (children, grandchildren, etc.) of the current node and the current node itself |
| following          | Selects everything in the document after the closing tag of the current node |
| following-sibling  | Selects all siblings after the current node                  |
| namespace          | Selects all namespace nodes of the current node              |
| parent             | Selects the parent of the current node                       |
| preceding          | Selects all nodes that appear before the current node in the document, except ancestors, attribute nodes and namespace nodes |
| preceding-sibling  | Selects all siblings before the current node                 |
| self               | Selects the current node                                     |

- 按照这样的格式写 `axisname::nodetest[predicate]`

```python
xpath(
    "//li[1]/ancestor::*"
)  # [<Element html at 0x23e7cacd0c0>, <Element body at 0x23e7cb06a80>, <Element div at 0x23e7ca78140>, <Element ul at 0x23e7ca79500>]
xpath("//li[1]/ancestor::div")  # [<Element div at 0x23e7cb05b00>]
xpath("//li[1]/attribute::*")  # ['item-0']
xpath('//li[1]/child::a[@href="link1.html"]')  # [<Element a at 0x23e7cb053c0>]
xpath("//li[1]/descendant::span")  # []
xpath("//li[1]/following::*[2]")  # [<Element a at 0x23e7afc0c40>]
xpath("//li[1]/following-sibling::*[2]")  # [<Element li at 0x23e7cacdc80>]
```

#### 属性过滤

- 使用 `@` 符号可以对属性进行过滤。例如，选取 `class=item-0` 的列表元素

```python
from lxml import etree

html = etree.parse("parser/example.html", etree.HTMLParser())
result = html.xpath('//li[@class="item-0"]')
print(result)
```

#### 文本获取

- 使用 `text()` 方法获取节点中的文本
- 我们直接选中 `li` 节点，并且获取其中的文本。返回的是一个只有一个元素的列表——一个换行符。
  - 因为自动修复在换行后加入了 `</li>` 
  - 不会选中子元素的文字


```python
xpath('//li[@class="item-0"]/text()')
# ['\r\n        ']
```

- 选中 `a` 节点来获得其中的文本。

```python
xpath('//li[@class="item-0"]/a/text()')
# ['first item', 'fifth item']
```

- 选择所有子孙节点的文本
  - 多定位几层再写 `text()` 是一个更好的办法。直接这么干效果不好。

```python
xpath('//li[@class="item-0"]//text()')
# ['first item', 'fifth item', '\r\n        ']
```

#### 属性获取

- 获取所有 `li` 节点下 `a` 节点的属性。

```python
xpath('//li/a/@href')
# ['link1.html', 'link2.html', 'link3.html', 'link4.html', 'link5.html']
```

#### 多值属性匹配

- 考虑一个包含多个值的属性

```html
<div class="row container" style="display: flex;">
    <div class="column left" style="flex: 50%;">
        第一列
    </div>
    <div class="column right" style="flex: 50%;">
        第二列
    </div>
</div>
```

- 选择内部左侧列。我们发现，什么都没有选中。

```python
xpath('//div[@class="left"]/text()')
# []
```

- 为了解决这个问题，就需要使用 `contains()` 函数。然后就好了。

```python
xpath('//div[contains(@class, "left")]/text()')
# ['\n                    第一列\n                    ']
```

- `class` 的属性通常有多个，因此往往需要使用 `contains`

#### 布尔运算

- 可以使用 `and` 来连接多个属性

```python
xpath('//div[contains(@class, "left") and contains(@class, "column") and @style="flex: 50%;"]/text()')
# ['\n                    第一列\n                    ']
```

- `XPath` 还有很多别的运算符号
  - `or`, `and` 或者以及与
  - `|` 节点集的并集
  - `mod` 余数
  - `+`, `-`, `*`, `div` 加减乘除
  - `=`, `!=` 等于和不等于
  - `<`, `<=`, `>`, `>=` 小于，小于等于，大于，大于等于
  - `[]` 节点以列表方式返回，可使用索引访问

#### 按序选择

```python
xpath("//li[1]/a/text()")  # ['first item']
xpath("//li[last()]/a/text()")  # ['fifth item']
xpath("//li[position()<3]/a/text()")  # ['first item', 'second item']
xpath("//li[last()-2]/a/text()")  # ['third item']
```

- `xpath` 中的列表是从 1 开始计算的。

## Beautiful Soup

- 简单来说， Beautiful Soup 就是 Python 的一个 HTML 或 XML 的解析库，可以用它来方便地从网页中提取数据 。
- Beautiful Soup 依赖于解析器；除了支持标准库中的解析器，还支持 `lxml` 等第三方解析器

| 解析器               | 使用方法                               | 优 势                                                        | 劣势                                                    |
| -------------------- | -------------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------- |
| Python 标准库        | `BeautifulSoup(markup, "html.parser")` | `Python`的内置标准库、执行速度适中、文档容错能力强           | `Python 2.7.3`及`Python 3.2.2` 之前的版本文档容错能力差 |
| `lxml` `HTML` 解析器 | `BeautifulSoup(markup, "1xml")`        | 速度快、文档容错能力强                                       | 需要安装 C 语言库                                       |
| `lxml` `XML`解析器   | `BeautifulSoup(markup, "xml")`         | 速度快、唯一支持XML的 解析器                                 | 需要安装 C 语言库                                       |
| `html5lib`           | `BeautifulSoup(markup, "html5lib")`    | 最好的容错性、以浏览器的方式解析; 文档、生成HTML5 格式的文档 | 速度慢、不依赖外部扩展                                  |

- 推荐使用 `lxml` ，必要时，使用 `html.parser`
- 节点筛选能力弱，但是选择速度快
- `find()` 之类的方法多用
- `CSS` 选择器挺好使的

### 用例

我们将会使用如下的例子文件：

```html
<html>

<head>
    <title>The Dormouse's story</title>
    <meta charset="utf-8">
</head>
<p class="title" name="dormouse">
    <b>The Dormouse's story</b>
</p>
<p class="story">Once upon a time there were three little sisters; and their names were
    <a href="http://example.com/elsie" class="sister" id="link1">
        <!-- Elsie -->
    </a>,
    <a href="http://example.com/lacie" class="sister" id="link2">Lacie</a> and
    <a href="http://example.com/tillie" class="sister" id="link3">Tillie</a>; and they lived at the bottom of a well.
</p>
<p class="story">...</p>

</html>
```

在方法选择器部分，使用如下样例：

```html
<div class="panel">
    <div class="panel-heading">
        <h4>Hello</h4>
    </div>
    <div class="panel-body">
        <ul class="list" id="list-1">
            <li class="element">Foo</li>
            <li class="element">Bar</li>
            <li class="element">Jay</li>
        </ul>
        <ul class="list list-small" id="list-2">
            <li class="element">Foo</li>
            <li class="element">Bar</li>
        </ul>
    </div>
</div>
```

### 导入

```python
from bs4 import BeautifulSoup

with open('parser/example2.html') as html:
    soup = BeautifulSoup(html, 'lxml')
    print(soup.prettify())
    print(soup.title.string)
```

- `BeautifulSoup` 可以自动更正 `HTML` 文本中的错误之处
- `BeautifulSoup` 可以格式化 `HTML` 字符串，调用 `prettify`
- 通过加点的方式，我们可以访问元素的子元素。最终，调用 `string` 就可以的到其内部的文本了。

### 选择元素

- 通过不断加点的方式来选择子元素——也就是访问属性。
- 访问的属性实际上返回 `bs4.element.tag` 元素，这种元素可以如法炮制来不断访问其子元素（嵌套选择）。
  - 这个元素是 `Beautiful Soup` 中很重要的一种元素
  - 如果有多个节点可以被选中，只选择到第一个匹配的节点，后面的都会忽略

```python
print(
    soup.title, # <title>The Dormouse's story</title>
    type(soup.title), # <class 'bs4.element.Tag'>
    soup.title.string, # <class 'bs4.element.Tag'>
    soup.head, # <head><title>The Dormouse's story</title><meta charset="utf-8"/></head>
    soup.p, # <p class="title" name="dormouse"><b>The Dormouse's story</b></p>
    sep = '\n'
)
```

#### `tag` 元素的常用属性

`name` 获取节点名称

```python
print(soup.title.name) # title
```

`attrs` 是一个字典，可以获取节点的属性。

- 可以直接访问字典来获得属性
- 可以直接对 `tag` 进行 `__getitem__`
  - 需要注意的是，有些属性是列表，如 `class`

```python
print(soup.p.attrs) # {'class': ['title'], 'name': 'dormouse'}
print(soup.p.attrs['name']) # dormouse
print(soup.p['name']) # dormouse
```

`string` 获取节点中的文本内容。这不包括其内部节点的文本。

```python
print(soup.p.find_next_sibling('p').string) # None
```

`text` 获取节点以及其子节点中的文本内容。

```python
print(soup.p.find_next_sibling('p').text) # 一堆文本
```

### Axes 导航

#### 子节点和子孙节点

- `contents` 返回所有子节点，以及子节点之间的文本。

  - ```python
    ['Once upon a time there were three little sisters; and their names were\n    ',
     <a class="sister" href="http://example.com/elsie" id="link1">
     <!-- Elsie -->
     </a>,
     ',\n    ',
     <a class="sister" href="http://example.com/lacie" id="link2">Lacie</a>,
     ' and\n    ',
     <a class="sister" href="http://example.com/tillie" id="link3">Tillie</a>,
     '; and they lived at the bottom of a well.\n']
    ```

  - 所有都是直接子节点。孙子节点，以及更深层级的节点并不会被单独选出。

- `children` 可以得到类似的结果，不过是一个迭代器

  - ```python
    for i, child in enumerate(story.children):
        print(i, repr(child).replace("\n", ""), sep=" | ")
    print(type(story.children))
    ```

  - ```python
    0 | 'Once upon a time there were three little sisters; and their names were\n    '
    1 | <a class="sister" href="http://example.com/elsie" id="link1"><!-- Elsie --></a>
    2 | ',\n    '
    3 | <a class="sister" href="http://example.com/lacie" id="link2">Lacie</a>
    4 | ' and\n    '
    5 | <a class="sister" href="http://example.com/tillie" id="link3">Tillie</a>
    6 | '; and they lived at the bottom of a well.\n'
    <class 'list_iterator'>
    ```

  - 返回的东西都是一样的

- `descendants` 可以遍历所有的子孙节点

  - ```python
    for i, child in enumerate(story.descendants):
        print(i, repr(child).replace("\n", ""), sep=" | ")
    ```

  - ```python
    0 | 'Once upon a time there were three little sisters; and their names were\n    '
    1 | <a class="sister" href="http://example.com/elsie" id="link1"><!-- Elsie --></a>
    2 | '\n'
    3 | ' Elsie '
    4 | '\n'
    5 | ',\n    '
    6 | <a class="sister" href="http://example.com/lacie" id="link2">Lacie</a>
    7 | 'Lacie'
    8 | ' and\n    '
    9 | <a class="sister" href="http://example.com/tillie" id="link3">Tillie</a>
    10 | 'Tillie'
    11 | '; and they lived at the bottom of a well.\n'
    ```

  - 返回 `NavigableString`, `Comment`, 以及 `Tag` 对象

  - 递归的生成器，因此，会把父元素以及其子元素一层一层遍历下去，遍历到最深之后再回来遍历父节点。

- `parent` 节点元素的父节点

  - ```python
    print(soup.a.parent)
    ```

  - 得到的节点就是 `p` 节点，也就是 `story` 节点。

- `parents` 所有父节点（祖先节点）

  - ```python
    print(
        type(soup.a.parent),
        *[
            f"{num:<2} | " + parent.name
            for num, parent in enumerate(soup.a.parents)
        ],
        sep='\n'
    )
    ```

  - 打出来的就是这群父节点元素

  - ```python
    <class 'bs4.element.Tag'>
    0  | p
    1  | body
    2  | html
    3  | [document]
    ```

#### 兄弟节点

- `next_sibling`, `previous_sibling`, `next_siblings`, `previous_siblings`

  - 获得兄弟节点

  - ```python
    print("Next sibling", soup.a.next_sibling, sep="\n")
    print("Previous sibling", soup.a.previous_sibling, sep="\n")
    print("Next siblings", *gen_formatter(soup.a.next_siblings), sep="\n")
    print("Previous siblings", *gen_formatter(soup.a.previous_siblings), sep="\n")
    ```

  - ```python
    Next sibling
    ,
        
    Previous sibling
    Once upon a time there were three little sisters; and their names were
        
    Next siblings
    1  | ,
        
    2  | a
    3  |  and
        
    4  | a
    5  | ; and they lived at the bottom of a well.
    
    Previous siblings
    1  | Once upon a time there were three little sisters; and their names were
    ```

  - 和其他 `tag` 获取信息的方法一样，我们也可以如法炮制来获取相邻节点的信息。只不过，`NavigableString` 需要注意一下。

### 方法选择器

#### `find_all`

- 查询所有符合条件的元素。传入一些属性或者文本，就可以得到符合条件的元素。
- `find_all(name, attrs, recursive, text, **kwargs)`

- 选择所有 `ul` 节点

  - ```python
    print(soup.find_all(name="ul"))  # 返回 ul 列表
    print(type(soup.find_all(name="ul")[0]))  # bs4.element.Tag
    print(soup.find_all("does-not-exist"))  # 返回空列表
    ```

- 选择所有 `ul` 节点内部的 `li` 节点

  - ```python
    for ul in soup.find_all(name="ul"):
        print(ul.find_all(name="li"))
    ```

- 因为返回的结果是列表类型，内部的所有元素还是 `Tag`，我们还可以进一步对内部元素调用方法。

  - ```python
    for ul in soup.find_all(name='ul'):
        for li in ul.find_all(name='li'):
            print(li.string)
    ```

- `attrs` 传入一些属性的字典来进行查询

  - ```python
    print(soup.find_all(attrs={"id": "list-1"}))
    print(soup.find_all(attrs={"name": "elements"}))
    ```

    - 返回结果为所有包含属性的 `tag`

- 我们也可以直接用 `kwargs` 的方式来对属性进行过滤。

  - ```python
    print(soup.find_all(id="list-1"))
    print(soup.find_all(class_="element"))
    ```

- `text` 匹配节点的文本内容。接受字符串，或者正则表达式对象。

  - ```python
    import regex as re
    
    print(soup.find_all(text="Foo"))  # NavigableString
    print([nvi.parent for nvi in soup.find_all(text=re.compile(r"\w{3}", re.I))])  # tag
    ```

#### `find`

- 返回第一个匹配的元素（单个元素），而非所有匹配元素组成的列表。方法签名和 `find_all` 基本一致。

- ```python
  print(soup.find(name="ul"))
  print(soup.find(class_='list'))
  ```

#### 类似的方法

- `find_parents`, `find_parent` 在自己的父节点以及祖先节点中寻找一个或多个节点满足条件。
- `find_next_siblings`, `find_next_siblings` 寻找后面的兄弟节点，或后面所有的兄弟节点
- `find_previous_siblings`, `find_previous_sibsling` 寻找前面的兄弟节点
- `find_all_next`, `find_next`, `find_all_previous`, `find_previous` 寻找前面或后面符合条件的节点。

### `CSS` 选择器

- 调用 `select` 方法并传入 `CSS` 选择器就可以选择元素了

```python
print(
    soup.select(".panel > .panel-heading"),
    soup.select("ul li"),
    soup.select("#list-2 .element"),
    type(soup.select("ul")[0]),
    sep="\n",
)
```

- 因为返回的是 `bs4.element.Tag` 类型的对象。上述从 `tag` 元素中获取属性的所有方法都可以照旧工作。

## `pyquery`

- 一个和 `jQuery` 类似的网页解析器

### 用例

我们使用这个网页作为用例

```html
<div>
    <ul>
         <li class="item-0">first item</li>
         <li class="item-1"><a href="link2.html">second item</a></li>
         <li class="item-0 active"><a href="link3.html"><span class="bold">third item</span></a></li>
         <li class="item-1 active"><a href="link4.html">fourth item</a></li>
         <li class="item-0"><a href="link5.html">fifth item</a></li>
     </ul>
 </div>
```

有时，我们也会直接使用网址

### 导入

直接传入 `HTML` 文本

```python
from pyquery import PyQuery as pq
doc = pq(html) # 传入 html 文本
print(doc('li')) # 返回仍然是 pyquery.pyquery.PyQuery 对象
```

也可以直接使用 `url` 来初始化

```python
doc = pq(url="https://www.google.com")
print(doc("title"))
```

或者直接传入文件名

```python
doc = pq(filename=r"parser\example4.html")
print(doc("title"))
```

**最常用的还是直接传字符串**

### `CSS` 选择器

- 直接把 `PyQuery` 对象当作 `Callable` 调用并传入 `CSS` 选择器即可。
  - 返回的对象仍然是 `PyQuery` 类型

```python
print(doc('#container .list li'))
```

### 父子，兄弟节点

- 同样的，使用 `CSS` 选择器来选择这些节点
- `find` 传入 `CSS` 选择器可以获得所有的子孙节点

```python
items = doc('.list')
print(type(items))
print(items)
lis = items.find('li')
print(type(lis))
print(lis)
```

- `children` 可以获得所有的直接子节点

```python
items.children('a') # 什么都找不到
items.find('a') # 找到了所有内部的 a
```

- `parent` 获得直接父节点

```python
container = items.parent()
print(type(container), container, sep="\n")
```

```python
items.find('a').parent('.active') # 甚至可以用在多个对象的 PyQuery 身上
```

- `parents` 获取祖先节点

```python
items.find('a').parents('div#container') # 去重，只留下一个 container 节点
```

- `siblings` 可以获取所有的兄弟节点（包括在自己之前和自己之后的）

```python
li = doc('.list .item-0.active')
print(li.siblings())
```

- `items` 返回一个可以用来遍历内部对象的生成器，每一个元素都是 `PyQuery` 类型的。特别的，如果不调用这个方法直接遍历，得到的是 `lxml.etree` 对象

```python
for sib in li.siblings().items():
    print(sib)
```

### 提取信息

- `attr()` 接受属性名称，返回属性值

```python
a = doc(".item-0.active a")
print(a, type(a), a.attr("href"))
```

- 当包含多个节点时，返回第一个节点的属性。为此，使用遍历的方式来解决这个问题。

```python
a = doc("a")
print(a.attr("href")) # link2.html
print(a.attr.href) # link2.html
```

- 调用 `text()` 方法来获取节点中的文本。这回忽略掉节点内部包含的 `HTML` 而直接返回纯文字内容。
- 调用 `html()` 方法来或缺节点内部的 `HTML` 文本。

```python
a = doc('.item-0.active a')
print(a)
print(a.text())
print(a.html())
```

- 如果包含多个节点，那么 `text()` 返回所有节点内部的纯文本并以空格分开，`html()` 只返回第一个节点的 HTML 文本。

### 修改节点

#### `addClass` & `removeClass`

```python
li = doc('.item-0.active')
print(li)
li.remove_class('active')
print(li)
li.add_class('active')
print(li)
```

- 动态的改变 `class` 属性

#### `attr`, `text` 和 `html`

- 如果给这些方法传入一个参数，那么就可以改变节点中的内容为传入的字符串。

```python
li = doc('.item-0.active')
print(li)
li.attr('id', 'link')
print(li)
li.text('changed item')
print(li)
li.html('<strong>changed item</strong>')
print(li)
# <li class="item-0 active" id="link"><strong>changed item</strong></li>
```

- 特别的，这种改变会直接映射到其父元素，甚至文档中。

#### `remove`, `prepend`, `append`, `empty`

- 操作节点

```python
html = '''
<div class="container">
Hello, World<span>This is trash.</span>
</div>
'''
doc = pq(html)
con = doc('.container')
print(con.text())
con.find('span').remove() # 返回移除的元素
print(con.text())
con.append(pq('<span>This is useful.</span>'))
con.prepend(pq('<span>This is some useful header.</span>'))
print(doc)
con.empty()
print(doc)
```

#### 伪类选择

```python
print(
    doc("li:first-child"), # 第一个 li 节点
    doc("li:last-child"), # 最后一个 li 节点
    doc("li:nth-child(2)"), # 第二个 li 节点
    doc("li:gt(2)"), # 第三个 li 节点之后的节点
    doc("li:nth-child(2n)"), # 偶数节点
    doc("li:contains(second)"), # 包含 second 文本的节点
    sep="\n",
)
```

- `CSS3` 的伪类选择器还是很好用的



### 
