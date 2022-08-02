from base import *

with root() as root:
    num = StringVar()
    ttk.Label(root, textvariable=num).grid(
        row=0, column=0, sticky='we')  # auto
    manual = ttk.Label(root)
    manual.grid(column=0, row=1, sticky='we')

    def update_lb(val):
        manual['text'] = f"Scale at {val}"
    s = ttk.Scale(root, orient=HORIZONTAL, length=200, from_=1.0,
                  to=100.0, variable=num, command=update_lb)
    s.grid(column=0, row=2, sticky='we')
    s.set(20)
    root.columnconfigure(0, weight=1)
    print(s.get())