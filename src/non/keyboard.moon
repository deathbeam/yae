-------------------------------------------------------------------------------
-- User input from keyboard.
-------------------------------------------------------------------------------
-- Keyboards signal user input by generating events for pressing and releasing
-- a key. Each event carries with it a key-code that identifies the key that
-- was pressed/released. These key-codes differ from platform to platform.
-- NÖN tries to hide this fact by providing its own key-code table. You can
-- query which keys are currently being pressed via polling.
--
-- @module non.keyboard

Gdx = java.require "com.badlogic.gdx.Gdx"
Peripheral = java.require "com.badlogic.gdx.Input$Peripheral"

c = require "non.internal.constants"

---
-- Check if one of specified keys is down
-- @tparam ... keys keys to check for
-- @treturn boolean true one of keys is pressed
-- @usage
-- is_down = non.keyboard.isDown "a", "b", "c"
isDown = (...) ->
  args = table.pack ...
  found = false

  for i = 1, args.n
    keycode = c.keys[args[i]]
    found = found or Gdx.input\isKeyPressed keycode

  return found

---
-- Checks if on-screen keyboard is visible (mobile devices only)
-- @treturn boolean true if keyboard is visible
-- @usage
-- visible = non.keyboard.isVisible!
isVisible = ->
  return Gdx.input\isPeripheralAvailable Peripheral.OnscreenKeyboard

---
-- Change on-screen keyboard visibility (mobile devices only)
-- @tparam boolean visible set if keyboard will be visible or not
-- @usage
-- non.keyboard.setVisible true
setVisible = (visible) ->
  Gdx.input\setOnscreenKeyboardVisible visible

{
  :isDown
  :isVisible
  :setVisible
}