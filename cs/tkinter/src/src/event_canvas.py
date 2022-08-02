from base import *

color = 'black'
lastx, lasty = 0, 0


def set_color(new_color):
    global color
    color = new_color


def add_line(event):
    global lastx, lasty
    canvas.create_line((lastx, lasty, canvas.canvasx(
        event.x), canvas.canvasy(event.y)), fill=color)
    lastx, lasty = canvas.canvasx(event.x), canvas.canvasy(event.y)


with root() as root:
    canvas = Canvas(root, width=500, height=500)
    xs, ys = [ttk.Scrollbar(root, orient=direction)
              for direction in ('horizontal', 'vertical')]

    xs.config(command=canvas.xview)
    canvas.config(xscrollcommand=xs.set)
    ys.config(command=canvas.yview)
    canvas.config(yscrollcommand=ys.set)
    canvas.config(scrollregion=(0, 0, 1000, 1000))

    xs.grid(row=1, column=0, sticky='ew')
    ys.grid(row=0, column=1, sticky='ns')

    id = canvas.create_rectangle((10, 10, 30, 30), fill='red')
    canvas.tag_bind(id, '<Button-1>', lambda x: set_color('red'))
    id = canvas.create_rectangle((10, 35, 30, 55), fill="blue")
    canvas.tag_bind(id, "<Button-1>", lambda x: set_color("blue"))
    id = canvas.create_rectangle((10, 60, 30, 80), fill="black")
    canvas.tag_bind(id, "<Button-1>", lambda x:
                    set_color("black"))
    canvas.bind('<Button-1>', lambda e: globals().update(lastx=e.x, lasty=e.y))
    canvas.bind('<B1-Motion>', add_line)
    canvas.grid(row=0, column=0)

    root.columnconfigure(0, weight=1)
    root.rowconfigure(0, weight=1)
