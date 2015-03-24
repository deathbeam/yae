non.keyboard = {}

local Gdx = NON_GDX
local Non = NON_NON
  
function non.keyboard.show()
  Gdx.input:setOnscreenKeyboardVisible(true)
end
  
function non.keyboard.hide()
  Gdx.input:setOnscreenKeyboardVisible(false)
end
  
function non.keyboard.down(key)
  local keycode = Non:getKey(name)
  if Gdx.input:isKeyPressed(keycode) and keycode ~= 0 then return true end
  return false
end