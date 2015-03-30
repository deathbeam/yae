--[[--------
User input from accelerometer (works only on mobile devices).

An accelerometer measures the acceleration of a device on three axes (at least on Android). From this acceleration one can derive the tilt or orientation of the device.

Acceleration is measured in meters per second per second (m/s²). If an axis is pointing straight towards the center of the earth, its acceleration will be roughly -10 m/s². If it is pointing in the opposite direction, the acceleration will be 10 m/s².

Unfortunately, this configuration is different for tablets. Android devices have a notion called default orientation. For phones, portrait mode (as in the image above) is the default orientation. For tablets, landscape mode is the default orientation. A default landscape orientation device has its axes rotated, so that the y-axis points up the smaller side of the device and the x-axis points to the right of the wider side.

NÖN takes care of this and presents the accelerometer readings as shown in the image above, no matter the default orientation of the device (positive z-axis comes out of the screen, positive x-axis points to the right along the wider side of the device, positive y-axis points upwards along the smaller side of the device).

@module non.accelerometer
]]

non.accelerometer = {}

--[[----------
Get current X relative to the center of earth
@treturn number x value of accelerometer
@usage
-- lua -------------------------------------------------------------------------------------
x = non.accelerometer.getX()
-- moonscript ------------------------------------------------------------------------------
x = non.accelerometer.getX
]]
function non.accelerometer.getX()
  return NON.gdx.input:getAccelerometerX()
end

--[[----------
Get current Y relative to the center of earth
@treturn number y value of accelerometer
@usage
-- lua -------------------------------------------------------------------------------------
y = non.accelerometer.getY()
-- moonscript ------------------------------------------------------------------------------
y = non.accelerometer.getY!
]]
function non.accelerometer.getY()
  return NON.gdx.input:getAccelerometerY()
end

--[[----------
Get current Z relative to the center of earth
@treturn number z value of accelerometer
@usage
-- lua -------------------------------------------------------------------------------------
z = non.accelerometer.getZ()
-- moonscript ------------------------------------------------------------------------------
z = non.accelerometer.getZ!
]]
function non.accelerometer.getZ()
  return NON.gdx.input:getAccelerometerZ()
end

--[[----------
Get current X, Y and Z relative to the center of earth
@treturn table x, y and z values of accelerometer
@usage
-- lua -------------------------------------------------------------------------------------
x, y, z = non.accelerometer.getRotation()
-- moonscript ------------------------------------------------------------------------------
x, y, z = non.accelerometer.getRotation!
]]
function non.accelerometer.getRotation()
  return non.accelerometer.getX(), non.accelerometer.getY(), non.accelerometer.getZ()
end

--[[----------
Get current orientation of the device
@treturn number an orientation of device
@usage
-- lua -------------------------------------------------------------------------------------
orientation = non.accelerometer.getOrientation()
-- moonscript ------------------------------------------------------------------------------
orientation = non.accelerometer.getOrientation!
]]
function non.accelerometer.getOrientation()
  return NON.gdx.input:getRotation()
end