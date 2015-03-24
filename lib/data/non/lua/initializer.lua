-- reserved globals - NON_NON, NON_GDX, NON_MODULE, NON_SCRIPT

non = {}

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
function non.ready() end
function non.update(dt) end
function non.draw() end
function non.quit() end
function non.pause() end
function non.resume() end
function non.resize(w, h) end

-- Input Callbacks
function non.keydown(key) end
function non.keyup(key) end
function non.keytyped(character) end
function non.touchdown(x, y, pointer, button) end
function non.touchup(x, y, pointer, button) end
function non.touchdragged(x, y, pointer) end
function non.mousemoved(x, y) end
function non.mousescrolled(amount) end

-- Network Callbacks
function non.connected(connection) end
function non.disconnected(connection) end
function non.received(connection, data) end

require "main"