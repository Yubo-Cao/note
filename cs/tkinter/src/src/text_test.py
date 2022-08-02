from base import *

with root() as root:
    t = Text(root, width=40, height=10, wrap='char')
    # t['state'] = 'disabled'  # Not ttk. So no state/instate command
    ys = ttk.Scrollbar(root, orient=VERTICAL, command=t.yview)
    t['yscrollcommand'] = ys.set
    xs = ttk.Scrollbar(root, orient=HORIZONTAL, command=t.xview)
    t['xscrollcommand'] = xs.set
    t.grid(row=0, column=0)
    ys.grid(row=0, column=1, sticky='NS')
    xs.grid(row=1, column=0, sticky='WE')
