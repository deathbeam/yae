NON.mouse = {}

local Gdx = NON_GDX
local Non = NON_NON
  
function NON.mouse.show()
  Gdx.input:setCursorCatched(false)
end
  
function NON.mouse.hide()
  Gdx.input:setCursorCatched(true)
end
  
function NON.mouse.x()
  return Gdx.input:getX()
end
  
function NON.mouse.y()
  return Gdx.input:getY()
end
  
function NON.mouse.move(x, y)
  return Gdx.input:setCursorPosition(x, y)
end
  
function NON.mouse.down(button)
  if button == nil then buton = "left" end
  local buttoncode = Non:getButton(name)
  return Gdx.input:isButtonPressed(buttoncode)
end