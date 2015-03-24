local Gdx = NON_GDX
local Non = NON_NON

function non.width()
  return Gdx.graphics:getWidth()
end
  
function non.height()
  return Gdx.graphics:getHeight()
end

function non.fps()
  return Gdx.graphics:getFramesPerSecond()
end
  
function non.delta()
  return Gdx.graphics:getDeltaTime()
end
  
function non.getClipboard()
  return Gdx.app:getClipboard():getContents()
end
  
function non.setClipboard(object)
  Gdx.app:getClipboard():setContents(object)
end
  
function non.version()
  return Gdx.app:getVersion()
end
  
function non.platform()
  return Non:getPlatform()
end
  
function non.log(tag, msg)
  Gdx.app:log(tag, msg)
end
  
function non.debug(tag, msg)
  Gdx.app:debug(tag, msg)
end
  
function non.error(tag, msg)
  Gdx.app:error(tag, msg)
end
  
function non.quit()
  Gdx.app:exit()
end