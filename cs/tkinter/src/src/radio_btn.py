from tkinter import *
from tkinter import ttk

window = Tk()

root = ttk.Frame(window)
root.grid(column=0, row=0, padx=8, pady=8)
window.columnconfigure(0, weight=1)
window.rowconfigure(0, weight=1)

question = ttk.Label(root, text='What is answer of 1+1?')
question.grid(row=0, column=0)

answer = IntVar(value=10)
selection = ttk.Frame(root)
for order, option in enumerate('A. 3, B. 2, C. 1'.split(', ')):
    choice = ttk.Radiobutton(selection, text=option,
                             variable=answer, value=option.split('. ')[1])
    choice.grid(row=order, column=0)
    choice.grid_configure(padx=4, pady=4)
selection.grid(row=1, column=0)

for child in root.winfo_children():
    child.grid_configure(padx=8, pady=8)

root.mainloop()
