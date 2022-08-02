import re
from base import *

with root(debug=True) as root:
    def check_num(newval):
        return bool(re.match(r'^\d{,5}$', newval))
    check_num_wrapper = (root.register(check_num), '%P')

    num = StringVar()
    entry = ttk.Entry(root, textvariable=num, validate='key',  # Validate On Key Stroke
                      validatecommand=check_num_wrapper)
    entry.grid(column=0, row=0, sticky='we')
