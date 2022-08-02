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
