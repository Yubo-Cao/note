from base import *
from tkinter import messagebox

with app() as app:
    ttk.Entry(app).grid()
    m = Menu(app)
    sysmenu = Menu(m, name='system')
    m.add_cascade(menu=sysmenu, label='System')
    
    m_edit = Menu(m)
    m.add_cascade(menu=m_edit, label='Edit')
    m_edit.add_command(label="Paste", command=lambda: app.focus_get().event_generate('<<Paste>>'))
    m_edit.add_command(label="Find...", command=lambda: app.event_generate('<<OpenFindDialog>>'))
    app['menu'] = m

    def launchFindDialog(*args):
        messagebox.showinfo(message="Hope you find what you are looking for")
    app.bind("<<OpenFindDialog>>", launchFindDialog)
    