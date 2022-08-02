from base import *

with root() as root:
    tree = ttk.Treeview(root)
    tree.config(columns=('size'))
    tree.heading('size', text='Size')
    tree.column('size', width=100, anchor='center')
    tree.insert('', 'end', 'widgets', text='Widget tour')
    size = tree.set('widgets', 'size')
    tree.insert('', 'end', text='Listbox', values=('10Kb'))
    tree.grid()