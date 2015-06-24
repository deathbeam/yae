Gdx = java.require "com.badlogic.gdx.Gdx"

c = require "non.internal.constants"

{
  get_x: ->
    Gdx.input\getX!

  get_y: ->
    Gdx.input\getY!

  get_position: ->
    @getX!, @getY!

  is_down: (...) ->
    args = table.pack ...
    found = false

    for i = 1, args.n
      btncode = c.buttons[args[i]]
      found = found or Gdx.input\isButtonPressed btncode

    return found

  is_visible: ->
    not Gdx.input\isCursorCatched!

  set_position: (x, y) ->
    Gdx.input\setCursorPosition x, y

  set_visible: (visible) ->
    Gdx.input\setCursorCatched visible

  set_x: (x) ->
    @set_position x, @get_y!

  set_y: (y) ->
    @set_position @get_x!, y
}