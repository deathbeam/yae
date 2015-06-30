-------------------------------------------------------------------------------
-- Provides an interface for modifying and retrieving information about the
-- program's window.
-------------------------------------------------------------------------------
-- @module non.window

Gdx = java.require "com.badlogic.gdx.Gdx"
windowTitle = config.name

---
-- Converts a number from pixels to density-independent units. 
-- @number px The x-axis value of a coordinate in pixels.
-- @number py The y-axis value of a coordinate in pixels.
-- @treturn number x The converted x-axis value of the coordinate, in
-- density-independent units.
-- @treturn number y The converted y-axis value of the coordinate, in
-- density-independent units.
-- @usage
-- x, y = non.window.fromPixels px, py
fromPixels = (px, py) ->
  scale = getPixelScale!

  if py != nil
    return px / scale, py / scale

  px / scale

---
-- Gets a list of supported display modes. 
-- @treturn table modes A table of width/height pairs.
-- (Note that this may not be in order.)
-- @usage
-- modes = non.window.getDisplayModes!
getDisplayModes = ->
  jmodes = Gdx.graphics.getDisplayModes!
  modes = {}

  for i = 0, jmodes.length
    modes[i + 1] = jmodes[i]

  modes

---
-- Gets the display mode and properties of the window. 
-- @treturn number width Window width
-- @treturn number height Window height
-- @treturn number fullscreen True if window is fullscreen
-- @usage
-- width, height, fullscreen = non.window.getMode!
getMode = ->
  getWidth!, getHeight!, isFullscreen!

---
-- Gets the height of the window. 
-- @treturn number The height of the window.
-- @usage
-- height = non.window.getHeight!
getHeight = ->
  Gdx.graphics\getHeight!

---
-- Gets the DPI scale factor associated with the window. In Mac OS X with the
-- window in a retina screen and the highdpi window flag enabled this will be
-- 2.0, otherwise it will be 1.0. 
-- @treturn number The pixel scale factor associated with the window.
-- @usage
-- scale = non.window.getPixelScale!
getPixelScale = ->
  Gdx.graphics\getDensity!

---
-- Gets the window title.
-- @treturn string The current window title.
-- @usage
-- title = non.window.getTitle!
getTitle = ->
  windowTitle

---
-- Gets the width of the window. 
-- @treturn number The width of the window.
-- @usage
-- width = non.window.getWidth!
getWidth = ->
  Gdx.graphics\getWidth!

---
-- Gets whether the window is fullscreen.
-- @treturn bool True if the window is fullscreen, false otherwise.
-- @usage
-- fullscreen = non.window.isFullscreen!
isFullscreen = ->
  Gdx.graphics\isFullscreen!

---
-- Enters or exits fullscreen. The display to use when entering fullscreen is
-- chosen based on which display the window is currently in, if multiple
-- monitors are connected. 
-- @bool fullscreen Whether to enter or exit fullscreen mode.
-- @usage
-- non.window.setFullscreen fullscreen
setFullscreen = (fullscreen) ->
  setMode getWidth!, getHeight!, fullscreen

---
-- Sets the display mode and properties of the window.
-- @number width Display width.
-- @number height Display height.
-- @bool[opt=false] fullscreen Fullscreen (true), or windowed (false).
-- @usage
-- non.window.setMode width, height, fullscreen
setMode = (width, height, fullscreen=false) ->
  Gdx.graphics\setDisplayMode width, height, fullscreen

---
-- Sets the window title. Do not works on Android and iOS.
-- @string title The new window title.
-- @usage
-- non.window.setTitle title
setTitle = (title) ->
  windowTitle = title
  Gdx.graphics\setTitle windowTitle

---
-- Converts a number from density-independent units to pixels. 
-- @number x The x-axis value of a coordinate in density-independent units to
-- convert to pixels.
-- @number y The y-axis value of a coordinate in density-independent units to
-- convert to pixels.
-- @treturn number px The converted x-axis value of the coordinate, in pixels.
-- @treturn number py The converted y-axis value of the coordinate, in pixels.
-- @usage
-- px, py = non.window.toPixels x, y
toPixels = (x, y) ->
  scale = getPixelScale!

  if y != nil
    return x * scale, y * scale

  x * scale

{
  :fromPixels
  :getMode
  :getFullscreenModes
  :getHeight
  :getWidth
  :isFullscreen
  :setFullscreen
  :setMode
  :setTitle
  :toPixels
}