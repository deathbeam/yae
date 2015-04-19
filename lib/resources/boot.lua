function require(file)
	if (string.sub(file,-4) == ".lua") then file = string.sub(file, 1, -5) end
	file = string.gsub(file, "%.", "/")
	file = file .. ".lua"
	print("require: ".."'"..tostring(file).."'")
	return non.filesystem.load(file)()
end

function non.draw() end
function non.keypressed() end
function non.keyreleased() end
function non.load() end
function non.mousemoved() end
function non.mousepressed() end
function non.mousereleased() end
function non.mousescrolled() end
function non.resize() end
function non.textinput() end
function non.touchmoved() end
function non.touchpressed() end
function non.touchreleased() end
function non.quit() end
function non.update() end
function non.visible() end

function non.run()
  local dt = non.timer and non.timer.getDelta() or 0
  if non.update then non.update(non.timer.getDelta()) end
  non.graphics.clear()
  non.graphics.origin()
  if non.draw then non.draw() end
  non.graphics.present()
end

non.java = {}

function non.java.bind(javaobjname)
  return luajava.bindClass(javaobjname)
end

function non.java.new(javaobjname, ...)
  return luajava.newInstance(javaobjname, ...)
end

function non.java.extend(javaobjname, luaobj)
  return luajava.createProxy(javaobj, luaobj)
end

require "main"