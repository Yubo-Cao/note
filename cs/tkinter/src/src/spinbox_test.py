from base import *

with root() as root:
    spinval = StringVar()
    s = ttk.Spinbox(root, from_=1.0, to=100.0,
                    textvariable=spinval, increment=0.2)
    s.grid(row=0, column=0)

    s2 = ttk.Spinbox(root, values=[0, 50, 100], textvariable=spinval)
    s2.grid(row=1, column=0)
