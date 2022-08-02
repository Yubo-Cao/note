# Commands

- The kernel of Linux system is responsible for the allocation and scheduling of hardware resources, which plays a very important role in the normal operation of the system.
  - **However, we need a wrapper to play around with it.** It is very hard to handle Linux kernel directly.
- **Bash** (bourne-again shell), among with other shells which translate the directions from user to executable machine code, is a good choice. Why?
  - Ubuntu installed it by default, and so do a lot of other Linux distributions. It is de-facto standard shell in Linux.
  - A bunch of hotkeys, especially tab completion
  - You can write a script, do configurations, etc.
- Those days, better shell alternative exists, e.g., **Zsh**, **Fish**, and **PowerShell**.
  - There is no big difference between those alternatives, so choose one you prefer.
  - Remember to install them in competition if you decided to use them.
- **GUI** is a good invention, e.g., GParted/LVM Manager/Virt Manager. There is no reason not to use them in competition. However:
  - They don't work when you need to rescue the computer.
  - They don't work if you want to writing a script to automate this process. Unless, you want to use a window detection mechanism and mouse event emulator.

## Overview

- We are just type string into computer. Following format is used:

- `executable_name [optional_parameters] positional_parameters`

  - Parameters has different format in Linux:
    - Short-format: `-\w`. There is only one hyphen before the parameter, followed by a character
      - Short format have a short hand -- you can mix them together without type `-` multiple times.
      - Example: `sort -n -u` and `sort -nu`
    - Long-format: `--\w+`. There is two hyphen before the parameter, followed by a word
    - Example: `sort -u` and `sort --unique`
  - In those formats, any content that enclosed in brackets, is optional. Anything that are not enclosed in brackets, is mandatory.

- Hotkeys for bash

  - | Key                     | function                                                         |
    | ----------------------- | ---------------------------------------------------------------- |
    | `tab`                   | complete command/direction path                                  |
    | `Ctrl+c`                | terminate current process                                        |
    | `Ctrl+shift+c`          | copy selection area                                              |
    | `Ctrl+d`                | terminate keyboard input, `KeyboardInterruptException` in python |
    | `Ctrl+l`                | clear terminal content. scroll up if you want to see old output  |
    | `Ctrl+r`                | search your history                                              |
    | `↑`, `↓`                | previous executed command, next executed command                 |
    | `Ctrl+w`                | delete word                                                      |
    | `Ctrl+u`                | delete line                                                      |
    | And more emacs stuff... |                                                                  |

## Get Help

### `man`

- Use this command to check system reference manuals.

- `man [man options] [[section] page ...] ...`

  - `page` is usually name of executable
  - `section` consists of page. Some program might split their manual in different sections, and you need to access them by passing section number to `man`. `man` display default section/page, even there are multiple alternatives available.

- Hotkeys

  - | Key       | function                       |
    | --------- | ------------------------------ |
    | `Space`     | Next page                      |
    | `Page down` | Next page                      |
    | `Page up`   | Previous page                  |
    | `home`      | First page                     |
    | `end`       | Last page                      |
    | `/`         | Search for keyword, up to down |
    | `?`         | Search for keyword, down to up |
    | `n`         | Next search result             |
    | `N`         | Previous search result         |
    | `q`         | Exit manual                    |

### `tldr`, `google`, `archwiki`, `stackoverflow`, `info`

- It is hard to read manual pages. Better alternatives are
  - Ask `google` if unsure
    - Ask `arch wiki`, and usually, you can apply same thing to `ubuntu`
    - `stackoverflow`, needless to say
  - Ask `tldr` which gives you examples about how to use command, which covers 80% of use case
  - Ask `info` for some GNU software. Info was developed by GNU as a replacement of `man`, which obviously, it failed.

## Common

### `echo`

- Linux equivalent of `System.out.println`

- `echo [options] string`

  - | option | function                                       |
    | ------ | ---------------------------------------------- |
    | `-n`     | do not print newline character at the end      |
    | `-e`     | allow escape char using `\`                    |
    | `-E`     | does not allow escape char using `\` (default) |

  - For escape char, same escape char you use in Java would work here.

    - `\n` new line
    - `\t` tab
    - `\a` get an alarm sound

- examples

  - ```bash
    [root@yuck ~]# echo -n "Hello world"
    Hello world[root@yuck ~]#
    ```

  - ```bash
    [root@yuck ~]# echo "Hello world"
    Hello world
    ```

  - ```bash
    [root@yuck ~]# echo -e "[[1.1\t2.2],\n [3\t4.3]]"
    [[1.1   2.2],
     [3     4.3]]
    ```

  - ```bash
    [root@yuck ~]# echo "[[1.1\t2.2],\n [3\t4.3]]"
    [[1.1\t2.2],\n [3\t4.3]]
    ```

### `read`

- Linux equivalent of `InputStreamReader(System.in)` or `input`. It is built in command of `bash`, so it may not work in other shell!

- `read [option] name	`

  - | option | function                                                |
    | ------ | ------------------------------------------------------- |
    | `-d`   | delimiter                                               |
    | `-n`   | return immediately after read `n` char                  |
    | `-N`   | return if and only if read n char, new line won’t do it |
    | `-p`   | prompt                                                  |
    | `-t`   | timeout                                                 |

- examples

  - ```bash
    [yubo@yubo-s2series linux]$ read -n 1 -p "Proceed(n/y)? (4 seconds remaining)" -t 4 ans
    [yubo@yubo-s2series linux]$ echo $ans
    y
    ```

  - ```bash
    [yubo@yubo-s2series linux]$ read -p "What is answer?" $ans
    What is answer?Answer
    [yubo@yubo-s2series linux]$ echo $ans
    Answer
    ```

### `date`

- Linux equivalent of `java.time.LocalDate`

- `date [option] [+format]`

  - | format | function                                           |
    | ------ | -------------------------------------------------- |
    | `%S`     | Seconds                                            |
    | `%M`     | Minutes                                            |
    | `%H`     | 24 Hours                                           |
    | `%d`     | Day of month                                       |
    | `%m`     | Month of year                                      |
    | `%Y`     | Years                                              |
    | `%p`     | AM \| PM                                           |
    | `%a`     | Abbreviated weekday name                           |
    | `%A`     | Full weekday name                                  |
    | `%b`     | Abbreviated month name                             |
    | `%B`     | Full month name                                    |
    | `%j`       | Day of year (001-366)                              |
    | `%s`     | Unix timestamp, seconds since (1970-1-1 00:00 UTC) |

- | option                | function                           |
  | --------------------- | ---------------------------------- |
  | `-d`, `--date=STRING` | display time described by `STRING` |
  | `-s`, `--set=STRING`  | set date described by `STRING`     |

- `STRING` consists of items that represents date, time, date-time, time zone, weekday, relative time. Order of those items does not matter, but they need to be separated by whitespace, which may include whitespaces by themselves.

  - date: `2022-6-1`, `6/1/2022`, `1 Jun 2022`, `Jun 1 2022`. Year may be omitted, then current year is assumed.
  - time: `14:24`, `2:24 pm`, `14:24:00.00 +06:00`
  - time zone: `UTC`,`Z`, `+05:30` Numeric form is preferred, `UTC` and `Z` are only time zone that include alphabets and recommended
  - date time: place a T between date and time. `2022-06-01T14:24:00`
  - day of week: use English to spell them out, e.g., `Thur`. You may place ordinal number before them to indicate upcoming weekday, e.g., `10 Monday` iterate in calendar, until 10 Monday has been encountered.
  - relative: `1 year`, `1 year ago`
  - accurate: `@unixtimestamp`, e.g., `@0`
  - **Any thing you can express in natural language, STRING would work**. Check `date --date=STRING` before you issue command, to make sure desired result is achieved.

- examples

  - Without any parameters, it print the current date time

    - ```bash
      [root@yuck ~]# date
      Sat Jun  4 01:47:13 PM EDT 2021
      ```

  - Specify the format to force it print in year-month-date `hour:month:second` format

    - ```bash
      [root@yuck ~]# date "+%Y-%m-%d %H:%M:%S"
      2021-06-04 17:46:18
      ```

  - Set the date by using `-s` parameter. Notice for this to work, `timedatectl status | rg 'NTP service' | xargs` should not be active, since otherwise, it will correct you automatically.

    ```bash
    [root@yuck ~]# date -s "2022-6-1 00:00:00"
    Wed Jun  1 12:00:00 AM EDT 2022
    [root@yuck ~]# date
    Wed Jun  1 12:00:00 AM EDT 2022
    ```

  - Another way to specify date

    - ```bash
      [root@yuck ~]# date --date="-1 month" +%B
      May
      ```


### `wget` & `curl`

#### `wget`

- `wget` is used to download file from internet from terminal.

- `wget [option] url`

  - | option                      | function                                                     |
    | --------------------------- | ------------------------------------------------------------ |
    | `-b`, `--background`        | Background download mode                                     |
    | `-P`, `--directory-prefix=` | Download to the specified directory                          |
    | `-t`                        | Maximum number of attempts                                   |
    | `-c`, `--continue`          | Resume downloads                                             |
    | `-p`, `--page-requisites`   | Download all the resources on the page, including images, videos, and more |
    | `-r`, `--recursive`         | Recursive download                                           |

- example

  - download homepage of GSMST

    ```bash
    [root@yuck ~]# wget https://www.gcpsk12.org/Domain/83
    --2022-06-07 07:56:17--  https://www.gcpsk12.org/Domain/83
    Loaded CA certificate '/etc/ssl/certs/ca-certificates.crt'
    Resolving www.gcpsk12.org (www.gcpsk12.org)... 54.230.250.48, 54.230.250.56, 54.230.250.118, ...
    Connecting to www.gcpsk12.org (www.gcpsk12.org)|54.230.250.48|:443... connected.
    HTTP request sent, awaiting response... 200 OK
    Length: 364056 (356K) [text/html]
    Saving to: ‘83.2’
    
    83.2               100%[===============>] 355.52K  --.-KB/s    in 0.09s
    
    2022-06-07 07:56:17 (3.66 MB/s) - ‘83’ saved [364056/364056]
    ```

  - download all the resources of GSMST website, recursively. put them into a directory called `gsmst`.

    > Sidenote: hit `Ctrl+c` to stop download

    ```bash
    [root@yuck ~]# wget -r -p -P gsmst https://www.gcpsk12.org/Domain/83
    ```

#### `curl`

- curl is a tool to transfer data from or to a server. it could replace `wget`, if you want.

- `curl [option] URL`

  - | option                                                      | function                                                     |
    | ----------------------------------------------------------- | ------------------------------------------------------------ |
    | `-o`, `--output`                                            | write output to file                                         |
    | `-L`, `--location`                                          | allow jump/redirection between sites if server claim the requested content has moved to a different location |
    | `-i`, `--include`                                           | show HTTP response header                                    |
    | `-v`, `--verbose`, `--trace <file>`, `--trace-ascii <file>` | show communication process. trace show communication process to a greater detail. |
    | `-X <method>`                                               | specify the request method                                   |
    | `--data-urlencode <data>`                                   | encode the data so you can send data by `POST`               |
    | `--user-agent <ua>`                                         | simulate user-agent to avoid some anti-spider mechanisms     |
    | `--cookie <cookie, k=v pair>`                               | send cookies to simulate login information                   |
    | `--user name:password`                                      | use this if website uses HTTP basic authentication           |

- examples

  - send a get request to `httpbin.org`
  
    - ```bash
      [root@yuck ~]# curl 'http://httpbin.org/get'
      {
        "args": {},
        "headers": {
          "Accept": "*/*",
          "Host": "httpbin.org",
          "User-Agent": "curl/7.83.1",
          "X-Amzn-Trace-Id": "Root=1-629e988a-55c7dde03fcc18e273a92abb"
        },
        "origin": "24.131.62.201",
        "url": "http://httpbin.org/get"
      }
      ```
  
  - verbose. show the entire process of HTTP get request.
  
    - ```bash
      [root@yuck ~]# curl -v 'http://httpbin.org/get'
      *   Trying 34.227.213.82:80...
      * Connected to httpbin.org (34.227.213.82) port 80 (#0)
      > GET /get HTTP/1.1
      > Host: httpbin.org
      > User-Agent: curl/7.83.1
      > Accept: */*
      >
      * Mark bundle as not supporting multiuse
      < HTTP/1.1 200 OK
      < Date: Tue, 07 Jun 2022 00:15:44 GMT
      < Content-Type: application/json
      < Content-Length: 254
      < Connection: keep-alive
      < Server: gunicorn/19.9.0
      < Access-Control-Allow-Origin: *
      < Access-Control-Allow-Credentials: true
      <
      {
        "args": {},
        "headers": {
          "Accept": "*/*",
          "Host": "httpbin.org",
          "User-Agent": "curl/7.83.1",
          "X-Amzn-Trace-Id": "Root=1-629e98b0-5a6c5ea8008f1fdf31bc3179"
        },
        "origin": "24.131.62.201",
        "url": "http://httpbin.org/get"
      }
      * Connection #0 to host httpbin.org left intact
      ```
  
  - send the POST request to `httpbin.org/post` with form information. Use `--data-urlencode` to make sure that characters are properly escaped and formatted like a decent Internet user do. In addition, fake our user-agent to fight anti-spider technique
  
    - ```bash
      [root@yuck ~]# curl -X 'POST' --data 'school=GSMST' --data-urlencode 'club=Cyber Pariot' --user-agent 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.5005.63 Safari/53
      7.36 Edg/102.0.1245.33' 'http://httpbin.org/post'
      {
        "args": {},
        "data": "",
        "files": {},
        "form": {
          "club": "Cyber Pariot",
          "school": "GSMST"
        },
        "headers": {
          "Accept": "*/*",
          "Content-Length": "30",
          "Content-Type": "application/x-www-form-urlencoded",
          "Host": "httpbin.org",
          "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.5005.63 Safari/537.36 Edg/102.0.1245.33",
          "X-Amzn-Trace-Id": "Root=1-629e996b-736e4116543a18a578148fb1"
        },
        "json": null,
        "origin": "24.131.62.201",
        "url": "http://httpbin.org/post"
      }
      ```
  

### `ps`

- `ps [option]`

  - | option | function                                            |
    | ------ | --------------------------------------------------- |
    | `-a`   | Show all processes (including those of other users) |
    | `-u`   | Show users and other details                        |
    | `-x`   | Show processes that do not have tty                 |

- example

  - ```bash
    [root@yuck ~]# ps -aux | head -n 5
    USER        PID %CPU %MEM    VSZ(Virtual Memory KB)   RSS (resident set size/memory without swap in KB) TTY      STAT START   TIME COMMAND
    root           1  0.0  0.3 101992  3904 ?        Ss   Jun06   0:02 /sbin/init
    root           2  0.0  0.0      0     0 ?        S    Jun06   0:00 [kthreadd]
    root           3  0.0  0.0      0     0 ?        I<   Jun06   0:00 [rcu_gp]
    root           4  0.0  0.0      0     0 ?        I<   Jun06   0:00 [rcu_par_gp]
    ```

- status

  - | state | description                                                         |
    | ----- | ------------------------------------------------------------------- |
    | `D`   | uninterruptible sleep (usually IO)                                  |
    | `I`   | Idle kernel thread                                                  |
    | `R`   | running or runnable (on run queue)                                  |
    | `S`   | interruptible sleep (waiting for an event to complete)              |
    | `T`   | stopped by job control signal                                       |
    | `t`   | stopped by debugger during the tracing                              |
    | `Z`   | defunct ("zombie") process, terminated but not reaped by its parent |

- additional states
  - | state | description                                                   |
    | ----- | ------------------------------------------------------------- |
    | `<`   | high-priority (not nice to other users)                       |
    | `N`   | low-priority (nice to other users)                            |
    | `L`   | has pages locked into memory (for real-time and custom IO)    |
    | `s`   | is a session leader (contain thread)                          |
    | `l`   | is multi-threaded (using CLONE_THREAD, like NPTL pthreads do) |

### `pstree`

- print process as tree

- example

  - print process as tree

    - ```bash
      [root@yuck ~]# pstree
      systemd-+-ModemManager---2*[{ModemManager}]
              |-NetworkManager---2*[{NetworkManager}]
              |-accounts-daemon---2*[{accounts-daemon}]
              |-acpid
              |-agetty
      ```

  - print process with pid

    - ```bash
      [root@yuck ~]# pstree -p
      systemd(1)-+-ModemManager(505)-+-{ModemManager}(525)
                 |                   `-{ModemManager}(529)
                 |-NetworkManager(421)-+-{NetworkManager}(462)
                 |                     `-{NetworkManager}(463)
                 |-accounts-daemon(412)-+-{accounts-daemon}(452)
      ```

  - print process of certain user

    - ```bash
      # print process of certain user
      [yubo@yuck /root]$ pstree yubo
      bash───pstree
      ```

### `top`

- display dynamic real-time information about running processes, equivalent to `taskmgr.exe`

- example

  - show task manager

    - ```bash
      [root@yuck ~]# top
      top - 20:17:05 up 10 days, 22:23,  1 user,  load average: 0.00, 0.00, 0.00
      Tasks:  98 total,   1 running,  97 sleeping,   0 stopped,   0 zombie
      %Cpu(s):  0.0 us,  6.2 sy,  0.0 ni, 93.8 id,  0.0 wa,  0.0 hi,  0.0 si,  0.
      MiB Mem :    991.8 total,    120.3 free,    191.9 used,    679.6 buff/cache
      MiB Swap:   2048.0 total,   1995.3 free,     52.7 used.    580.3 avail Mem
      
          PID USER      PR  NI    VIRT    RES    SHR S  %CPU  %MEM     TIME+
            1 root      20   0  102144   8436   4788 S   0.0   0.8   0:23.32
            2 root      20   0       0      0      0 S   0.0   0.0   0:00.10
            3 root       0 -20       0      0      0 I   0.0   0.0   0:00.00
            4 root       0 -20       0      0      0 I   0.0   0.0   0:00.00
            5 root       0 -20       0      0      0 I   0.0   0.0   0:00.00
            7 root       0 -20       0      0      0 I   0.0   0.0   0:00.00
           10 root       0 -20       0      0      0 I   0.0   0.0   0:00.00
           11 root      20   0       0      0      0 S   0.0   0.0   0:00.00
           12 root      20   0       0      0      0 S   0.0   0.0   0:00.00
           13 root      20   0       0      0      0 S   0.0   0.0   0:02.42
           14 root      20   0       0      0      0 I   0.0   0.0   2:02.49
           15 root      rt   0       0      0      0 S   0.0   0.0   0:07.26
           16 root     -51   0       0      0      0 S   0.0   0.0   0:00.00
           17 root      20   0       0      0      0 S   0.0   0.0   0:00.00
           18 root      20   0       0      0      0 S   0.0   0.0   0:00.00
           19 root       0 -20       0      0      0 I   0.0   0.0   0:00.00
      ```

- command

  - `h` to get help
  - `q` to exit
  - `↑` and `↓` to scroll through process list
  - `k` to kill a process, type PID after it.
  - `n` to change maximum taks
  - uppercase
    - `M` sort by memory sage
    - `N` sort by PID
    - `T` sort by running time
    - `P` sort by CPU
  - `u` to only display certain user
  - `e` to change unit for display memory, i.e., Kb, Mb, Gb, etc.
  - `Z` to colorize your `top`
  - `c` to show command line
  - `h` to show process hierarchy
  - `i`to show active tasks only
  - `r` to change nicety
  - `o` to filter, e.g., `USER=root`

### `nice`

- Change the priority of a program.

- `nice -n priority service_name`

- example

  - ```bash
    [root@yuck ~]# nice -n -20 fish
    ```

  - lower the number, higher the priority (i.e., if you are not so nice, you get more food when dining with others...)

### `pidof`

- Gets the ID of a process using its name.

- `pidof [option] process_name`

  - | option | function                                     |
    | ------ | -------------------------------------------- |
    | `-s`   | instructs the program to only return one pid |

- example

  - ```bash
    [root@yuck ~]# (python3 - <<DOC &) && echo "PID of Python3 is $(pidof -s python3)"
    import time
    while True:
        time.sleep(0.01)
    DOC
    ```

  - ```bash
    [root@yuck ~/Lecture]# pidof sshd
    42194 527
    ```

### `kill`/`killall`

- Terminate a program by sending signal to a program.

- `kill [signal] pid+`

- Common signals

  - | Signal        | Numbers | function                                                     |
    | ------------- | ------- | ------------------------------------------------------------ |
    | **`SIGHUP`**  | 1       | The terminal suspends or the control process terminates. <br />When the user exits the shell, all processes started by the process receive this signal.<br />The default action is to terminate the process. |
    | **`SIGINT`**  | 2       | The interrupted keyboard shortcut is issued. <br />When the user presses the `Ctrl+C` key combination, the user terminal issues this signal to the running program started by the terminal.<br />The default action is to terminate the process |
    | `SIGQUIT`     | 3       | The exited keyboard shortcut is issued. <br />When the user presses the `Ctrl+D` or `Ctrl+\` key combination, the user terminal issues this signal to a running program started by the terminal. <br />The default action is as the exit program. |
    | `SIGFPE`      | 8       | Issued when a fatal arithmetic error occurs. <br />By default, the process is terminated and a core file is generated. |
    | **`SIGKILL`** | 9       | Terminate the process unconditionally.<br />When the process receives this signal, it terminates immediately without clean up. <br />This signal cannot be ignored, processed, and blocked, and it provides the system administrator a way to kill any process. |
    | `SIGALRM`     | 14      | The timer times out and defaults to the process as a termination. |
    | **`SIGTERM`** | 15      | The end of the program signal can be generated by the kill command. <br />Unlike `SIGKILL`, `SIGTERM` signals can be blocked and terminated so that the program can clean up before exit. |

- Example

  - Kill python

    - ```bash
      # First issue 2 python
      [root@yuck ~/Lecture]# pidof python3
      43980 43973
      [root@yuck ~/Lecture]# kill -15 43980 43973
      fish: Job 2, 'python3 test.py >> /dev/null &' terminated by signal SIGTERM (Polite quit request)
      fish: Job 1, 'python3 test.py > /dev/null &' terminated by signal SIGTERM (Polite quit request)
      ```

  - Forcefully kill python

    - ```bash
      [root@yuck ~/Lecture]# kill -9 (pidof python3)
      fish: Job 1, 'python3 test.py >> /dev/null &' terminated by signal SIGKILL (Forced quit)
      ```

  - Hang python

    - You killed it! Yeah!

    - Usually, this is used to reload daemon. For example, I want to restart `sshd`

    - ```bash
      [root@yuck ~]# kill -HUP `pidof -s sshd`
      Connection to 172.21.219.130 closed by remote host.
      Connection to 172.21.219.130 closed.
      # After I come back to powershell, issue
      ssh $(Resolve-DnsName ubuntu-vm.mshome.net | Select-Object -ExpandProperty "IPAddress")
      # Then I come back to my linux box again!
      ```

- Some times, you want to kill a lot of process. It would be very slow to do so, e.g., you need to kill 3 pythons.

  - One way is to write a for loop to kill them, like

    - ```bash
      [root@yuck ~/Lecture]# for pid in `pidof python3`; do kill $pid; done;
      ```

  - But easier way is just writing `killall`

    - ```bash
      [root@yuck ~/Lecture]# killall python3
      python3: no process found
      ```

    - And obviously, you may choose any process name or series of pid. You may specify the signal as before.

### `lsof`

- Lists open files and the corresponding processes.

- `lsof [option] file`

  - | option      | function                                                     |
    | ----------- | ------------------------------------------------------------ |
    | `-t`        | list only process id                                         |
    | `-u`        | list file opened by given user. use `^` to exclude user.     |
    | `-c`        | list file opened by given process/commend                    |
    | `-p`        | list open file with such PID                                 |
    | `+D`        | list open files in directory, as well as its subdirectories, etc. |
    | `-s`        | specify in `protocol:state`                                  |
    | `-i [addr]` | list files with specified internet address in format of `[46][protocol][@(hostname|hostaddr)][:service|port]`. if not address is specified, list all internet files. if 4 or 6 is specified, only ipv4/ipv6 is listed. |

- example

  - list all `IPv4` ports with state `LISTENING`

    - ```bash
      [root@yuck /h/s/L/create]# lsof -i 4 -sTCP:LISTEN
      COMMAND    PID            USER   FD   TYPE DEVICE SIZE/OFF NODE NAME
      systemd      1            root  105u  IPv4  15955      0t0  TCP *:sunrpc (LISTEN)
      systemd-r  368 systemd-resolve   14u  IPv4  19511      0t0  TCP localhost:domain (LISTEN)
      cupsd      510            root    7u  IPv4  20508      0t0  TCP localhost:ipp (LISTEN)
      sshd       525            root    3u  IPv4  20570      0t0  TCP *:ssh (LISTEN)
      rpc.statd  567           statd    9u  IPv4  20927      0t0  TCP *:57305 (LISTEN)
      smbd       624            root   47u  IPv4  21586      0t0  TCP *:microsoft-ds (LISTEN)
      smbd       624            root   48u  IPv4  21587      0t0  TCP *:netbios-ssn (LISTEN)
      rpcbind   1449            _rpc    4u  IPv4  15955      0t0  TCP *:sunrpc (LISTEN)
      rpc.mount 1866            root    5u  IPv4  26544      0t0  TCP *:46109 (LISTEN)
      rpc.mount 1866            root    9u  IPv4  26556      0t0  TCP *:59695 (LISTEN)
      rpc.mount 1866            root   13u  IPv4  26568      0t0  TCP *:46403 (LISTEN)
      ```

  - list open file of `yubo`

    - ```bash
      [yubo@yuck /home/smbuser/Lecture/create]$ sudo lsof -u yubo
      COMMAND   PID USER   FD   TYPE DEVICE SIZE/OFF    NODE NAME
      bash    13562 yubo  cwd    DIR    8,2     4096 5768998 /home/smbuser/Lecture/create
      bash    13562 yubo  rtd    DIR    8,2     4096       2 /
      bash    13562 yubo  txt    REG    8,2  1396520 2490462 /usr/bin/bash
      bash    13562 yubo  mem    REG    8,2 14575936 2495672 /usr/lib/locale/locale-archive
      bash    13562 yubo  mem    REG    8,2  2216304 2496427 /usr/lib/x86_64-linux-gnu/libc.so.6
      bash    13562 yubo  mem    REG    8,2   200136 2497541 /usr/lib/x86_64-linux-gnu/libtinfo.so.6.3
      bash    13562 yubo  mem    REG    8,2    27002 3148626 /usr/lib/x86_64-linux-gnu/gconv/gconv-modules.cache
      bash    13562 yubo  mem    REG    8,2   240936 2496092 /usr/lib/x86_64-linux-gnu/ld-linux-x86-64.so.2
      bash    13562 yubo    0u   CHR  136,0      0t0       3 /dev/pts/0
      bash    13562 yubo    1u   CHR  136,0      0t0       3 /dev/pts/0
      bash    13562 yubo    2u   CHR  136,0      0t0       3 /dev/pts/0
      bash    13562 yubo  255u   CHR  136,0      0t0       3 /dev/pts/0
      ```

  - list open files and processes

    - ```bash
      [root@yuck /h/s/L/create]# lsof
      COMMAND     PID   TID TASKCMD               USER   FD      TYPE             DEVICE SIZE/OFF       NODE NAME
      systemd       1                             root  cwd       DIR                8,2     4096          2 /
      systemd       1                             root  rtd       DIR                8,2     4096          2 /
      systemd       1                             root  txt       REG                8,2  1849992    2490981 /usr/lib/systemd/systemd
      systemd       1                             root  mem       REG                8,2   149760    2496819 /usr/lib/x86_64-linux-gnu/libgpg-error.so.0.32.1
      ```

  - list processes running on a specific port

    - ```bash
      [root@yuck /h/s/L/create]# lsof -i TCP:22
      COMMAND  PID USER   FD   TYPE DEVICE SIZE/OFF NODE NAME
      sshd     525 root    3u  IPv4  20570      0t0  TCP *:ssh (LISTEN)
      sshd     525 root    4u  IPv6  20599      0t0  TCP *:ssh (LISTEN)
      sshd    6215 root    4u  IPv4 193729      0t0  TCP yuck.mshome.net:ssh->Windows-Host.mshome.net:58848 (ESTABLISHED)
      ```

  - list IPV4 open ports

    - ```bash
      [root@yuck /h/s/L/create]# lsof -i 4
      COMMAND    PID            USER   FD   TYPE DEVICE SIZE/OFF NODE NAME
      systemd      1            root  105u  IPv4  15955      0t0  TCP *:sunrpc (LISTEN)
      systemd      1            root  106u  IPv4  15958      0t0  UDP *:sunrpc
      systemd-r  368 systemd-resolve   13u  IPv4  19510      0t0  UDP localhost:domain
      systemd-r  368 systemd-resolve   14u  IPv4  19511      0t0  TCP localhost:domain (LISTEN)
      avahi-dae  434           avahi   12u  IPv4  20312      0t0  UDP *:mdns
      avahi-dae  434           avahi   14u  IPv4  20314      0t0  UDP *:46993
      NetworkMa  439            root   25u  IPv4  20763      0t0  UDP yuck.mshome.net:bootpc->Windows-Host.mshome.net:bootps
      cupsd      510            root    7u  IPv4  20508      0t0  TCP localhost:ipp (LISTEN)
      sshd       525            root    3u  IPv4  20570      0t0  TCP *:ssh (LISTEN)
      ```

  - take a look at `sshd`

    - ```bash
      [root@yuck /h/s/L/create]# lsof -i 4:22 -s TCP:LISTEN
      COMMAND PID USER   FD   TYPE DEVICE SIZE/OFF NODE NAME
      sshd    525 root    3u  IPv4  20570      0t0  TCP *:ssh (LISTEN)
      ```

    - ```bash
      [root@yuck /h/s/L/create]# lsof -c sshd -i 4:22 -s TCP:LISTEN
      COMMAND  PID USER   FD   TYPE             DEVICE SIZE/OFF    NODE NAME
      sshd     525 root  cwd    DIR                8,2     4096       2 /
      sshd     525 root  rtd    DIR                8,2     4096       2 /
      sshd     525 root  txt    REG                8,2   917192 2490468 /usr/sbin/sshd
      sshd     525 root  mem    REG                8,2   149760 2496819 /usr/lib/x86_64-linux-gnu/libgpg-error.so.0.32.1
      ...
      ```

## Status

### `net-tools` vs `ip`

- `net-tools` is already an very old tool (the latest update is 2001, and deprecated since that, [Net tools Source Forge](https://sourceforge.net/projects/net-tools/files/)). Therefore, we will introduce an alternative, `iproute2` instead.
- `iproute2` is a complete set of tools to handle internet interface. They are `ip`, `ss` (statistics), `tc` (traffic control), `arpd` (address resolution protocol daemon). we will only worry about `ip` here.

#### `ip`

- Show / manipulate routing, devices, policy routing and tunnels

- `ip [ OPTIONS ] OBJECT { COMMAND | help }`

- option

  - | option | function             |
    | ------ | -------------------- |
    | `-d`   | show details         |
    | `-c`   | show coloured output |

- examples

  - get list of interfaces

    - ```bash
      [root@yuck ~/Lecture]# ip link
      1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN mode DEFAULT group default qlen 1000
          link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
      2: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc mq state UP mode DEFAULT group default qlen 1000
          link/ether 00:15:5d:00:05:01 brd ff:ff:ff:ff:ff:ff
      ```

  - show information about a specific interface

    - ```bash
      [root@yuck ~/Lecture ]# ip link show eth0
      2: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc mq state UP mode DEFAULT group default qlen 1000
          link/ether 00:15:5d:00:05:01 brd ff:ff:ff:ff:ff:ff
      ```

  - change device attributes

    - | atrribute | function |
      | --------- | -------- |
      |address             | specify unicast link layer (MAC) address|
      |arp                 | change ARP flag on device|
      |broadcast   brd     | specify broadcast link layer (MAC) address|
      |down                | change state to down|
      |dynamic             | change DYNAMIC flag on device|
      |mtu                 | specify maximum transmit unit|
      |multicast           | change MULTICAST flag on device|
      |name                | change name of device|
      |peer                | specify peer link layer (MAC) address|
      |promisc             | set promiscuous mode|
      |txqueuelen  txqlen  | specify length of transmit queue|
      |up                  | change state to up|

    - ```bash
      # change the max transmit unit
      yuck# ip link set lo mtu 65536 
      ```

  - add interface

    - ```bash
      [root@yuck ~/Lecture]# ip link add test type dummy
      [root@yuck ~/Lecture]# ip link show test
      3: test: <BROADCAST,NOARP> mtu 1500 qdisc noop state DOWN mode DEFAULT group default qlen 1000
          link/ether ee:5d:99:ea:15:9b brd ff:ff:ff:ff:ff:ff
      ```

  - show addresses and interfaces

    - ```bash
      [root@yuck ~/Lecture]# ip address
      1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN group default qlen 1000
          link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
          inet 127.0.0.1/8 scope host lo
             valid_lft forever preferred_lft forever
          inet6 ::1/128 scope host
             valid_lft forever preferred_lft forever
      2: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc mq state UP group default qlen 1000
          link/ether 00:15:5d:00:05:01 brd ff:ff:ff:ff:ff:ff
          inet 172.21.219.130/20 brd 172.21.223.255 scope global dynamic noprefixroute eth0
             valid_lft 76592sec preferred_lft 76592sec
          inet6 fe80::c35f:1ee3:edf1:204d/64 scope link noprefixroute
             valid_lft forever preferred_lft forever
      3: test: <BROADCAST,NOARP> mtu 1500 qdisc noop state DOWN group default qlen 1000
          link/ether ee:5d:99:ea:15:9b brd ff:ff:ff:ff:ff:ff
      ```

    - ```bash
      [root@yuck ~/Lecture ]# ip -d address show eth0
      2: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc mq state UP group default qlen 1000
          link/ether 00:15:5d:00:05:01 brd ff:ff:ff:ff:ff:ff promiscuity 0 minmtu 68 maxmtu 65521 numtxqueues 64 numrxqueues 64 gso_max_size 62780 gso_max_segs 65535 parentbus vmbus parentdev 51f4ddc7-15bc-4102-ba1d-1e7d77210046
          inet 172.21.219.130/20 brd 172.21.223.255 scope global dynamic noprefixroute eth0
             valid_lft 76546sec preferred_lft 76546sec
          inet6 fe80::c35f:1ee3:edf1:204d/64 scope link noprefixroute
             valid_lft forever preferred_lft foreve
      ```

  - specify the family of address (ipv4/ipv6)

    - ```bash
      [root@yuck ~/Lecture]# ip --family inet address show eth0
      2: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc mq state UP group default qlen 1000
          inet 172.21.219.130/20 brd 172.21.223.255 scope global dynamic noprefixroute eth0
             valid_lft 76472sec preferred_lft 76472sec
      ```

  - show statistics of an interface

    - ```bash
      [root@yuck ~/Lecture]# ip --stats address show eth0
      2: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc mq state UP group default qlen 1000
          link/ether 00:15:5d:00:05:01 brd ff:ff:ff:ff:ff:ff
          inet 172.21.219.130/20 brd 172.21.223.255 scope global dynamic noprefixroute eth0
             valid_lft 76435sec preferred_lft 76435sec
          inet6 fe80::c35f:1ee3:edf1:204d/64 scope link noprefixroute
             valid_lft forever preferred_lft forever
          RX(Received):  bytes packets errors dropped  missed   mcast
           581736612  263214      0       0       0  128224
          TX(Transmitted):  bytes packets errors dropped carrier collsns
            12058047  122695      0       0       0       0
      ```

  - show routing table

    - ```bash
      [root@yuck ~/Lecture]# ip route
      default via 172.21.208.1 dev eth0 proto dhcp metric 100
      172.21.208.0/20 dev eth0 proto kernel scope link src 172.21.219.130 metric 100
      ```

  - modify your routing table

    - ```bash
      yuck# ip route add 20.0.0.0/8 via 127.0.0.1
      yuck# ip route get 20.0.0.1
      20.0.0.1 dev lo src 172.21.219.130 uid 0
          cache <local>
      yuck# ip route get 20.0.0.2
      20.0.0.2 dev lo src 172.21.219.130 uid 0
          cache <local>
      ```

  - ```bash
    yuck# ip route
    default via 172.21.208.1 dev eth0 proto dhcp metric 100
    20.0.0.0/8 via 127.0.0.1 dev lo
    172.21.208.0/20 dev eth0 proto kernel scope link src 172.21.219.130 metric 100
    ```

#### `ss`

- A utility to replace `netstat`(i, which display network related information such as open connections, open socket ports, etc. It might be necessary to use `ip` to fully replace `netstat`

- `ss [options]/netstat [options]`

  - | option | function                                                     |
    | ------ | ------------------------------------------------------------ |
    | `-n`   | show IP address rather than service name, not **human-readable** |
    | `-u`   | display UDP sockets                                          |
    | `-t`   | display TCP sockets                                          |
    | `-p`   | show process using socket (show PID), which implies show socket in use |
    | `-l`   | show only listening sockets, which are omitted by default    |
    | `-e`   | show details socket information, include UID, inode number (for virtual file system), and cookie |
    | `-a`   | display both listening and non listening sockets             |

  - In addition to that, `netstat` also provide the following options, which are replaced by `ip route`

  - | option | function                                  |
    | ------ | ----------------------------------------- |
    | `-i`   | display a table of all network interfaces |
    | `-r`   | display routing table                     |

- example

  - show all TCP sockets

    - ```bash
      [root@yuck ~]# ss -a -t
      State  Recv-Q Send-Q  Local Address:Port   Peer Address:Port Process
      LISTEN 0      128         127.0.0.1:ipp         0.0.0.0:*
      LISTEN 0      4096    127.0.0.53%lo:domain      0.0.0.0:*
      LISTEN 0      128           0.0.0.0:ssh         0.0.0.0:*
      ESTAB  0      0      172.21.219.130:ssh    172.21.208.1:53686
      LISTEN 0      128             [::1]:ipp            [::]:*
      LISTEN 0      128              [::]:ssh            [::]:*
      ```

    - ```bash
      [root@yuck ~]# netstat -a -t | head
      Active Internet connections (servers and established)
      Proto Recv-Q Send-Q Local Address           Foreign Address         State
      tcp        0      0 0.0.0.0:ssh             0.0.0.0:*               LISTEN
      tcp        0      0 localhost:ipp           0.0.0.0:*               LISTEN
      tcp        0      0 localhost:domain        0.0.0.0:*               LISTEN
      tcp        0     80 ubuntu-vm.mshome.ne:ssh Windows-Host.msho:53686 ESTABLISHED
      tcp6       0      0 [::]:ssh                [::]:*                  LISTEN
      tcp6       0      0 ip6-localhost:ipp       [::]:*                  LISTEN
      ```

  - show TCP and UDP sockets, including listening without resolve their ip address, and the process

    - ```bash
      [root@yuck ~]# ss -tlunp
      Netid    State     Recv-Q    Send-Q        Local Address:Port          Peer Address:Port    Process
      udp      UNCONN    0         0                   0.0.0.0:36915              0.0.0.0:*        users:(("avahi-daemon",pid=49224,fd=14))
      udp      UNCONN    0         0             127.0.0.53%lo:53                 0.0.0.0:*        users:(("systemd-resolve",pid=378,fd=13))
      udp      UNCONN    0         0                   0.0.0.0:631                0.0.0.0:*        users:(("cups-browsed",pid=46776,fd=7))
      udp      UNCONN    0         0                   0.0.0.0:5353               0.0.0.0:*        users:(("avahi-daemon",pid=49224,fd=12))
      udp      UNCONN    0         0                      [::]:37419                 [::]:*        users:(("avahi-daemon",pid=49224,fd=15))
      udp      UNCONN    0         0                      [::]:5353                  [::]:*        users:(("avahi-daemon",pid=49224,fd=13))
      tcp      LISTEN    0         128               127.0.0.1:631                0.0.0.0:*        users:(("cupsd",pid=46758,fd=7))
      tcp      LISTEN    0         4096          127.0.0.53%lo:53                 0.0.0.0:*        users:(("systemd-resolve",pid=378,fd=14))
      tcp      LISTEN    0         128                 0.0.0.0:22                 0.0.0.0:*        users:(("sshd",pid=527,fd=3))
      tcp      LISTEN    0         128                   [::1]:631                   [::]:*        users:(("cupsd",pid=46758,fd=6))
      tcp      LISTEN    0         128                    [::]:22                    [::]:*        users:(("sshd",pid=527,fd=4))
      ```

    - ```bash
      [root@yuck ~]# netstat -tlunp
      Active Internet connections (only servers)
      Proto Recv-Q Send-Q Local Address           Foreign Address         State       PID/Program name
      tcp        0      0 0.0.0.0:22              0.0.0.0:*               LISTEN      527/sshd: /usr/sbin
      tcp        0      0 127.0.0.1:631           0.0.0.0:*               LISTEN      46758/cupsd
      tcp        0      0 127.0.0.53:53           0.0.0.0:*               LISTEN      378/systemd-resolve
      tcp6       0      0 :::22                   :::*                    LISTEN      527/sshd: /usr/sbin
      tcp6       0      0 ::1:631                 :::*                    LISTEN      46758/cupsd
      udp        0      0 0.0.0.0:36915           0.0.0.0:*                           49224/avahi-daemon:
      udp        0      0 127.0.0.53:53           0.0.0.0:*                           378/systemd-resolve
      udp        0      0 0.0.0.0:631             0.0.0.0:*                           46776/cups-browsed
      udp        0      0 0.0.0.0:5353            0.0.0.0:*                           49224/avahi-daemon:
      udp6       0      0 :::37419                :::*                                49224/avahi-daemon:
      udp6       0      0 :::5353                 :::*                                49224/avahi-daem
      ```

### `history`

- This is built-in command of `bash` and most other shell program to display command line history.

- `history [option]`

  - | option                  | function                                                     |
    | ----------------------- | ------------------------------------------------------------ |
    | `-c`                    | clear history                                                |
    | `-d (offset|start-end)` | delete history entry at position offset, or between start-end |
    | `-w filename`           | write history to file                                        |

- example

  - print history

    - ```bash
      [root@yuck ~]# history
          1  history
      ```

  - clear history

    - ```bash
      [root@yuck ~]# history -c
      ```

  - save history (**I think we should use that in competition for script development and process backup!**)

    - ```bash
      [root@yuck ~/Lecture]# history -w history
      [root@yuck ~/Lecture]# cat history
      cd Lecture/
      ls
      history -w history
      ```

  - history is stored in `~/.bash_history`, so obviously you can check that out

    - ```bash
      [root@yuck ~]# head ~/.bash_history
      EDITOR=vi visudo
      nano /etc/ssh/sshd_config
      ip --family inet --color address | perl -nle 'print $1 if /((\\d{1,3}\\.){3}\\d{1,3}).*eth0/'
      ```

### `uname`

- Get system architecture and OS kernel informations.

- example

  - ```bash
    yuck# uname -a
    Linux yuck 5.15.0-37-generic #39-Ubuntu SMP Wed Jun 1 19:16:45 UTC 2022 x86_64 x86_64 x86_64 GNU/Linux
    ```

  - ```bash
    yuck# uname --kernel-name --nodename --kernel-release --kernel-version --machine --processor --hardware-platform --operating-system
    Linux yuck 5.15.0-37-generic #39-Ubuntu SMP Wed Jun 1 19:16:45 UTC 2022 x86_64 x86_64 x86_64 GNU/Linux
    ```

### `uptime`

- Tell how long the system has been running and other information.

- example

  - Print current time, uptime, number of logged-in users and load average

    ```bash
    yuck# uptime
     22:35:31 up 11 days, 42 min,  1 user,  load average: 0.02, 0.01, 0.00
    ```

  - Pretty print amount of time the system has been booted for

    ```bash
    yuck# uptime --pretty
    up 1 week, 4 days, 42 minutes
    ```

  - Print the date and time since the system has boot up

    ```bash
    yuck# uptime --since
    2022-06-21 21:53:14
    ```

### `free`

- Display amount of free and used memory in the system.

- example

  - display memory usage in human readable format

    ```bash
    yuck# free -h
                   total        used        free      shared  buff/cache   available
    Mem:           953Mi       206Mi       112Mi       0.0Ki       634Mi       535Mi
    Swap:          2.0Gi        35Mi       2.0Gi
    ```

  - display memory usage in bytes

    ```bash
    yuck# free
                   total        used        free      shared  buff/cache   available
    Mem:          976716      211740      114812         392      650164      548544
    Swap:        2097148       36316     2060832
    ```

### `who`/`whoami`/`id`

- `who` Display who is logged in and related data (processes, boot time).
- `whoami` Display username.
- `id` Display current user and group identity.

- example

  - ```bash
    [root@yuck ~]# who
    root     pts/0        2022-07-03 07:43 (172.21.208.1)
    ```

  - ```bash
    [root@yuck ~]# whoami
    root
    ```

  - ```bash
    [root@yuck ~]# id
    uid=0(root) gid=0(root) groups=0(root)
    ```

### `ping`

- Send ICMP ECHO_REQUEST packets to network, use to **test connectivity between hosts**

- `ping [option] hostname`

  -

  - | option | function                                  |
    | ------ | ----------------------------------------- |
    | `-c`   | Total number of sends                     |
    | `-I`   | Specifies the network interface card name |
    | `-i`   | Interval time per session (seconds)       |
    | `-W`   | Maximum wait time (seconds)               |

- example

  - ping google

    - ```bash
      [root@yuck ~]# ping www.google.com
      PING www.google.com (108.177.122.106) 56(84) bytes of data.
      64 bytes from ym-in-f106.1e100.net (108.177.122.106): icmp_seq=1 ttl=55 time=13.8 ms
      64 bytes from ym-in-f106.1e100.net (108.177.122.106): icmp_seq=2 ttl=55 time=13.7 ms
      ^C
      --- www.google.com ping statistics ---
      2 packets transmitted, 2 received, 0% packet loss, time 1002ms
      rtt min/avg/max/mdev = 13.719/13.750/13.782/0.031 ms
      ```

  - ping google 4 times with `eth0`, wait at most `1` seconds for each time and send packets each `0.5` seconds

    - ```bash
      [root@yuck ~]# ping -i 0.001 -c 4 -I eth0 -W 1 www.google.com
      PING www.google.com (108.177.122.106) from 172.21.219.130 eth0: 56(84) bytes of data.
      64 bytes from ym-in-f106.1e100.net (108.177.122.106): icmp_seq=1 ttl=55 time=13.0 ms
      64 bytes from ym-in-f106.1e100.net (108.177.122.106): icmp_seq=2 ttl=55 time=11.9 ms
      64 bytes from ym-in-f106.1e100.net (108.177.122.106): icmp_seq=3 ttl=55 time=12.2 ms
      64 bytes from ym-in-f106.1e100.net (108.177.122.106): icmp_seq=4 ttl=55 time=11.8 ms
      
      --- www.google.com ping statistics ---
      4 packets transmitted, 4 received, 0% packet loss, time 24ms
      rtt min/avg/max/mdev = 11.835/12.224/12.998/0.464 ms, pipe 2
      ```

### `tracepath`

- Trace route of data packets from here to destination host.

- `tracepath [option] hostname`

- example

  - trace google

    - ```bash
      root@yuck ~ [SIGINT]# tracepath -p 33434 -b www.google.com
       1?: [LOCALHOST]                      pmtu 1500
       1:  Windows-Host.mshome.net (172.21.208.1)                0.694ms
       1:  Windows-Host.mshome.net (172.21.208.1)                0.453ms
       2:  192.168.0.1 (192.168.0.1)                             4.604ms
       3:  96.120.5.201 (96.120.5.201)                          11.653ms
       4:  96.110.115.77 (96.110.115.77)                        10.960ms
       5:  xe-8-1-0-0-sur01.y4cumming.ga.atlanta.comcast.net (162.151.29.209)  12.043ms asymm  4
       6:  ae-25-ar01.b0atlanta.ga.atlanta.comcast.net (162.151.29.213)  47.792ms asymm  5
       7:  no reply
       8:  be-33011-cs01.56marietta.ga.ibone.comcast.net (96.110.43.65)  16.271ms asymm  7
       9:  be-2104-pe04.56marietta.ga.ibone.comcast.net (96.110.37.98)  14.798ms asymm  8
      10:  50.208.232.10 (50.208.232.10)                        16.353ms asymm 14
      ```

    - we use port `33434` since this is preferred. we also use `-b` to print both hostname and ip address.

## File Explorer

### `pwd`

- print current working directory

- `pwd [option]`

- example

  - print the current directory

    - ```bash
      [root@yuck ~]# pwd
      /root
      ```

    - side note: don't bother doing that. you can look at your shell prompt and figure out where you are, e.g., I am in my home directory, so my shell prompt is `root@yuck:~`. Shell prompt consists of `<username>@<hostname>:<directory><#|$>`. `#` denote root privilege, and `$` denote normal user.

  - print the current directory and resolve symbol links

    - ```bash
      [root@yuck ~/Lecture/Symb]# pwd
      /root/Lecture/Symb
      [root@yuck ~/Lecture/Symb]# pwd -P
      /root/Lecture/Test
      ```

    - ```bash
      [root@yuck ~/Lecture]# ls -l
      total 12
      -rw------- 1 root root   37 Jul  3 08:55 history
      lrwxrwxrwx 1 root root    4 Jul  3 09:01 Symb -> Test
      drwxr-xr-x 2 root root 4096 Jul  3 09:01 Test
      -rw-r--r-- 1 root root  125 Jul  2 20:45 test.py
      ```

### `cd`

- Change current working directory

- `cd [dir]`

  - go to `/etc`

    - ```bash
      [root@yuck ~/Lecture]# cd /etc
      [root@yuck /etc]#
      ```

  - go to home dir (we will talk about glob & directory structure later)

    - ```bash
      [root@yuck /etc]# cd ~
      [root@yuck ~]# cd -
      [root@yuck /etc]# cd
      [root@yuck ~]#
      ```

  - go to previous directory

    - ```bash
      [root@yuck ~]# cd -
      /etc
      [root@yuck /etc]# cd -
      /root
      [root@yuck ~]#
      ```

  - go to parent directory

    - ```bash
      [root@yuck ~]# cd ..
      [root@yuck /]#
      ```

  - go to yubo's home dir

    - ```bash
      [root@yuck /]# cd ~yubo
      [root@yuck /home/yubo]#
      ```

### `ls`

- List files in the directory (notice directory is also a file)

- `ls [option] [filename]`

- option

  - | option                  | function                                                     |
    | ----------------------- | ------------------------------------------------------------ |
    | `-a`, `--all`           | show hidden files (list all files)                           |
    | `-F`                    | append a character to each file to indicate the file type<br />- executable: `*`<br />- directory: `/`<br />- symbolic link: `@`<br />- sockets: `=`<br />- regular: nothing<br />- FIFO/named pipe: `|` |
    | `-1`                    | one file per line                                            |
    | `-h`                    | display file size in human readable format                   |
    | `-d`                    | display directory, but not their content                     |
    | `-l`/`--format=verbose` | show name, number of hard links, file type, file mode, owner name and group, size, and time stamp, etc. |

- example

  - show all files in directory

    - ```bash
      [root@yuck ~/Lecture]# ls -a
      .  ..  fifo  history  Symb  Test  test.py
      ```

  - show files with details information

    - ```bash
      [root@yuck ~/Lecture]# ls -l
      total 12
      prw-r--r-- 1 root root    0 Jul  3 09:14 fifo
      -rw------- 1 root root   37 Jul  3 08:55 history
      lrwxrwxrwx 1 root root    4 Jul  3 09:01 Symb -> Test
      drwxr-xr-x 2 root root 4096 Jul  3 09:01 Test
      -rwxr--r-- 1 root root  125 Jul  2 20:45 test.py
      ```

    - notice the first character is used to represents file type

      - `b` is block
      - `d` is directory
      - `c` is character device (find some in `/dev/`)
      - `p` is pipe
      - `l` is link
      - `s` is socket
      - `-` is regular file

  - show files 1 per line with last char representing file type

    - ```bash
      [root@yuck ~/Lecture]# ls -1F
      fifo|
      history
      Symb@
      Test/
      test.py*
      ```

  - show file size in human readable way

    - ```bash
      [root@yuck ~/Lecture]# ls -lh
      total 12K
      prw-r--r-- 1 root root    0 Jul  3 09:14 fifo
      -rw------- 1 root root   37 Jul  3 08:55 history
      lrwxrwxrwx 1 root root    4 Jul  3 09:01 Symb -> Test
      drwxr-xr-x 2 root root 4.0K Jul  3 09:01 Test
      -rwxr--r-- 1 root root  125 Jul  2 20:45 test.py
      ```

  - sort file according to creation time

    - ```bash
      [root@yuck ~/Lecture]# ls --time=ctime -1
      test.py
      fifo
      Symb
      Test
      history
      ```

  - sort file according to size

    - ```bash
      [root@yuck ~/Lecture]# ls --sort=size
      Test  test.py  history  Symb  fifo
      ```

  - show directories

    - ```bash
      [root@yuck ~/Lecture]# ls -d */
      Symb/  Test/
      [root@yuck ~/Lecture]# echo */
      Symb/ Test/
      ```

### `tree`

- list files as a tree

- example

  - ```bash
    [root@yuck ~/Lecture]# tree .
    .
    ├── fifo
    ├── history
    ├── Symb -> Test
    ├── Test
    │   ├── 1
    │   │   ├── a
    │   │   ├── b
    │   │   └── c
    │   ├── 2
    │   │   ├── a
    │   │   ├── b
    │   │   └── c
    │   └── 3
    │       ├── a
    │       ├── b
    │       └── c
    └── test.py
    ```

### `find`

- find file according to certain predicates.

- `find [location] predicates`

- option

  - | option                | function                                                     |
    | --------------------- | ------------------------------------------------------------ |
    | `-name`                 | Match name pattern                                           |
    | `-path`               | Match path pattern                                           |
    | `-perm`                 | Match permissions (append a `-` to make permission included) |
    | `-user`                 | Match the owner                                              |
    | `-group`                | Matches all groups                                           |
    | `-mtime -n +n`          | Match the time of the modified content (-n refers to n days, +n means n days ago) |
    | `-atime -n +n`          | Match the time to access the file (-n means within n days, +n means n days ago) |
    | `-ctime -n +n`          | Match the time to modify the file permissions (-n refers to n days, +n refers to n days ago) |
    | `-nouser`               | Matches files that have no owner                             |
    | `-nogroup`              | Matches files without all groups                             |
    | `-newer f1 !f2`         | Matches a file newer than the file f1 but older than f2      |
    | `-type b/d/c/p/l/f`     | Match file type (followed by subtitle letters for block device, directory, character device, pipe, link file, text file) |
    | `-size`                 | Match file size (+50KB for finding files over 50KB, and -50KB for finding files smaller than 50KB) |
    | `-exec …… {}\;`         | Processing each matching result concurrently. `{}` is replaced/expanded by actual file name. |
    | `-not`, `-and`, `-or` | Combine predicates                                           |

- example

  - find all files that start with `host`

    - ```bash
      [root@yuck /etc]# find -name "host*"
      ./host.conf
      ./apparmor.d/abstractions/hosts_access
      ./hostname
      ./hosts.allow
      ./avahi/hosts
      ./hosts
      ./hosts.deny
      ./hostid
      ```

  - find all files in root directory that has SUID permission

    - ```bash
      [root@yuck /etc]# find / -perm -4000
      /usr/libexec/polkit-agent-helper-1
      /usr/sbin/pppd
      /usr/lib/openssh/ssh-keysign
      /usr/lib/dbus-1.0/dbus-daemon-launch-helper
      /usr/lib/xorg/Xorg.wrap
      /usr/lib/snapd/snap-confine
      /usr/bin/newgrp
      ```

  - find all files that owned by `yubo`

    - ```bash
      [root@yuck /etc]# find / -user yubo
      /home/yubo
      /home/yubo/Videos
      /home/yubo/Desktop
      ```

  - find files that are block device

    - ```bash
      [root@yuck /]# find /dev -type b
      /dev/sda2
      /dev/sda1
      /dev/sda
      /dev/sr0
      ```

  - most important stuff, `-exec`. the idea is

    - start from `-exec` token, every parameter here after is consider as executable, or parameters to such executable. the first encounter of `\;` is considered the end command. in the example below, we find file that is not empty and print its directory and content.

    - ```bash
      [root@yuck /]# find ~/Lecture/ -name "*.txt" -exec bash -c 'if [ "`cat {}`" != "" ]; then echo {}; cat {}; fi' \;
      /root/Lecture/Test/2/c/test.txt
      Hello World!
      ```

    - since the execution is concurrent, so thousands of millions of files can be quickly handled by this. compare this with `Executors.ThreadPoolExecutor.submit()`, you would see the usefulness of this command immediately.

### `locate`

- a faster alternative to locate file according to name. it is necessary to update database before you issue this command if new files are added.

- example

  - ```bash
    [root@yuck /]# updatedb
    [root@yuck /]# locate test.txt
    /root/Lecture/Test/1/a/test.txt
    /root/Lecture/Test/1/b/test.txt
    /root/Lecture/Test/1/c/test.txt
    /root/Lecture/Test/1/test.txt
    ```

### `whereis`

- use to find binary executable, source code, and documentation location.

- example

  - find binary, source, and manual of bash

    - ```bash
      [root@yuck /]# whereis bash
      bash: /usr/bin/bash /usr/share/man/man1/bash.1.gz
      ```

### `which`

- find binary program by looking up `PATH`

- example

  - find binary of bash

    - ```bash
      [root@yuck ~]# which bash
      /usr/bin/bash
      ```

### `touch`

- Change a file access and modification times (atime, mtime).

- `touch [option] path`

  - | option | function                    |
    | ------ | --------------------------- |
    | `-a`   | modify atime                |
    | `-m`   | modify mtime                |
    | `-d`   | modify both atime and mtime |

- example

  - create a file called `test`

    - ```bash
      [root@yuck /h/s/L/create]# touch test
      ```

  - change its mtime and atime

    - ```bash
      [root@yuck /h/s/L/create]# touch -d '2022-06-04 13:29:23.809509936' test
      [root@yuck /h/s/L/create]# stat test | rg Modify
      Modify: 2022-06-04 13:29:23.809509936 -0400
      ```

### `mkdir`

- create directory

- `mkdir [option] path ..`

  - | option | function                                                     |
    | ------ | ------------------------------------------------------------ |
    | `-p`   | recursively create (i.e., if parent directory does not exists, create one) |

- example

  - create directory called `tdir`

    - ```bash
      [root@yuck /h/s/L/create]# mkdir tdir
      ```

  - create multiple directory, `adir/adir,adir/bdir,bdir/adir,bdir/bdir`

    - ```bash
      [root@yuck /h/s/L/create]# mkdir -p {a,b}dir/{a,b}dir
      [root@yuck /h/s/L/create]# tree
      .
      ├── adir
      │   ├── adir
      │   └── bdir
      └── bdir
          ├── adir
          └── bdir
      
      6 directories, 0 files
      ```

### `cp`

- copy files and directories.

- `cp [option] src... dst`

  - if `dst` exists and is a directory, `src` will be copied to `dst`
  - if `dst` exists and is a file, `cp` will ask you if you want to replace them
  - otherwise, new file is created

- option

  - | option                | function                                                   |
    | --------------------- | ---------------------------------------------------------- |
    | `-d`                  | copy symbolic links rather than resolve them               |
    | `-i`                  | prompt whether to overwrite                                |
    | `-L`, `--dereference` | resolve symbol links and make sure copies are regular file |
    | `-p`                  | preserve all attributes of the original files              |
    | `-r`                  | recursive copy, must use this with directories             |
    | `-a`                  | archive, `-pdr`                                            |
    | `-f`                  | force                                                      |

- example

  - ```bash
    [root@yuck /h/s/L/create]# touch test
    [root@yuck /h/s/L/create]# cp test{,bak}
    [root@yuck /h/s/L/create]# ls
    test  testbak
    ```

  - ```bash
    [root@yuck /h/s/L/create]# cp -i test{,bak}
    cp: overwrite 'testbak'?
    ```

### `mv`

- Move or rename files and directories.

- `mv [option] src ... dst`

- option

  - | option | function                                                     |
    | ------ | ------------------------------------------------------------ |
    | `-n`   | don't overwrite                                              |
    | `-u`   | update files in dst, i.e., don't modify file with same or newer timestamp |
    | `-i`   | interactive                                                  |

- example

  - ```bash
    [root@yuck /h/s/L/create]# mv test{,bak}
    [root@yuck /h/s/L/create]# ls
    testbak
    ```

- > # Side Note
  >
  > If you feel like some thing is suspicious during competition, try `sudo mv filename{,.bak}` rather than delete them, so that, when scoring script punish you, you can `mv filename{.bak,}` to get your score back.

### `rm`

- Remove file or directory

- `rm [option] path`

  - | option | function                               |
    | ------ | -------------------------------------- |
    | `-f`   | no confirm                             |
    | `-i`   | ask before deletion (i.e., interative) |
    | `-r`   | delete the directory                   |
    | `-v`   | display the process (i.e., verbose)    |

- example

  - ```bash
    [root@yuck /h/s/L/create]# rm testbak
    [root@yuck /h/s/L/create]# ls
    ```

### `file`

- Linux custom is not use file extension to determine file type -- it check magic number, binary data of file directly. File can check file type.

- `file path`

- example

  - download a html page and check

    - ```bash
      [root@yuck /h/s/L/create]# curl -o test 'https://en.wikipedia.org/wiki/Gwinnett_School_of_Mathematics,_Science,_and_Technology'
        % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                       Dload  Upload   Total   Spent    Left  Speed
      100 91183    0 91183    0     0   330k      0 --:--:-- --:--:-- --:--:--  332k
      [root@yuck /h/s/L/create]# file test
      test: HTML document, Unicode text, UTF-8 text, with very long lines (7320)
      ```

  - download a png and check

    - ```bash
      [root@yuck /h/s/L/create]# curl -o test 'https://upload.wikimedia.org/wikipedia/en/6/6f/Seal_of_GSMST.png'
        % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                       Dload  Upload   Total   Spent    Left  Speed
      100 99830  100 99830    0     0   401k      0 --:--:-- --:--:-- --:--:--  401k
      [root@yuck /h/s/L/create]# file test
      test: PNG image data, 177 x 256, 8-bit/color RGBA, non-interlaced
      ```

  - create empty stuff and check

    - ```bash
      [root@yuck /h/s/L/create]# file test
      test: empty
      ```

  - write something and check

    - ```bash
      [root@yuck /h/s/L/create]# echo "Hello World!" > test
      [root@yuck /h/s/L/create]# file test
      test: ASCII text
      [root@yuck /h/s/L/create]# echo "中文" > test
      [root@yuck /h/s/L/create]# file test
      test: Unicode text, UTF-8 text
      ```

### `tar`

- Linux use `tar`, rather then `zip/rar`. Much unfortunately, the command that I introduce below shall only handle `tar`, and you need `unzip`, `7zip`, `lzma`, all different cli tool to handle different flavour of compressed file.

- `tar [option] paths`

  - | option | function                                             |
    | ------ | ---------------------------------------------------- |
    | `-c`     | create a compressed file                             |
    | `-x`     | unzip the compressed file                            |
    | `-t`     | see what files are inside the package                |
    | `-z`     | compress or decompress with gzip (`gz`)             |
    | `-j`     | compress or decompress with bzip2 (`bz2`)            |
    | `-v`     | displays the process of compression or decompression |
    | `-f`     | the destination file name                            |
    | `-p`     | retain the original permissions and attributes       |
    | `-P`     | use absolute paths for compression                   |
    | `-C`     | specifies the directory to extract to                |

- example

  - compress all config files into `etc.tar.gz`

    - ```bash
      [root@yuck /h/s/L/create]# tar czvf etc.tar.gz /etc
      ```

  - decompress those config files into `~/Lecture/create/etc`

    - ```bash
      [root@yuck /h/s/L/create]# tar xzvf etc.tar.gz -C etc
      ```

## Text Files

> ### Side Note
>
> `Perl`(Pathologically Eclectic Rubbish Lister) is a super useful tool that some how replaces all the tools I shall present after. It makes **tedious things easy and hard things possible**!
> I, hereby admit: I don't know Perl. :grin: Someone else should make a tutorial for me here. However, I covered some basic `awk` here, which is a DSL that is opinionated, but very powerful and only powerful in term of handle text files.

### `cat`

- print and concatenate files (by using re-direct).

- `cat [option] path`

  - | option | function                        |
    | ------ | ------------------------------- |
    | `-n`   | show line number                |
    | `-e`   | put `$` at end of each line     |
    | `-v`   | display non-printable character |
    | `-t`   | display tab                     |

- example

  - concatenate two files

    - ```bash
      [root@yuck /h/s/Lecture]# echo "Hello" > hello.txt
      [root@yuck /h/s/Lecture]# echo "World!" > world.txt
      [root@yuck /h/s/Lecture]# cat hello.txt world.txt | tee hello_world.txt
      Hello
      World!
      ```

  - see content of file with line number

    - ```bash
      [root@yuck /h/s/Lecture]# cat -n hello_world.txt
           1  Hello
           2  World!
      ```

  - show content of file with `$`

    - ```bash
      [root@yuck /h/s/Lecture]# cat -e hello_world.txt
      Hello$
      World!$
      ```

  - just display content

    - ```bash
      [root@yuck /h/s/Lecture]# cat hello_world.txt
      Hello
      World!
      ```

### `more`/`less`

- Open a file for interactive reading, allowing scrolling and search. Actually, `man` use `less` as pager.

- `less/more path`

- example

  - ```bash
    [root@yuck /h/s/Lecture]# less /etc/passwd
    root:x:0:0:root:/root:/usr/bin/fish
    daemon:x:1:1:daemon:/usr/sbin:/usr/sbin/nologin
    bin:x:2:2:bin:/bin:/usr/sbin/nologin
    sys:x:3:3:sys:/dev:/usr/sbin/nologin
    sync:x:4:65534:sync:/bin:/bin/sync
    games:x:5:60:games:/usr/games:/usr/sbin/nologin
    man:x:6:12:man:/var/cache/man:/usr/sbin/nologin
    lp:x:7:7:lp:/var/spool/lpd:/usr/sbin/nologin
    mail:x:8:8:mail:/var/mail:/usr/sbin/nologin
    news:x:9:9:news:/var/spool/news:/usr/sbin/nologin
    uucp:x:10:10:uucp:/var/spool/uucp:/usr/sbin/nologin
    ...
    (END)
    ```

- some hotkeys

  - | hotkey  | function                        |
    | ------- | ------------------------------- |
    | `space` | next page                       |
    | `b`     | previous page                   |
    | `/`     | search next                     |
    | `?`     | search prev                     |
    | `n`     | next match                      |
    | `N`     | previous match                  |
    | `q`     | exit                            |
    | `v`     | open in editor                  |
    | `F`     | waiting for more data/follow up |

### `head`/`tail`

- see first or last `N` lines.

- `head/tail -n N path`

- example

- > # Side note
    >
    > I made a counter program, which create a text file and write 0, 1, 2, 3 ... into it every 0.5 second
    >
    > ```bash
    > [root@yuck /h/s/Lecture]# fish -c 'set count 0; while true; echo $count >> counter.txt; set count (math $count + 1); sleep 0.5; end;' &
    > ```
    >
    > Just kill it
    >
    > ```bash
    > [root@yuck /h/s/Lecture]# kill 6352
    > fish: Job 1, 'fish -c 'set count 0; while tru…' terminated by signal SIGTERM (Polite quit request)
    > ```

  - see first 10 lines of the `counter.txt`

    - ```bash
      [root@yuck /h/s/Lecture]# head counter.txt
      0
      1
      2
      3
      4
      5
      6
      7
      8
      9
      ```

  - see last 10 lines of the `counter.txt`

    - ```bash
      [root@yuck /h/s/Lecture]# tail counter.txt
      9
      10
      11
      12
      13
      14
      15
      16
      17
      18
      ```

  - follow up with that, i.e., follow the update

    - ```bash
      [root@yuck /h/s/Lecture]# tail -f counter.txt
      13
      14
      15
      16
      17
      18
      19
      20
      21
      22
      23
      24
      25
      26
      ```

### `tr`

- Replace characters in a file.

- `tr [src] [dst]`

- example

  - lower case to upper case

    - ```bash
      [root@yuck /h/s/Lecture]# cat hello_world.txt | tr [:lower:] [:upper:]
      HELLO
      WORLD!
      ```

  - delete some character

    - ```bash
      [root@yuck /h/s/Lecture]# cat hello_world.txt | tr -d 'Helo'
      
      Wrd!
      ```

  - squeeze multiple occurrence of specified character

    - ```bash
      [root@yuck /h/s/Lecture]# cat hello_world.txt | tr -s 'Helo'
      Helo # Here, Hello -> Helo
      World!
      ```

### `wc` (!Water Closet)

- Count lines, words, and bytes. I suspect English language art teacher need that.

- `wc [option]`

  - | option          | function                                                |
    | --------------- | ------------------------------------------------------- |
    | `-c`, `--bytes` | byte count (`str.getBytes().length`)                    |
    | `-w`, `--words` | word count                                              |
    | `-l`, `--lines` | line count                                              |
    | `-m`, `--chars` | character count (`str.codePointCount(0, str.length())`) |

- example

  - ```bash
    [root@yuck /h/s/Lecture]# wc normal.txt
    1 1 8 normal.txt
    [root@yuck /h/s/Lecture]# cat normal.txt
    English
    ```

  - ```bash
    [root@yuck /h/s/Lecture]# wc multi_bytes.txt
     2  2 12 multi_bytes.txt
    [root@yuck /h/s/Lecture]# wc -c multi_bytes.txt
    12 multi_bytes.txt
    [root@yuck /h/s/Lecture]# wc -m multi_bytes.txt
    5 multi_bytes.txt
    [root@yuck /h/s/Lecture]# cat multi_bytes.txt
    𒐐
    中文
    ```

  - It obviously don't play with Unicode very well. Since the `EOF` also count, it usually count one more than expected.

### `stat`

- Display status

- `stat path`

- example

  - ```bash
    [root@yuck /h/s/Lecture]# stat multi_bytes.txt
      File: multi_bytes.txt
      Size: 12              Blocks: 8          IO Block: 4096   regular file
    Device: 802h/2050d      Inode: 5768987     Links: 1
    Access: (0644/-rw-r--r--)  Uid: (    0/    root)   Gid: (    0/    root)
    Access: 2022-07-04 10:42:39.155460628 -0400
    Modify: 2022-07-04 10:41:52.031330063 -0400
    Change: 2022-07-04 10:41:52.031330063 -0400
     Birth: 2022-07-04 10:41:52.031330063 -0400
    ```

### `grep`/`rg`

- Get information in a text file if and only if text in this line **contains** (not **match**) pattern provided.

- `grep/rg [option] path`

  - | option                    | function                                      |
    | ------------------------- | --------------------------------------------- |
    | **`-n`**                  | **show line number**                          |
    | **`-v`**                  | **show lines that does not contains pattern** |
    | `-i`                      | ignore case                                   |
    | `-c`                      | show count of lines that pass predicate       |
    | `-b`                      | search binary as text                         |
    | `-E`                      | extended regex                                |
    | `-P`                      | PCRE regex                                    |
    | `-A`, `--after-conext=`   | print several lines after matched line        |
    | `-B`, `--before-context=` | print several lines before matched line       |

- example

  - get users that can not login (i.e., their terminal is `/sbin/nologin`)

    - ```bash
      [root@yuck /h/s/Lecture]# grep /sbin/nologin /etc/passwd
      daemon:x:1:1:daemon:/usr/sbin:/usr/sbin/nologin
      ...
      ```

  - get count of users that can login

    - ```bash
      [root@yuck /h/s/Lecture]# grep -cv /sbin/nologin /etc/passwd
      11
      ```

  - find lines that has paired parenthesis

    - ```bash
      [root@yuck /h/s/Lecture]# cat parenthesis.txt
      ((1+3)+3)
      ())
      (()
      ((test)()())
      (
      ```

    - ```bash
      [root@yuck /h/s/Lecture]# grep -P '^(?P<paren>\(((?:[^()]+)|(?&paren))*\))$' parenthesis.txt
      ((1+3)+3)
      ((test)()())
      ```

- > # Side note
      >
      > Obviously, I could have use `-x` to match entire line. But why not just use regex anchor?
      > And regarding this regex, I plan to give another lecture on it. I hope no one hate regex...

### `sort`

- Sort text content

- `sort [option] path`

  - | option | function              |
    | ------ | --------------------- |
    | `-f`   | ignore case           |
    | `-b`   | spaces are ignored    |
    | `-n`   | sort by number        |
    | `-r`   | reverse sorting       |
    | `-u`   | remove duplicate rows |
    | `-t`   | specifies the spacer  |
    | `-k`   | sets the field range  |

- example

  - ```bash
    [root@yuck /h/s/L/cpj]# cat grade_book_no_header.txt
    y*o 61 1
    s*n 99 2
    a*w 100 3
    ```

  - sort by first column

    - ```bash
      [root@yuck /h/s/L/cpj]# sort -t ' ' -k 1 grade_book_no_header.txt
      a*w 100 3
      s*n 99 2
      y*o 61 1
      ```

  - sort by grade numerically (notice in string comparison, `100` < `99`)

    - ```bash
      [root@yuck /h/s/L/cpj]# sort -t ' ' -k 2 -n grade_book_no_header.txt
      y*o 61 1
      s*n 99 2
      a*w 100 3
      ```

  - sort `/etc/passwd` by UID descending

    - ```bash
      [root@yuck /h/s/L/cpj]# tail -n 5 /etc/passwd
      sshd:x:128:65534::/run/sshd:/usr/sbin/nologin
      smbuser:x:1001:1001::/home/smbuser:/bin/sh
      _rpc:x:129:65534::/run/rpcbind:/usr/sbin/nologin
      statd:x:130:65534::/var/lib/nfs:/usr/sbin/nologin
      nfsuser:x:1002:1002::/home/nfsuser:/bin/sh
      ```

    - ```bash
      [root@yuck /h/s/L/cpj]# sort -t ':' -k 3 -n -r /etc/passwd
      nobody:x:65534:65534:nobody:/nonexistent:/usr/sbin/nologin
      nfsuser:x:1002:1002::/home/nfsuser:/bin/sh
      smbuser:x:1001:1001::/home/smbuser:/bin/sh
      yubo:x:1000:1000:yubo,,,:/home/yubo:/bin/bash
      ```

### `cut`

- Get certain field in each line.

- `cut [option] path`

  - | option | function  |
    | ------ | --------- |
    | `-d`   | delimiter |
    | `-f`   | field     |

- example

  - print all users in the system

    - ```bash
      [root@yuck /h/s/Lecture]# cut -d ':' -f 1 /etc/passwd
      root
      daemon
      bin
      sys
      sync
      ```

### `paste`

- Merge columns into a file

- `paste [option] (path|-(STDIN))*`

  - | option | function          |
    | ------ | ----------------- |
    | `-d`   | specify delimiter |
    | `-s`   | horizontal paste  |

- example

  - ```bash
    [root@yuck /h/s/L/cpj]# cat num.txt
    1
    2
    3
    4
    5
    6
    7
    8
    9
    [root@yuck /h/s/L/cpj]# cat chr.txt
    a
    b
    c
    d
    ```

  - merge two files

    - ```bash
      [root@yuck /h/s/L/cpj]# paste num.txt chr.txt
      1       a
      2       b
      3       c
      4       d
      5
      6
      7
      8
      9
      ```

  - merge them repeatedly

    - ```bash
      [root@yuck /h/s/L/cpj]# paste chr.txt  num.txt chr.txt
      a       1       a
      b       2       b
      c       3       c
      d       4       d
              5
              6
              7
              8
              9
      ```

  - take stdin as a file

    - ```bash
      [root@yuck /h/s/L/cpj]# seq 5 | paste -d ' ' - -
      1 2
      3 4
      5
      ```

  - arrange them horizontally

    - ```bash
      [root@yuck /home/smbuser/Lecture/cpj]# paste -s <(seq 5) chr.txt
      1       2       3       4       5
      a       b       c       d
      ```

### `join`

- merge dict. It requires input files must be sorted.

- `join [option] path1 path2`

- example

  - ```bash
    [root@yuck /h/s/L/cpj]# paste -d ' ' gender.txt grade_book.txt
    Name Gender Id Name Grade Id
    y*o 1 1 y*o 61 1
    s*n 1 2 s*n 99 2
    a*w 1 3 a*w 100 3
    ```

  - We need to sort them before we go

    - Use `tail` to remove header

      - ```bash
        [root@yuck /h/s/L/cpj]# tail -n +2 grade_book.txt > grade_book_no_header.txt
        ```

    - After that, lets sort according to first column

      - ```bash
        [root@yuck /h/s/L/cpj]# sort -t ' ' -k 1 grade_book_no_header.txt > grade_book_sorted.txt
        ```

    - Do the same thing with `gender.txt`.

      - ```bash
        [root@yuck /h/s/L/cpj]# tail -n +2 gender.txt | sort -t ' ' -k 1 | tee gender_sorted.txt
        a*w 1 3
        s*n 1 2
        y*o 1 1
        ```

  - join them together as a big sheet

    - ```bash
      [root@yuck /h/s/L/cpj]# join grade_book_sorted.txt gender_sorted.txt
      a*w 100 3 1 3
      s*n 99 2 1 2
      y*o 61 1 1 1
      ```

  - change join field (by id)

    - ```bash
      [root@yuck /h/s/L/cpj]# join -j 3 gender.txt grade_book.txt
      Id Name Gender Name Grade
      1 y*o 1 y*o 61
      2 s*n 1 s*n 99
      3 a*w 1 a*w 100
      ```

### `awk` [^1]

[^1]: Read that for more information <https://www.gnu.org/software/gawk/manual/gawk.html>

- A programming language to deal with files. You could ditch `cut` after you get yourself familiar with `awk`.
- A brief introduction to `awk` here!

#### Structure of `awk` program

- ```awk
  // Define: cmd = expr1 {stmts} expr2 {stmts} ...
  BEGIN {cmd}
  {cmd}
  END {cmd}
  ```

  1. Execute BEGIN block
  2. For each line in the file, execute `cmd` in middle.
  3. Execute END block

- Notice `BEGIN` and `END` are optional, and thus may be omitted.

#### Data

- Awk only have numerical value and string.

#### Variable

- Fields are separated by separator specified. First field is `$1`, second is `$2`, and so on. The entire line is defined as `$0`. No new line character is included in `$0`.

- Awk has some built in variables

  - `NF`, number of fields
  - `NR`, number of input records

  - `FS`, field separator
  - `ARGV`, all parameters that passed to `awk` as an array. Notice the executable itself also count as one parameter.
  - `ENVIRON`, environment variable as dict
  - `FILENAME`, current file that  processing
  - `RLENGTH`, `RSTART`, length of matching and start of matching

#### Function

- If removal of parenthesis does not cause ambiguity, one may do so.

- | function name                  | function                                                     |
  | ------------------------------ | ------------------------------------------------------------ |
  | `printf(fmt, *val)`            | format string printing                                       |
  | `print(*val)`                  | print as `' '.join(val) + '\n'`                              |
  | `length(str)`                  | get length of string                                         |
  | `match(str, regex)`            | return first occurance of regex in str, 0 represents not find. Since 0 is false, one can use that in `if` |
  | `index(str, substr)`           | index of first occurance of substr in str, string's index start from 1 |
  | `gsub(regex, sub, str)`        | regex replacement                                            |
  | `split(str, arr, regex)`       | split str and store result into `arr`                        |
  | `strtonum(str)`                | `Integer.parseInt` or `int()`. Return 0 if str is not a valid numerical literal. |
  | `substr(str, start, len)`      | get substr start from start with length L                    |
  | `tolower(str)`, `toupper(str)` | self explanatory                                             |
  | `systime()`                    | get unix time stamp                                          |

#### Operator

- Logical: `||`, `&&`, `!`
- Comparison: `>=`, `<=`, `>`, `<`, `==`, `!=`
- Arithmetic: `+`,`-`,`*`,`/`,`%`
- Exp: `**`, `^`
- Ternery: `?:`
- Concatenate strings: use space
- Short hand assignments
- Some bash like stuff
  - `data | cmd`  pipe
  - `data > file` or `data >> file` re direct output. **We will talk about that soon!**

#### Stmt

- Use `;` to terminate a expr to form statement. Last expr in a block may omit `;`.

#### Rosetta

| Awk                                                          | Python                            | Java                                                         |
| ------------------------------------------------------------ | --------------------------------- | ------------------------------------------------------------ |
| `if(cond) {} else {}`                                        | `if(cond): else:`                 | `if(cond) {} else {}`                                        |
| `while (cond) {}`                                            | `while(cond):`                   | `while (cond) {}`                                            |
| `for (i = 0; i <= 10; i++){}`                                | `for i in range(0, 10):`          | `for int i = 0; i <= 10; i++){}`                             |
| `arr[idx] = x`. Notice in `awk`, you don't create array. Just assignment would expand/create array | `arr[idx] = x`                    | `arr[idx] = x`                                               |
| `/regex/ {stmt}`                                             | `if re.search(regex, line): stmt` | `if(Pattern.compile("regex")`.<br />`matcher(line).find()){stmt}` |
| `for(i in arr){}`                                            | `for i in arr:`                   | `for(var i:arr)`                                             |
| `delete arr[idx]`                                            | `del arr[idx]`                    | `var res = new Object[];`<br />`System.arraycopy(arr, 0, res, 0, idx);`<br />`System.arraycopy(arr, idx + 1, res, idx, arr.length - idx - 1)`<br />`return res;` |

#### Example

- ```bash
  [root@yuck /home/smbuser/Lecture/cpj]# cat grade_book.txt
  Name    Grade   Id
  y*o     61      1
  s*n     99      2
  n*s     N/A     5
  a*w     100     3
  w*a     0       4
  ```

- Print grade and name with line number

  - ```bash
    [root@yuck /h/s/L/cpj]# awk 'BEGIN{FS=" "} {print FNR, $1,$2}' grade_book.txt
    1 Name Grade
    2 y*o 61
    3 s*n 99
    4 n*s N/A
    5 a*w 100
    6 w*a 0
    ```

    - Here we specify separator by `FS`

- Print grade if an only if grade is a number

  - ```bash
    [root@yuck /h/s/L/cpj]# awk 'BEGIN{FS=" "} $2 ~ /[0-9]+/ {print $1, $2}' grade_book.txt
    y*o 61
    s*n 99
    a*w 100
    w*a 0
    ```

- Classify student based on their grade as pass or fail and pretty print a header like `Name Grade Type`

  - ```bash
    [root@yuck /h/s/L/cpj ]# awk 'BEGIN{FS=" "; OFS="\t"; print "Name", "Grade", "Type";} $2 ~ /[0-9]+|N\/A/{print $1, $2, (($2 ~ /[0-9]+/ && strtonum($2) > 60
    )? "pass": "fail" )}' grade_book.txt
    Name    Grade   Type
    y*o     61      pass
    s*n     99      pass
    n*s     N/A     fail
    a*w     100     pass
    w*a     0       fail
    ```

- A classifier with higher granularity, which classify student as A, B, C, etc... Also, print average grade points of all those student. Also make an output file like `grade_summary.txt`

  - ```awk
    [root@yuck /home/smbuser/Lecture/cpj]# cat classifer.awk
    #!/usr/bin/awk
    function classify(grade){
      if (grade >= 95)
        return "A+";
      else if (grade >= 60)
        return "B/C";
      else
        return "D/F";
    }
    
    BEGIN {
      FS = " ";
      OFS = "\t";
      print "Name", "Grade", "Type";
    }
    
    {
      grade = strtonum($2);
      sum += grade;
      if($2 !~ /N\/A/){
        count += 1;
        type = classify(grade);
      } else {
        type = "N/A";
      }
      print $1, $2, type;
    }
    
    END {
      cmd = "tee -a 'grade_summary.txt'";
      print "Avg. Grade:", sum / count | cmd;
      print "Part. Student:", count | cmd;
      print "All Student:", FNR | cmd;
      close(cmd);
    }
    ```

  - ```bash
    [root@yuck /home/smbuser/Lecture/cpj]# awk -f classifer.awk <(sort -t ' ' -k 2 -n <(tail -n +2 grade_book.txt))
    Name    Grade   Type
    n*s     N/A     N/A
    w*a     0       D/F
    y*o     61      B/C
    s*n     99      A+
    a*w     100     A+
    Avg. Grade:     65
    Part. Student:  4
    All Student:    5
    ```

  - ```bash
    [root@yuck /home/smbuser/Lecture/cpj]# cat grade_summary.txt
    Avg. Grade:     65
    Part. Student:  4
    All Student:    5
    ```

## `vim`

- Everything in Linux is a file. Registry does not exist. Therefore, you **must know how to write configuration files.**

- `vim`, `nano`, and `emacs` are all editors that has long history. They are extremely popular in Linux community and has extremely deep learning curve.
  - We will study `vim` and `vi`. They are far more useful then `nano` and `gedit`.
  - I don't use `emacs`, but if you know it, it is still a good idea to use `evil` plugin and enjoy `vim` key bindings.

#### 3 mode

- `vi` has three different operation mode
  - `Normal` Navigate in the text file, copy, paste, delete, find
  - `Insert` Type as you would in Word, Visual Studio (Code)
  - `Endline` Execute command, configure your `vim`, `save` and `exit`
- This avoided you from remember `Ctrl+Alt+Shift+Win+*` and their combinations.
- Transition
  - `Normal => Insert` Type `i,A,a,O,o` or any of them
  - `Insert => Normal` Hit `ESC`
  - `Normal => Endline` Type `:`
  - `Endline => Nomral` Hit `ESC`

#### Commands

| Command            | function                                                     |
| ------------------ | ------------------------------------------------------------ |
| **`:w`**          | **Save**                                                     |
| **`:q`**           | **Exit**                                                     |
| **`:q!`**          | **Exit, even if that means not saved**                       |
| **`:wq!`**         | **Exit, even if that means not saved**                       |
| **`:w !sudo tee %`** | Buffer content, the stuff displayed on your screen, is piped to `tee`, which save the buffer content to `%` with help of `sudo` |
| `:set nu`            | Show line number                                             |
| `:set nonu`          | Hide line number                                             |
| `:command`           | Execute that command                                         |
| `:s/a/b`             | Replace first occurrence of `a` in this line to `b`          |
| `:s/a/b/g`           | Replace all occurrence of `a` in this line to `b`            |
| `:%s/a/b/g`          | Replace all occurrence of `a` in this file to `b`            |
| `?a`                 | Search `a` from bottom to up                                 |
| `/a`                 | Search `a` from up to bottom                                 |

- That's enough for most of use cases. You may want to learn more about it: then run `vimtutor` in your terminal.

  ```bash
  [root@yuck ~]# vimtutor
  ===============================================================================
  =    W e l c o m e   t o   t h e   V I M   T u t o r    -    Version 1.7      =
  ===============================================================================
  ```

  - And just follows it will help you learn more basics about it. If you want to exit, type `:q` normally.

### `Systemd`

- `Systemd` is a Linux tool which used to start daemons.
  - Daemon is a special type of process that runs in the background to perform specific system tasks.
  - Many daemons start at system boot time and run until the system shuts down.
- `Systemd` include an awful large amount of commands.
  - ![img](assets/bg2016030703-16543701362773.png)
  - We will not handle all of them today, but rather a small subsets of them.

#### `timedatectl`

- Control the system time and date.

- `timedatectl [options] [command]`

  - | command        | function                                                     |
      | -------------- | ------------------------------------------------------------ |
      | `status`         | Displays status information                                  |
      | `list-timezones` | List available time zones, one per line                      |
      | `set-time`       | Set the system clock to the specified time. Use format "YYYY-MM-DD HH:MM:SS" |
      | `set-timezone`   | Set the system time zone to the specified value, it must be one from list-timezones. |
      | `set-ntp`        | Controls whether the network time synchronization is enabled. true/false. |

- examples

  - ```bash
    [root@yuck ~]# timedatectl
                   Local time: Sat 2022-06-04 14:59:25 EDT
               Universal time: Sat 2022-06-04 18:59:25 UTC
                     RTC time: Sat 2022-06-04 18:59:26
                    Time zone: America/New_York (EDT, -0400)
    System clock synchronized: yes
                  NTP service: active
              RTC in local TZ: no
    [root@yuck ~]# diff --brief <(timedatectl) <(timedatectl status) && echo "same" || echo "diff"
    same
    ```

    - If no command is specified print current status as if `status` is command

  - ```bash
    [root@yuck ~]# timedatectl list-timezones | head -n 5
    Africa/Abidjan
    Africa/Accra
    Africa/Addis_Ababa
    Africa/Algiers
    Africa/Asmara
    ```

  - ```bash
    [root@yuck ~]# timedatectl set-timezone Asia/Shanghai
    ```

  - ```bash
    [root@yuck ~]# timedatectl set-time "2022-6-1 14:24:00"
    [root@yuck ~]# timedatectl set-time "6/1/2022"
    Failed to parse time specification '6/1/2022': Invalid argument
    ```

  - ```bash
    [root@yuck ~]# timedatectl set-ntp true
    ```

#### `systemctl`

- Control the `systemd` system and service manager.

- `systemctl [options...] command [unit...]`

  - ```bash
    [root@yuck ~]# systemctl reboot
    [root@yuck ~]# systemctl poweroff
    [root@yuck ~]# systemctl halt
    [root@yuck ~]# systemctl suspend
    [root@yuck ~]# systemctl hibernate
    [root@yuck ~]# systemctl hybrid-sleep
    [root@yuck ~]# systemctl resuce
    ```

    - `suspend` put core and peripherals on low power consumption mode. If battery runs out, all unsaved changes will be lost.
    - `hibernate` put all information in RAM to non-volatile storage, and completely power off the computer. Regardless whether battery runs out, saved state is restored to RAM.
    - `halt` stop all CPU functions
    - `resuce` boot into a temporary OS for diagnosing problems.
    - **self-explanatory**

  - ```bash
    [root@yuck ~]# systemctl get-default
    graphical.target
    ```

  - ```bash
    [root@yuck ~]# systemctl set-default multi-user.target
    Created symlink /etc/systemd/system/default.target → /lib/systemd/system/multi-user.target.
    ```

  - ```bash
    [root@yuck ~]# systemctl list-dependencies graphical.target
    graphical.target
    ● ├─accounts-daemon.service
    ● └─multi-user.target
    ...
    ```

    - There are multiple target where the system can reach for you to operate. For example, you can reach graphical target, pure text target, even emergent target.

    - | Target | Description |
      | ------ | ----------- |
      |`multi-user.target`|Plain text mode|
      |`graphical.target`|Text plus graphical interface, in fact, `multi-user.target` plus graphical operations.|
      |`rescue.target`|In the case of not being able to log in with root, systemd will add an additional temporary system at boot time, regardless of your original system. At this point you can get root privileges to maintain your system.|
      |`emergency.target`|Emergency handling system errors, try this if `rescue.target` failed|

#### `systemd-analyze`

- Show timing about start units.

- `systemd-analyze [options] command [unit]`

  - | command        | function                                                     |
    | -------------- | ------------------------------------------------------------ |
    | `time`           | default option, if no command is specified. prints time spent in kernel, initrd, userspace, etc. |
    | `blame`          | prints a list of all running units, ordered by the time they took to initialize (notice, the time they wait for their dependencies also count here) |
    | `critical-chain` | prints a tree of the time-critical chain of units (may be inaccurate because time of dependency is included). Put an unit after it to look for a specific unit. |

- examples

  - ```bash
    [root@yuck ~]# systemd-analyze
    Startup finished in 68ms (firmware) + 4.075s (loader) + 824ms (kernel) + 1.810s (userspace) = 6.779s
    graphical.target reached after 1.801s in userspace.
    ```

  - ```bash
    [root@yuck ~]# systemd-analyze blame | head -n 5
    5.665s man-db.service
     739ms mariadb.service
     495ms dev-sda2.device
     254ms dhcpcd.service
     124ms ldconfig.service
    ```

  - ```bash
    [root@yuck ~]# systemd-analyze critical-chain
    The time when unit became active or started is printed after the "@" character.
    The time the unit took to start is printed after the "+" character.
    
    graphical.target @1.801s
    └─multi-user.target @1.799s
      └─mariadb.service @1.057s +739ms
        └─network.target @1.050s
          └─dhcpcd.service @788ms +254ms
            └─basic.target @785ms
              └─sockets.target @784ms
                └─dbus.socket @784ms
                  └─sysinit.target @782ms
                    └─systemd-update-done.service @775ms +7ms
                      └─ldconfig.service @649ms +124ms
                        └─local-fs.target @649ms
                          └─boot.mount @613ms +35ms
                            └─dev-sda1.device @613ms
    ```

  - ```bash
    [root@yuck ~]# systemd-analyze critical-chain mariadb.service
    The time when unit became active or started is printed after the "@" character.
    The time the unit took to start is printed after the "+" character.
    
    mariadb.service +739ms
    └─network.target @1.050s
      └─dhcpcd.service @788ms +254ms
        └─basic.target @785ms
          └─sockets.target @784ms
            └─dbus.socket @784ms
              └─sysinit.target @782ms
                └─systemd-update-done.service @775ms +7ms
                  └─ldconfig.service @649ms +124ms
                    └─local-fs.target @649ms
                      └─boot.mount @613ms +35ms
                        └─dev-sda1.device @613ms
    ```

#### `hostnamectl`

- Control the hostname of the computer.

- `hostnamectl [options] [command]`

  - | command        | function                                              |
    | -------------- | ----------------------------------------------------- |
    | `status`       | Show system hostname and related information. Default |
    | `hostname`     | Show system hostname                                  |
    | `set-hostname` | Set system hostname                                   |

- examples

  - ```bash
    [root@yuck ~]# hostnamectl status
     Static hostname: yuck
           Icon name: computer
          Machine ID: 1761ae02144244f38dad554d3abeaf30
             Boot ID: 718f29bf624445d88c0d4e1dcdd77ec8
      Virtualization: microsoft
    Operating System: Arch Linux
              Kernel: Linux 5.18.1-arch1-1
        Architecture: x86-64
     Hardware Vendor: Microsoft Corporation
      Hardware Model: Virtual Machine
    Firmware Version: Hyper-V UEFI Release v4.1
    ```

  - ```bash
    [root@yuck ~]# hostnamectl hostname
    yuck
    ```

  - ```bash
    [root@yuck ~]# hostnamectl set-hostname "yuck"
    ```

#### `localectl`

- Control the system locale and keyboard layout settings

- `localectl [option] [command]`

  - | command        | function                                                     |
    | -------------- | ------------------------------------------------------------ |
    | `status`       | Show current settings of the system locale and keyboard mapping. Default. |
    | `list-locales` | List available locales.                                      |
    | `set-locale`   | Set locale to one of available locales.                      |
    | `list-keymaps` | List available keyboard mappings.                            |
    | `set-keymap`   | Set keyboard mapping for X11 and framebuffer console to one of available keyboard mappings. |

- examples

  - ```bash
    [root@yuck ~]# localectl status
       System Locale: LANG=en_US.UTF-8
           VC Keymap: n/a
          X11 Layout: n/a
    ```

  - ```bash
    [root@yuck ~]# localectl list-locales
    en_US.UTF-8
    zh_CN.UTF-8
    ```

    - For more locale, edit `/etc/locale.gen` and run `locale-gen`

  - ```bash
    [root@yuck ~]# localectl set-locale zh_CN.UTF-8
    ```

  - ```bash
    [root@yuck ~]# localectl list-keymaps | head -n 5
    ANSI-dvorak
    adnw
    amiga-de
    amiga-us
    apple-a1048-sv
    ```

  - ```bash
    [root@yuck ~]# localectl set-keymap us
    ```

#### `loginctl`

- Control the `systemd login` manager

- `loginctl [option] [command] [name]`

  - | command         | function                                                     |
    | --------------- | ------------------------------------------------------------ |
    | `list-sessions` | List current sessions. Default.                              |
    | `list-users`    | List currently logged in users                               |
    | `show-user`     | Show properties of one or more users or the manager itself.  |
    | `show-session`  | Show properties of one or more sessions or the manager itself. |

  - | option              | function                                             |
    | ------------------- | ---------------------------------------------------- |
    | `-p`, `--property=` | limit display of certain function                    |
    | `--value`           | print the value directly, skip `=` and property name |

- examples

  - ```bash
    [root@yuck ~]# loginctl list-sessions
    SESSION UID USER SEAT TTY
         18   0 root
    
    1 sessions listed.
    ```

  - ```bash
    [root@yuck ~]# loginctl list-users
    UID USER
      0 root
    
    1 users listed.
    ```

  - ```bash
    [root@yuck ~]# loginctl show-user 0
    UID=0
    GID=0
    Name=root
    Timestamp=Sun 2021-06-01 05:40:54 CST
    TimestampMonotonic=40987743521
    RuntimePath=/run/user/0
    Service=user@0.service
    Slice=user-0.slice
    Display=18
    State=active
    Sessions=18
    IdleHint=no
    IdleSinceHint=0
    IdleSinceHintMonotonic=0
    Linger=no
    ```

  - ```bash
    [root@yuck ~]# loginctl show-session 18
    Id=18
    User=0
    Name=root
    Timestamp=Sun 2021-06-01 05:40:54 CST
    TimestampMonotonic=40987749187
    VTNr=0
    Remote=yes
    RemoteHost=172.24.192.1
    Service=sshd
    Scope=session-18.scope
    Leader=4924
    Audit=18
    Type=tty
    Class=user
    Active=yes
    State=active
    IdleHint=no
    IdleSinceHint=0
    IdleSinceHintMonotonic=0
    LockedHint=no
    ```

  - ```bash
    [root@yuck ~]# loginctl --property=Id show-session 18
    Id=18
    [root@yuck ~]# loginctl --property=Id --value show-session 18
    18
    ```

#### `journalctl`

- Use this to see all the journals of `Unit`

- `journalctl [option]`

  - | option                 | function                                                     |
    | ---------------------- | ------------------------------------------------------------ |
    | `-k`, `--dmesg`        | show only kernel messages                                    |
    | `-b`                   | show messages from specific boot. by default, last boot. One may put an offset, -1 means one before last, 1 means first boot find in journal. |
    | `--since`, `--until`   | logs during certain time period. pretty machine same syntax with `date` |
    | `-n`, `--lines=`       | show last several lines of log. default 10 lines             |
    | `-f`, `--follow`       | show most recent journal and continuously print new ones as they appeared |
    | `-u`, `--unit=`        | print journal of a specific unit                             |
    | `_UID=`, `--user-unit` | print journal of a specific user                             |
    | `-p`, `--priority=`    | show journals of of a specific logging level (emergency). from 0 - 7, most emergent to less emergent. |

- examples

  - print all journals of this boot

    ```bash
    [root@yuck ~]# journalctl
    ```

  - print kernel messages

    ```bash
    [root@yuck ~]# journalctl -k
    ```

  - print journal of this boot

    ```bash
    [root@yuck ~]# journalctl -b -0
    [root@yuck ~]# journalctl -b
    ```

  - print journal of last boot

    ```bash
    [root@yuck ~]# journalctl -b -1
    ```

  - print journal between certain time

    ```bash
    [root@yuck ~]# journalctl --since="1 days ago"
    ```

  - print last 10 lines and update in real time

    ```bash
    [root@yuck ~]# journalctl -n 10 -f
    ```

  - print journal of a specific service/user/executable file

    ```bash
    [root@yuck ~]# journalctl /usr/bin/bash
    [root@yuck ~]# journalctl _PID=1
    [root@yuck ~]# journalctl _UID=1000
    [root@yuck ~]# journalctl --unit=sshd.service
    [root@yuck ~]# journalctl -u vsftpd.service -u sshd.service
    ```

  - print journal of a specific priority

    ```bash
    [root@yuck ~]# journalctl -p 0
    [root@yuck ~]# journalctl -p 4
    ```

#### `unit`

- `Systemd` manage daemon processes as `unit`.

- `systemctl` could be used to manage units

  - | command                                      | function                                                     |
    | -------------------------------------------- | ------------------------------------------------------------ |
    | `list-units`                                 | List units that `systemd` currently has in memory.           |
    | `status`                                     | Query the status of the unit                                 |
    | `is-active`, `is-failed`, `is-enabled`       | Look if the service is running, failed to run, or has added as start up service. |
    | `start`, `stop`, `restart`, `kill`, `reload` | Start service, stop service, restart service, kill all the processes of the service, and reload configuration file of a service. |
    | `daemon-reload`                              | Ask `systemd` to reload all the configuration files. Any modification to configuration files requires that. |
    | `show`                                       | Show properties of unit. Use `--property` trick to filter them as desired. |
    | `set-property`                               | Set property of unit.                                        |
    | `list-dependcies`                            | Show all the dependencies of a `unit`.                       |
    | `list-unit-files`                            | Show all the units files, and their status                   |
    | `cat`, `edit`                                | Show content of a configuration file, even edit it.          |

- the outputs of some command will not be shown here, due to their are interactive GUI creature in CLI; and doing so will explode this document.

- examples

  - List all the units that is currently active or have failed.

    ```bash
    systemctl list-units
    ```

  - List all the units, regardless their states

    ```bash
     systemctl list-units --all
    ```

  - List all the units that are not running

    ```bash
    systemctl list-units --all --state=inactive
    ```

  - List all the units that has failed

    ```bash
    systemctl list-units --failed
    ```

  - List all the units that are `service`

    ```bash
    systemctl list-units --type=service
    ```

    - Units has different types, including
    - Service unit, Target unit (group of service), Device unit, Mount unit, Automount unit, Path unit, Scope unit, Slice unit, Snapshot unit, Socket unit, Swap unit, Timer unit.

  - Look for status of system

    ```bash
    [root@yuck ~]# systemctl status
    ● yuck
        State: degraded
        Units: 241 loaded (incl. loaded aliases)
         Jobs: 0 queued
       Failed: 1 units
        Since: Mon 2022-06-06 01:45:27 CST; 1 day 3h ago
      systemd: 251.2-1-arch
       CGroup: /
               ├─init.scope
               │ └─1 /sbin/init
    ```

  - Look of status of a specific service

    ```bash
    [root@yuck ~]# systemctl status sshd.service
    ● sshd.service - OpenSSH Daemon
         Loaded: loaded (/usr/lib/systemd/system/sshd.service; enabled; vendor preset: disabled)
         Active: active (running) since Mon 2022-06-06 01:45:30 CST; 1 day 3h ago
       Main PID: 309 (sshd)
          Tasks: 1 (limit: 2274)
         Memory: 4.5M
            CPU: 58ms
         CGroup: /system.slice/sshd.service
                 └─309 "sshd: /usr/bin/sshd -D [listener] 0 of 10-100 startups"
    
    Jun 06 01:45:30 yuck sshd[309]: Server listening on :: port 22.
    ```

  - Look for runtime status of a specific service

    ```bash
    [root@yuck ~]# systemctl is-enabled sshd.service
    enabled
    [root@yuck ~]# systemctl is-failed sshd.service
    active
    [root@yuck ~]# systemctl is-active sshd.service
    active
    ```

  - Manage a service

    ```bash
    [root@yuck ~]# systemctl stop dhcpcd
    [root@yuck ~]# systemctl start dhcpcd
    [root@yuck ~]# systemctl disable dhcpcd
    Removed "/etc/systemd/system/multi-user.target.wants/dhcpcd.service".
    [root@yuck ~]# systemctl enable dhcpcd
    Created symlink /etc/systemd/system/multi-user.target.wants/dhcpcd.service → /usr/lib/systemd/system/dhcpcd.service.
    [root@yuck ~]# systemctl kill dhcpcd
    [root@yuck ~]# systemctl restart dhcpcd
    [root@yuck ~]# systemctl reload dhcpcd
    Failed to reload dhcpcd.service: Job type reload is not applicable for unit dhcpcd.service. # Because it does not have ExecReload, so only way is daemon-reload
    [root@yuck ~]# systemctl daemon-reload
    ```

  - Show properties of a service

    ```bash
    [root@yuck ~]# systemctl show dhcpcd | head -n 5
    Type=forking
    ExitType=main
    Restart=no
    PIDFile=/run/dhcpcd/pid
    NotifyAccess=none
    [root@yuck ~]# systemctl show dhcpcd --property LimitCPU
    LimitCPU=infinity
    ```

  - Set properties of a service

    ```bash
    [root@yuck ~]# systemctl set-property dhcpcd CPUShares=500
    ```

    - To master that, you need to understand `cgroups`, which allows to resources (such as CPU time, system memory, network bandwidth, or a combination of these resources).
    - Shares, can be used to set relative weight of resources. Resources is distributed proportional to weight, in case the system run out of resources.
    - **Someone else may work on creating Cgroup presentation.** I haven't master it, yet.

  - Some units has to be launched after some other units.

    - ```bash
      [root@yuck ~]# systemctl list-dependencies mariadb
      mariadb.service
      ```

  - List unit files

    - ```bash
      [root@yuck ~]# systemctl list-unit-files
      ```

      - This gives STATE of configuration files:

        | command    | function                                |
        | ---------- | --------------------------------------- |
        | `enabled`  | start on boot                           |
        | `disabled` | does not start on boot                  |
        | `static`   | won't run independently, no `[Install]` |
        | `masked`   | can not start on boot                   |

      - Notice this is not the same like status of service
  
  - Show property of `cpus.service`
  
    - ```bash
      [root@yuck ~]# systemctl show --property=Type,MainPID cups.service
      Type=notify
      MainPID=46758
      ```
  
    -

##### Writing `Unit`

- Most exciting part is here. Unit is a configuration file follows `toml`/`ini` like style and format.
- `Unit` files is stored in directory, `/etc/systemd/system`, which the majority of them are just symbol links to `/usr/lib/systemd/system`

- Different tables for the configuration file.

  - `[Unit]` defines metadata of the unit

    - | key               | value                                                        |
      | ----------------- | ------------------------------------------------------------ |
      | `Description`     | A short human readable title of the unit. Title case         |
      | `Documentation`   | List of URIs as documentation                                |
      | `Wants`           | The units in this option will be started; however, it may failed without causing this unit to fail. |
      | `Requires`        | The units in this option must be started for this unit to start; their failure causes this unit to fail. |
      | `BindsTo`         | Same as `Requires`. But this unit will be stopped if `BindsTo` unit are stopped |
      | `Before`, `After` | Execute before or after this `Unit`                          |
      | `Conflict`        | Can not execute together                                     |

    - Use space to separate multiple items.

  - `[Install]` defines how to start, whether it starts on boot, etc.

    - | key          | value                                                        |
      | ------------ | ------------------------------------------------------------ |
      | `WantedBy`   | One or more targets. When this `Unit` is enabled, the symbolic link of it will be putted into `/etc/systemd/system/<target_name>.wants/` directory. For example, `/etc/systemd/system/multi-user.target.wants/sshd.service` |
      | `RequiredBy` | One or more target. When this `Unit` is enabled, the symbolic link of it will be putted into `/etc/systemd/system/<target_name>.required/` directory |
      | `Alias`      | Other name for this unit to start.                           |
      | `Also`       | If this Unit enabled, other Unit that will be enabled.       |

  - `[Service]` defines some configuration for `service` specifically.

    - | key                             | value                                                        |
      | ------------------------------- | ------------------------------------------------------------ |
      | `Type`                          | Type of the process:<br />- `simple`, execute main process, once forked, done<br />- `exec`, execute main process, wait until binary has been executed, done<br />- `forking`, when parent process is exited, done<br />- `oneshot`, when main process is exited, done<br />- `dbus`, use dbus to executed<br />- `notify`, considered done until it send a notification, then `Systemd` will continue<br />- `idle`, wait until last (within 5 seconds) to execute |
      | `ExecStart`                     | Execute to start it                                          |
      | `ExecStartPre`, `ExecStartPost` | Execute before or after `ExecStart`. If any one of them failed, the unit fails. Every process must have been executed before it continues |
      | `ExecReload`                    | Execute when ask to reload                                   |
      | `ExecStop`                      | Execute when ask to stop                                     |
      | `Restart`                       | When `Systemd` will restart this service (notice if you manually stop/restart it, it does restart/stop):<br />- `no` never restart<br />- `always` always restart, regardless why<br />- `on-success` if it exited normally, restart<br />- `on-failure` if return code != 0 or process it not exited normally, restart it<br />- `on-abnormal` if process is killed or it times out, restart<br />- `on-abort` restart if process is killed<br />- `on-watchdog` restart if `keep-alive ping` timeout. |
      | `RestartSec`                    | How many seconds before restart. Default as 100ms.           |
      | `Environment`                   | Define of environment variable, K-V pair separated by space, e.g., `Environment=CLUB='CyberPatriot' SCHOOl='GSMST'` |
      | `TimeoutSec`                    | Set max time for this service to be started or stopped simultaneously. One may set `TimeoutStartSec` and `TimeoutStopSec` individually. `infinity` means never timeout |

- example

  - From `systemctl cat sshd.service`

    ```ini
    [Unit]
    Description=OpenSSH Daemon
    Wants=sshdgenkeys.service
    After=sshdgenkeys.service
    After=network.target
    
    [Service]
    ExecStart=/usr/bin/sshd -D
    ExecReload=/bin/kill -HUP $MAINPID
    KillMode=process
    Restart=always
    
    [Install]
    WantedBy=multi-user.target
    ```

  - From `systemctl cat vsftpd.service`

    ```ini
    [Unit]
    Description=vsftpd daemon
    After=network.target
    
    [Service]
    ExecStart=/usr/bin/vsftpd
    ExecReload=/bin/kill -HUP $MAINPID
    KillMode=process
    
    [Install]
    WantedBy=multi-user.target
    ```

  - If you want to write a unit, just copy and paste from those service and modify them a little. Then put it into `/usr/lib/systemd/system`. After `systemctl daemon-reload`, you should be able to use `systemctl` to manage your new `Unit`

## `GUI`

### `Xrandr` [^2]

[^2]: <https://www.x.org/releases/current/doc/man/man1/xrandr.1.xhtml> for more details.

- An official configuration utility to the `RandR` X Window System extension. You almost always want to run this for your virtual machine.

- `RandR` is an device dependent components, that provides ability to
  - resize, rotate, and reflect root window of screen
  - set screen refresh rate and screen size
  - **Control multiple monitors**

- `xrandr [option]`

  - | option                    | function                                                     |
    | ------------------------- | ------------------------------------------------------------ |
    | `–display`                | specify display to be adjusted (X abstraction of X server)   |
    | `–screen`                 | specify screen to be manipulated (X abstraction of virtual monitors, which may consist of multiple frame buffers) |
    | `–output`                 | specify output to be reconfigured (physical output)          |
    | `–auto`                   | enable output and use their preferred mode                   |
    | `–fb <width>x<height>`    | specify the screen size                                      |
    | `–dpi dpi`                | self explanatory                                             |
    | `--brightness brigthness` | self explanatory                                             |

- example

  - display available screens

    - ```bash
      [root@yuck ~]# xrandr --query | grep -P '(?=\b)connected'
      eDP-1 connected (normal left inverted right x axis y axis)
      HDMI-2 connected primary 1920x1080+0+0 (normal left inverted right x axis y axis) 527mm x 296mm
      ```

  - enable output `eDP-1`. put it below`HDMI-2`

    - ```bash
      [root@yuck ~]# xrandr --output eDP-1 --below HDMI-2
      ```

  - enable output `eDP-1` and mirror

    - ```bash
      [root@yuck ~]# xrandr --output eDP-1 --same-as HDMI-2
      ```

  - change `eDP-1`'s resolution to `1024x768` (one of existing mode printed from `--query`)

    - ```bash
      [root@yuck ~]# xrandr --output eDP-1 --size 1024x768
      ```

  - force `edP-1`'s resolution to `1024x1024` (not existing in mode)

    - ```bash
      [root@yuck ~]# gtf 1024 1024 60 | perl -nle 'print $1 if /^\s*Modeline\s*(.*)$/;' | head -n 1 "1024x1024_60.00"  87.51  1024 1088 1200 1376  1024 1025 1028 1060  -HSync +Vsync
      [root@yuck ~]# xrandr --newmode "1024x1024_60 Lecture"  87.51  1024 1088 1200 1376  1024 1025 1028 1060  -HSync +Vsync
      [root@yuck ~]# xrandr --addmode eDP-1 "1024x1024_60 Lecture"
      [root@yuck ~]# xrandr --output eDP-1 --mode "1024x1024_60 Lecture"
      ```

  - delete this mode

    - ```bash
      [root@yuck ~]# xrandr --delmode eDP-1 '1024x1024_60 Lecture'
      [root@yuck ~]# xrandr --rmmode '1024x1024_60 Lecture'
      ```

- a little script

  - ```bash
    #!/usr/bin/bash
    
    function set_resolution() {
        local x=${1:-1920}
        local y=${2:-1080}
        local refresh=${3:-60}
        local output=${4:-$(xrandr | grep ' connected' | head -n 1 | cut -d ' ' -f 1)}
    
        local mode="$(gtf $x $y $refresh | perl -nle 'print $1 if /^\s*Modeline\s*(.*)$/;' | head -n 1)"
        local mode_name="$(echo $mode | cut -d ' ' -f 1 | xargs)"
    
        echo " --newmode $mode" | xargs xrandr &>/dev/null
    
        xrandr --addmode $output $mode_name
        xrandr --output $output --mode $mode_name
    
        local sec=9
        echo -ne "Keep current configuration? (y/n) [$sec] "
        while [ $sec -gt 0 ]; do
            read -n 1 -t 1 answer
            if [ "$answer" == "y" ]; then
                echo ""
                return 0
            elif [ "$answer" == "n" ]; then
                echo ""
                xrandr --output $output --auto
                xrandr --delmode $output $mode_name
                xrandr --rmmode $mode_name
                return 1
            fi
            sec=$((sec - 1))
            echo -ne "\rKeep current configuration? (y/n) [$sec] "
        done
    
        xrandr --output $output --auto
        xrandr --delmode $output $mode_name
        xrandr --rmmode $mode_name
    }
    
    set_resolution 1920 1000 60
    ```

## Reference

- `man` pages from <https://manned.org/>
- <https://www.gnu.org/software/coreutils/>
- <https://www.linuxprobe.com/basic-learning-02.html#23> An interesting book
- <https://www.ruanyifeng.com/blog/2016/03/systemd-tutorial-commands.html> An interesting blog
- <https://catonmat.net/cookbooks/curl> An interesting book about curl
