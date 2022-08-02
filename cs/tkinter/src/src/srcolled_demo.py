from base import *

with root() as root:
    l = Listbox(root, height=5)
    l.grid(column=0, row=0, sticky=(N, W, E, S))
    s = ttk.Scrollbar(root, orient=VERTICAL, command=l.yview)
    l['yscrollcommand'] = s.set
    ttk.Label(root, text="Status", anchor=(W,)).grid(
        column=0, columnspan=2, row=1, sticky=(W, E))
    root.columnconfigure(0, weight=1)
    root.rowconfigure(0, weight=1)
    for i in range(1, 101):
        l.insert('end', "Line %d of 100" % i)
    l.bind('<Enter>', lambda e:s.grid(column=1, row=0, sticky=(N, S)))
    l.bind('<Leave>', lambda e:s.grid_remove())

    root.mainloop()
