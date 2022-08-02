from urllib.parse import urlparse

res = urlparse("http://www.quizlet.com/index.html;user;domain?id=15#comment")
print(type(res), res)
