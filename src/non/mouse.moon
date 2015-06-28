-------------------------------------------------------------------------------
-- User input from mouse (works only on desktop devices).
-------------------------------------------------------------------------------
-- Mouse and touch input allow the user to point at things on the screen. Both
-- input mechanisms report the location of interaction as 2D coordinates
-- relative to the upper left corner of the screen, with the positive x-axis
-- pointing to the right and the y-axis pointing downward.
--   
-- Mouse input comes with additional information, namely which button was
-- pressed. Most mice feature a left and a right mouse button as well as a
-- middle mouse button. In addition, there's often a scroll wheel which can be
-- used for zooming or scrolling in many applications.
--
-- @module non.mouse

Gdx = java.require "com.badlogic.gdx.Gdx"

c = require "non.internal.constants"

{
  ---
  -- Get X coordinate of mouse cursor position
  -- @treturn number x coordinate
  -- @usage
  -- x = non.mouse.getX!
  getX: ->
    Gdx.input\getX!

  ---
  -- Get Y coordinate of mouse cursor position
  -- @treturn number y coordinate
  -- @usage
  -- y = non.mouse.getY!
  getY: ->
    Gdx.input\getY!

  ---
  -- Get mouse cursor position
  -- @treturn number x coordinate of the cursor
  -- @treturn number y coordinate of the cursor
  -- @usage
  -- x, y = non.mouse.getPosition!
  getPosition: ->
    @getX!, @getY!

  ---
  -- Check if one of specified buttons is down
  -- @tparam ... buttons buttons to check for
  -- @treturn boolean true one of buttons is pressed
  -- @usage
  -- isDown = non.mouse.isDown "left", "right"
  isDown: (...) ->
    args = table.pack ...
    found = false

    for i = 1, args.n
      btncode = c.buttons[args[i]]
      found = found or Gdx.input\isButtonPressed btncode

    return found

  ---
  -- Check if mouse cursor is visible
  -- @treturn boolean true if cursor is visible
  -- @usage
  -- visible = non.mouse.isVisible!
  isVisible: ->
    not Gdx.input\isCursorCatched!

  ---
  -- Set mouse cursor position
  -- @tparam number x the x coordinate
  -- @tparam number y the y coordinate
  -- @usage
  -- non.mouse.setPosition 10, 10
  setPosition: (x, y) ->
    Gdx.input\setCursorPosition x, y

  ---
  -- Change mouse cursor visibility
  -- @tparam boolean visible set if cursor will be visible or not
  -- @usage
  -- non.mouse.setVisible true
  setVisible: (visible) ->
    Gdx.input\setCursorCatched not visible

  ---
  -- Set X coordinate of mouse cursor position
  -- @tparam number x the x coordinate
  -- @usage
  -- non.mouse.setX 10
  setX: (x) ->
    @setPosition x, @getY!

  ---
  -- Set Y coordinate of mouse cursor position
  -- @tparam number y the y coordinate
  -- @usage
  -- non.mouse.setY 10
  setY: (y) ->
    @setPosition @getX!, y
}