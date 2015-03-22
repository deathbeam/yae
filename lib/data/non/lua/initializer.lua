-- reserved globals - NON_NON, NON_GDX, NON_MODULE, NON_SCRIPT

NON = {}

function require(file)
  return NON_SCRIPT:eval(NON_GDX.files:internal(file..".lua"):readString())
end

require "non/lua/files"
require "non/lua/app"
require "non/lua/audio"
require "non/lua/graphics"
require "non/lua/network"
require "non/lua/input/accelerometer"
require "non/lua/input/keyboard"
require "non/lua/input/mouse"
require "non/lua/input/touch"

-- Callbacks
function ready() end
function update(dt) end
function draw() end
function quit() end
function pause() end
function resume() end
function resize(w, h) end

-- Input Callbacks
function keydown(key) end
function keyup(key) end
function keytyped(character) end
function touchdown(x, y, pointer, button) end
function touchup(x, y, pointer, button) end
function touchdragged(x, y, pointer) end
function mousemoved(x, y) end
function mousescrolled(amount) end

-- Network Callbacks
function connected(connection) end
function disconnected(connection) end
function received(connection, data) end

require "main"