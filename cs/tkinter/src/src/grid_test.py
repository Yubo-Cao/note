from base import *

with root() as root:
    frame = ttk.Frame(root, borderwidth=5, relief='ridge',
                      width=200, height=100)
    frame.grid(row=0, column=0, rowspan=2, columnspan=3, sticky='news')

    name = ttk.Label(root, text="Name")
    name.grid(row=0, column=3, columnspan=2)

    entry = ttk.Entry(root)
    entry.grid(row=1, column=3, columnspan=2)

    ttk.Checkbutton(root, text="One").grid(column=0, row=2)
    ttk.Checkbutton(root, text="Two").grid(column=1, row=2)
    ttk.Checkbutton(root, text="Three").grid(column=2, row=2)

    ok = ttk.Button(root, text="Okay")
    cancel = ttk.Button(root, text="Cancel")
    
    for i in range(3):
        root.columnconfigure(i, weight=3)    
    for i in range(2):
        root.rowconfigure(i, weight=3)

    ok.grid(row=2, column=3)
    cancel.grid(row=2, column=4)
