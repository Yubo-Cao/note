import requests

url = "https://roambarcelona.com/clock-pt{num}?verify=Na2Q%2BeqhSP5hTRLDwpTNoA%3D%3D"
trgt = "https://roambarcelona.com/get-flag?verify=Na2Q%2BeqhSP5hTRLDwpTNoA%3D%3D&string={code}"

# query each url, obtain code, and validate if correct by passing the result to string
# and query trgt
code = ""
for num in range(1, 6):
    lcode = requests.get(url.format(num=num)).text
    code += lcode
    
print(requests.get(trgt.format(code=f'{code}')).text)

    
