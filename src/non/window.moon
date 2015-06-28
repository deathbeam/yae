Gdx = java.require "com.badlogic.gdx.Gdx"

{
  fromPixels: (px, py) ->
    scale = @getPixelScale!

    if py != nil
      return px / scale, py / scale

    return px / scale

  getMode: ->
    @getWidth!, @getHeight!, @isFullscreen!

  getFullscreenModes: ->
    jmodes = Gdx.graphics.getDisplayModes!
    modes = {}

    for i = 0, jmodes.length
      modes[i + 1] = jmodes[i]

    return modes

  getHeight: ->
    Gdx.graphics\getHeight!

  getPixelScale: ->
    Gdx.graphics\getDensity!

  getTitle: ->
    config.name

  getWidth: ->
    Gdx.graphics\getWidth!

  isFullscreen: ->
    Gdx.graphics\isFullscreen!

  setFullscreen: (fullscreen) ->
    @setMode @getWidth!, @getHeight!, fullscreen

  setMode: (width, height, fullscreen=false) ->
    Gdx.graphics\setDisplayMode width, height, fullscreen

  setTitle: (title) ->
    config.name = title
    Gdx.graphics\setTitle config.name

  toPixels:(dx, dy) ->
    scale = @getPixelScale!

    if dy != nil
      return dx * scale, dy * scale

    return dx * scale
}