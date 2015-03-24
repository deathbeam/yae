package = "non"
version = "0.6.0-1"

source = {
  url = "git://github.com/non2d/non.git",
  tag = "v0.6.0"
}

description = {
  summary = "Make cross-platform games in Lua and Moonscript",
  detailed = "NÖN is an engine you can use to make 2D games in Lua and MoonScript. It's free, open-source, and works on Windows, Mac OS X, Linux, Android and iOS.",
  homepage = "http://nononsense.science",
  maintainer = "Thomas Slusny <slusnucky@gmail.com>",
  license = "MIT"
}

dependencies = {
  "lua ~> 5.1",
  "moonscript"
}

build = {
  type = "builtin",
  
  modules = {
    ["non"] = "bin/non.lua"
  },
  
  install = {
    bin = { "bin/non.jar", "bin/non" }
  }
}