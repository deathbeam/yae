#!/usr/bin/env lua

local FILE = debug.getinfo(1,"S").source:sub(2)..".jar"

NON = {}

function NON.hello(language) {
    os.execute("java -jar \""..FILE.."\" hello "..language)
}

function NON.start(platform) {
    os.execute("java -jar \""..FILE.."\" start "..platform)
}

function NON.build(platform) {
    os.execute("java -jar \""..FILE.."\" build "..platform)
}

function NON.update() {
    os.execute("java -jar \""..FILE.."\" update")
}

function NON.clean() {
    os.execute("java -jar \""..FILE.."\" clean")
}

function NON.version() {
    os.execute("java -jar \""..FILE.."\" version")
}