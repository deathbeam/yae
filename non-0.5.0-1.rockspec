package = "non"
version = "0.5.0-1"

source = {
  url = "git://github.com/non2d/non.git",
  tag = "v0.5.0"
}

description = {
  summary = "A platform for developing games using Ruby and Lua on Windows, Linux, Mac, Android and iOS.",
  detailed = "NÖN is an engine you can use to make 2D games in Ruby and Lua. It's free, open-source, and works on Windows, Mac OS X, Linux, Android and iOS.",
  homepage = "http://non2d.github.io",
  maintainer = "Thomas Slusny <slusnucky@gmail.com>",
  license = "MIT"
}

dependencies = {
  "lua ~> 5.1",
}

build = {
  type = "builtin",
  
  modules = {
    ["non"] = "bin/lua/non.lua"
  },
  
  install = {
    bin = { "bin/non.jar", "bin/lua/non" }
  }
}