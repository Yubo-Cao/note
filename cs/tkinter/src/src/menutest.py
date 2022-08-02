from base import *
from tkinter.filedialog import askopenfilename

with app() as root:
    menubar = Menu(root)
    menu_file = Menu(menubar)
    menu_edit = Menu(menubar)

    menubar.add_cascade(menu=menu_file, label='File')
    menubar.add_cascade(menu=menu_edit, label='Edit')

    menu_file.add_command(label='Open...', command=askopenfilename, underline=0)
    menu_recent = Menu(menu_file)
    menu_file.add_cascade(menu=menu_recent, label='Open Recent')
    for f in ['a', 'b', 'c']:
        menu_recent.add_command(label=f, command=lambda: print('Open ' + f))
    menu_file.add_command(label='Close', command=lambda: print('Close'), underline=0)

    check = StringVar()
    menu_file.add_checkbutton(
        label='Check', variable=check, onvalue=1, offvalue=0)
    radio = StringVar()
    menu_file.add_radiobutton(label='One', variable=radio,
                              value=1)
    menu_file.add_radiobutton(label='Two', variable=radio,
                              value=2)

    root['menu'] = menubar
    print(menu_file.entrycget(0, 'label'))
    print(menu_file.entryconfigure(0))
    
    menu_file.entryconfigure('Close', state=DISABLED)
