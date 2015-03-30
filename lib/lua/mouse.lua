--[[--------
User input from mouse (works only on desktop devices).

Mouse and touch input allow the user to point at things on the screen. Both input mechanisms report the location of interaction as 2D coordinates relative to the upper left corner of the screen, with the positive x-axis pointing to the right and the y-axis pointing downward.

Mouse input comes with additional information, namely which button was pressed. Most mice feature a left and a right mouse button as well as a middle mouse button. In addition, there's often a scroll wheel which can be used for zooming or scrolling in many applications.

@module non.mouse
]]

non.mouse = {}

--[[----------
Show mouse cursor
@usage
-- lua -------------------------------------------------------------------------------------
non.mouse.show()
-- moonscript ------------------------------------------------------------------------------
non.mouse.show!
]]
function non.mouse.show()
  NON.gdx.input:setCursorCatched(false)
end

--[[----------
Hide mouse cursor
@usage
-- lua -------------------------------------------------------------------------------------
non.mouse.hide()
-- moonscript ------------------------------------------------------------------------------
non.mouse.hide!
]]
function non.mouse.hide()
  NON.gdx.input:setCursorCatched(true)
end

--[[----------
Change mouse cursor visibility
@tparam boolean visible set if cursor will be visible or not
@usage
-- lua -------------------------------------------------------------------------------------
non.mouse.setVisible(true) 
-- moonscript ------------------------------------------------------------------------------
non.mouse.setVisible true
]]
function non.mouse.setVisible(visible)
  NON.gdx.input:setCursorCatched(not visible)
end

--[[----------
Check if mouse cursor is visible
@treturn boolean true if cursor is visible
@usage
-- lua -------------------------------------------------------------------------------------
cursorVisible = non.mouse.isVisible()
-- moonscript ------------------------------------------------------------------------------
cursorVisible = non.mouse.isVisible!
]]
function non.mouse.isVisible()
  return not NON.gdx.input:isCursorCatched()
end

--[[----------
Get X coordinate of mouse cursor position
@treturn number x coordinate
@usage
-- lua -------------------------------------------------------------------------------------
x = non.mouse.getX()
-- moonscript ------------------------------------------------------------------------------
x = non.mouse.getX!
]]
function non.mouse.getX()
  return NON.gdx.input:getX()
end

--[[----------
Get Y coordinate of mouse cursor position
@treturn number y coordinate
@usage
-- lua -------------------------------------------------------------------------------------
y = non.mouse.getY()
-- moonscript ------------------------------------------------------------------------------
y = non.mouse.getY!
]]
function non.mouse.getY()
  return NON.gdx.input:getY()
end

--[[----------
Get mouse cursor position
@treturn number x coordinate of the cursor
@treturn number y coordinate of the cursor
@usage
-- lua -------------------------------------------------------------------------------------
x, y = non.mouse.getPosition()
-- moonscript ------------------------------------------------------------------------------
x, y = non.mouse.getPosition!
]]
function non.mouse.getPosition()
  return non.mouse.getX(), non.mouse.getY()
end

--[[----------
Set mouse cursor position
@tparam number x the x coordinate
@tparam number y the y coordinate
@usage
-- lua -------------------------------------------------------------------------------------
non.mouse.setPosition(10, 10)
-- moonscript ------------------------------------------------------------------------------
non.mouse.setPosition 10, 10
]]
function non.mouse.setPosition(x, y)
  return NON.gdx.input:setCursorPosition(x, y)
end

--[[----------
Check if specified mouse button is down
@tparam string button button to check for (defaults to "Left")
@treturn boolean true if specified mouse button is pressed
@usage
-- lua -------------------------------------------------------------------------------------
clicked = non.mouse.isDown("Right")
-- moonscript ------------------------------------------------------------------------------
clicked = non.mouse.isDown "Right"
]]
function non.mouse.isDown(button)
  if button == nil then buton = "Left" end
  local buttoncode = NON:getButton(name)
  return NON.gdx.input:isButtonPressed(buttoncode)
end