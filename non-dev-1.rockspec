package = "non"
version = "dev-1"

source = {
  url = "git://github.com/non2d/non.git",
}

description = {
  summary = "Make cross-platform games in Lua and MoonScript",
  homepage = "http://nononsense.science",
  maintainer = "Thomas Slusny <slusnucky@gmail.com>",
  license = "MIT"
}

dependencies = {
  "lua >= 5.1",
  "moonscript"
}

build = {
  type = "command",
  
  modules = {
    ["non"] = "bin/non.lua"
  },
  
  install = {
    bin = { "bin/non.jar", "bin/non" }
  },
  
  platforms = {
    unix = {
      build_command = "sh install"
    },
    windows = {
      build_command = "install"
    }
  }
}