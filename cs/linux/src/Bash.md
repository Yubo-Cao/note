# Bash

- `Bourne-Again SHell`,an sh-compatible command-line interpreter. It is one choice of shell of Linux/Unix system.

- I am going to use `zsh/fish` rather than `bash` for the following examples.
  - `zsh/fish` is designed for interactive use and support a lot of plugins which auto-complete command. They are simply simpler to use as well as study compare to `bash`
  - all of them are `bash` compatible. hence, everything that works on `bash` are expected to work with them

## Shell

- definition

  - User interface for user to communicate with kernel

  - A interpreter that execute command received from user, as well as support control flow/variable and other feature that a programming language would contain.

- see the login shell of the current user

  - ```bash
    root@yuck ~# echo $SHELL
    /usr/bin/zsh
    ```

- see the shell you are currently using

  - ```bash
    root@yuck ~# ps -p $$
        PID TTY          TIME CMD
      21112 pts/1    00:00:00 zsh
    ```

    - notice `$$` is PID of current bash session.

- see all possible shell that you may choose

  - ```bash
    root@yuck /root# cat /etc/shells
    # /etc/shells: valid login shells
    /bin/sh
    /bin/bash
    /usr/bin/sh
    /usr/bin/fish
    /usr/bin/zsh
    ```

- print current version

  - ```bash
    root@yuck /root# echo $BASH_VERSION
    5.1.16(1)-release
    ```

## Syntax

### `;`

- Use `;` to terminate a statement.

  - To contain multiple command in one line

    ```bash
    root@yuck /root# ls /DNE; ls
    ls: cannot access '/DNE': No such file or directory
    Desktop    Downloads  Pictures  snap       Videos
    Documents  Music      Public    Templates
    ```

  - Notice command will execute regardless whether previous statement success or fail.

### `&&` and `||`

- `&&` is used to combine two command.
  - If the first command success, the second command will execute.
  - If the first command fail, the second command will not execute.
- `||` is used to combine two command.
  - If the first command success, the second command will not execute.
  - If the first command fail, the second command will execute.
- example
  - display content of file as well as it's properties, if exists.

    ```bash
    root@yuck /root/Lecture# cat filelist.txt && ls -l filelist.txt
    /root
    /root/Lecture
    -rw-r--r-- 1 root root 49 Jul  8 18:57 filelist.txt
    ```

  - create `bar` if can't create `foo` directory

    ```bash
    root@yuck /root/Lecture# mkdir foo || mkdir bar
    ```
  
## Globing

- when bash parse your command into token, it automatically expand special characters in the token, and then call the corresponding command

- | char                 | function                                                     |
  | -------------------- | ------------------------------------------------------------ |
  | `~[user]`            | home directory of `user` (default to `whoami`). error message is print if `user` does not exist |
  | `?`                  | any single character                                         |
  | `*`                  | 0 (include `""`) to infinite number of any character, `.* `. ignore file started with `.`. |
  | `[...]`              | any character between `[` and `]` . one may use `-` to represents interval. multiple intervals are supported. |
  | `[^...]` or `[!...]` | any character that's not between `[` and `]`                 |
  | `{...}`              | all value in between `{` and `}` will be expanded. no space are accepted. one may use this to produce Cartesian product. use `..` for interval. |

- | expr                             | function                                                     |
  | -------------------------------- | ------------------------------------------------------------ |
  | `$(cmd)` or <code>\`cmd\`</code> | execute the command and use it's result as return value      |
  | `$((expr))`                      | calculate **integer** expression, return 0 for false and 1 for true. `Java`/`C`/ family operator |
  | `[[:alnum:]]`                    | character class. not more useful than literally type `[A-Z0-9]` |
  | `$var`                           | evaluate this variable                                       |
  | `${!str*}` or `${!str@}`         | find all variables that match `str`                          |
  | `?(pat)`                         | match 0 or 1 time of `pat`                                   |
  | `*(pat)`                         | match 0 or infinite time of `pat`                            |
  | `+(pat)`                         | match 1 or infinite time of `pat`                            |
  | `@(pat)`                         | match only1 time of `pat` (i.e., match and then must not match again) |
  | `!(pat)`                         | match if does not match `pat`                                |

- minutiae

  - `?`, `*`, and character classes are like regex -- it must match something in order for you to get anything print out. However, `{...}` expand regardless whether it match or not, it just expand.
  - all those matching classes, if they don't understand your meaning (i.e., syntax error), they fail silently and leave the token unmodified.

- configuration

  - manage glob

    ```bash
    root@yuck ~# set -o noglob
    root@yuck ~/Lecture# echo *
    *
    root@yuck ~/Lecture# set +o noglob
    root@yuck ~/Lecture# echo *
    ac.txt ad.txt bc.txt bd.txt dir filelist.txt
    ```

  - | option       | function                                                     |
    | ------------ | ------------------------------------------------------------ |
    | `dotglob`    | include hidden file when use `*`                             |
    | `nullglob`   | return `""` when no file matches                             |
    | `failglob`   | throw an exception when no file matches (i.e., print `bash: no match: x`) |
    | `extglob`    | support extended glob                                        |
    | `nocaseglob` | globing ignore case                                          |
    | `globstar`   | let `**` to match sub directories                            |


- example

  - sample setup

    ```bash
    root@yuck ~/Lecture# ls -a
    .  ..  ac.txt  ad.txt  bc.txt  bd.txt  dir  .hidden
    ```

  - get home directory of `root` and `yubo`. Observe `~DNE` (do not exist)

    ```bash
    root@yuck ~# echo ~
    /root
    root@yuck ~# echo ~yubo
    /home/yubo
    root@yuck ~# echo ~DNE
    ~DNE
    ```

  - get current directory without `pwd`

    ```bash
    root@yuck ~# echo ~+ 
    /root
    ```

  - find files with 2 character long filename and end with `.txt`. find files with 1 char long file name (DNE)

    ```bash
    root@yuck ~/Lecture# echo ??.txt
    1a.txt 1b.txt 3a.txt 3b.txt 5a.txt 5b.txt 7a.txt 7b.txt ac.txt ad.txt bc.txt bd.txt
    root@yuck ~/Lecture# echo ?.txt
    ?.txt
    ```

  - find all file end with `.txt`

    ```bash
    root@yuck ~/Lecture# echo *.txt
    1a.txt 1b.txt 3a.txt 3b.txt 5a.txt 5b.txt 7a.txt 7b.txt ac.txt ad.txt bc.txt bd.txt
    ```

  - find all files in the directory, hidden or not hidden.

    ```bash
    root@yuck ~/Lecture# echo *
    1a.txt 1b.txt 3a.txt 3b.txt 5a.txt 5b.txt 7a.txt 7b.txt ac.txt ad.txt bc.txt bd.txt dir
    root@yuck ~/Lecture# echo .*
    . .. .hidden
    root@yuck ~/Lecture# echo .[^.]*
    .hidden
    # root@yuck ~/Lecture# shopt -s extglob
    root@yuck ~/Lecture# echo ?(.[^.])*
    1a.txt 1b.txt 3a.txt 3b.txt 5a.txt 5b.txt 7a.txt 7b.txt ac.txt ad.txt bc.txt bd.txt dir .hidden
    ```

  - find all subdirectories

    - ```bash
      root@yuck ~/Lecture# shopt -s globstar
      root@yuck ~/Lecture# echo **/*
      1a.txt 1b.txt 3a.txt 3b.txt 5a.txt 5b.txt 7a.txt 7b.txt ac.txt ad.txt bc.txt bd.txt dir dir/a dir/a/c dir/a/d dir/b dir/b/c dir/b/d
      ```

  - find all file that start with `a` followed by `c` or `d`

    - ```bash
      root@yuck ~/Lecture# echo a[cd].txt
      ac.txt ad.txt
      root@yuck ~/Lecture# echo DNE[cd].txt
      DNE[cd].txt
      ```

  - find all file that start with a number and all file that does not start with a number

    - ```bash
      root@yuck ~/Lecture# echo [0-9]*
      1a.txt 1b.txt 3a.txt 3b.txt 5a.txt 5b.txt 7a.txt 7b.txt
      root@yuck ~/Lecture# echo [^0-9]*
      ac.txt ad.txt bc.txt bd.txt dir
      ```

  - braces does not care about whether the file exists, it just expand. multiple brace can be used to create cartesian product (`itertools.product`)

    - ```bash
      root@yuck ~/Lecture# echo {1..3}
      1 2 3
      root@yuck ~/Lecture# echo s{a,e}p{a,e}r{a,e}te
      saparate saparete saperate saperete separate separete seperate seperete
      root@yuck ~/Lecture# echo {j{p,pe}g,png}
      jpg jpeg png
      ```

  - move `1a.txt` as backup

    - ```bash
      root@yuck ~/Lecture# mv 1a.txt{,.bak}
      ```

  - move all file start with a number to backup, provided then are not backup itself

    - ```bash
      root@yuck ~/Lecture# printf "%s\n" [0-9]*.txt!(.bak) | parallel mv {} {}.bak
      # Reset it
      root@yuck ~/Lecture# printf "%s\n" [0-9]*.bak | parallel mv {} {.}
      ```

  - `range`

    - ```bash
      root@yuck ~/Lecture# echo {1..4}
      1 2 3 4
      root@yuck ~/Lecture# echo {1..4..2}
      1 3
      ```

  - print `today is %s`

    - ```bash
      root@yuck ~/Lecture# echo "today is $(date)"
      today is Sat Jul  9 07:55:34 AM EDT 2022
      ```

## Escape

- bash provided a lot of special characters. And you want to still be able to use those special characters.

- use backslash to escape special characters or create some white spaces

  - ```bash
    root@yuck ~/Lecture# echo \$20\ dollars
    $20 dollars
    root@yuck ~/Lecture# echo "this is a backslash \\"
    this is a backslash \
    # You can escape newline character, too
    root@yuck ~/Lecture# echo long\
    > line
    longline
    # Must use in echo -e
    root@yuck ~/Lecture# echo -e "newline \n"
    newline
    ```

- single quoted string

  - nothing would be escaped, `\'` don't work (unless you put `$` in front)

    ```bash
    root@yuck ~/Lecture# echo '$SHELL'
    $SHELL
    # Pathological example. Normal human should use double quote
    root@yuck ~/Lecture# echo $'this is a single quote \''
    this is a single quote '
    ```

- double quoted string

  - most of special character would loss it's meaning. However, `$`, <code>\`</code>, and `\` would still work. 

    ```bash
    root@yuck ~/Lecture# echo "login shell is $SHELL"
    login shell is /usr/bin/zsh
    root@yuck ~/Lecture# echo "today is $(date)"
    today is Sat Jul  9 08:04:26 AM EDT 2022
    root@yuck ~/Lecture# echo "today is `date`"
    today is Sat Jul  9 08:04:31 AM EDT 2022
    root@yuck ~/Lecture# echo "this is a double quote \""
    this is a double quote "
    ```

- both double and single quoted string support multiline literal

  - ```bash
    root@yuck ~/Lecture# echo 'hello
    world'
    hello
    world
    root@yuck ~/Lecture# echo "hello
    world"
    hello
    world
    ```

- heredoc

  - this is one way to type multiline string literal and pipe it as standard input of a command. In heredoc, variable/command would work, but double quote, single quote, and globing of file would not.

    ```bash
    root@yuck ~/Lecture# cat << EOF
    $foo *
    "$foo"
    '$foo'
    $(date)
    `date`
    $((1+1))
    EOF
    hello java *
    "hello java"
    'hello java'
    Sat Jul  9 08:09:17 AM EDT 2022
    Sat Jul  9 08:09:17 AM EDT 2022
    2
    ```

  - if you nothing to work, put `'` around the EOF

    ```bash
    root@yuck ~/Lecture# cat << 'EOF'
    $foo *
    "$foo"
    '$foo'
    $(date)
    `date`
    $((1+1))
    EOF
    $foo *
    "$foo"
    '$foo'
    $(date)
    `date`
    $((1+1))
    ```

  - you don't have to use EOF. Any token would work.

- here string

  - some command are opinionated and only accept standard input. 

    ```bash
    root@yuck ~/Lecture# md5sum <<< 'Hello Java'
    e99b24f8fb9261df3b27d097ab0ffe07  -
    ```

## Variable

### Environment Variable

| variable | function |
| -------- | -------- |
|`BASHPID`      |The process ID of the Bash process|
|`BASHOPTS`     |The parameters of the current shell, which can be modified with the `shopt` command|
|`DISPLAY`      |The monitor name of the graphical environment, usually `:0`, indicating the first monitor of the X Server|
|`EDITOR`       |The default text editor|
|`HOME`         |The user's home directory|
|`HOST`         |The name of the current host|
|`IFS`          |The separator between words, which by default is a space|
|`LANG`         |The character set and the language encoding, such as zh_CN|
|`PATH`         |A list of directories separated by colons, which will be searched when the executable name is entered|
|`PS1`          |Shell prompt|
|`PS2`          |The secondary shell prompt when entering multi-line commands|
|`PWD`          |Current working directory|
|`RANDOM`       |Returns a random number between 0 and 32767|
|`SHELL`        |Shell name|
|`SHELLOPTS`    |Parameters for the set command that started the current shell|
|`TERM`         |Terminal type name, e.g., support 256 color, true color, or monochrome?|
|`UID`          |The ID number of the current user|
|`USER`         |The username of the current user|

- use `env` to print all environment variables

  ```bash
  root@yuck ~# env | head -n 5
  SHELL=/usr/bin/zsh
  LSCOLORS=Gxfxcxdxbxegedabagacad
  LESS=-R
  PWD=/root
  LOGNAME=root
  ```

- use `set` to print all environment and custom variable

  ```bash
  root@yuck ~# set | head -n 5
  BASH=/usr/bin/bash
  BASHOPTS=checkwinsize:cmdhist:complete_fullquote:expand_aliases:extquote:force_fignore:globasciiranges:histappend:hostcomplete:interactive_comments:progcomp:promptvars:sourcepath
  BASH_ALIASES=()
  BASH_ARGC=([0]="0")
  BASH_ARGV=()
  ```

### Declare

- ```bash
  name=val
  ```

- there is no type declaration, like `String name=val` or `int count=10`. Only type in `bash` is string/array.

- there must not be any space between `name` and `val`. If there are, bash treat it as executing command `name` with `=` and `val` as arguments

### Read

- ```bash
  $name
  # or
  ${name}
  ```

- use above syntax to read a variable. if variable is undefined, an empty string is returned. The reason of using second syntax is when the variable is mixed in a part of larger string, where the subsequent part (that are not variable name) are still legitimate identifier component.

  ```bash
  root@yuck ~# a=10
  root@yuck ~# echo "$a"
  10
  root@yuck ~# echo "${a}pple"
  10pple
  root@yuck ~# echo "$apple"
  ```

- it is almost always a bug if a variable is read not inside double quote.

  ```bash
  root@yuck ~# b="Hello          world"
  # Echo received 2 arguments
  root@yuck ~# echo $b
  Hello world
  # Echo receivced only 1 arguments
  root@yuck ~# echo "$b"
  Hello          world
  ```

- one may read a variable of a variable (i.e., the pointer of a pointer) through `${!var}` syntax. 

  ```bash
  root@yuck ~# echo ${!c}
  /usr/bin/zsh
  ```

### Delete

- use `unset`

  ```bash
  root@yuck ~# unset c
  root@yuck ~# echo $c
  ```

- set it to empty string

  ```bash
  root@yuck ~# b=''
  root@yuck ~# echo $b
  ```

### Export

- using `export` to make subshell have access to the variable in the parent shell

```bash
root@yuck ~# export a=1
root@yuck ~# bash
root@yuck ~# echo $a
1
root@yuck ~# a=10
root@yuck ~#
exit
root@yuck ~# echo $a
```

### Magic

| variable | function                                                     |
| -------- | ------------------------------------------------------------ |
| `$?`     | exit code of previous command, 0 is OK, anything else is error |
| `$$`     | `$BASHPID`                                                   |
| `$_`     | last arguments of previous command                           |
| `$!`     | PID of last background process                               |
| `$0`     | name of shell or name of srcipt                              |
| `$-`     | arguments to startup `bash`                                  |
| `$#`     | number of arguments                                          |
| `$@`     | all the arguments in a script, as an array                   |

- example

  ```bash
  root@yuck ~/Lecture# bash magic.sh arg1
  0               exit code
  10916           pid
  /usr/bin/bash   last argument
                  PID of last async
  magic.sh        script name
  hB              bash startup args
  1               argc
  arg1            argv
  ```

  ```bash
  root@yuck ~/Lecture# cat magic.sh
  #!/usr/bin/bash
  vars=("$?" "$$" "$_" "$!" "$0" "$-" "$#" "$@")
  desc=("exit code" "pid" "last argument" "PID of last async" "script name" "bash startup args" "argc" "argv")
  
  for idx in "${!vars[@]}"; do
          printf "%-15s   %s\n" "${vars[$idx]}" "${desc[$idx]}"
  done
  ```

### Default Var

- I mainly use this to get similar functionality like `Python`'s default arguments. They are used to guarantee that variable are not null

- | expr              | function                                                     |
  | ----------------- | ------------------------------------------------------------ |
  | `${var:-default}` | if var exists, return `var`, else return `default`           |
  | `${var:=default}` | if var exists, return `var`, else assign `var=default` and return `default` |
  | `${var:+default}` | if var exists, return `default`, else empty                  |
  | `${var:?msg}`     | if `var` exists, return `var`, else throw an Exception with `msg` as message |

- example

  - if count exists, return count; else 0

    ```bash
    root@yuck ~/Lecture# echo "${count:-0}"
    0
    root@yuck ~/Lecture# count=10
    root@yuck ~/Lecture# echo "${count:-0}"
    10
    ```

  - if name does not exists, throw an Exception

    ```bash
    root@yuck ~/Lecture# echo "${name:?Name is required.}"
    zsh: name: Name is required.
    root@yuck ~/Lecture# name="yubo"
    root@yuck ~/Lecture# echo "${name:?Name is required.}"
    yubo
    ```

  - if password does not exists, assign password to `cyb!erp@triot` (grab Anish for exact password to use. I forgot how to spell it.) 

    ```bash
    root@yuck ~/Lecture# echo "${passwd:=cyb\!erp@atriot}"
    cyb!erp@atriot
    root@yuck ~/Lecture# echo $passwd
    cyb!erp@atriot
    ```

  - check if `password` is set

    ```bash
    root@yuck ~/Lecture# if [ ${password:+1} ]; then echo "passwwod is set"; else echo "you haven't set a password"; fi
    you haven't set a password
    root@yuck ~/Lecture# password=cyberpatriot
    root@yuck ~/Lecture# if [ ${password:+1} ]; then echo "passwwod is set"; else echo "you haven't set a password"; fi
    passwwod is set
    ```

## Shell Built-in

### `type`

- Determine type of the command that the shell will execute. For example, `cut` is a binary file provided by `coreutils`, where `type` and `cd` are shell built-in.
- `type [option] cmd`
- | option | function |
  | ------ | --------- |
  | `-a` | display all the locations containing the `cmd` |
- example
  - determine type of `type`

    ```bash
    root@yuck /root/Lecture# type type
    type is a shell built-in.
    ```
  
  - determine type of `cut`

    ```bash
    root@yuck /root/Lecture# type cut
    cut is /usr/bin/cut
    ```
  
  - display all the locations containing `echo` executable.

    ```bash
    root@yuck /root/Lecture# type -a echo
    echo is a shell builtin
    echo is /usr/bin/echo
    echo is /bin/echo
    ```

### `shopt`

- Manage Bash shell options. Stand for `set option`

- `shopt [option] option_name`

- | option | function                                     |
    | ------ | -------------------------------------------- |
    | `-u`   | unset an option (off)                        |
    | `-s`   | set an option (on)                           |
    | `-p`   | print a list of all options and their status |

### `set`

- Manage generic Unix/Linux shell option
- `set +o/-o option_name`
  - `+o` is on
  - `-o` is off

### `declare`

- Declare variables and give them attributes.
- `declare [option] name=val`

- | option | function                     |
  | ------ | ---------------------------- |
  | `-a`   | declare an array             |
  | `-f`   | function                     |
  | `-F`   | output all name of functions |
  | `-i`   | declare an integer           |
  | `-l`   | lower case                   |
  | `-p`   | print variable info          |
  | `-r`   | const                        |
  | `-u`   | upper case                   |
  | `-x`   | `export`                     |

- example

  - integer arithmetic

    - ```bash
      root@yuck ~# v1=12; v2=5
      root@yuck ~# declare -i result
      root@yuck ~# result=v1*v2
      root@yuck ~# echo $result
      60
      ```

  - const

    - ```bash
      root@yuck ~# declare -r pi=3.1415926
      root@yuck ~# echo $pi
      3.1415926
      root@yuck ~# pi=2323
      zsh: read-only variable: pi
      ```

  - uppercase letter

    - ```bash
      root@yuck ~# declare -u val=upper
      root@yuck ~# echo $val
      UPPER
      ```

  - lowercase letter

    - ```bash
      root@yuck ~# declare -l val=LOWER
      root@yuck ~# echo $val
      lower
      ```

  - print variable info

    - ```bash
      root@yuck ~# declare -p val
      typeset -l val=LOWER
      ```

  - print all functions

    - ```bash
      root@yuck ~# declare -f | head -n 3
      VCS_INFO_formats () {
              setopt localoptions noksharrays NO_shwordsplit
              local msg tmp
      #...
      ```

## String

### `len`/`length`

- `${#str}` to find length of a string

  ```bash
  root@yuck ~# str="hello"
  root@yuck ~# echo ${#str}
  5
  ```

### `[:]`/`substring`

- `${str:offset[:length]}` (notice this is not `start_index:end_index`). If length is unspecified, then it is assumed to substr until end of string.

  ```bash
  root@yuck ~# str="hello"
  root@yuck ~# echo ${str:0:1}
  h
  root@yuck ~# echo ${str:4:1}
  o
  root@yuck ~# echo ${str:5:1}
  
  ```

- However, if the length or length is negative, then it is equivalent as specify `$((${#str} + length/offset)`. If offset is negative, then there must be a space before that.

  ```bash
  root@yucu:~# echo ${str: -2:1}
  l
  root@yucu:~# echo ${str:-2:1}
  hello
  root@yucu:~# echo ${str: -5:-2}
  hel
  root@yucu:~# echo ${str: -5:2}
  he
  ```

### `{var##?pat}`/`stripLeading`

- Check if the string that `var` pointed to matches `pat` at start. If so, remove matching part and return; otherwise, remove nothing and return string unchanged.

  - `#` determines if the matching is greedy. A greedy match means, it tries to match as much character as possible.

  ```bash
  ```

  
