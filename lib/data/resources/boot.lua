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