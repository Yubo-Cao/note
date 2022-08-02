from base import *

with root() as root:
    p = ttk.PanedWindow(root, orient=VERTICAL)
    f1 = ttk.LabelFrame(p, text="Pane1", width=100, height=100)
    f2 = ttk.LabelFrame(p, text="Pane2", width=100, height=100)
    p.add(f1)
    p.add(f2)
    p.grid()
