from urllib.error import URLError
import requests


def fetch_one(url: str, session: requests.Session):
    headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.64 Safari/537.36 Edg/101.0.1210.53",
        "Accept-Language": "Accept-Language: zh,en-GB;q=0.9,en;q=0.8,en-US;q=0.7",
    }
    cookies = {
        k: v
        for k, v in (
            cookie.split("=", 1)
            for cookie in "__mta=147749768.1653779114513.1653779223010.1653779303086.8; uuid_n_v=v1; uuid=9CCAB590DEDA11ECAD698F35F4537E4C97952BDC18714205A62038DD6ADA57C6; _csrf=ffb954e07ebb5124a2aad0e86fc6288915cb0930e5ea86290ab100a6a7e26b53; ai_user=9+Pxu7uRtww5D5pgFFqBOw|2022-05-28T23:05:06.964Z; trdipcktrffcext=1; _lxsdk_cuid=1810cea9660c8-08c3aed8c11fb9-4c647e53-1fa400-1810cea9660c8; _lxsdk=9CCAB590DEDA11ECAD698F35F4537E4C97952BDC18714205A62038DD6ADA57C6; __mta=147749768.1653779114513.1653779114513.1653779114513.1; _lx_utm=utm_source%3Dgoogle%26utm_medium%3Dorganic; _lxsdk_s=1810cea9661-14d-f6f-03b%7C%7C27".split(
                ";"
            )
        )
    }
    try:
        response = session.get(url, headers=headers, cookies=cookies, verify=False)
    except URLError as e:
        print(e.reason)
    else:
        if response.status_code == 200:
            return response.text
    raise URLError(f"Unable to fetch website. {url}")


with requests.Session() as s:
    print(
        fetch_one(
            "https://www.maoyan.com/board/4?timeStamp=1653779422080&channelId=40011&index=5&signKey=6a0b48fc74d929f65a6d3bb9e04b262d&sVersion=1&webdriver=false",
            s,
        ),
    )
