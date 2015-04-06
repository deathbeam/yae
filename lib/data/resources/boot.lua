function non.run(dt)
  if non.update then non.update(dt) end
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