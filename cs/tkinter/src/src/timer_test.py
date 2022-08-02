from base import *

with root() as root:
    interrupt = False

    def start():
        b.config(text="Stop", command=stop)
        l.config(text="Working")
        global interrupt; interrupt = False
        root.after(1, step)

    def stop():
        global interrupt
        interrupt = True

    def step(count=0):
        p['value'] = count
        if interrupt:
            result(None)
            return
        root.after(100)
        if count == 20:
            result(42)
            return
        root.after(1, lambda: step(count+1))

    def result(answer):
        p['value'] = 0
        b.configure(text='Start!', command=start)
        l['text'] = "Answer: " + str(answer) if answer else "No Answer"

    b = ttk.Button(root, text="Start!", command=start)
    l = ttk.Label(root, text="No Answer")
    p = ttk.Progressbar(root, orient="horizontal", mode="determinate",
                        maximum=20)
    b.grid(column=1, row=0)
    l.grid(column=0, row=0)
    p.grid(column=0, row=1, columnspan=2)
