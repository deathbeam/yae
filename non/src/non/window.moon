gdx = require("non.internal.common").gdx

{
  fromPixels: (px, py) ->
    scale = @getPixelScale!

    if py != nil
      return px / scale, py / scale

    return px / scale

  getMode: ->
    @getWidht!, @getHeight!, @isFullscreen!

  getFullscreenModes: ->
    jmodes = gdx.graphics.getDisplayModes!
    modes = {}

    for i = 0, jmodes.length
      modes[i + 1] = jmodes[i]

    return modes

  getHeight: ->
    gdx.graphics\getHeight!

  getPixelScale: ->
    gdx.graphics\getDensity!

  getTitle: ->
    config.name

  getWidht: ->
    gdx.graphics\getWidth!

  isFullscreen: ->
    gdx.graphics\isFullscreen!

  setFullscreen: (fullscreen) ->
    @setMode @getWidth, @getHeight, fullscreen

  setMode: (width, height, fullscreen=false) ->
    gdx.graphics\setDisplayMode width, height, fullscreen

  setTile: (title) ->
    config.name = title
    gdx.graphics\setTitle config.name

  toPixels:(dx, dy) ->
    scale = @getPixelScale!

    if dy != nil
      return dx * scale, dy * scale

    return dx * scale
}