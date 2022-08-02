from base import *

def save_posn(event):
    global lastx, lasty
    lastx, lasty = event.x, event.y
    
def add_line(event):
    canvas.create_line(lastx, lasty, event.x, event.y)
    save_posn(event)

with root() as root:
    canvas = Canvas(root, width=500, height=400)

    b = ttk.Button(canvas, text='Add line')
    canvas.create_window(10, 10, anchor='nw', window=b)

    canvas.bind("<Button-1>", save_posn)
    canvas.bind("<B1-Motion>", add_line)
    canvas.grid()