from base import *

with root() as root:
    lf = ttk.LabelFrame(root, text="Nuclear Plant", width=100, height=100)
    lnl = ttk.LabelFrame(root, width=100, height=100)
    lf.grid(row=1, column=0)
    lnl.grid(row=0, column=0)
