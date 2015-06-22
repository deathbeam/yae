msg = "Hello World from MoonScript"
print msg
print package.path

vm = non.java.require "non.NonVM"
vm.logger\log "test", "testmsg"