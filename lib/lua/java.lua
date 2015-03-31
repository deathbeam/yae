--[[--------
Simplifies binding to Java classes and methods.  

@module non.java
]]

non.java = {}

--[[----------
Bind Java object to Lua
@tparam string name Java object name
@treturn Object a Java object converted to Lua
@usage
-- lua -------------------------------------------------------------------------------------
system = non.java.bind("java.lang.System")
print(system:currentTimeMillis)
-- moonscript ------------------------------------------------------------------------------
system = non.java.bind "java.lang.System"
print system\currentTimeMillis
]]
function non.java.bind(name)
  return luajava.bindClass(name)
end

--[[----------
Create new instance of specified Java object
@tparam string name Java object name
@param ... constructor parameters
@treturn Object an instance of specified Java class
@usage
-- lua -------------------------------------------------------------------------------------
scanner = non.java.new("java.util.Scanner", "1 fish 2 fish red fish blue fish")
scanner:useDelimiter("\\s*fish\\s*")
print(scanner:next())
-- moonscript ------------------------------------------------------------------------------
scanner = non.java.new "java.util.Scanner", "1 fish 2 fish red fish blue fish"
scanner\useDelimiter("\\s*fish\\s*")
print scanner\next!
]]
function non.java.new(name, ...)
  return luajava.newInstance(name, ...)
end

--[[----------
Extend specified Java object
@tparam string name Java object name
@tparam table options options
@treturn Object an extended Java object
@usage
-- lua -------------------------------------------------------------------------------------
runnable = non.java.extend("java.lang.Runnable", {
  run = function() end
})
-- moonscript ------------------------------------------------------------------------------
runnable = non.java.extend "java.lang.Runnable",
  run: ->
]]
function non.java.extend(name, options)
  return luajava.newInstance(name, options)
end