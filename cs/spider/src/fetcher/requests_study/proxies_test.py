import requests

proxies = {
    "https":"http://10.10.1.10:1080",
    "http": "http://10.10.1.10:3128"
}
requests.get("https://www.taobao.com", proxies=proxies)