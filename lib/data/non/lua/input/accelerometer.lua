NON.accelerometer = {}

local Gdx = NON_GDX

function NON.accelerometer.x()
  return Gdx.input:getAccelerometerX()
end
  
function NON.accelerometer.y()
  return Gdx.input:getAccelerometerY()
end
  
function NON.accelerometer.z()
  return Gdx.input:getAccelerometerZ()
end

function NON.accelerometer.rotation()
  return NON.accelerometer.x(), NON.accelerometer.y(), NON.accelerometer.z()
end
  
function NON.accelerometer.orientation()
  return Gdx.input:getRotation()
end