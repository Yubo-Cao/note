from bs4 import BeautifulSoup

with open('parser/example2.html') as html:
    soup = BeautifulSoup(html, 'lxml')
    print(soup.prettify())
    print(soup.title.string)