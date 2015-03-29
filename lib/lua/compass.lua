--[[--------
User input from compass (works only on mobile devices).

Some Android devices and iOS devices have an integrated magnetic field sensor that provides information on how the device is oriented with respect to the magnetic north pole.

***note:*** The compass is currently not available on iOS devices since there is no implementation in the RoboVM - backend yet.

@module non.compass
]]

non.compass = {}

----------
-- Get the angle of the device's orientation around the z-axis. The positive z-axis points towards the earths center
-- @treturn number azimuth
-- @usage azimuth = non.compass.getAzimuth()
function non.compass.getAzimuth()
  return NON.gdx.input:getAzimuth()
end
  
  ----------
-- Get the angle of the device's orientation around the z-axis. The positive z-axis points towards the earths center.
-- @treturn number pitch
-- @usage pitch = non.compass.getPitch()
function non.compass.getPitch()
  return NON.gdx.input:getPitch()
end

----------
-- Get the angle of the device's orientation around the y-axis. The positive y-axis points toward the magnetic north pole of the earth while remaining orthogonal to the other two axes.
-- @treturn number roll 
-- @usage roll = non.compass.getRoll()
function non.compass.getRoll()
  return NON.gdx.input:getRoll()
end