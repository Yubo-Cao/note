from collections import namedtuple
from pprint import pprint
Student = namedtuple("Student", "name section")
names = "Anderson Brown Davis Garcia Harris Jackson Johnson Jones Martin Martinez Miller Moore Robinson Smith Taylor Thomas Thompson White Williams Willson".split(
    " "
)
values = "2 3 3 4 1 3 4 3 1 2 2 1 2 4 3 4 4 2 3 4".split()
students = [print(f"new Student(\"{names[i]}\",{values[i]}),") for i in range(len(names))]
