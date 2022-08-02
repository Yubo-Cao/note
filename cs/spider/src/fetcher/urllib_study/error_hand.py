from urllib.error import URLError
from urllib.request import urlopen

try:
    response = urlopen("https://www.doesnotexistsreallyitshouldnot.com")
except URLError as e:
    print(e.reason)
