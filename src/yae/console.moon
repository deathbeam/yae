-------------------------------------------------------------------------------
-- Handles input and output to console.
-------------------------------------------------------------------------------
-- @module yae.console

---
-- Write text to console
-- @param ... text to write
-- @usage
-- yae.console.write "Hello", "World"
write = write

---
-- Read text from console
-- @treturn string entered text
-- @usage
-- message = yae.console.read!
-- print message
read = read

{
  :read
  :write
}