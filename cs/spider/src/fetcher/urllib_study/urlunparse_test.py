from urllib.parse import urlunparse

data = ["http", "www.quizlet.com", "index.html", "user", "a=6&b=10", "comment"]
print(urlunparse(data))
