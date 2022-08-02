import requests

r = requests.get("https://www.jianshu.com")
attrs = ["status_code", "headers", "cookies", "url", "history"]
for t, attr in zip(
    map(lambda attr: repr(type(getattr(r, attr))), attrs),
    map(lambda attr: getattr(r, attr), attrs),
):
    print(t.ljust(40), attr)
