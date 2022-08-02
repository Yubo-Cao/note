from functools import partial
from tkinter.messagebox import askyesno
from base import *

with app() as app:
    def gradient_in(count=0):
        if count == 100:
            app.update_idletasks()
            app.withdraw()
            return None
        app.after(25)
        app.geometry(
            f'{int(10 ** (2.477 / 100 * count))}x{int(10 ** (2 / 100 * count))}-{int(30/100*count)}-{int(30/100*count)}')
        app.update_idletasks()
        app.after(25, gradient_in(count+1))

    app.wm_attributes('-alpha', '0.5')
    app.geometry('300x100-30-30')
    entry = ttk.Label(app, text="Notification")
    entry.grid()
    app.after(50, gradient_in)
    app.mainloop()
