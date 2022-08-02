import time
from base import *

with root() as root:
    progress = DoubleVar()
    p = ttk.Progressbar(root, orient=HORIZONTAL,
                        length=200, mode='determinate', maximum=100.0, variable=progress)
    p2 = ttk.Progressbar(root, orient=VERTICAL,
                         length=200, mode='indeterminate')
    p.grid(column=0, row=0)
    p2.grid(column=0, row=1)

    p.start()
    p2.start()
