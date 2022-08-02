# `Samba` | `NFS` & File Sharing

## `Samba`

- 1987, Microsoft and Intel created `SMB` (Server Messages Block) protocol to facilitate sharing of files / printer in the same LAN.
- Samba is an open source implementation of SMB protocol by Tridgwell in Linux.

### Install

- ```bash
  root@yuck ~# apt install samba
  ```

### Config

- The config file is stored at `/etc/samba/samba.cnf`

```bash
root@yuck ~# rg -v '^[#;]' /etc/samba/smb.conf | rg -v '^$'
[global]
   workgroup = WORKGROUP
   server string = %h server (Samba, Ubuntu)
   log file = /var/log/samba/log.%m
   max log size = 1000
   logging = file
   panic action = /usr/share/samba/panic-action %d
   server role = standalone server
   obey pam restrictions = yes
   unix password sync = yes
   passwd program = /usr/bin/passwd %u
   passwd chat = *Enter\snew\s*\spassword:* %n\n *Retype\snew\s*\spassword:* %n\n *password\supdated\ssuccessfully* .
   pam password change = yes
   map to guest = bad user
   usershare allow guests = yes
[printers]
   comment = All Printers
   browseable = no
   path = /var/spool/samba
   printable = yes
   guest ok = no
   read only = yes
   create mask = 0700
[print$]
   comment = Printer Drivers
   path = /var/lib/samba/printers
   browseable = yes
   read only = yes
   guest ok = no
```

- Since we are only interested in sharing files, modify our config file as follows

```bash
root@yuck /e/samba# cat /etc/samba/smb.conf
[global]
        workgroup = WORKGROUP
        security = user
        passdb backend = tdbsam
```

- The `samba` file consists of sections and parameters. 
- A section beings with the name of section like `[global]` with `k=v` pairs in between. 
  - Use new line character to add more parameters, and `\`to continue between lines.
  - `[global]` serve as default configuration for everything else.
  - `[users]` serve as easy way to share home directories of users, and `[printers]` serve as an easy way to share printers

#### Important Parameters

| Option                          | Function                                                     |
| ------------------------------- | ------------------------------------------------------------ |
| `security`                      | default is `security = user`, affects the authentication methods used by `samba`.<br />- `USER` default, client must login with a valid username and password before use. <br />- `DOMAIN` validate the username/password by passing it to a windows NT Primary or backup domain controller |
| `passdb backend`                | choose backend for storing user and possibly group information<br />- `smbpasswd`, deprecated plaintext passdb backend<br />- `tdbsam`, tdb based password storage backend<br />- `lldapsam`, LDAP (light weight directory access protocol) based passdb backend |
| `printing`                      | change printing backend, e.g., `bsd`, `cups`, `aix`, etc     |
| `printcap name`                 | set `printcap name = cups` will use printer configuration file provided by `CUPS`. such file looks like <br />```bash<br />print1|My Printer 1<br />print2|My Printer 2<br />``` to specify alias of a printer. |
| `load printers = yes`           | whether or not load printers                                 |
| `cups options = raw`            | options string that passed directly to cups                  |
| `comment`                       | description                                                  |
| `valid users`                   | list of users that are allowed to login to this service. empty this list to allow any user to login, except in `invalid users`. |
| `invalid users`                 | list of users that are forbidden to login to this service.   |
| `browseable`                    | whether this share can be seen in the list of available shares. |
| `read only`                     | prevent write                                                |
| `inherit acls`                  | make sure if access control list exists in the parent directory, then inherit them in new file created here after |
| `printable`                     | whether a printer can be used                                |
| `path`                          | the shared directory                                         |
| `write list`                    | list of users that can write                                 |
| `force group`                   | user group list                                              |
| `create mask`, `directory mask` | apply `&` with translated mask from windows file system as to avoid excessive permission. |

- A little bit of variable substitutions

  - | variable | substitution                                       |
    | -------- | -------------------------------------------------- |
    | `%S`     | name o the current service                         |
    | `%w`     | the winbind separator, which usually is`\`         |
    | `%D`     | name of the domain or workgroup of he current user |

#### Config

```bash
[lecture]
    comment = This is for lecturing purpose.
    path = /home/smbuser/Lecture
    guest ok = false
    browsable = false
    writable = yes
```

#### Create Directory

```bash
root@yuck ~/Lecture# mkdir -p /home/smbuser/Lecture/
```

- Create user of `samba` service, using `pdbedit`, which is used to edit samba user data base by `useradd smbuser && pdbedit -a smbuser`

##### `pdbedit`

- `pdbedit [option] username`

- | option           | function    |
  | ---------------- | ----------- |
  | `-a`, `--create` | create user |
  | `-x`, `--delete` | delete user |
  | `-L`             | list user   |
  | `-v`             | verbose     |

- example

  - we want to create a user called `smbuser`

    - ```bash
      root@yuck ~/Lecture# useradd -d /home/smbuser/ smbuser
      root@yuck ~/Lecture# pdbedit -a smbuser
      new password:
      retype new password:
      Unix username:        smbuser
      NT username:
      Account Flags:        [U          ]
      User SID:             S-1-5-21-1897428662-2202846973-1737022946-1001
      Primary Group SID:    S-1-5-21-1897428662-2202846973-1737022946-513
      Full Name:
      Home Directory:       \\YUCK\smbuser
      HomeDir Drive:
      Logon Script:
      Profile Path:         \\YUCK\smbuser\profile
      Domain:               YUCK
      Account desc:
      Workstations:
      Munged dial:
      Logon time:           0
      Logoff time:          Wed, 06 Feb 2036 10:06:39 EST
      Kickoff time:         Wed, 06 Feb 2036 10:06:39 EST
      Password last set:    Sun, 03 Jul 2022 20:24:36 EDT
      Password can change:  Sun, 03 Jul 2022 20:24:36 EDT
      Password must change: never
      Last bad password   : 0
      Bad password count  : 0
      Logon hours         : FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
      ```

  - we want to remove a user called `smbuser`

    - ```bash
      # Remove the user in the database first
      root@yuck ~/Lecture# pdbedit -x smbuser
      root@yuck ~/Lecture# pdbedit -L
      # Remove the user in linux completely too!
      root@yuck ~/Lecture# userdel -r smbuser
      userdel: smbuser mail spool (/var/mail/smbuser) not found
      ```

  - finally, list all the users

    - ```bash
      root@yuck ~/Lecture# pdbedit -L
      smbuser:1001:
      root@yuck ~/Lecture# pdbedit -Lv
      ---------------
      Unix username:        smbuser
      NT username:
      Account Flags:        [U          ]
      User SID:             S-1-5-21-1897428662-2202846973-1737022946-1001
      Primary Group SID:    S-1-5-21-1897428662-2202846973-1737022946-513
      ...
      ```


#### Restart Service

Restart `smb` service to let it reload configuration

- ```bash
  root@yuck ~/Lecture# systemctl restart smbd
  root@yuck ~/Lecture# systemctl is-active smbd
  active
  ```

#### Test

##### Windows

- Grab the ip address of virtual machine's NIC, and try to login on your Windows box

  - ```bash
    root@yuck ~/Lecture# ip -j -4 address show eth0 | jq '.[] | .addr_info[] | .local'
    "172.30.104.152"
    ```

  - Type the IP Address you obtained above to `Win+R` as follows

    - ![Win+R Example](assets/win-r%20example.png)

  - And then, you click on any folder in the explorer. Vola! Here we are

    - ![Samba File Explorer Snip](assets/samba%20file%20explorer%20snip.png)

##### Linux

- Grab your other linux virtual machine. Install `cifs-utils`. 

- One shot solution. 

  - ```bash
    root@yuch ~# mount -t cifs -o username=smbuser,password=e\*ecf51d20 //172.30.104.152/lecture ~/Lecture/
    root@yuch ~# mount -t cifs -o username=smbuser,password=e\*ecf51d20 //172.30.104.152/lecture ~/Lecture/
    ```

- `fstab` solution, which would work even if the system restarted.

  - Create a credentials file

    - ```bash
      root@yuch ~/Auth# cat ~/Auth/auth.smb
      username=smbuser
      password=e*ecf51d20
      ```

  - Create entry in `fstab`

    - ```bash
      root@yuch ~/Auth# tail -n 2 /etc/fstab
      # Lecture Samba
      //172.30.104.152/lecture /root/Lecture cifs credentials=/root/Auth/auth.smb 0 0
      ```

  - Restart

    - ```bash
      root@yuch ~/Auth# systemctl reboot now
      ```

  - Everything is still here

    - ```bash
      root@yuch ~# ls ~/Lecture/
      fifo*  history*  Perl/  smb.conf*  Symb/  Test/  test.py*  test.txt*
      ```

  - > ##### Warning
    >
    > Notice virtual NIC created by VMWare &| Hyper-V not necessary has a static IP Address. Hence, you might need to adjust IP Address every time...

    - It won't cause problem to boot your system, but will create some error messages in your journal.

    - ```bash
      root@yuch ~# journalctl -perr
      ...
      Jul 04 08:50:05 yuch kernel: CIFS: VFS: Error connecting to socket. Aborting operation.
      Jul 04 08:50:05 yuch kernel: CIFS: VFS: cifs_mount failed w/return code = -113
      Jul 04 08:50:05 yuch systemd[1]: Failed to mount /root/Lecture.
      ```

## `NFS`

- Network file sharing, an alternative of samba to share files between computers under the same LAN.  
- NFS is  an  Internet  Standard  protocol created by Sun Microsystems in 1984.

### Install

- ```bash
  root@yuck ~# apt install nfs-kernel-server
  # Enable those services
  root@yuck ~# systemctl enable --now rpcbind
  Synchronizing state of rpcbind.service with SysV service script with /lib/systemd/systemd-sysv-install.
  Executing: /lib/systemd/systemd-sysv-install enable rpcbind
  root@yuck ~# systemctl enable --now nfs-kernel-server
  Synchronizing state of nfs-kernel-server.service with SysV service script with /lib/systemd/systemd-sysv-install.
  Executing: /lib/systemd/systemd-sysv-install enable nfs-kernel-server
  ```

### Config

- The configuration file is located at `/etc/exports`. Put desired sharing in the following formats
  - `path parameters`
  - The parameters included the following

#### Important Parameters

| option                  | function                                                     |
| ----------------------- | ------------------------------------------------------------ |
| `nfsvars=`, `vars`      | specify version of server's NFS. If not specified, NFS will try all possible versions, and stick with the highest possible version. |
| `soft`                  | fail if server does not response.                            |
| `hard` or not specified | indefinitely retry until server response. more secure than `soft` and ensured data integrity |
| `timeo=n`               | deciseconds of NFS client waiting before retries an NFS request |
| `retrans=n`             | number of times of retries of a request before give up       |
| **`rw`**                | read and write                                               |
| `ro`                    | read only                                                    |
| `no_all_squash`         | disables squashing                                           |
| `sync`                  | let change in file immediately synchronize with server and higher cache  coherence is ensured. significant performance lost |
| **`async`**             | save the changes into memory, which imposes risk of data loss, and only synchronize with server if <br />- OOM<br />- File is flushed explicitly, or closed<br />It does make things faster, but it is less secure |
| `no_all_squash`         | disable squashing                                            |
| **`root_squash`**       | if NFS client access as root, serve him/her as an anonymous user |
| `no_root_squash`        | disable squashing on root                                    |
| `all_squash`            | no matter what account, serve him/her as an anonymous user   |
| **`nodev`**[^1]         | prevent filesystem to contain character devices              |
| **`nosuid`**[^1]        | SUID/SGID allow elevated root privilege for normal user, doing such prevent a file system from contain such binary |
| **`no_subtree_check`**  | check if the file is still available for every request       |
| `sub_tree_check`        | don't check                                                  |

[^1]: This is actually option for mount. To use them, the shared directory must be in a separate partition/disk so that you can mount them with such options 

#### Config

```bash
root@yuck ~# vim /etc/exports
root@yuck ~# tail /etc/exports
/home/nfsuser/Lecture yuch(rw, sync, root_squash)
```

#### Create Directory

```bash
root@yuck ~# ln -s /home/smbuser/Lecture/ /home/nfsuser/Lecture
```

#### Restart Service

```bash
root@yuck ~# systemctl restart rpcbind
root@yuck ~# systemctl restart nfs-kernel-server
```

#### Test

##### `showmount`

- Display information about NFS filesystems.

- `showmount [option] server`

- | option | function                                                     |
  | :----- | :----------------------------------------------------------- |
  | `-e`   | Displays all the file systems exported on the server.        |
  | `-a`   | Displays all Network File System (NFS) clients and the directories on the server each has mounted. |
  | `-d`   | Displays all directories on the server that are currently mounted by NFS clients. |

- example

  - Check and see what are available for us

    - ```bash
      root@yuch ~# showmount -e 172.30.104.152
      Export list for 172.30.104.152:
      /home/smbuser/Lecture 172.30.104.*
      ```

- Mount

  - ```bash
    root@yuch ~ [1]# mkdir ~/NFSLecture
    root@yuch ~# mount -t nfs 172.30.104.152:/home/nfsuser/Lecture ~/NFSLecture/
    ```

- Always Mount

  - ```bash
    # Lecture NFS
    172.30.104.152:/home/nfsuser/Lecture               /root/NFSLecture     nfs   defaults                                 0    0
    ```

## `AutoFS`

- There are two way for us to mount remote shared resources in our Linux box.
  - Type `mount -t cifs/nfs src dist` by hand when necessary
  - Type some stuff into `/etc/fstab`
- The first way can be seen as lazy method, i.e., only when you use the resource, you mount it --  but it is very possible that you would miss several key strokes and come back to lookup this presentation again. The second way is pre-emptive, as it act before necessary, causing waste of resources.
- `AutoFS` is a daemon process, which automatically mount services when you access it.

### Install

- Install `autofs` package.

```bash
root@yuck ~# sudo apt install autofs
```

### Config

- The config file of `autofs` is located in `/etc/autofs.master`. The purpose of this config file is mainly to tell `autofs` all sub config file location.

  - ```bash
    root@yuch ~# cat /etc/autofs/auto.master
    #
    # Sample auto.master file
    # This is a 'master' automounter map and it has the following format:
    # mount-point [map-type[,format]:]map [options]
    # For details of the format look at auto.master(5).
    #
    /remote /etc/autofs/lectures.misc --timeout=60
    /misc   /etc/autofs/auto.misc
    #
    # NOTE: mounts done from a hosts map will be mounted with the
    #       "nosuid" and "nodev" options unless the "suid" and "dev"
    #       options are explicitly given.
    #
    /net    -hosts
    #
    # Include /etc/autofs/auto.master.d/*.autofs
    # To add an extra map using this mechanism you will need to add
    # two configuration items - one /etc/autofs/auto.master.d/extra.autofs file
    # (using the same line format as the auto.master file)
    # and a separate mount map (e.g. /etc/auto.extra or an auto.extra NIS map)
    # that is referred to by the extra.autofs file.
    #
    +dir:/etc/autofs/auto.master.d
    #
    # If you have fedfs set up and the related binaries, either
    # built as part of autofs or installed from another package,
    # uncomment this line to use the fedfs program map to access
    # your fedfs mounts.
    #/nfs4  /usr/sbin/fedfs-map-nfs4 nobind
    #
    # Include central master map if it can be found using
    # nsswitch sources.
    #
    # Note that if there are entries for /net or /misc (as
    # above) in the included master map any keys that are the
    # same will not be seen as the first read key seen takes
    # precedence.
    #
    +auto.master
    ```

- Create corresponding directories. The directory name determines the key in the configuration file

  - ```bash
    root@yuch ~# mkdir -p /remote/{Lecture,NFSLecture}
    ```

- Create corresponding configuration files

  - ```bash
    root@yuch ~# cat /etc/autofs/lectures.misc
    NFSLecture      -ftype=nfs                                              172.30.104.152:/home/nfsuser/Lecture
    # Notice Samba is not very stable with AutoFS
    Lecture         -ftype=cifs,rw,credentials=/root/Auth/auth.smb          ://172.30.104.152/lecture
    ```

- Start the service

  - ```bash
    root@yuch ~# systemctl enable --now autofs
    Created symlink /etc/systemd/system/multi-user.target.wants/autofs.service → /usr/lib/systemd/system/autofs.service.
    ```

### Test

- Initially, we start at our home directory and have not attempted to go to `/remote/*` yet

  - ```bash
    root@yuch /# df -h
    Filesystem      Size  Used Avail Use% Mounted on
    dev             948M     0  948M   0% /dev
    run             956M  508K  956M   1% /run
    /dev/sda2       127G  5.0G  120G   4% /
    tmpfs           956M     0  956M   0% /dev/shm
    tmpfs           956M     0  956M   0% /tmp
    /dev/sda1       800M   58M  743M   8% /boot
    tmpfs           192M     0  192M   0% /run/user/0
    ```

- Then if you go to here, everything is mounted for you

  - ```bash
    root@yuch ~# cd /remote/NFSLecture/
    root@yuch /r/NFSLecture# df -h
    Filesystem                            Size  Used Avail Use% Mounted on
    dev                                   948M     0  948M   0% /dev
    run                                   956M  512K  956M   1% /run
    /dev/sda2                             127G  5.0G  120G   4% /
    tmpfs                                 956M     0  956M   0% /dev/shm
    tmpfs                                 956M     0  956M   0% /tmp
    /dev/sda1                             800M   58M  743M   8% /boot
    tmpfs                                 192M     0  192M   0% /run/user/0
    172.30.104.152:/home/nfsuser/Lecture  124G   11G  108G   9% /remote/NFSLecture
    ```

  - 



