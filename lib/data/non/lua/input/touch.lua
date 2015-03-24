non.touch = {}

local Gdx = NON_GDX

function non.touch.x(pointer)
  if pointer == nil then pointer = 0 end
  return Gdx.input:getX(pointer)
end
  
function non.touch.y(pointer)
  if pointer == nil then pointer = 0 end
  return Gdx.input:getY(pointer)
end
  
function non.touch.down(pointer)
  if pointer == nil then pointer = 0 end
  return Gdx.input:isTouched(pointer)
end