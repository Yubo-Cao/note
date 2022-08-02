from urllib.robotparser import RobotFileParser

r = RobotFileParser('https://www.google.com/robots.txt')
r.read()
print(r.can_fetch('*', 'https://www.google.com/m/finance'))
print(r.can_fetch('*', 'https://www.google.com/search/about'))