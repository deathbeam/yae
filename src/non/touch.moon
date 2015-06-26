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

Gdx = java.require "com.badlogic.gdx.Gdx"

{
  ---
  -- Get X coordinate of finger touching the screen
  -- @tparam number pointer index of finger touching the screen for multitouch
  -- devices (defaults to 0)
  -- @treturn number x coordinate
  -- @usage
  -- x = non.touch.get_x 1
  get_x: (pointer=0) ->
    Gdx.input\getX pointer

  ---
  -- Get Y coordinate of finger touching the screen
  -- @tparam number pointer index of finger touching the screen for multitouch
  -- devices (defaults to 0)
  -- @treturn number y coordinate
  -- @usage
  -- y = non.touch.get_y 1
  get_y: (pointer=0) ->
    Gdx.input\getY pointer

  ---
  -- Get the position of finger touching the screen
  -- @tparam number pointer index of finger touching the screen for multitouch
  -- devices (defaults to 0)
  -- @treturn number x coordinate
  -- @treturn number y coordinate
  -- @usage
  -- x, y = non.touch.get_position 1
  get_position: (pointer) ->
    @getX pointer, @getY pointer

  ---
  -- Check if finger is touching the screen
  -- @tparam number pointer index of finger touching the screen for multitouch devices (defaults to 0)
  -- @treturn boolean true if finger is touching the screen
  -- @usage
  -- touching = non.touch.is_down 1
  is_down: (pointer) ->
    Gdx.input\isTouched pointer
}