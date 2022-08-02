from tkinter import *
from tkinter import ttk

root = Tk()

frame = ttk.Frame(root)
name = ttk.Label(frame, text="Your Name: ", image=(
    _ := PhotoImage(file='src/feather.png')), compound='right') # garbage collection if no reference
name['compound'] = 'right'
name.grid(row=0, column=0)

caption = ttk.Label(frame, text='Figure 1.\nPretty Picture.')
caption.grid(row=1, column=0)

es = ttk.Style(frame)
es.configure('Error.TLabel', foreground='red', font=('TkCaptionFont', 14))
error = ttk.Label(frame, text='Named must not be blank', style='Error.TLabel')
error.grid(row=2, column=0)

for child in root.winfo_children():
    child.grid_configure(padx=5, pady=5)

root.mainloop()
