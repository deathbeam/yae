-------------------------------------------------------------------------------
-- User input from touch screen (works only on mobile devices).
-------------------------------------------------------------------------------
-- Note that touch can be implemented quite differently on different devices.
-- This can affect how pointer indexes are specified and released and when
-- touch events are fired. Be sure to test your control scheme on as many
-- devices as possible. There are also many input test apps available in the
-- market which can help determine how a particular device reports touch and
-- aid in designing a control scheme that works best across a range of devices.
--
-- @module non.touch

import java from non
Gdx = java.require "com.badlogic.gdx.Gdx"

---
-- Get X coordinate of finger touching the screen
-- @number[opt=0] pointer index of finger touching the screen for multitouch devices
-- @treturn number x coordinate
-- @usage
-- x = non.touch.getX 1
getX = (pointer=0) ->
  Gdx.input\getX pointer

---
-- Get Y coordinate of finger touching the screen
-- @number[opt=0] pointer index of finger touching the screen for multitouch devices
-- @treturn number y coordinate
-- @usage
-- y = non.touch.getY 1
getY = (pointer=0) ->
  Gdx.input\getY pointer

---
-- Get the position of finger touching the screen
-- @number[opt=0] pointer index of finger touching the screen for multitouch devices
-- @treturn number x coordinate
-- @treturn number y coordinate
-- @usage
-- x, y = non.touch.getPosition 1
getPosition = (pointer) ->
  getX pointer, getY pointer

---
-- Check if finger is touching the screen
-- @number[opt=0] pointer index of finger touching the screen for multitouch devices
-- @treturn bool true if finger is touching the screen
-- @usage
-- touching = non.touch.isDown 1
isDown = (pointer=0) ->
  Gdx.input\isTouched pointer

{
  :getX
  :getY
  :getPosition
  :isDown
}