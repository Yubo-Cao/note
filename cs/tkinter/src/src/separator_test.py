from base import *

with root() as root:
    ttk.Label(root, text="Before separator").grid(row=0, column=0)
    ttk.Separator(root, orient=HORIZONTAL).grid(row=1, column=0, sticky='ew')
    ttk.Label(root, text="After separator").grid(row=2, column=0)
    ttk.Separator(root, orient=VERTICAL).grid(row=3, column=0, sticky='ew')
    root.columnconfigure(0, weight=1)
    root.rowconfigure(0, weight=1)
