NON.touch = {}

local Gdx = NON_GDX

function NON.touch.x(pointer)
  if pointer == nil then pointer = 0 end
  return Gdx.input:getX(pointer)
end
  
function NON.touch.y(pointer)
  if pointer == nil then pointer = 0 end
  return Gdx.input:getY(pointer)
end
  
function NON.touch.down(pointer)
  if pointer == nil then pointer = 0 end
  return Gdx.input:isTouched(pointer)
end