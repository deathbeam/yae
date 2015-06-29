-------------------------------------------------------------------------------
-- Provides high-resolution timing functionality.
-------------------------------------------------------------------------------
-- @module non.system

TimeUtils = java.require "com.badlogic.gdx.utils.TimeUtils"
Thread = java.require "java.lang.Thread"
Gdx = java.require "com.badlogic.gdx.Gdx"

---
-- Returns the time between the last two frames. 
-- @treturn number The time passed (in seconds).
-- @usage
-- dt = non.timer.getDelta!
getDelta = ->
  Gdx.graphics\getDeltaTime! * 1000

---
-- Returns the current frames per second. 
-- @treturn number The current FPS.
-- @usage
-- fps = non.timer.getFPS!
getFPS = ->
  Gdx.graphics\getFramesPerSecond!

---
-- Returns the value of a timer with an unspecified starting time. This
-- function should only be used to calculate differences between points
-- in time, as the starting time of the timer is unknown.
-- @treturn number The time in seconds.
-- @usage
-- time = non.timer.getTime!
getTime = ->
  TimeUtils\millis! * 1000

---
-- Pauses the current thread for the specified amount of time.
-- @tparam number seconds Seconds to sleep for.
-- @usage
-- non.timer.sleep seconds
sleep = (seconds) ->
  Thread\sleep seconds / 1000

{
  :getDelta
  :getFPS
  :getTime
  :sleep
}