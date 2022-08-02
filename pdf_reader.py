import pyperclip
import regex
from tkinter import *

win = Tk()
space = regex.compile("-\\s*\n\\s*|\\s+", regex.MULTILINE)

def daemon():
    try:
        data = pyperclip.paste()
        if data:
            pyperclip.copy(space.sub(" ", data).strip())
    except pyperclip.PyperclipException as e:
        print(f"{e!r}")
    win.after(500, daemon)
daemon()
win.withdraw()
win.mainloop()
    