package = "non"
version = "0.7.0-1"

source = {
  url = "git://github.com/non2d/non.git",
  tag = "v0.7.0"
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
  type = "builtin",
  
  modules = {
    ["non"] = "bin/non.lua"
  },
  
  install = {
    bin = { "bin/non.jar", "bin/non" }
  }
}