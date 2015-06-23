gdx = require("non.internal.common").gdx

{
  getX: ->
    gdx.input\getAccelerometerX!

  getY: ->
    gdx.input\getAccelerometerY!

  getZ: ->
    gdx.input\getAccelerometerZ!

  getRotation: ->
    @getX!, @getY!, @getZ!
}