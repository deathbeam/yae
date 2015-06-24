-------------------------------------------------------------------------------
-- User input from accelerometer (works only on mobile devices).
-------------------------------------------------------------------------------
-- An accelerometer measures the acceleration of a device on three axes. From
-- this acceleration one can derive the tilt or orientation of the device.  
--   
-- Acceleration is measured in meters per second per second (m/s²). If an axis
-- is pointing straight towards the center of the earth, its acceleration will
-- be roughly -10 m/s². If it is pointing in the opposite direction, the
-- acceleration will be 10 m/s².
-- 
-- @module non.accelerometer

Gdx = java.require "com.badlogic.gdx.Gdx"

{
  ---
  -- Get current X relative to the center of earth
  -- @treturn number x value of accelerometer
  -- @usage
  -- x = non.accelerometer.get_x!
  get_x: ->
    Gdx.input\getAccelerometerX!

  ---
  -- Get current Y relative to the center of earth
  -- @treturn number y value of accelerometer
  -- @usage
  -- y = non.accelerometer.get_y!
  get_y: ->
    Gdx.input\getAccelerometerY!

  ---
  -- Get current Z relative to the center of earth
  -- @treturn number z value of accelerometer
  -- @usage
  -- z = non.accelerometer.get_z!
  get_z: ->
    Gdx.input\getAccelerometerZ!

  ---
  -- Get current X, Y and Z relative to the center of earth
  -- @treturn number x value of accelerometer
  -- @treturn number y value of accelerometer
  -- @treturn number z value of accelerometer
  -- @usage
  -- x, y, z = non.accelerometer.get_rotation!
  get_rotation: ->
    @get_x!, @get_y!, @get_z!
}