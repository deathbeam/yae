--[[--------
NÖN lifecycle callbacks

A NÖN project has a well defined life-cycle, governing the states of an application, like creating, pausing and resuming, rendering and disposing the application.

@script callbacks
@usage
function non.ready()
  -- do something here
end
]]

non = {}

function require(file)
  return NON:eval(NON.gdx.files:internal(file..".lua"))
end

require "non/files"
require "non/system"
require "non/audio"
require "non/graphics"
require "non/network"
require "non/accelerometer"
require "non/compass"
require "non/keyboard"
require "non/mouse"
require "non/touch"

----------
-- Method called once when the application is created after evaluating top-level code.
function non.ready() end

----------
-- Method called by the game loop from the application every time update should be performed.
-- @tparam number dt delta time
function non.update(dt) end

----------
-- Method called by the game loop from the application every time after `update`.
function non.draw() end

----------
-- Called when the application is destroyed and it is preceded by a call to `pause`.
function non.quit() end

----------
-- On Android this method is called when the Home button is pressed or an incoming call is received and on desktop this is called just before `quit` when exiting the application.
function non.pause() end

----------
-- This method is only called on Android, when the application resumes from a paused state.
function non.resume() end

----------
-- This method is called every time the game screen is re-sized and the game is not in the paused state and it is also called once just after the `ready` method.
-- @tparam number w the new width the screen has been resized to in pixels
-- @tparam number h the new height the screen has been resized to in pixels
function non.resize(w, h) end

----------
-- Called every time key is pressed
-- @tparam string key code
function non.keydown(key) end

----------
-- Called every time key is released
-- @tparam string key code
function non.keyup(key) end

----------
-- Called every time key is typed on keyboard. Can be used for text input.
-- @tparam string character character typed
function non.keytyped(character) end

----------
-- Called every time user touches or clicks the screen
-- @tparam number x x coordinate of finger or mouse cursor
-- @tparam number y y coordinate of finger or mouse cursor
-- @tparam number pointer index of finger touching the screen (for touch devices)
-- @tparam string button button (for touch devices this is always "left")
function non.touchdown(x, y, pointer, button) end

----------
-- Called every time user releases mouse or lifts finger from the screen
-- @tparam number x x coordinate of finger or mouse cursor
-- @tparam number y y coordinate of finger or mouse cursor
-- @tparam number pointer index of finger touching the screen (for touch devices)
-- @tparam string button button (for touch devices this is always "left")
function non.touchup(x, y, pointer, button) end

----------
-- Called every time user is touching or clicking the screen and moves cursor or finger
-- @tparam number x x coordinate of finger or mouse cursor
-- @tparam number y y coordinate of finger or mouse cursor
-- @tparam number pointer index of finger touching the screen (for touch devices)
function non.touchdragged(x, y, pointer) end

----------
-- Called every time user is not clicking the screen and moves mouse cursor
-- @tparam number x x coordinate of mouse cursor
-- @tparam number y y coordinate of mouse cursor
function non.mousemoved(x, y) end

----------
-- Called every time user scrolls with mouse middle button
-- @tparam number amount how much user scrolled
function non.mousescrolled(amount) end

----------
-- Called every time client connects to server
-- @tparam Connection connection
function non.connected(connection) end

----------
-- Called every time client disconnects from server
-- @tparam Connection connection
function non.disconnected(connection) end

----------
-- Called every time client or server receives some data
-- @tparam Connection connection
-- @tparam table data byte data what can be read using `network.buffer`
function non.received(connection, data) end

require "main"