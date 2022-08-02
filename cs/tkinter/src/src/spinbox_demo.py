from base import *

with root() as root:
    integer, weekday, double = IntVar(value=5), StringVar(
        value="Wednesday"), DoubleVar(value=25.5)

    isp = ttk.Spinbox(root, from_=0.0, to=100,
                      increment=1, textvariable=integer)
    wsp = ttk.Spinbox(root, values=['Monday', 'Tuesday', 'Wednesday',
                      'Thursday', 'Friday', 'Saturday', 'Sunday'], textvariable=weekday)
    dsp = ttk.Spinbox(root, from_=0.0, to=100,
                      increment=0.5, textvariable=double)
    dsp.state(['disabled'])

    isp.grid(row=0, column=0)
    wsp.grid(row=1, column=0)
    dsp.grid(row=2, column=0)
