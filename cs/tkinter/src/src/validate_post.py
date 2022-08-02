import re
from base import *

first_time = True
def check_zip(newval, op):
    global first_time
    if first_time:
        zip.set('')
        first_time = False
        return True
    errmsg.set('')
    valid = bool(re.match(r'^\d{5}(?:-\d{4})?$', newval))
    btn.state([f'{"!" if valid else ""}disabled'])
    if not newval:
        zip.set('Zip code')
        first_time = True
        default = ttk.Style()
        default.configure('DefaultPrompt.TEntry', foreground='#323232')
        entry.config(style='DefaultPrompt.TEntry')
    else:
        entry.config(style='TEntry')

    if op == 'key':
        if not (res := bool(re.match(r'^[-0-9]*$', newval))):
            errmsg.set(formatmsg)
        return res
    elif op == 'focusout':
        if not valid:
            errmsg.set(formatmsg)
            btn.focus()
    return valid


with root() as root:
    errmsg = StringVar()
    formatmsg = "Zip should be \d{5} pr \d{5}-\d{4}"
    check_zip_wrapper = (root.register(check_zip), '%P', '%V')
    print(check_zip_wrapper)
    zip = StringVar(value='Zip code')
    entry = ttk.Entry(root, textvariable=zip, validate='all', # all make it call on other triggers
                      validatecommand=check_zip_wrapper)
    # key, focusout, focusin are available stuff.
    # if validatecommand raise an error, then the validation will be disabled for that
    # widget
    # manually call validate is possible, but %V = forced
    # invalidcommnad, call when validation fails. 
    # this can make some nasty things, like force people go back.
    # whether widget is valid/invalid is automatically invoked when validation succeeds
    # or fails
    
    # %P new content, %s prior content, %d difference between insert and delete
    # %i index of insert or delete, %S what is deleted/inserted, %v current setting of
    # validate
    # %W name of the widget.
    entry.grid(column=0, row=0)
    btn = ttk.Button(root, text="Process")
    btn.grid(column=1, row=0)
    btn.state(['disabled'])
    msg = ttk.Label(root, font='TkSmallCaptionFont',
                    foreground='red', textvariable=errmsg)
    msg.grid(column=0, row=1, columnspan=2, sticky='ew')
