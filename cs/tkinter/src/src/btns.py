from tkinter import *
from tkinter import ttk

window = Tk()

root = ttk.Frame(window)
root.grid(column=0, row=0, padx=8, pady=8)
window.columnconfigure(0, weight=1)
window.rowconfigure(0, weight=1)

ok = ttk.Button(root, text="Okay", command=lambda: print(
    "Okay")).grid(row=0, column=0)
cancel = ttk.Button(root, text="Cancel", command=lambda: print(
    "Cancel"))
cancel.bind('<Return>', lambda e: print("Cancel"))
cancel.grid(row=1, column=0)
print_btn = ttk.Button(root, text="print", image=(_ := PhotoImage(file='src/printer.png')),
                       compound='top', command=lambda: print("Print"), default='normal')
print_btn.state(['disabled'])
print_btn.grid(row=0, column=1, rowspan=2)
for child in root.winfo_children():
    child.grid_configure(ipadx=5, ipady=5)

root.mainloop()
