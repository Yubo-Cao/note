from functools import partial
from tkinter import *
from tkinter import ttk

window = Tk()

root = ttk.Frame(window)
root.grid(column=0, row=0, padx=8, pady=8)
window.columnconfigure(0, weight=1)
window.rowconfigure(0, weight=1)

measure_system = StringVar()


def on_change():
    print('changed', measure_system.get())


check = ttk.Checkbutton(root, text='Use Metric', command=on_change,
                        variable=measure_system, onvalue='metric', offvalue='imperial')
check.grid(column=0, row=0)
check.state(['alternate'])
for child in root.winfo_children():
    child.grid_configure(ipadx=5, ipady=5)

root.mainloop()
