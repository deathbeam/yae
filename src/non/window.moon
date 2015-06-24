Gdx = java.require "com.badlogic.gdx.Gdx"

{
  from_pixels: (px, py) ->
    scale = @getPixelScale!

    if py != nil
      return px / scale, py / scale

    return px / scale

  get_mode: ->
    @get_width!, @get_height!, @is_fullscreen!

  get_fullscreen_modes: ->
    jmodes = Gdx.graphics.getDisplayModes!
    modes = {}

    for i = 0, jmodes.length
      modes[i + 1] = jmodes[i]

    return modes

  get_height: ->
    Gdx.graphics\getHeight!

  get_pixel_scale: ->
    Gdx.graphics\getDensity!

  get_title: ->
    config.name

  get_width: ->
    Gdx.graphics\getWidth!

  is_fullscreen: ->
    Gdx.graphics\isFullscreen!

  set_fullscreen: (fullscreen) ->
    @setMode @getWidth!, @getHeight!, fullscreen

  set_mode: (width, height, fullscreen=false) ->
    Gdx.graphics\setDisplayMode width, height, fullscreen

  set_title: (title) ->
    config.name = title
    Gdx.graphics\setTitle config.name

  to_pixels:(dx, dy) ->
    scale = @getPixelScale!

    if dy != nil
      return dx * scale, dy * scale

    return dx * scale
}