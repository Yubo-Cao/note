# Password input!

import sv_ttk
from tkinter import *
from tkinter import ttk

window = Tk()

root = ttk.Frame(window)
root.grid(column=0, row=0, padx=8, pady=8)
window.columnconfigure(0, weight=1)
window.rowconfigure(0, weight=1)


_count = 0
_pics = []


def field_gen(root, desc, icon_path, default_value: str = "", width: int = 60):
    global _count
    global _pics
    field_value = StringVar(
        value=default_value)
    field_entry = ttk.Entry(root, textvariable=field_value, width=width)
    field_entry.grid(row=_count, column=1, sticky='W')
    field_label = ttk.Label(root, text=desc, image=(
        _pics := _pics + [PhotoImage(file=icon_path)])[-1], compound='left')
    field_label.grid(row=_count, column=0, stick='W')
    _count += 1
    return (field_value, field_entry, field_label)


uv, ue, ul = field_gen(
    root, "URL", "src/web.png", "https://publish.gwinnett.k12.ga.us/gcps/home/gcpslogin")
ue.state(['disabled'])
sv, se, sl = field_gen(root, "username", "src/user.png")

pv, pe, pl = field_gen(root, "password", "src/padlock.png")
pe['show'] = '*'

for child in root.winfo_children():
    child.grid_configure(padx=4, pady=4)

sv_ttk.set_theme('light')

root.mainloop()
