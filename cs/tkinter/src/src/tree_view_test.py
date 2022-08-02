from base import *

with root() as root:
    tree = ttk.Treeview(root)
    tree.insert("", "end", "widgets", text="Widget tour")
    tree.insert("", 0, "gallery", text="Applications")
    id = tree.insert('', 'end', text="Tutorial")  # empty string parent, top level node
    tree.insert('widgets', 'end', text='Canvas')
    tree.insert(id, 'end', text='Tree')  # specify parent and index, let treeview to choose id
    tree.insert(id, 'end', text='canvas')
    t = tree.insert(id, 'end', text='Text')
    tree.move('widgets', 'gallery', 'end') # item, parent, index
    tree.grid()
    
    tree.item('widgets', open=True) # control if children is dispalyed
    isopen = tree.item('widgets', 'open')
    print(isopen) # 1
    
    to_be_printed = ['']
    for node in to_be_printed:
        print(node)
        to_be_printed += tree.get_children(node)
    # get children, next, prev, parent. A lot to traverse tree
    
    
