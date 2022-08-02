from tkinter import *
from tkinter import ttk

root = Tk()
s = ttk.Style()
s.configure('Danger.TFrame', background='red', borderwidth=5, relief='raised')
frame = ttk.Frame(root, width=200, height=200, style='Danger.TFrame')
frame['padding'] = (5, 100, 5, 100) # left, top, right, bottom
frame.pack()
root.mainloop()
