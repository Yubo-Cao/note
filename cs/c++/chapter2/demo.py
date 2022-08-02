from tkinter import ttk, Tk
from itertools import product
import sv_ttk

tk = Tk()
sv_ttk.set_theme('light')
root = ttk.Frame(tk)

root.grid(column=0, row=0, sticky="NEWS")
root["padding"] = (16, 16)

[
    widget.grid(row=idx[0], column=idx[1], sticky="NEWS", padx=4, pady=4)
    for widget, idx in zip(
        [
            ttk.Label(root, text="Password:"),
            ttk.Entry(root, show="*"),
            ttk.Label(root, text="Username:"),
            ttk.Entry(root),
        ],
        product(range(2), range(2)),
    )
]
conf = lambda node, idx, **kwargs: (
    node.columnconfigure(idx, weight=1, **kwargs)
    or node.rowconfigure(idx, weight=1, **kwargs)
)
conf(tk, 0)
[conf(root, i, pad=8) for i in range(2)]

tk.mainloop()
