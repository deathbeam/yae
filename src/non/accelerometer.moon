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
Peripheral = java.require "com.badlogic.gdx.Input$Peripheral"

---
-- Get current X relative to the center of earth
-- @treturn number x value of accelerometer
-- @usage
-- x = non.accelerometer.getX!
getX = ->
  Gdx.input\getAccelerometerX!

---
-- Get current Y relative to the center of earth
-- @treturn number y value of accelerometer
-- @usage
-- y = non.accelerometer.getY!
getY = ->
  Gdx.input\getAccelerometerY!

---
-- Get current Z relative to the center of earth
-- @treturn number z value of accelerometer
-- @usage
-- z = non.accelerometer.getZ!
getZ = ->
  Gdx.input\getAccelerometerZ!

---
-- Get current X, Y and Z relative to the center of earth
-- @treturn number x value of accelerometer
-- @treturn number y value of accelerometer
-- @treturn number z value of accelerometer
-- @usage
-- x, y, z = non.accelerometer.getRotation!
getRotation = ->
  getX!, getY!, getZ!

---
-- Checks if accelerometer is available on current device
-- @treturn bool True if accelerometer is available
-- @usage
-- available = non.accelerometer.isAvailable!
isAvailable = ->
  Gdx.input\isPeripheralAvailable Peripheral.Accelerometer

{
  :getX
  :getY
  :getZ
  :getRotation
  :isAvailable
}