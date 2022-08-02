import requests

r = requests.get("https://www.github.com/favicon.ico")
# print(r.text)
# print(r.content)
with open("github.ico", "w+b") as f:
    f.write(r.content)
