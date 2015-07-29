-- Easy type checking for MoonScript classes
oldType = type
export type = (value) ->
  baseType = oldType value
  if base_type == "table"
    cls = value.__class
    return cls.__name if cls
  baseType

-- Initialize java module first
print "Initializing the java module"
non.java = require "non.java"

-- Initialize the objects
print "Initializing the objects"
non.File = require "non.objects.File"
non.Font = require "non.objects.Font"
non.Image = require "non.objects.Image"
non.Quad = require "non.objects.Quad"
non.Source = require "non.objects.Source"

-- Initialize the modules
print "Initializing the accelerometer module"
non.accelerometer = require "non.accelerometer"

print "Initializing the audio module"
non.audio = require "non.audio"

print "Initializing the compass module"
non.compass = require "non.compass"

print "Initializing the filesystem module"
non.filesystem = require "non.filesystem"

print "Initializing the graphics module"
non.graphics = require "non.graphics"

print "Initializing the keyboard module"
non.keyboard = require "non.keyboard"

print "Initializing the mouse module"
non.mouse = require "non.mouse"

print "Initializing the system module"
non.system = require "non.system"

print "Initializing the timer module"
non.timer = require "non.timer"

print "Initializing the touch module"
non.touch = require "non.touch"

print "Initializing the window module"
non.window = require "non.window"

-- Wrap the callbacks to be usefull for the engine
Constants = require "non.constants"
import keycodes, buttoncodes from Constants

non._keypressed = (keycode) ->
  non.keypressed(keycodes[keycode]) if non.keypressed

non._keyreleased = (keycode) ->
  non.keyreleased(keycodes[keycode]) if non.keyreleased

non._mousepressed = (x, y, buttoncode) ->
  non.mousepressed(buttoncodes[buttoncode]) if non.mousepressed

non._mousereleased = (x, y, buttoncode) ->
  non.mousereleased(buttoncodes[buttoncode]) if non.mousereleased

non._quit = ->
  if non.quit then non.quit!
  non.audio.stopAll!

-- Main loop function
non.run = ->
  dt = non.timer.getDelta!
  non.update(dt) if non.update
  non.graphics.clear!
  non.graphics.origin!
  non.draw! if non.draw
  non.graphics.present!

require "main"
