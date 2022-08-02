from tkinter import *
from tkinter.ttk import *
from tkinter import messagebox
import sv_ttk


class Window(Tk):
    def __init__(self):
        super().__init__()
        self.title("Hello tkinter")
        self.label_text = StringVar()
        self.label_text.set("My Name is: ")
        self.geometry("500x300")

        self.name_text = StringVar()

        self.label = Label(self, textvar=self.label_text)
        self.label.pack(side=TOP, fill=BOTH, expand=1, padx=40, pady=40)

        self.name_entry = Entry(self, textvar=self.name_text)
        self.name_entry.pack(side=BOTTOM, fill=BOTH, expand=1, padx=10, pady=10)

        hbtn = Button(
            self,
            text="Hello",
            command=lambda: messagebox.showinfo(
                "Hello", "Hello, " + self.name_text.get()
            ),
        )
        hbtn.pack(side=LEFT, padx=(20, 0), pady=(0, 20))
        gbtn = Button(
            self,
            text="GoodBye",
            command=lambda: self.after(2000, self.destroy())
            if messagebox.askyesno(
                "Goodbye", "Do you want to goodbye, " + self.name_text.get()
            )
            else None,
        )
        gbtn.pack(side=RIGHT, padx=(0, 20), pady=(0, 20))


win = Window()
sv_ttk.set_theme("light")
win.mainloop()
