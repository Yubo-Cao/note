from base import *
from tkinter.filedialog import askopenfile, askdirectory
import tkinter.colorchooser

with root() as root:
    ttk.Button(root, text="Choose File",
               command=askopenfile).grid()
    ttk.Button(root, text="Choose Directory",
               command=askdirectory).grid()
    ttk.Button(root, text="Color picker",
               command=tkinter.colorchooser.askcolor).grid()

    def font(font):
        print(font)
    root.tk.call('tk', 'fontchooser', 'configure', '-font',
                 'arial 24', '-command', root.register(font))
    ttk.Button(root, text="Font picker",
               command=lambda: root.tk.call('tk', 'fontchooser', 'show')).grid()
