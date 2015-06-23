input = require "non.input"

export non = {}
export java = require "non.java"

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