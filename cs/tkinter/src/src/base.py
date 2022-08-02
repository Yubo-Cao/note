import sv_ttk
from tkinter import *
from tkinter import ttk
from contextlib import contextmanager



@contextmanager
def root(padx=4, pady=4, ipadx=4, ipady=4, debug=False):
    window = Tk()
    window.option_add('*tearOff', FALSE)
    sv_ttk.set_theme('light')

    root = ttk.Frame(window)
    root.grid(column=0, row=0, padx=8, pady=8, sticky='news')
    if debug:
        s = ttk.Style()
        s.configure('Debug.TFrame', background='red', relief='raised')
        root.configure(style='Debug.TFrame')
    window.columnconfigure(0, weight=1)
    window.rowconfigure(0, weight=1)
    
    yield root

    for child in root.winfo_children():
        child.grid_configure(padx=padx, pady=pady, ipadx=ipadx, ipady=ipady)
    window.mainloop()

@contextmanager
def app():
    app = Tk()
    app.option_add('*tearOff', FALSE)
    sv_ttk.set_theme('light')
    
    yield app
    
    app.mainloop()

    