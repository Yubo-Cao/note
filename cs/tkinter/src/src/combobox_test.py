from itertools import count
from base import *

with root() as root:
    country_var = StringVar()
    country = ttk.Combobox(root, textvariable=country_var)
    # bind to virtual event is better than trace country_var
    country_var.trace_add(('read', 'write'), lambda variable_name, _,
                          action: print(variable_name, _, action, country_var.get(), sep=', '))
    country.bind('<<ComboboxSelected>>', lambda e: country.select_clear())
    # use select clear to get rid of selection of readonly variables
    country.state(['readonly'])

    country['values'] = ('USA', 'UK', 'CN')
    country.grid(column=0, row=0)

    btn = ttk.Button(root, text="Placeholder")
    btn.grid(column=0, row=1)
