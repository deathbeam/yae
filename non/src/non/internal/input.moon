keys = {
  "unknown": -1
  -- Alpha-numeric
  "0": 7
  "1": 8
  "2": 9
  "3": 10
  "4": 11
  "5": 12
  "6": 13
  "7": 14
  "8": 15
  "9": 16
  "a": 29
  "b": 30
  "c": 31
  "d": 32
  "e": 33
  "f": 34
  "g": 35
  "h": 36
  "i": 37
  "j": 38
  "k": 39
  "l": 40
  "m": 41
  "n": 42
  "o": 43
  "p": 44
  "q": 45
  "r": 46
  "s": 47
  "t": 48
  "u": 49
  "v": 50
  "w": 51
  "x": 52
  "y": 53
  "z": 54
  -- Symbols
  "*": 17
  "#": 18
  ",": 55
  ".": 56
  "`": 68
  "-": 69
  "=": 70
  "[": 71
  "]": 72
  "\\": 73
  ";": 74
  ":": 243
  "'": 75
  "/": 76
  "@": 77
  "+": 81
  -- Keypad
  "kp0": 144
  "kp1": 145
  "kp2": 146
  "kp3": 147
  "kp4": 148
  "kp5": 149
  "kp6": 150
  "kp7": 151
  "kp8": 152
  "kp9": 153
  -- Function
  "f1": 244
  "f2": 245
  "f3": 246
  "f4": 247
  "f5": 248
  "f6": 249
  "f7": 250
  "f8": 251
  "f9": 252
  "f10": 253
  "f11": 254
  "f12": 255
  -- Navigation
  "up": 19
  "down": 20
  "left": 21
  "right": 22
  "center": 23
  "end": 132
  "pageup": 92
  "pagedown": 93
  -- Command
  "insert": 133
  "delete": 67
  "backspace": 67
  -- Gamepad
  "btna": 96
  "btnb": 97
  "btnc": 98
  "btnx": 99
  "btny": 100
  "btnz": 101
  "btnl1": 102
  "btnr1": 103
  "btnl2": 104
  "btnr2": 105
  "btnthumbl": 106
  "btnthumbr": 107
  "btnstart": 108
  "btnselect": 109
  "btnmode": 110
  -- D-pad
  "dpup": 19
  "dpdown": 20
  "dpleft": 21
  "dpright": 22
  "dpcenter": 23
  -- Special
  "lalt": 57
  "ralt": 58
  "lshift": 59
  "rshift": 60
  "tab": 61
  "space": 62
  "lctrl": 129
  "rctrl": 130
  "escape": 131
  "return": 66
  -- Media
  "volumeup": 24
  "volumedown": 25
  "pause": 85
  "stop": 86
  "next": 87
  "prev": 88
  "rewind": 89
  "fastforward": 90
  "mute": 91
  -- Other
  "sleft": 1
  "sright": 2
  "home": 3
  "back": 4
  "call": 5
  "endcall": 6
  "power": 26
  "camera": 27
  "menu": 82
  "notification": 83
  "search": 84
  "clear": 28
  "sym": 63
  "www": 64
  "mail": 65
  "num": 78
  "headsethook": 79
  "focus": 80
  "pictsymbols": 94
  "charset": 95
  "forwarddelete": 112
}

buttons = {
  "left": 0
  "right": 1
  "middle": 2
  "back": 3
  "forward": 4
}

-- Create flipped table for keys to be able to convert keycodes to strings
keys_r = {}
for k, v in pairs keys
  keys_r[v] = k

-- Create flipped table for buttons to be able to convert buttoncodes to strings
buttons_r = {}
for k, v in pairs buttons
  buttons_r[v] = k

{
  key2string: (code) -> keys_r[code]
  key2code: (name) -> keys[name]
  btn2string: (code) -> buttons_r[code]
  btn2code: (name) -> buttons[name]
}