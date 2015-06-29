-------------------------------------------------------------------------------
-- User input from compass (works only on mobile devices).
-------------------------------------------------------------------------------
-- Some Android devices and iOS devices have an integrated magnetic field
-- sensor that provides information on how the device is oriented with respect
-- to the magnetic north pole.  
--   
-- ***Note =*** The compass is currently not available on iOS devices since
-- there is no implementation in the RoboVM - backend yet.
--
-- @module non.compass

Gdx = java.require "com.badlogic.gdx.Gdx"

  ---
-- Get the angle of the device's orientation around the z-axis. The positive
-- z-axis points towards the earths center
-- @treturn number azimuth
-- @usage
-- azimuth = non.compass.getAzimuth!
getAzimuth = ->
  Gdx.input\getAzimuth!

---
-- Get the angle of the device's orientation around the x-axis. The positive
-- x-axis roughly points to the west and is orthogonal to the z- and y-axis
-- @treturn number pitch
-- @usage
-- pitch = non.compass.getPitch!
getPitch = ->
  Gdx.input\getPitch!

---
-- Get the angle of the device's orientation around the y-axis. The positive
-- y-axis points toward the magnetic north pole of the earth while remaining
-- orthogonal to the other two axes.
-- @treturn number roll 
-- @usage
-- roll = non.compass.getRoll!
getRoll = ->
  Gdx.input\getRoll!

---
-- Get current compass azimuth, pitch and roll
-- @treturn number azimuth
-- @treturn number pitch
-- @treturn number roll
-- @usage
-- azimuth, pitch, roll = non.compass.getOrientation!
getOrientation = ->
  getAzimuth!, getPitch!, getRoll!

{
  :getAzimuth
  :getPitch
  :getRoll
  :getOrientation
}