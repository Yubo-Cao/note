from base import *

countrycodes = ('ar', 'au', 'be', 'br', 'ca', 'cn', 'dk',
                'fi', 'fr', 'gr', 'in', 'it', 'jp', 'mx', 'nl', 'no', 'es',
                'se', 'ch')
countrynames = ('Argentina', 'Australia', 'Belgium',
                'Brazil', 'Canada', 'China', 'Denmark',
                'Finland', 'France', 'Greece', 'India', 'Italy',
                'Japan', 'Mexico', 'Netherlands', 'Norway', 'Spain',
                'Sweden', 'Switzerland')
populations = {'ar': 41000000, 'au': 21179211, 'be': 10584534,
               'br': 185971537,
               'ca': 33148682, 'cn': 1323128240, 'dk': 5457415,
               'fi': 5302000, 'fr': 64102140, 'gr': 11147000,
               'in': 1131043000, 'it': 59206382, 'jp': 127718000,
               'mx': 106535000, 'nl': 16402414,
               'no': 4738085, 'es': 45116894, 'se': 9174082,
               'ch': 7508700}
gifts = {'card': 'Greeting card', 'flowers': 'Flowers',
         'nastygram': 'Nastygram'}
with root() as root:
    cnames = StringVar(value=countrynames)
    gift, sentmsg, statusmsg = (StringVar() for _ in range(3))

    def show_population(*args):
        idxs = lbox.curselection()
        statusmsg.set('\n'.join(
            f'The population of {countrynames[idx]} ({(code:=countrycodes[idx])}) is {populations[code]}' for idx in idxs))
        sentmsg.set('')

    def send_gift(*args):
        idxs = lbox.curselection()
        sentmsg.set('\n'.join(
            f'Sent {gifts[gift.get()]} to leader of {countrynames[idx]} ({(code:=countrycodes[idx])})' for idx in idxs))

    lbox = Listbox(root, listvariable=cnames, height=5)
    lb = ttk.Label(root, text="Send to country's leader:")

    for idx, (gname, g) in enumerate(gifts.items()):
        btn = ttk.Radiobutton(root, text=g, variable=gift, value=gname)
        btn.grid(row=idx + 1, column=1, sticky='W', padx=20)

    send = ttk.Button(root, text='Send gift',
                      command=send_gift, default='active')
    sendlb = ttk.Label(root, textvariable=sentmsg, anchor='center')
    status = ttk.Label(root, textvariable=statusmsg, anchor='w')

    lbox.grid(column=0, row=0, rowspan=6, sticky='nsew')
    lb.grid(column=1, row=0)
    send.grid(column=2, row=4, sticky='e')
    sendlb.grid(column=1, row=5, columnspan=2, sticky=N)
    status.grid(column=0, row=6, columnspan=2, sticky=(W, E))

    lbox.bind('<<ListboxSelect>>', show_population)
    lbox.bind('<Double-1>', send_gift)
    root.bind('<Return>', send_gift)

    for i in range(0, len(countrynames), 2):
        lbox.itemconfig(i, background='#f0f0ff')

    gift.set('card')
    sentmsg.set('')
    statusmsg.set('')
    lbox.selection_set(0)
    show_population()
