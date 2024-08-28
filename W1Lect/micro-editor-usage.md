# Micro Text Editor
- somewhere in between vim and nano (v difficult / steep learning curve vs v easy but no features)
    - syntax highlighting on file save

- use the command `micro` after installation using `brew`, `apt` or `scoop` package managers.
```bash
$ micro
... opens empty file


$ micro <file_name>
```

- display micro help menu:
    1. run micro on any file or just open the editor itself.
    2. press `Ctrl-E`  to enter *command mode* and input the command args of `help <topic_name>`.

- display shortcut menu: 
    - `Alt-G`: short form
    - Enter Command Mode > `help defaultkeys`: verbose form

## Windows
- switch between windows: `Ctrl-W` (if multiple windows have been opened)

- open new window (command must be lowercase)
    - horizontal: `hsplit`
    - vertical: `vsplit`

- open a specific file in a new window
    - horizontal: `hsplit <filename>`
    - vertical: `vsplit <filename>`

- opening and switching between new tabs:
    - enter command mode with `Ctrl-E`, then type `tab <filename>.java` 

## Key bindings and configuration file
- Location on Windows: `%userprofile%/.config/micro/bindings.json`