NON.keyboard = {}

local Gdx = NON_GDX
local Non = NON_NON
  
function NON.keyboard.show()
  Gdx.input:setOnscreenKeyboardVisible(true)
end
  
function NON.keyboard.hide()
  Gdx.input:setOnscreenKeyboardVisible(false)
end
  
function NON.keyboard.down(key)
  local keycode = Non:getKey(name)
  if Gdx.input:isKeyPressed(keycode) and keycode ~= 0 then return true end
  return false
end