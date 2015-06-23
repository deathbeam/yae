input = require "non.input"

export java = require "non.java"
export non = {}
non.accelerometer = require "non.accelerometer"
non.compass = require "non.compass"
non.filesystem = require "non.filesystem"
non.system = require "non.system"
non.timer = require "non.timer"

non._keypressed = (keycode) ->
  if non.keypressed then non.keypressed input.key2string(keycode)

non._keyreleased = (keycode) ->
  if non.keyreleased then non.keyreleased input.key2string(keycode)

non._mousepressed = (x, y, buttoncode) ->
  if non.mousepressed then non.mousepressed input.btn2string(buttoncode)

non._mousereleased = (x, y, buttoncode) ->
  if non.mousereleased then non.mousereleased input.btn2string(buttoncode)

non.run = () ->
  dt = 0
  if non.timer then dt = non.timer.getDelta!
  if non.update then non.update dt
  if non.draw then non.draw!

require "main"