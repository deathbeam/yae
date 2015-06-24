Gdx = java.require "com.badlogic.gdx.Gdx"

{
  getX: ->
    Gdx.input\getAccelerometerX!

  getY: ->
    Gdx.input\getAccelerometerY!

  getZ: ->
    Gdx.input\getAccelerometerZ!

  getRotation: ->
    @getX!, @getY!, @getZ!
}