package = "non"
version = "0.6.2-1"

source = {
  url = "git://github.com/non2d/non.git",
  tag = "v0.6.2"
}

description = {
  summary = "Make cross-platform games in Lua and MoonScript",
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