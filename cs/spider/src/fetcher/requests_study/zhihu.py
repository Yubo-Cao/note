import requests
import regex as re

headers = {
    "User-Agent": "Mozilla/5.0 (X11; Linux x86_64; rv:100.0) Gecko/20100101 Firefox/100.0"
}

r = requests.get("https://www.zhihu.com/explore", headers=headers)

# An failed attempt to use regex.
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