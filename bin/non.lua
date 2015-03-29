#!/usr/bin/env lua

local FILE = debug.getinfo(1,"S").source:sub(2):replace(".lua", ".jar")

non = {}

function non.hello(language)
    if language == nil then language = "lua" end
    os.execute("java -jar \""..FILE.."\" hello "..language)
end

function non.start(platform)
    if platform == nil then platform = "desktop" end
    os.execute("java -jar \""..FILE.."\" start "..platform)
end

function non.build(platform)
    if platform == nil then platform = "desktop" end
    os.execute("java -jar \""..FILE.."\" build "..platform)
end

function non.update()
    os.execute("java -jar \""..FILE.."\" update")
end

function non.clean()
    os.execute("java -jar \""..FILE.."\" clean")
end

function non.version()
    os.execute("java -jar \""..FILE.."\" version")
end