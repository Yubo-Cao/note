import requests

r = requests.get("https://www.google.com")
if r.status_code == requests.codes.ok:
    print("Ok")
else:
    print("Failed")
