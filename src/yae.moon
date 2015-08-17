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
yae.java = require "yae.java"

-- Initialize the objects
print "Initializing the objects"
yae.File = require "yae.objects.File"
yae.Font = require "yae.objects.Font"
yae.Image = require "yae.objects.Image"
yae.Quad = require "yae.objects.Quad"
yae.Source = require "yae.objects.Source"

-- Initialize the modules
print "Initializing the accelerometer module"
yae.accelerometer = require "yae.accelerometer"

print "Initializing the audio module"
yae.audio = require "yae.audio"

print "Initializing the compass module"
yae.compass = require "yae.compass"

print "Initializing the filesystem module"
yae.filesystem = require "yae.filesystem"

print "Initializing the graphics module"
yae.graphics = require "yae.graphics"

print "Initializing the keyboard module"
yae.keyboard = require "yae.keyboard"

print "Initializing the mouse module"
yae.mouse = require "yae.mouse"

print "Initializing the system module"
yae.system = require "yae.system"

print "Initializing the timer module"
yae.timer = require "yae.timer"

print "Initializing the touch module"
yae.touch = require "yae.touch"

print "Initializing the window module"
yae.window = require "yae.window"

-- Wrap the callbacks to be usefull for the engine
Constants = require "yae.constants"
import keycodes, buttoncodes from Constants

yae._keypressed = (keycode) ->
  yae.keypressed(keycodes[keycode]) if yae.keypressed

yae._keyreleased = (keycode) ->
  yae.keyreleased(keycodes[keycode]) if yae.keyreleased

yae._mousepressed = (x, y, buttoncode) ->
  yae.mousepressed(buttoncodes[buttoncode]) if yae.mousepressed

yae._mousereleased = (x, y, buttoncode) ->
  yae.mousereleased(buttoncodes[buttoncode]) if yae.mousereleased

yae._quit = ->
  if yae.quit then yae.quit!
  yae.audio.stopAll!

-- Main loop function
yae.run = ->
  dt = yae.timer.getDelta!
  yae.update(dt) if yae.update
  yae.graphics.clear!
  yae.graphics.origin!
  yae.draw! if yae.draw
  yae.graphics.present!

require "main"
