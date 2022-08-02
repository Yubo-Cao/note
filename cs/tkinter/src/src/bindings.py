from tkinter import *
from tkinter import ttk

root = Tk()
l = ttk.Label(root, text="Starting...")
l.grid()
l.bind('<Enter>', lambda e: l.config(text="Moved mouse inside"))
l.bind('<Leave>', lambda e: l.config(text="Moved mouse outside"))
l.bind('<ButtonPress-1>', lambda e: l.config(text="Clicked left mouse button"))
l.bind('<MouseWheel>', lambda e: l.config(text="Scroll in here") or print(e.delta))
l.bind('<3>', lambda e: l.config(text="right-clicked"))
l.bind('<Double-1>', lambda e: l.config(text="double cliecked"))
l.bind('<B3-Motion>', lambda e: l.config(text="right button drag to %d, %d" % (e.x, e.y)))



root.mainloop()
