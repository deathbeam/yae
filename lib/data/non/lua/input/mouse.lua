non.mouse = {}

local Gdx = NON_GDX
local Non = NON_NON
  
function non.mouse.show()
  Gdx.input:setCursorCatched(false)
end
  
function non.mouse.hide()
  Gdx.input:setCursorCatched(true)
end
  
function non.mouse.x()
  return Gdx.input:getX()
end
  
function non.mouse.y()
  return Gdx.input:getY()
end
  
function non.mouse.move(x, y)
  return Gdx.input:setCursorPosition(x, y)
end
  
function non.mouse.down(button)
  if button == nil then buton = "left" end
  local buttoncode = Non:getButton(name)
  return Gdx.input:isButtonPressed(buttoncode)
end