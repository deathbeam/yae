input = require("non.internal.input")
gdx = require("non.internal.common").gdx

{
  getX: ->
    gdx.input\getX!

  getY: ->
    gdx.input\getY!

  getPosition: ->
    @getX!, @getY!

  isDown: (...) ->
    args = table.pack ...
    found = false

    for i = 1, args.n
      btncode = input.btn2code args[i]
      found = found or gdx.input\isButtonPressed btncode

    return found

  isVisible: ->
    not gdx.input\isCursorCatched!

  setPosition: (x, y) ->
    gdx.input\setCursorPosition x, y

  setVisible: (visible) ->
    gdx.input\setCursorCatched visible

  setX: (x) ->
    @setPosition x, @getY!

  setX: (y) ->
    @setPosition @getX!, y
}