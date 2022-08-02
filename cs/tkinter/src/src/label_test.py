from tkinter import *
from tkinter import ttk

root = Tk()
label = ttk.Label(root, text="Full name:", font='TkTextFont')
resultsContent = StringVar()
label['textvariable'] = resultsContent
resultsContent.set("We now see an interesting icon.\n Muy bien. Como te llamas? Me llamo Secuela. Que desea usted? Me trae un arroz con leche. Gracias!")
label['wraplength'] = 100 # Wrap!
image = PhotoImage(file='src/icon.png')
label.config(image=image, compound='top')
label.grid()
root.mainloop()