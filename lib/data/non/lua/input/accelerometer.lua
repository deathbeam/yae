non.accelerometer = {}

local Gdx = NON_GDX

function non.accelerometer.x()
  return Gdx.input:getAccelerometerX()
end
  
function non.accelerometer.y()
  return Gdx.input:getAccelerometerY()
end
  
function non.accelerometer.z()
  return Gdx.input:getAccelerometerZ()
end
  
function non.accelerometer.rotation()
  return Gdx.input:getRotation()
end