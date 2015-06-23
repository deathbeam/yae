-- Initialize java library
export java = require "java"

-- Initialize the modules
export non = {
  accelerometer: require "non.accelerometer"
  audio: require "non.audio"
  compass: require "non.compass"
  filesystem: require "non.filesystem"
  keyboard: require "non.keyboard"
  mouse: require "non.mouse"
  system: require "non.system"
  timer: require "non.timer"
  window: require "non.window"
}

input = require "non.internal.input"

non._keypressed = (keycode) ->
  if non.keypressed then non.keypressed input.key2string(keycode)

non._keyreleased = (keycode) ->
  if non.keyreleased then non.keyreleased input.key2string(keycode)

non._mousepressed = (x, y, buttoncode) ->
  if non.mousepressed then non.mousepressed input.btn2string(buttoncode)

non._mousereleased = (x, y, buttoncode) ->
  if non.mousereleased then non.mousereleased input.btn2string(buttoncode)

non._quit = ->
  if non.quit then non.quit!
  non.audio.stopAll!

non.run = () ->
  dt = 0
  if non.timer then dt = non.timer.getDelta!
  if non.update then non.update dt
  if non.draw then non.draw!

require "main"