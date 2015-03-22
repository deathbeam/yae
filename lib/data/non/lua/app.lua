local Gdx = NON_GDX
local Non = NON_NON

function NON.width()
  return Gdx.graphics:getWidth()
end
  
function NON.height()
  return Gdx.graphics:getHeight()
end

function NON.fps()
  return Gdx.graphics:getFramesPerSecond()
end
  
function NON.delta()
  return Gdx.graphics:getDeltaTime()
end
  
function NON.getClipboard()
  return Gdx.app:getClipboard():getContents()
end
  
function NON.setClipboard(object)
  Gdx.app:getClipboard():setContents(object)
end
  
function NON.version()
  return Gdx.app:getVersion()
end
  
function NON.platform()
  return Non:getPlatform()
end
  
function NON.log(tag, msg)
  Gdx.app:log(tag, msg)
end
  
function NON.debug(tag, msg)
  Gdx.app:debug(tag, msg)
end
  
function NON.error(tag, msg)
  Gdx.app:error(tag, msg)
end
  
function NON.quit()
  Gdx.app:exit()
end