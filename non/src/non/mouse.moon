c = require("non.internal.constants")
Gdx = java.require "com.badlogic.gdx.Gdx"

{
  getX: ->
    Gdx.input\getX!

  getY: ->
    Gdx.input\getY!

  getPosition: ->
    @getX!, @getY!

  isDown: (...) ->
    args = table.pack ...
    found = false

    for i = 1, args.n
      btncode = c.buttons[args[i]]
      found = found or Gdx.input\isButtonPressed btncode

    return found

  isVisible: ->
    not Gdx.input\isCursorCatched!

  setPosition: (x, y) ->
    Gdx.input\setCursorPosition x, y

  setVisible: (visible) ->
    Gdx.input\setCursorCatched visible

  setX: (x) ->
    @setPosition x, @getY!

  setX: (y) ->
    @setPosition @getX!, y
}