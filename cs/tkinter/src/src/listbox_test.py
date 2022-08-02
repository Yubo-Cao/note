from base import *

with root() as root:
    choices = ['apple', 'orange', 'banana']
    choicevar = StringVar(value=choices)
    listbox = Listbox(root, height=10, listvariable=choicevar)
    choices.append('peach')
    choicevar.set(choices)
    listbox.grid(row=0, column=0, sticky='news')
    listbox['selectmode'] = 'extended'
    listbox.bind('<<ListboxSelect>>', lambda e: print(
        e, listbox.curselection(), listbox.selection_set(1), listbox.see(1)))
    root.columnconfigure(0, weight=1)
