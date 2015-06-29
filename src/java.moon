-------------------------------------------------------------------------------
-- Provides direct access to Java 
-------------------------------------------------------------------------------
-- @module java

---
-- Bind Java object to Lua
-- @tparam string name Java object name
-- @treturn Object a Java object converted to Lua
-- @usage
-- System = java.bind "java.lang.System"
-- print System\currentTimeMillis!
require = (name) ->
  luajava.bindClass name

---
-- Create new instance of specified Java object
-- @tparam string name Java object name
-- @param ... constructor parameters
-- @treturn Object an instance of specified Java class
-- @usage
-- Scanner = java.require "java.util.Scanner"
-- scanner = java.new Scanner, "1 fish 2 fish red fish blue fish"
-- scanner\useDelimiter "\\s*fish\\s*"
-- print scanner\next!
new = (name, ...) ->
  luajava.newInstance name, ...

---
-- Extend specified Java object
-- @tparam string name Java object name
-- @tparam table options options
-- @treturn Object an extended Java object
-- @usage
-- runnable = java.extend "java.lang.Runnable",
--  run: ->
extend = (name, options) ->
  luajava.newInstance name, options

{
  :require
  :new
  :extend
}