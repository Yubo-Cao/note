from urllib.parse import urljoin

print(
    urljoin(
        "http://www.google.com:80", "index.html"
    ),  # http://www.google.com:80/index.html
    urljoin(
        "http://www.google.com:80", "http://www.baidu.com/index.html"
    ),  # http://www.baidu.com/index.html
    urljoin(
        "http://www.google.com/about.html", "http://www.baidu.com/index.html"
    ),  # http://www.baidu.com/index.html
    urljoin(
        "http://www.google.com/about.html",
        "http://www.baidu.com/FAQ.html;user=10?question=2",
    ),  # http://www.baidu.com/FAQ.html;user=10?question=2
    urljoin(
        "http://www.google.com/about.html;user=10?question=10",
        "www.baidu.com/index.php",
    ),  # http://www.google.com/www.baidu.com/index.php
    urljoin(
        "http://www.google.com/about.html;user=10?question=10", "www.baidu.com"
    ),  # http://www.google.com/www.baidu.com
    urljoin(
        "www.google.com", "?category=2#comment"
    ),  # www.google.com?category=2#comment
    sep="\n",
)
