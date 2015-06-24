Gdx = java.require "com.badlogic.gdx.Gdx"

c = require "non.internal.constants"

{
  get_x: (pointer=0) ->
    Gdx.input\getX pointer

  get_y: (pointer=0) ->
    Gdx.input\getY pointer

  get_position: (pointer) ->
    @getX pointer, @getY pointer

  is_down:  (pointer) ->
    Gdx.input\isTouched pointer
}