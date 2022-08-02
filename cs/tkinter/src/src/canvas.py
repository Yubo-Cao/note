from tkinter import *
from tkinter.ttk import *

window = Tk()

canvas = Canvas(window, bg="white", width=300, height=300)
canvas.pack()

canvas.create_oval((0, 0, 300, 300), fill="yellow")

canvas.create_arc((50, 100, 100, 150), extent=180, fill="black")
canvas.create_arc(
    (200, 100, 250, 150), extent=180, fill="black"
)  # You specify the position as if you are drawing full circle


def pt(x, y):
    canvas.create_oval((x - 1, y - 1, x + 1, y + 1), fill="black")


canvas.create_line((50, 200, 110, 240), fill="red", width=5)
canvas.create_line((110, 240, 190, 240), fill="red", width=5)
canvas.create_line((190, 240, 250, 200), fill="red", width=5)

window.mainloop()
