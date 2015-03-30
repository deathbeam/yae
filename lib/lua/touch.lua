--[[--------
User input from touch screen (works only on mobile devices).

Touch input does not have the notion of buttons and is complicated by the fact that multiple fingers might be tracked depending on the hardware. First generation Android phones only supported single-touch. Starting with phones like the Motorola Droid, multi-touch became a standard feature on most Android phones.

Note that touch can be implemented quite differently on different devices. This can affect how pointer indexes are specified and released and when touch events are fired. Be sure to test your control scheme on as many devices as possible. There are also many input test apps available in the market which can help determine how a particular device reports touch and aid in designing a control scheme that works best across a range of devices.

@module non.touch
]]

non.touch = {}

--[[----------
Get X coordinate of finger touching the screen
@tparam number pointer index of finger touching the screen for multitouch devices (defaults to 0)
@treturn number x coordinate
@usage
-- lua -------------------------------------------------------------------------------------
x = non.touch.getX(1)
-- moonscript ------------------------------------------------------------------------------
x = non.touch.getX 1
]]
function non.touch.getX(pointer)
  if pointer == nil then pointer = 0 end
  return NON.gdx.input:getX(pointer)
end

--[[----------
Get Y coordinate of finger touching the screen
@tparam number pointer index of finger touching the screen for multitouch devices (defaults to 0)
@treturn number y coordinate
@usage
-- lua -------------------------------------------------------------------------------------
y = non.mouse.getY(3)
-- moonscript ------------------------------------------------------------------------------
y = non.mouse.getY 3
]]
function non.touch.getY(pointer)
  if pointer == nil then pointer = 0 end
  return NON.gdx.input:getY(pointer)
end

--[[----------
Get position of finger touching the screen
@tparam number pointer index of finger touching the screen for multitouch devices (defaults to 0)
@treturn number x finger coordinate
@treturn number y finger coordinate
@usage
-- lua -------------------------------------------------------------------------------------
x, y = non.touch.getPosition(0)
-- moonscript ------------------------------------------------------------------------------
x, y = non.touch.getPosition 0
]]
function non.touch.getPosition(pointer)
  return non.touch.getX(pointer), non.touch.getY(pointer)
end

--[[----------
Check if finger is touching the screen
@tparam number pointer index of finger touching the screen for multitouch devices (defaults to 0)
@treturn boolean true if finger is touching the screen
@usage
-- lua -------------------------------------------------------------------------------------
touching = non.touch.isDown(4)
-- moonscript ------------------------------------------------------------------------------
touching = non.touch.isDown 4
]]
function non.touch.isDown(pointer)
  if pointer == nil then pointer = 0 end
  return NON.gdx.input:isTouched(pointer)
end