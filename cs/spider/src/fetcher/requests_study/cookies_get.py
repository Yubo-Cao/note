from email import header
import requests

cookie = "ai_user=XlC7FG14rMQkeifu8Z7zGy|2022-05-28T11:36:00.441Z; PD-H-SESSION-ID=1_OzaWfIiFIEqhAXIX2zYDL4ftOat8Z10EGLtJN9aaMsBJVEGHhOM=_AAAAAAA=_anGTbqr8zIwAhkILKFPTiB5wqEE=; PD_STATEFUL_1f058092-232b-11ec-bfa4-001a4a16017e=%2Fspvue; ASP.NET_SessionId=axgiishraj2xnvbbqqe5slev; PVUE=08; JSESSIONID=0000sIfLWpzKUVmyyqDpjQ5-TRy:1cgcq4nr3; trdipcktrffcext=1"

headers = {
    "Cookie": cookie,
    "Host": "apps.gwinnett.k12.ga.us",
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.64 Safari/537.36 Edg/101.0.1210.53",
}

r = requests.get(
    "https://apps.gwinnett.k12.ga.us/dca/student/dashboard", headers=headers
)
print(r.text)
