from base import *

with root() as root:
    n = ttk.Notebook(root)
    f1 = ttk.Frame(n, width=100, height=100)
    f2 = ttk.Frame(n, width=100, height=100)
    f3 = ttk.Frame(n, width=100, height=100)
    t1 = n.add(f1, text="One", state="disabled")
    t2 = n.add(f2, text="Two", state="hidden")
    t3 = n.add(f3, text="Three")
    
    print(n.tab(t1))
    
